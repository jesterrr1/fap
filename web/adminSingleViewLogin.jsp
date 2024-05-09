<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Single View Login</title>
    </head>
    <header>
            <h1><% out.print(getServletContext().getInitParameter("contextHeader9"));%></h1>
        </header>
    <body>
        <h1>User Details</h1><br>
        <p>Username: ${username}</p><br>
        <p>User ID: ${userID}</p><br>
        <p>Role: ${role}</p><br>
    </body>
    <footer>
            <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
            <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
            <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
