<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Prevent caching of this page
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.
    
    //Check if session attributes are null
    String username = (String) request.getSession().getAttribute("username");
    String role = (String) request.getSession().getAttribute("role");
    if (username == null || role == null) {
        // Session attributes are null, redirect to error page
        response.sendRedirect("error_session.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
        <link rel="stylesheet" href="styles.css">       
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>STUDENT LOG</title>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader5"));%></h1>
    </header>
    <body>
        <div class="success-container">
            <div class="header-container">
                <h1>Welcome, <span id="user-customized">Student <%=session.getAttribute("USER_ID")%></span></h1>
            </div>
            <div class="options-box">
                <div class="login-container">
                    <div class="button-box">
                        <h2>Login</h2>
                        <form action="ViewRecords" method="post">
                            <input type="hidden" name="action" value="loginViewStudent">
                            <button type="submit" class="btn">Single View</button>
                        </form>
                        <h2>Expense Log</h2>
                        <form action="ViewRecords" method="post">
                            <input type="hidden" name="action" value="expenseViewStudent">
                            <button type="submit" class="btn">Single View</button>
                        </form>
                        <br><br>
                        <form action="Logout" method="post">
                            <button type="submit" class="btn">Logout</button>           
                        </form>
                    </div>
                </div>
            </div>
            <div class="options-box">
                <div class="login-container2">
                        <form action="ViewRecords" method="post">
                            <button type="submit" class="btn">Single View</button>
                        </form>
                        <form action="Logout" method="post">
                            <button type="submit" class="btn">Logout</button>           
                        </form>
                </div>
            </div>
        </div>
        </div>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
