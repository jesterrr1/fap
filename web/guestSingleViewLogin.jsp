<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>STUDENT LOGIN VIEW</title>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader6"));%></h1>
    </header>
    <body>
        <h2>LOGIN</h2>
        <p></p>

        <h2>Student ID#: <%= session.getAttribute("USER_ID") %></h2>
        <p>Username: <%= session.getAttribute("username") %></p>
        <p>Role: <%= session.getAttribute("role") %></p>   
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
