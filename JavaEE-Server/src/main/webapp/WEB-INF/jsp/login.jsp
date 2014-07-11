<%-- 
    Document   : display
    Created on : Jul 11, 2014, 12:17:40 AM
    Author     : Ehsun Behravesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Capture Home</title>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script>
            $(function() {
               $("#password").focus();
            });
        </script>
    </head>
    <body>
        <h1>Login</h1>
        <form action="" method="post">
            Password: <input type="password" id="password" name="password"/>
            <input type="submit" value="Login" name="ok"/>
        </form>
    </body>
</html>
