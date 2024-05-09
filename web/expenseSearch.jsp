<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ENROLLMENT EXPENSE SEARCH</title>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader9"));%></h1>
    </header>
    <body>
        <h1>Enter a Username</h1>
        <form action="ViewRecords" method="get">
            <input type="hidden" name="action" value="searchExpense">
            <label for="username">Username:</label><br>
            <input type="text" id="username" name="username"><br>
            <input type="submit" value="Search">
        </form>
        <button onclick="window.location.href='success.jsp'">Back to Admin Log</button>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
