<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN VIEW</title>
         <style>
            .scrollable-table {
                width: 100%;
                max-height: 400px;
                overflow-y: auto;
                -ms-overflow-style: -ms-autohiding-scrollbar;
            }
        </style>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader7"));%></h1>
    </header>
    <body>
        <div class="table-container">
            <div class="scrollable-table">
                <table>
                    <tr>
                        <th>Username</th>
                        <th>Role</th>
                        <!-- Add more columns as needed -->
                    </tr>
                    <%
                        List<Map<String, String>> loginRecords = (List<Map<String, String>>) request.getAttribute("loginRecords");
                        session.setAttribute("loginRecords", loginRecords);  // Store loginRecords in the session
                        String loggedInUser = (String) session.getAttribute("username");
                        for (Map<String, String> record : loginRecords) {
                            String username = record.get("username");
                    %>
                    <tr>
                        <td class="tdlogin"><%= username.equals(loggedInUser) ? "*" + username : username %></td>
                        <td class="tdlogin"><%= record.get("role") %></td>
                        <!-- Add more cells as needed -->
                    </tr>
                    <% } %>
                </table>
            </div>
            <div>
                <form action="Logout" method="post">
                    <input type="submit" value="Logout">
                </form>
                <form action="GeneratePDF" method="post">
                    <input type="hidden" name="action" value="adminLoginMultiplePDF">
                    <input type="submit" value="Generate PDF">
                </form>
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