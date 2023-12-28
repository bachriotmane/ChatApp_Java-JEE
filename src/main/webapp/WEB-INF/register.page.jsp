<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/AuthPageStyle.css">
    <title>Registration Page</title>
</head>
<body>
    <div class="container">
        <form class="form" onsubmit="return validateForm()" method="post">
            <h2>Register</h2>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required minlength="6">
            
            <label for="confirm-password">Confirm Password:</label>
            <input type="password" id="confirm-password" name="confirm-password" required>
            
            <button type="submit">Register</button>
            
            <p>Already a user? <a href="http://localhost:8080/ChatAppTP/Login">Create Account</a></p>
        </form>
    </div>
    <script>
        function checkPasswordMatch() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;
    var passwordError = document.getElementById("password-error");

    if (password !== confirmPassword) {
        passwordError.textContent = "Passwords do not match!";
    } else {
        passwordError.textContent = "";
    }
}

function validateForm() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }

    return true;
}

    </script>
</body>
</html>
    