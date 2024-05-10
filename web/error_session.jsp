<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Session Destroyed Error Page</title>
        <!--Kapag nag attempt to go to success page without properly logging in-->
<!--        Session Timeout-->
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextErrorSession"));%></h1>
    </header>
    </head>
    <body>
        <div>
            <h1><span id="error-customized">Unauthorized Access</span></h1>
            <div class="error-intstruction">
                <h3>Please go back to the main page.</h3>
                <div class="back-container">
                    <div class="link__404">
                        <a href="index.jsp">Back to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
