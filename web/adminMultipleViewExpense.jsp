<%@page import="java.util.Map" %>
    <%@page import="java.util.List" %>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>
        
        <head>
            <link rel="stylesheet" href="styles.css">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>ENROLLMENT LOGS</title>
            <style>
                .scrollable-table {
                    width: 45%;
                    max-height: 600px;
                    overflow-y: auto;
                    -ms-overflow-style: -ms-autohiding-scrollbar;
                    margin: 0 auto;
                }
                /* Add this rule */
                .scrollable-table td {
                    padding: 0 30px; /* Adjust the values as needed */
                }
            </style>
        </head>
        <header>
            <h1>
                <% out.print(getServletContext().getInitParameter("contextHeader11"));%>
            </h1>
        </header>        
        <body>
            <div  class="scrollable-table">
                <table>
                    <tr>
                        <th>User ID</th>
                        <th>Date</th>
                        <th>Amount</th>
                        <th>Payment Method</th>
                        <th>Balance</th>
                        <th>Transaction ID</th>
                        <!-- Add more columns as needed -->
                    </tr>
                    <% List<Map<String,String>> expenseLogs = (List<Map<String,String>>)request.getAttribute("expenseLogs");
                            session.setAttribute("expenseLogs",expenseLogs);
                            String loggedInUser = (String) session.getAttribute("username");
                            for(Map<String,String> log : expenseLogs){
                                String username = log.get("username");
                                %>
                                <tr>
                                    <td>
                                        <%= log.get("UserID") %>
                                    </td>
                                    <td>
                                        <%= log.get("Date") %>
                                    </td>
                                    <td>
                                        <%= log.get("Amount") %>
                                    </td>
                                    <td>
                                        <%= log.get("PaymentMethod") %>
                                    </td>
                                    <td>
                                        <%= log.get("Balance") %>
                                    </td>
                                    <td>
                                        <%= log.get("TransactionID") %>
                                    </td>
                                    <!-- Add more table cells for additional columns if needed -->
                                </tr>
                                <% } %>
                </table>
            </div>
        
        
            <div class="button-row">
                <form action="Logout" method="post">
                    <input type="submit" value="Logout">
                </form>
        
                <form action="GeneratePDF" method="post">
                    <input type="hidden" name="action" value="adminExpenseMultiplePDF">
                    <input type="submit" value="Generate PDF">
                </form>
        
                <button onclick="window.location.href='success.jsp'">Back to Admin Log</button>
            </div>
        </body>
        <footer>
            <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
                <a href="https://activelearning.ph">
                    <% out.print(getServletContext().getInitParameter("contextFooter2"));%>
                </a>
                <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
        </footer>
        
        </html>