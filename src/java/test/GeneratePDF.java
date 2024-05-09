package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneratePDF extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action.equals("adminLoginSinglePDF")) {
            // Handle the case where no action is provided
        } else if (action.equals("adminLoginMultiplePDF")) {
            // Handle the case where action is adminLoginSinglePDF
        } else if (action.equals("adminExpenseSinglePDF")) {
            // Handle the case where action is adminLoginMultiplePDF
        } else if (action.equals("adminExpenseMultiplePDF")) {
            // Handle the case where action is adminExpenseSinglePDF
        } else if (action.equals("studentPDF")) {
            // Handle the case where action is adminExpenseMultiplePDF
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
    }
}
