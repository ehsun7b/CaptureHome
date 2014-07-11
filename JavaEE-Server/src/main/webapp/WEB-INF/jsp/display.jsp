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
                console.log("dome ready");
                $("#img").load(function() {
                    console.log("image loaded");
                    setTimeout(refreshFrame, 1000);
                }); 
                
                $("#img").error(function() {
                    console.log("image failed");
                    setTimeout(refreshFrame, 1000);
                }); 
            });

            function refreshFrame() {                
                $("#img").attr({"src": "/last?ts=" + new Date().getTime()});                
            }

        </script>
    </head>
    <body>
        <h1 id="timestamp"></h1>
        <img id="img" src="/last" width="600"/>
    </body>
</html>
