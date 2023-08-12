<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Global Career Connect - Home</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      background-image: url('images/homebackground.jpg'); /* Adjust path based on your image location */
      background-size: cover; /* This will cover the entire viewport */
      background-repeat: no-repeat; /* Prevent image from repeating */
      background-position: center center; /* Center the image */
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .container {
      width: 300px;
      padding: 16px;
      background-color: white;
      text-align: center;
      border-radius: 5px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    button {
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      margin: 8px 0;
      border: none;
      cursor: pointer;
      width: 100%;
      border-radius: 3px;
    }

    button:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>

<div class="container">
  <img src="images/logo.jpg" alt="GCC Logo" style="width: 200px; margin-bottom: 20px;">
  <h2>Welcome to Global Career Connect</h2>
  <button onclick="location.href='${pageContext.request.contextPath}/consultantlogin.jsp'">Consultant Login</button>
  <button onclick="location.href='jobseekerlogin.jsp'">Job Seeker Login</button>
</div>

</body>
</html>
