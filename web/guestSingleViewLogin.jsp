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
        <h1><% out.print(getServletContext().getInitParameter("contextHeader12"));%></h1>
    </header>
    <body>
        <h2>LOGIN</h2>
        <p></p>

        <h2>Student ID#: <%= session.getAttribute("USER_ID") %></h2>
        <p>Username: <%= session.getAttribute("username") %></p>
        <p>Role: <%= session.getAttribute("role") %></p> 
        <button onclick="window.location.href='success2.jsp'">Back to Admin Log</button>
        <form action="Logout" method="post">
            <input type="submit" value="Logout">
        </form>
        <form action="GeneratePDF" method="post">
            <input type="hidden" name="action" value="loginViewStudent">
            <input type="hidden" name="username" value="<%= session.getAttribute("username") %>">
            <input type="hidden" name="USER_ID" value="<%= session.getAttribute("USER_ID") %>">
            <input type="hidden" name="role" value="<%= session.getAttribute("role") %>">
            <input type="hidden" name="header" value="${initParam['contextHeader12']}">
            <input type="submit" value="Generate PDF">
        </form>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
