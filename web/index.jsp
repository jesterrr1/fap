<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader1"));%></h1>
    </header>
    <body>
        <div id="loader">
            <div id="shadow"></div>
            <div id="box"></div>
        </div>
        <div id="loader2">
            <div id="shadow2"></div>
            <div id="box2"></div>
        </div>
        <div id="loader3">
            <div id="shadow3"></div>
            <div id="box3"></div>
        </div>
        <script type="text/javascript">
            function redirect() {
                window.location.href = 'login.jsp';
            }
            setTimeout(redirect, 7000);
        </script>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
