<%--
  Created by IntelliJ IDEA.
  User: Sanjaya
  Date: 8/5/2023
  Time: 2:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f2f2f2;
        }

        .container {
            width: 300px;
            padding: 16px;
            background-color: white;
            margin: 0 auto;
            margin-top: 100px;
            border: 1px solid black;
            border-radius: 4px;
        }

        input[type=text], input[type=password], input[type=email] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }

        .login-icon {
            display: block;
            margin-left: auto;
            margin-right: auto;
            width: 50%;
        }

        select {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<form action="<%= request.getContextPath() %>/consultantregister" method="post">
    <div class="container">
        <img src="images/register.png" alt="Login Icon" class="login-icon">
        <label for="fname"><b>Full Name</b></label>
        <input type="text" placeholder="Enter Full Name" name="fname" required>

        <label for="email"><b>Email</b></label>
        <input type="email" placeholder="Enter Email" name="email" required>

        <label for="phone"><b>Phone</b></label>
        <input type="text" placeholder="Enter Phone" name="phone" required>

        <label for="spec_country"><b>Specialized Country</b></label>
        <select name="spec_country" required>
            <option value="UK">UK</option>
            <option value="USA">USA</option>
            <option value="Canada">Canada</option>
            <option value="New Zealand">New Zealand</option>
            <option value="Australia">Australia</option>
            <option value="Finland">Finland</option>
            <option value="Sweden">Sweden</option>
        </select>

        <label for="job_type"><b>Specialized Job Type</b></label>
        <select name="job_type" required>
            <option value="">Select Job Type</option>
            <option value="IT">IT</option>
            <option value="Engineering">Engineering</option>
            <option value="Construction">Construction</option>
            <option value="Finance">Finance</option>
            <option value="Health Care">Health Care</option>
        </select>

        <label for="uname"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="uname" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>

        <label for="confirmPsw"><b>Confirm Password</b></label>
        <input type="password" placeholder="Confirm Password" name="confirmPsw" required>


        <button type="submit">Register</button>
    </div>
</form>
<script>
    <% if (request.getAttribute("error") != null) { %>
    alert('<%= request.getAttribute("error") %>');
    <% } %>
</script>
</body>
</html>

