<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captcha Page</title>
    </head>
    <body>
        <!--To get the captcha-->
        <%= session.getAttribute("captcha") %>
        
        
        
        
        <!-- JavaScript to prevent copy-pasting -->
        <script>
            //Get the CAPTCHA input field
            var captchaInput = document.getElementById("captcha");

            // Disable paste functionality for CAPTCHA input field
            captchaInput.addEventListener("paste", function(e) {
                e.preventDefault();
                alert("Copying and pasting is not allowed for CAPTCHA!");
            });

            // Disable drop functionality for CAPTCHA input field
            captchaInput.addEventListener("drop", function(e) {
                e.preventDefault();
                alert("Draggin and dropping is not allowed for CAPTCHA!");
            });
        </script>
    </body>
</html>
