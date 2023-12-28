<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/AuthPageStyle.css">
    <title>Login Page</title>
</head>
<body>
    <div class="container">
        <form class="form" method="post" action = Login>
            <h2>Login</h2>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit">Login</button>
            
            <p>Not a member? <a href="http://localhost:8080/ChatAppTP/Register">Join us now</a></p>
        <%if(request.getAttribute("message")!=null){ %>
	        <h2 style = "color : red"><%=request.getAttribute("message") %></h2>
        <%} %>
        </form>
    </div>
        
</body>
</html>
    