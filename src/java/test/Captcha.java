package test;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.HttpSession;

public class Captcha extends HttpServlet {

    private int captchaLength; //variable to hold the captcha length
    
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        
        //Read the captcha length from the initialization parameter
        String captchaLengthParam = config.getInitParameter("captchaLength");
        captchaLength = Integer.parseInt(captchaLengthParam);
    }
               
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Captcha</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Captcha at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Generate a random CAPTCHA
        String generatedCaptcha = generateCaptcha(captchaLength);
        
        //Set in session attribute
        request.getSession().setAttribute("captcha",generatedCaptcha);
        
        //Redirect user to captcha.jsp to display CAPTCHA
        response.sendRedirect("captcha.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //Prevent from caching
        response.setHeader("Cache-control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        //Get the user-entered CAPTCHA
        String userCaptcha = request.getParameter("captcha");
        String captcha = (String) request.getSession().getAttribute("captcha");
        
        //Check if the user-entered CAPTCHA matches the stored CAPTCHA
        if(checkCaptcha(captcha,userCaptcha)){
            //CAPTCHA matched
            //Get the user role
            String userRole = (String) request.getSession().getAttribute("role");
            
            //Check if the usr role is "admin" or "guest"
            if("admin".equals(userRole)) //if user role is admin, success.jsp
            {
                response.sendRedirect("success.jsp");
                session.setAttribute("verifyCaptcha", false);
            }
            else if("guest".equals(userRole)) //if user role is guest, success2.jsp
                response.sendRedirect("success2.jsp");
                session.setAttribute("verifyCaptcha", false);
        }else{
            //CAPTCHA NOT MATCHED, regenerate another and update session
            String newGeneratedCaptcha = generateCaptcha(captchaLength);
            request.getSession().setAttribute("captcha",newGeneratedCaptcha);
            
            //Redirect back to Captcha.jsp with new Captcha and an error message
            response.sendRedirect("captcha.jsp?error=captcha");
        }
    }
    
    //Method to generate a Captcha of given length
    static String generateCaptcha(int length){
        // Characters to be included
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(chrs.length());
            captcha.append(chrs.charAt(index));
	}
	return captcha.toString();
    }
    
    //Method to check if two CAPTCHAs are the same
    static boolean checkCaptcha(String captcha, String userCaptcha){
        return captcha.equals(userCaptcha);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
