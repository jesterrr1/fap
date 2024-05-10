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
    
    private int currentPageNumber = 1; // Keep track of current page number
    private int totalPageNumber = 0; // Total pages

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String loggedInUsername = (String) session.getAttribute("username");
        //String loggedInRole = (String) session.getAttribute("role");
        
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
            //writer.setPageEvent(new PageNumber()); //Set page event for pagination
            //HeaderFooterPageEvent event = new HeaderFooterPageEvent(loggedInUsername);
            
            document.open(); //Open the document

            //Add date and time to header
            //addDateTimeToHeader(document,date,time);
                             
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
                
                //Add footer
                //Footer(writer, loggedInUsername, document);
                
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
                    table.addCell(record.get("username"));
                    table.addCell(record.get("role"));
                }

                document.add(table);                
                
                //Add footer
                //Footer(writer, loggedInUsername, document);
                
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
                
                //Add footer
                //Footer(writer, loggedInUsername, document);
                
                document.close();
            } else if (action.equals("adminExpenseMultiplePDF")) {
                String fileName = "ENROLLMENTLOGREPORT_" + timestamp + ".pdf";
                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\""); //Download
                Font detailFont = FontFactory.getFont(FontFactory.HELVETICA,16, Font.NORMAL);
                
                //Add header
                addDateTimeToHeader(document, date, time);
                                
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
                
                //Add footer
                //Footer(writer, loggedInUsername, document);
                
                document.close();
            } else if (action.equals("expenseViewStudent")) {
                String fileName = "ENROLLMENTLOGREPORT_" + timestamp + ".pdf";
//                response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
                
            }else if(action.equals("loginViewStudent")){
                
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
    
    //Set footer
    private void Footer(PdfWriter writer, String loggedInUsername, Document document) {
        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setWidthPercentage(100);
        footerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Left side: Username
        PdfPCell usernameCell = new PdfPCell(new Phrase(loggedInUsername, FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12)));
        usernameCell.setBorder(Rectangle.NO_BORDER);
        footerTable.addCell(usernameCell);

//        // Right side: Page number
//        PdfPCell pageNumberCell = new PdfPCell(new Paragraph("Page " + currentPageNumber + " of " + totalPageNumber, FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12)));
//        pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        pageNumberCell.setBorder(Rectangle.NO_BORDER);
//        footerTable.addCell(pageNumberCell);

        // Set the footer table position to be at the bottom of the page
        footerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        footerTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
    }
    
    
    // Add method to add date and time in the upper right corner and pdfHeader in upper left corner
    private void addDateTimeToHeader(Document document, String date, String time) throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        
        String pdfHeader = getServletContext().getInitParameter("pdfHeader");
        
        PdfPTable table = new PdfPTable(2); //Create a table with two columns
        table.setWidthPercentage(100); //set width to 100% of page
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER); //Remove Borders
        
        PdfPCell leftCell = new PdfPCell(new Phrase(pdfHeader, headerFont));
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        PdfPCell rightCell = new PdfPCell(new Phrase("Date: " + date + " " + time, headerFont));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        table.addCell(leftCell);
        table.addCell(rightCell);

        document.add(table); // Add table to document instead of separate paragraphs
        
    }
    
    class HeaderFooterPageEvent extends PdfPageEventHelper {
        private String loggedInUsername;

        public HeaderFooterPageEvent(String loggedInUsername) {
            this.loggedInUsername = loggedInUsername;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // Add header
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell headerCell = new PdfPCell(new Phrase("Your Header Content Here"));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(headerCell);
            headerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            headerTable.writeSelectedRows(0, -1, document.leftMargin(), document.top() + 10, writer.getDirectContent());
            // Add footer
            PdfPTable footerTable = new PdfPTable(1);
            footerTable.setWidthPercentage(100);
            footerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            PdfPCell usernameCell = new PdfPCell(new Phrase(loggedInUsername, FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12)));
            usernameCell.setBorder(Rectangle.NO_BORDER);
            footerTable.addCell(usernameCell);
            footerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
            footerTable.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
        }
    }
    
}
