<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ENROLLMENT LOG</title>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader10"));%></h1>
    </header>
    <body>
        <div>
            <h1><span id="user-customized">Enrollment Log</span></h1>
            <div>
                <h2>User ID#: <%= request.getAttribute("userID") %></h2>
            </div>
            <div>
                <table border="1">
                    <tr>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>Payment Method</th>
                        <th>Balance</th>
                        <th>Transaction ID</th>
                    </tr>
                    <%
                        List<Map<String,String>> expenseLogs = (List<Map<String,String>>)session.getAttribute("expenseLogs");
                        if(expenseLogs != null){
                            for(Map<String,String> log : expenseLogs){
                    %>
                    <tr>
                        <td><%= log.get("Date") %></td>
                        <td><%= log.get("Amount") %></td>
                        <td><%= log.get("PaymentMethod") %></td>
                        <td><%= log.get("Balance") %></td>
                        <td><%= log.get("TransactionID") %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </div>
                <div>
                    <form action="Logout" method="post">
                        <input type="submit" value="Logout">
                    </form>
                    <form action="GeneratePDF" method="post">
                        <input type="hidden" name="action" value="adminExpenseSinglePDF">
                        <input type="hidden" name="userID" value="<%= request.getAttribute("userID") %>">           
                        <input type="submit" value="Generate PDF">
                    </form>
                    <button onclick="window.location.href='expenseSearch.jsp'">Search Another</button>
                    <button onclick="window.location.href='success.jsp'">Back to Admin Log</button>
                </div>
        </div>
         
        
        
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
