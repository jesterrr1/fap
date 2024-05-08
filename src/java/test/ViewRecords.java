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
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try{
            Class.forName(ExpDBdriver);
            conn = DriverManager.getConnection(ExpDBurl,ExpDBusername,ExpDBpassword);
            
            HttpSession session = request.getSession();
            String loggedInUserID = (String) session.getAttribute("USER_ID");
            String role = (String) request.getSession().getAttribute("role");
            
            if("admin".endsWith(role)){
                String action = request.getParameter("action");
                if("singleView1".equals(action)){
                    //Login Single View Button 
                }
                else if("multipleView1".equals(action)){
                    //Login Multiple View Button
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
                else if("singleView2".equals(action)){
                    //Expense Single View Button
                }
                else if("multipleView2".equals(action)){
                    //Expense Multiple View Button
                }
            }else if("guest".equals(role)){
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
