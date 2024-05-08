<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student View</title>
    </head>
    <header>
            <h1><% out.print(getServletContext().getInitParameter("contextHeader6"));%></h1>
        </header>
    <body>
        <h1>Expense Logs</h1>
        <h2>Student ID#: <%= session.getAttribute("USER_ID") %></h2>
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
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
