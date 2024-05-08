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

public class Login extends HttpServlet {

    //Declaration of variables from DD
    private String DBname;          //LoginDB
    private String DBusername;      
    private String DBpassword;      
    private String DBdriver;        //org.apache.derby.jdbc.ClientDriver
    private String DBurl;           
    private String keyString;
    private SecretKeySpec keySpec;
    private Cipher cipher;
    private String cipherTransformation;
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        
        //Load DB credentials
        DBurl = getServletConfig().getInitParameter("DBurl");                   //jdbc:derby://localhost:1527/LoginDB
        DBusername = getServletConfig().getInitParameter("DBusername");         //app
        DBpassword = getServletConfig().getInitParameter("DBpassword");         //app
        keyString = getServletContext().getInitParameter("key");                //unpredictability(16-bit string)
        byte[] key = keyString.getBytes();                                           //{'u','n','p','r','e','d','i','c','t','a','b','i','l','i','t','y'}
        keySpec = new SecretKeySpec(key,"AES");
        cipherTransformation = getServletContext().getInitParameter("cipher");  // AES/ECB/PKCS5Padding
        
        //Exception Handling for cipher
        try {
            cipher = Cipher.getInstance(cipherTransformation);
        } catch (Exception e) {
            throw new ServletException("Error initializing cipher", e);
        }
    }
    
    protected Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
        } catch (SQLException e) {
            System.out.println("Error getting database connection: " + e.getMessage());
            // Optionally: handle the exception, for example by logging it, or rethrow it as a RuntimeException
        }
        return conn;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Redirect to Captcha servlet to generate a new CAPTCHA
        response.sendRedirect("Captcha");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Prevent from caching
        response.setHeader("Cache-control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        String username = request.getParameter("username"); //gets the inputted username of the user
        String password = request.getParameter("password"); //gets the inputted password of the user
        
        //Check if username & password field is empty else, throw NullValueException
        try(Connection conn = getConnection()){           
            if(username.isEmpty() && password.isEmpty())
                throw new ServletException("NullValueException");
        
            //Selecting data from the database
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM APP.LOGIN_INFO WHERE username = ?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                //Create session object
                HttpSession session = request.getSession();
                session.setAttribute("username",username);
                String role = rs.getString("role");
                session.setAttribute("role",role);
                
                //Fetch the UserId from the result set and store in the session
                String USER_ID = rs.getString("USER_ID");
                session.setAttribute("USER_ID",USER_ID);

                String encryptedPassword = rs.getString("password");             //password in the database
                String inputtedPassword = encrypt(password);                    //encrypts the inputted password

                if(encryptedPassword.equals(inputtedPassword)){ //if credentials are valid and correct
                    session.setAttribute("password",password);
                    response.sendRedirect("Captcha");
                    session.setAttribute("verifyCaptcha", true);
                }else{ //incorrect or invalid password
                    request.setAttribute("errorMessage", "Invalid password");
                    request.getRequestDispatcher("/error_2.jsp").forward(request,response);
                }
            }else{
                if(!username.isEmpty() && !password.isEmpty()){ //incorrect username and password
                    request.setAttribute("errorMessage","Both username and password are incorrect");
                    request.getRequestDispatcher("/error_3.jsp").forward(request,response);
                }else{ //username not in database and blank password
                    request.setAttribute("errorMessage","Invalid username");
                    request.getRequestDispatcher("/error_1.jsp").forward(request,response);
                }
            }            
        }catch(SQLException e){
            if("404".equals(e.getSQLState())) //error 404 page
                request.getRequestDispatcher("/error_4.jsp").forward(request,response);
            else
                throw new ServletException(e);
        }catch(ServletException e){ //redirects to noLoginCredentials.jsp
            if("NullValueException".equals(e.getMessage()))
                request.getRequestDispatcher("noLoginCredentials.jsp").forward(request, response);
        }                
    }
    
    //Encrypting method
    private String encrypt(String strToEncrypt){
        try{
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
            String encryptedString = Base64.encodeBase64String(encryptedBytes);
            System.out.println("Password to encrypt: " + strToEncrypt);
            System.out.println("Encrypted password: " + encryptedString);
            return encryptedString;
        }catch (Exception e){
            System.err.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
