package test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

public class ViewRecords extends HttpServlet {
    
    //Login Database
    private String DBname;
    private String DBusername;
    private String DBpassword;
    private String DBdriver;
    private String DBurl;
    
    //Expenses Database
    private String ExpDBname;
    private String ExpDBusername;
    private String ExpDBpassword;
    private String ExpDBdriver;
    private String ExpDBurl;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        
        //Login Database
        DBdriver = getServletConfig().getInitParameter("DBdriver");               //org.apache.derby.jdbc.ClientDriver
        DBurl = getServletConfig().getInitParameter("DBurl");                     //jdbc:derby://localhost:1527/LoginDB
        DBusername = getServletConfig().getInitParameter("DBusername");           //app
        DBpassword = getServletConfig().getInitParameter("DBpassword");           //app
        
        //Expenses Database
        ExpDBdriver = getServletConfig().getInitParameter("ExpDBdriver");         //com.microsoft.sqlserver.jdbc.SQLServerDriver
        ExpDBurl = getServletConfig().getInitParameter("ExpDBurl");               //jdbc:sqlserver://localhost\SQLEXPRESS:1433;databaseName=ExpensesDB;encrypt=true;trustServerCertificate=true;
        ExpDBusername = getServletConfig().getInitParameter("ExpDBusername");     //admin
        ExpDBpassword = getServletConfig().getInitParameter("ExpDBpassword");     //123        
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");      

            try{
                Class.forName(ExpDBdriver);
                conn = DriverManager.getConnection(ExpDBurl,ExpDBusername,ExpDBpassword);

                HttpSession session = request.getSession();
                String loggedInUserID = (String) session.getAttribute("USER_ID");
                String role = (String) request.getSession().getAttribute("role");

                //If-else statement to determine if user is admin or guest
                if("admin".endsWith(role)){ //user is admin role
                    String action = request.getParameter("action");

                    //LOGIN DB
                    if("singleView1".equals(action)){ //Login Single View Button
                        request.getRequestDispatcher("/loginSearch.jsp").forward(request, response);                
                    }
                    else if("searchLogin".equals(action)){ //once ma-click yung search for login database
                        String username = request.getParameter("username");
                        try {
                            // Query the database to check if the username exists
                            Class.forName(DBdriver);
                            conn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
                            stmt = conn.prepareStatement("SELECT USER_ID, role FROM APP.LOGIN_INFO WHERE username = ?");
                            stmt.setString(1, username);
                            rs = stmt.executeQuery();

                            if (rs.next()) {
                                // Username exists, proceed with search functionality
                                String userID = rs.getString("USER_ID");
                                //String role = rs.getString("role");

                                // Set the retrieved values as request attributes
                                request.setAttribute("username", username);
                                request.setAttribute("userID", userID);
                                request.setAttribute("role", role);

                                // Forward the request to adminSingleViewLogin.jsp
                                request.getRequestDispatcher("/adminSingleViewLogin.jsp").forward(request, response);
                            } else {
                                // Username doesn't exist, display error and clear the input field
                                request.setAttribute("errorMessage", "Username does not exist. Please enter another username.");
                                request.getRequestDispatcher("/loginSearch.jsp").forward(request, response);
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        } finally {
                            // Close resources
                            // ...
                        }
                    }
                    else if("multipleView1".equals(action)){ //Login Multiple View Button                   
                        try{
                            Class.forName(DBdriver);
                            conn = DriverManager.getConnection(DBurl,DBusername,DBpassword);

                            String username = (String) session.getAttribute("username");

                            stmt = conn.prepareStatement("SELECT * FROM APP.LOGIN_INFO");
                            rs = stmt.executeQuery();

                            List<Map<String, String>> loginRecords = new ArrayList<>();

                            // Process the result set
                            while(rs.next()) {
                                Map<String, String> record = new HashMap<>();
                                record.put("username", rs.getString("username"));
                                record.put("role", rs.getString("role"));
                                // process the rest of your columns...

                                // Add the record to the list
                                loginRecords.add(record);
                            }

                            // Close the resources
                            rs.close();
                            stmt.close();
                            conn.close();

                            // Store the login records in the request scope
                            request.setAttribute("loginRecords", loginRecords);

                            // Forward the request to the JSP page
                            request.getRequestDispatcher("/adminMultipleViewLogin.jsp").forward(request, response);                                   
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    //EXPENSES DB
                    else if("singleView2".equals(action)){ //Expense Single View Button
                        request.getRequestDispatcher("/expenseSearch.jsp").forward(request, response);
                    }
                    else if ("searchExpense".equals(action)) {
                        String username = request.getParameter("username");
                        Connection loginConn = null;
                        Connection expenseConn = null;
                        try {
                            // Establish connection to the Login Database (Derby)
                            Class.forName(DBdriver);
                            loginConn = DriverManager.getConnection(DBurl, DBusername, DBpassword);

                            // Query the LOGIN_INFO table to get the corresponding USER_ID based on the username
                            stmt = loginConn.prepareStatement("SELECT USER_ID FROM APP.LOGIN_INFO WHERE username = ?");
                            stmt.setString(1, username);
                            rs = stmt.executeQuery();

                            if (rs.next()) {
                                int userID = rs.getInt("USER_ID");
                                if(userID <= 20){
                                    //User is an admin, display message and redirect
                                    request.setAttribute("errorMessage","No enrollment expenses will be shown as the USER_ID is for admins.");
                                    request.getRequestDispatcher("/expenseSearch.jsp").forward(request, response);
                                }else{

                                    // Establish connection to the Expenses Database (MSSQL)
                                    Class.forName(ExpDBdriver);
                                    expenseConn = DriverManager.getConnection(ExpDBurl, ExpDBusername, ExpDBpassword);

                                    // Query the EXPENSE_LOGS table to fetch the expense records for the user
                                    String expenseQuery = "SELECT Date, Amount, PaymentMethod, Balance, TransactionID FROM EXPENSE_LOGS WHERE UserID = ?";
                                    stmt = expenseConn.prepareStatement(expenseQuery);
                                    stmt.setInt(1, userID);
                                    rs = stmt.executeQuery();

                                    List<Map<String,String>> expenseLogs = new ArrayList<>();
                                    while (rs.next()) {
                                        Map<String, String> log = new HashMap<>();
                                        log.put("Date", rs.getString("Date"));
                                        log.put("Amount", rs.getString("Amount"));
                                        log.put("PaymentMethod", rs.getString("PaymentMethod"));
                                        log.put("Balance", rs.getString("Balance"));
                                        log.put("TransactionID", rs.getString("TransactionID"));
                                        expenseLogs.add(log);
                                    }
                                    
                                    request.setAttribute("userID",userID);
                                    session.setAttribute("expenseLogs", expenseLogs);
                                    RequestDispatcher dispatcher = request.getRequestDispatcher("/adminSingleViewExpense.jsp");
                                    dispatcher.forward(request, response);
                                }
                            } else {
                                // Username doesn't exist, display error and clear the input field
                                request.setAttribute("errorMessage", "Username does not exist. Please enter another username.");
                                request.getRequestDispatcher("/expenseSearch.jsp").forward(request, response);
                            }
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                            // Forward the request to an error page with a meaningful error message
                            request.setAttribute("errorMessage", "An error occurred while processing the request.");
                            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
                        } finally {
                            // Close resources for both connections
                            try {
                                if (rs != null) {
                                    rs.close();
                                }
                                if (stmt != null) {
                                    stmt.close();
                                }
                                if (loginConn != null) {
                                    loginConn.close();
                                }
                                if (expenseConn != null) {
                                    expenseConn.close();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if("multipleView2".equals(action)){ //Expense Multiple View Button
                        try{
                            Class.forName(ExpDBdriver);
                            conn = DriverManager.getConnection(ExpDBurl,ExpDBusername,ExpDBpassword);

                            String username = (String) session.getAttribute("username");

                            stmt = conn.prepareStatement("SELECT * FROM EXPENSE_LOGS");
                            rs = stmt.executeQuery();

                            List<Map<String,String>> expenseLogs = new ArrayList<>();
                            while (rs.next()) {
                                Map<String, String> log = new HashMap<>();
                                log.put("UserID", rs.getString("UserID"));
                                log.put("Date", rs.getString("Date"));
                                log.put("Amount", rs.getString("Amount"));
                                log.put("PaymentMethod", rs.getString("PaymentMethod"));
                                log.put("Balance", rs.getString("Balance"));
                                log.put("TransactionID", rs.getString("TransactionID"));
                                expenseLogs.add(log);
                            }

                            // Close the resources
                            rs.close();
                            stmt.close();
                            conn.close();

                            // Store the login records in the request scope
                            request.setAttribute("expenseLogs", expenseLogs);

                            // Forward the request to the JSP page
                            request.getRequestDispatcher("/adminMultipleViewExpense.jsp").forward(request, response);                                   
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else if("guest".equals(role)){ //user is guest role
                    //Prepare a SQL query
                    String query = "SELECT Date, Amount, PaymentMethod, Balance, TransactionID FROM dbo.EXPENSE_LOGS WHERE UserID = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, loggedInUserID);

                    //Execute the query and get the result
                    rs = stmt.executeQuery();

                    List<Map<String,String>> expenseLogs = new ArrayList<>();
                    while(rs.next()){
                        Map<String, String> log = new HashMap<>();
                        log.put("Date", rs.getString("Date"));
                        log.put("Amount", rs.getString("Amount"));
                        log.put("PaymentMethod", rs.getString("PaymentMethod"));
                        log.put("Balance", rs.getString("Balance"));
                        log.put("TransactionID", rs.getString("TransactionID"));
                        expenseLogs.add(log);
                    }

                    session.setAttribute("expenseLogs",expenseLogs);            
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/studentView.jsp");
                    dispatcher.forward(request,response);
                }
            }catch(ClassNotFoundException | SQLException e){
                    e.printStackTrace();
                }finally{
                    if(rs !=null){
                        try{
                            rs.close();
                        }catch (SQLException e){}
                    }
                    if(stmt != null){
                        try{
                            stmt.close();
                        }catch (SQLException e){}
                    }
                    if(conn != null){
                        try{
                            conn.close();
                        }catch (SQLException e){}
                    }
                }
            }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
