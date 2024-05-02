<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
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
</html>
