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
        <div class="singlesearch-container">
            <div class="header-container">
                <h1><span id="user-customized">Enter a Username</span></h1>
            </div>
            <div class="search-box">
                <form action="ViewRecords" method="get">
                    <div class="input-container">
                        <input type="hidden" name="action" value="searchExpense">
                        <label for="username">Username:</label><br>
                        <input type="text" id="username" name="username" required><br>
                    </div>
            </div>
            <div class="input-container">
                <input type="submit" value="Search" class="btn">
            </div>
                </form>
                <div class="input-container">
                    <button onclick="window.location.href='success.jsp'" class="btn">Back to Admin Log</button>
                </div>
        </div>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
