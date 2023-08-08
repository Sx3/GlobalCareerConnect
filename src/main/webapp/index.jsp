<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<br/>
<a href="consultant_register.jsp">Consultant Register</a>
<br/>
<a href="bookAppointment.jsp">Book Appoinment</a>


<form action="<%= request.getContextPath() %>/getavailableslots" method="post">

  <button type="submit">Register</button>

</form>
</body>
</html>