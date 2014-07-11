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
                setInterval(function() {                    
                    $.ajax({
                        url: "/last?ts=" + new Date().getTime()
                    }).done(function(data) {
                        //console.log(data);
                        $("#img").attr({"src": "data:image/png;base64," + data});
                    });
                    
                }, 1000);
            });

        </script>
    </head>
    <body>
        <h1 id="timestamp"></h1>
        <img id="img" src="" width="600"/>
    </body>
</html>
