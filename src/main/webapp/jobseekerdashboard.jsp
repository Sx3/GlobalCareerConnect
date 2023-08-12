<%--
  Created by IntelliJ IDEA.
  User: Sanjaya
  Date: 8/11/2023
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    // Check if the user is authenticated
    if (session.getAttribute("username") == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Job Seeker Dashboard</title>
</head>
<body>
<h2>Welcome <%= session.getAttribute("username") %>!</h2>
<h3>Book Appointments</h3>
<!-- Appointment booking form -->

<h3>Your Appointments</h3>
<!-- Display a list/table of the user's past and upcoming appointments -->

<a href="${pageContext.request.contextPath}/logoutServlet">Logout</a>
</body>
</html>
