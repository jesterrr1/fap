package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;


public class GeneratePDF extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String loggedInUsername = (String) session.getAttribute("username");
        
        //Get the current date and time for FILENAME
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        
        //Date and time for display
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 
        
        String date = now.format(dateFormatter);
        String time = now.format(timeFormatter);
                
        Document document = new Document(PageSize.A4.rotate()); //Create new document in landscape form
        try{
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream()); //Initialize PDF writer
            writer.setPageEvent(new Footer(loggedInUsername));
            
            document.open(); //Open the document

                             
            if (action.equals("adminLoginSinglePDF")) {
                String fileName = "LOGINREPORT_" + timestamp + ".pdf";
                //response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\""); //Download
                
                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                                
                //Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA,22,Font.BOLDITALIC);
                Paragraph title = new Paragraph("Login Report",titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);                              
                       
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                //add username, userID, and role to the document
                String username = request.getParameter("username");
                String userID = request.getParameter("userID");
                String role = request.getParameter("role");
                Paragraph usernamePara = new Paragraph("Username: " + username, detailFont);
                usernamePara.setAlignment(Element.ALIGN_CENTER); // Center align Username
                Paragraph userIDPara = new Paragraph("User ID: " + userID, detailFont);
                userIDPara.setAlignment(Element.ALIGN_CENTER); // Center align User ID
                Paragraph rolePara = new Paragraph("Role: " + role, detailFont);
                rolePara.setAlignment(Element.ALIGN_CENTER); // Center align Role               
                document.add(usernamePara);
                document.add(userIDPara);
                document.add(rolePara); 
                  
                document.close();
            } else if (action.equals("adminLoginMultiplePDF")) {               
                String fileName = "LOGINREPORT_" + timestamp + ".pdf";
//                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\""); //Download
                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                                
                //Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA,22,Font.BOLDITALIC);
                Paragraph title = new Paragraph("Login Report",titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);                              
                       
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                PdfPTable table = new PdfPTable(2); //2 columns
                table.addCell("Username");
                table.addCell("Role");
                
                List<Map<String, String>> loginRecords = (List<Map<String, String>>) request.getSession().getAttribute("loginRecords");
                for (Map<String, String> record : loginRecords) {
                    String username = record.get("username");
                    String role = record.get("role");                    
                    //Check if the username matches the logged-in username
                    if(loggedInUsername != null && loggedInUsername.equals(username)){
                        //Append asterisk(*) to the username
                        username += "*";
                    }
                    table.addCell(record.get(username));
                    table.addCell(record.get(role));
                }
                document.add(table);                

                document.close();
            } else if (action.equals("adminExpenseSinglePDF")) {
                String fileName = "ENROLLMENTLOGREPORT_" + timestamp + ".pdf";
//                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\""); //Download
                String userID = request.getParameter("userID");
                
                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                                
                //Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA,22,Font.BOLDITALIC);
                Paragraph title = new Paragraph("Enrollment Log Report",titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);                              
                       
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                //Add the corresponding user ID of the user 
                
                Paragraph studIDPara = new Paragraph("User ID: " + userID, detailFont);
                studIDPara.setAlignment(Element.ALIGN_CENTER); // Center align Student ID
                document.add(studIDPara);
                
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                //Get the expenseLogs from the session
                List<Map<String,String>> expenseLogs = (List<Map<String,String>>)session.getAttribute("expenseLogs");
                
                if(expenseLogs != null){
                    PdfPTable table = new PdfPTable(5); // Create a table with five columns
                    table.setWidthPercentage(100); // Set width to 100% of page
                    table.addCell("Date");
                    table.addCell("Amount");
                    table.addCell("Payment Method");
                    table.addCell("Balance");
                    table.addCell("Transaction ID");

                    for(Map<String,String> log : expenseLogs){
                        // Add each record to the table
                        table.addCell(log.get("Date"));
                        table.addCell(log.get("Amount"));
                        table.addCell(log.get("PaymentMethod"));
                        table.addCell(log.get("Balance"));
                        table.addCell(log.get("TransactionID"));
                    }

                    document.add(table); // Add table to document
                }
                               
                document.close();
            } else if (action.equals("adminExpenseMultiplePDF")) {
                String fileName = "ENROLLMENTLOGREPORT_" + timestamp + ".pdf";
                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\""); //Download
                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                                                
                //Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA,22,Font.BOLDITALIC);
                Paragraph title = new Paragraph("Enrollment Log Report",titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);                              
                       
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                PdfPTable table = new PdfPTable(6); //2 columns
                table.addCell("User ID");
                table.addCell("Date");
                table.addCell("Amount");
                table.addCell("Payment Method");
                table.addCell("Balance");
                table.addCell("TransactionID");
                
                List<Map<String, String>> expenseLogs = (List<Map<String, String>>) request.getSession().getAttribute("expenseLogs");
                
                for (Map<String, String> log : expenseLogs) {
                    table.addCell(log.get("UserID"));
                    table.addCell(log.get("Date"));
                    table.addCell(log.get("Amount"));
                    table.addCell(log.get("PaymentMethod"));
                    table.addCell(log.get("Balance"));
                    table.addCell(log.get("TransactionID"));
                }

                document.add(table);                
                
                document.close();
            } else if (action.equals("expenseViewStudent")) {
                String fileName = "ENROLLMENTLOGREPORT_" + timestamp + ".pdf";
//                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
                String userID = request.getParameter("USER_ID");
                

                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                                
                //Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA,22,Font.BOLDITALIC);
                Paragraph title = new Paragraph("Enrollment Log Report",titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);                              
                       
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                //Add the corresponding user ID of the user 
                
                Paragraph studIDPara = new Paragraph("User ID: " + userID, detailFont);
                studIDPara.setAlignment(Element.ALIGN_CENTER); // Center align Student ID
                document.add(studIDPara);
                
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                //Get the expenseLogs from the session
                List<Map<String,String>> expenseLogs = (List<Map<String,String>>)session.getAttribute("expenseLogs");
                
                if(expenseLogs != null){
                    PdfPTable table = new PdfPTable(5); // Create a table with five columns
                    table.setWidthPercentage(100); // Set width to 100% of page
                    table.addCell("Date");
                    table.addCell("Amount");
                    table.addCell("Payment Method");
                    table.addCell("Balance");
                    table.addCell("Transaction ID");

                    for(Map<String,String> log : expenseLogs){
                        // Add each record to the table
                        table.addCell(log.get("Date"));
                        table.addCell(log.get("Amount"));
                        table.addCell(log.get("PaymentMethod"));
                        table.addCell(log.get("Balance"));
                        table.addCell(log.get("TransactionID"));
                    }

                    document.add(table); // Add table to document
                }
                                
                document.close();                               
            }else if(action.equals("loginViewStudent")){
                String fileName = "LOGINREPORT_" + timestamp + ".pdf";
//                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                String userID = request.getParameter("USER_ID");
                String username = request.getParameter("username");
                String role = request.getParameter("role");
                
                //Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA,22,Font.BOLDITALIC);
                Paragraph title = new Paragraph("Login Report",titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);                              
                       
                //add a line break for better formatting
                document.add(new Paragraph("\n"));
                
                //add username, userID, and role to the document
                
                Paragraph usernamePara = new Paragraph("Username: " + username, detailFont);
                usernamePara.setAlignment(Element.ALIGN_CENTER); // Center align Username
                Paragraph userIDPara = new Paragraph("User ID: " + userID, detailFont);
                userIDPara.setAlignment(Element.ALIGN_CENTER); // Center align User ID
                Paragraph rolePara = new Paragraph("Role: " + role, detailFont);
                rolePara.setAlignment(Element.ALIGN_CENTER); // Center align Role               
                document.add(usernamePara);
                document.add(userIDPara);
                document.add(rolePara);
            }
            
            document.close(); //close the document
        }catch(DocumentException e){
            e.printStackTrace();
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
    
    class Footer extends PdfPageEventHelper {
        String loggedInUsername;

        Footer(String loggedInUsername) {
            this.loggedInUsername = loggedInUsername;
        }

        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable footer = new PdfPTable(1);
            footer.setWidthPercentage(100);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell = new PdfPCell(new Phrase(loggedInUsername, FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(Rectangle.NO_BORDER);
            footer.addCell(cell);

            footer.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            footer.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
        }
    }
             
}
