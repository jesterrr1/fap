<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN VIEW</title>
    </head>
    <header>
            <h1><% out.print(getServletContext().getInitParameter("contextHeader7"));%></h1>
    </header>
    <body>
        <h1>User Details</h1>
        <p>Username: ${username}</p>
        <p>User ID: ${userID}</p>
        <p>Role: ${role}</p>
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>
        <form action="GeneratePDF" method="post">
            <input type="hidden" name="action" value="adminLoginSinglePDF">
            <input type="hidden" name="username" value="${username}">
            <input type="hidden" name="userID" value="${userID}">
            <input type="hidden" name="role" value="${role}">
            <input type="hidden" name="header" value="${initParam['contextHeader7']}">
            <input type="submit" value="Generate PDF">
        </form>
        <button onclick="window.location.href='loginSearch.jsp'">Search Another</button>
        <button onclick="window.location.href='success.jsp'">Back to Admin Log</button>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
