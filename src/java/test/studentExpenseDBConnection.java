package test;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class studentExpenseDBConnection extends HttpServlet {

    //Declaration of variables from DD
    private String ExpDBname;
    private String ExpDBusername;
    private String ExpDBpassword;
    private String ExpDBdriver;
    private String ExpDBurl;
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        
        //Load DB credentials
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
            //Load driver
            Class.forName(ExpDBdriver);
            
            //Establish a connection
            conn = DriverManager.getConnection(ExpDBurl,ExpDBusername,ExpDBpassword);
            
            //Get the username of the currently logged in user
            HttpSession session = request.getSession();
//            String loggedInUser = (String) session.getAttribute("username");
//            String loggedInUserRole = (String) session.getAttribute("role");
            String loggedInUserID = (String) session.getAttribute("USER_ID");
                       
            //Prepare a SQL query
            String query = "SELECT Date, Amount, PaymentMethod, Balance, TransactionID FROM dbo.EXPENSE_LOGS WHERE UserID = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, loggedInUserID);
            
            //Execute the query and get the result
            rs = stmt.executeQuery();
            
            //Process the result
            while(rs.next()){
                //Get data from the current row and do something with it
                String data1 = rs.getString("UserID");
                String data2 = rs.getString("Date");
                String data3 = rs.getString("Amount");
                String data4 = rs.getString("PaymentMethod");
                String data5 = rs.getString("Balance");
                String data6 = rs.getString("TransactionID");
                System.out.println("Column1: " + data1 + " Column2: " 
                        + data2 + " Column3: " + data3 + " Column4: " + data4 + " Column5: " 
                        + data5 + " Column6: " + data6 );
            }                       
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally{
            //Close the resources
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
