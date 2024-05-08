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
    
    private String ExpDBname;
    private String ExpDBusername;
    private String ExpDBpassword;
    private String ExpDBdriver;
    private String ExpDBurl;
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        
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
