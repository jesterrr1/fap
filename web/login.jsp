<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Expires", "0");
        response.addHeader("Vary", "*");
        
        if(session.getAttribute("verifyCaptcha")!=null)
            session.setAttribute("verifyCaptcha", null);
        
        if(session.getAttribute("username")!=null){
            session.setAttribute("username", null);
            session.invalidate();
        } 
    %>
    <head>
        <link rel="stylesheet" href="styles.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN</title>
    </head>
    <header>
        <h1><% out.print(getServletContext().getInitParameter("contextHeader2"));%></h1>
    </header>
    <body>
        <div class="container">
            <div class="login-box">
              <h2>Login</h2>
              <form action="Login" method="post">
                <div class="input-box">
                  <input type="text" name="username" required>
                  <label>Username</label>
                </div>
                <div class="input-box">
                  <input type="password" name="password" required>
                  <label>Password</label>
                </div>
                <button type="submit" class="btn">Login</button>
              </form>
            </div>
            <span style="--i:0;"></span>
            <span style="--i:1;"></span>
            <span style="--i:2;"></span>
            <span style="--i:3;"></span>
            <span style="--i:4;"></span>
            <span style="--i:5;"></span>
            <span style="--i:6;"></span>
            <span style="--i:7;"></span>
            <span style="--i:8;"></span>
            <span style="--i:9;"></span>
            <span style="--i:10;"></span>
            <span style="--i:11;"></span>
            <span style="--i:12;"></span>
            <span style="--i:13;"></span>
            <span style="--i:14;"></span>
            <span style="--i:15;"></span>
            <span style="--i:16;"></span>
            <span style="--i:17;"></span>
            <span style="--i:18;"></span>
            <span style="--i:19;"></span>
            <span style="--i:20;"></span>
            <span style="--i:21;"></span>
            <span style="--i:22;"></span>
            <span style="--i:23;"></span>
            <span style="--i:24;"></span>
            <span style="--i:25;"></span>
            <span style="--i:26;"></span>
            <span style="--i:27;"></span>
            <span style="--i:28;"></span>
            <span style="--i:29;"></span>
            <span style="--i:30;"></span>
            <span style="--i:31;"></span>
            <span style="--i:32;"></span>
            <span style="--i:33;"></span>
            <span style="--i:34;"></span>
            <span style="--i:35;"></span>
            <span style="--i:36;"></span>
            <span style="--i:37;"></span>
            <span style="--i:38;"></span>
            <span style="--i:39;"></span>
            <span style="--i:40;"></span>
            <span style="--i:41;"></span>
            <span style="--i:42;"></span>
            <span style="--i:43;"></span>
            <span style="--i:44;"></span>
            <span style="--i:45;"></span>
            <span style="--i:46;"></span>
            <span style="--i:47;"></span>
            <span style="--i:48;"></span>
            <span style="--i:49;"></span>
        </div>
    </body>
    <footer>
        <% out.print(getServletContext().getInitParameter("contextFooter1"));%>
        <a href="https://activelearning.ph"><% out.print(getServletContext().getInitParameter("contextFooter2"));%></a>
        <% out.print(getServletContext().getInitParameter("contextFooter3"));%>
    </footer>
</html>
