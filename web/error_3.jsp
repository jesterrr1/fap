<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Error 3</title>
        <!--username and password incorrect but not blank-->
    </head>
    <header>
        <h1><% out.print(getServletContext() .getInitParameter("contextError"));%></h1>
    </header>
    <body>
        <div id="error-container">
            <h1>Incorrect <span id="error-customized">Username and Password</span></h1>
            
            <a href="login.jsp">
                <button type="submit" class="btn" id="back-button3">Back</button>
            </a>
        </div>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
