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
        <h1>Enrollment Log</h1>
        <h2>Student ID#: <%= request.getAttribute("userID") %></h2>
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
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>
        <form action="GeneratePDF" method="post">
            <input type="hidden" name="action" value="adminExpenseSinglePDF">
            <input type="submit" value="Generate PDF">
        </form>
        <button onclick="window.location.href='expenseSearch.jsp'">Search Another</button>
        <button onclick="window.location.href='success.jsp'">Back to Admin Log</button>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
