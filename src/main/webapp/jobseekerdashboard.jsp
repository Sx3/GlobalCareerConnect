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
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
</head>
<body>
<h2>Welcome <%= session.getAttribute("fname") %>!</h2>
<h3>Book Appointments</h3>
<form action="${pageContext.request.contextPath}/bookAppointmentServlet" method="post">
    <!-- Country Selection -->
    <label for="country">Select Country:</label>
    <select id="country" name="country" onchange="fetchConsultants()">
        <option value="UK">UK</option>
        <option value="USA">USA</option>
        <option value="Canada">Canada</option>
        <option value="New Zealand">New Zealand</option>
        <option value="Australia">Australia</option>
        <option value="Finland">Finland</option>
        <option value="Sweden">Sweden</option>
    </select>

    <!-- Date and Time Selection -->
    <label for="date">Select Date:</label>
    <input type="date" id="date" name="date" onchange="fetchConsultants()">

    <label for="timeSlot">Select Time Slot:</label>
    <select id="timeSlot" name="timeSlot" onchange="fetchConsultants()">
        <option value="9:00">9:00</option>
        <option value="10:00">10:00</option>
        <option value="11:00">11:00</option>
        <option value="13:00">13:00</option>
        <option value="14:00">14:00</option>
        <option value="15:00">15:00</option>
    </select>

    <!-- Table of Available Consultants -->
    <table>
        <thead>
        <tr>
            <th>Select</th>
            <th>Consultant Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Specialization</th>
        </tr>
        </thead>
        <tbody id="consultantTable">
        <!-- Rows will be populated dynamically -->
        </tbody>
    </table>

    <input type="submit" value="Book Appointment">
</form>

<script>
    function fetchConsultants() {
        let country = document.getElementById('country').value;
        let date = document.getElementById('date').value;
        let timeSlot = document.getElementById('timeSlot').value;

        // Make an AJAX call to get the available consultants
        $.get("${pageContext.request.contextPath}/getAvailableConsultants?country=" + country + "&date=" + date + "&time=" + timeSlot, function(data) {
            let tableBody = document.getElementById('consultantTable');
            tableBody.innerHTML = ''; // Clear the table

            data.forEach(consultant => {
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                let cell4 = row.insertCell(3);
                let cell5 = row.insertCell(4);

                cell1.innerHTML = '<input type="radio" name="consultantId" value="' + consultant.id + '">';
                cell2.innerHTML = consultant.fname;
                cell3.innerHTML = consultant.email;
                cell4.innerHTML = consultant.phone;
                cell5.innerHTML = consultant.jobType;
            });
        });
    }
</script>


<h3>Your Appointments</h3>
<!-- Display a list/table of the user's past and upcoming appointments -->

<a href="${pageContext.request.contextPath}/logoutServlet">Logout</a>
</body>
</html>
