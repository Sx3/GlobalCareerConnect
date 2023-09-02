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
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 20px;
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

        h2, h3 {
            color: #333;
        }

        label, select, input, textarea, input[type=text], input[type=password], input[type=email] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button, input[type=submit] {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover, input[type=submit]:hover {
            opacity: 0.8;
        }

        .login-icon {
            display: block;
            margin-left: auto;
            margin-right: auto;
            width: 50%;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            margin-top: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007BFF;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
        }

        a:hover {
            background-color: #0056b3;
        }

        .content-wrapper {
            width: 80%;  /* Change this according to your preference */
            margin: 0 auto;  /* Center the container */
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
        }

    </style>
</head>
<body>
<div class="content-wrapper">
<h2>Welcome <%= session.getAttribute("fname") %>!</h2>
<h3>Book Appointments</h3>
<form action="${pageContext.request.contextPath}/bookAppointmentServlet" method="post" onsubmit="return validateForm()">
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

    <label for="note">Add a note (Optional):</label>
    <textarea id="note" name="note" rows="4" cols="50"
              placeholder="Any special requests or information you'd like to add?"></textarea>

    <input type="submit" value="Book Appointment">
</form>

<h3>Your Appointments</h3>
<table border="1">
    <thead>
    <tr>
        <th>Date</th>
        <th>Time Slot</th>
        <th>Consultant Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Specialization Country</th>
        <th>Job Type</th>
        <th>Note</th>
    </tr>
    </thead>
    <tbody id="appointmentsTable">
    </tbody>
</table>

<script>
    function fetchConsultants() {
        let country = document.getElementById('country').value;
        let date = document.getElementById('date').value;
        let timeSlot = document.getElementById('timeSlot').value;

        // Make an AJAX call to get the available consultants
        $.get("${pageContext.request.contextPath}/getAvailableConsultants?country=" + country + "&date=" + date + "&time=" + timeSlot, function (data) {
            let tableBody = document.getElementById('consultantTable');
            tableBody.innerHTML = ''; // Clear the table

            data.forEach(consultant => {
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                let cell4 = row.insertCell(3);
                let cell5 = row.insertCell(4);

                cell1.innerHTML = '<input type="radio" name="consultant_Id" value="' + consultant.id + '">';
                cell2.innerHTML = consultant.fname;
                cell3.innerHTML = consultant.email;
                cell4.innerHTML = consultant.phone;
                cell5.innerHTML = consultant.jobType;
            });
        });
    }

    function validateForm() {
        let date = document.getElementById('date').value;
        let consultantSelected = document.querySelector('input[name="consultant_Id"]:checked');

        if (!date) {
            alert('Please select a date.');
            return false;
        }

        if (!consultantSelected) {
            alert('Please select a consultant.');
            return false;
        }

        return true;  // if all checks passed
    }

    function fetchAppointments() {
        // Assume there's an endpoint that fetches appointments for the current user
        $.get("${pageContext.request.contextPath}/getUserAppointments", function (data) {
            let tableBody = document.getElementById('appointmentsTable');
            tableBody.innerHTML = ''; // Clear the table

            data.forEach(appointments => {
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                let cell4 = row.insertCell(3);
                let cell5 = row.insertCell(4);
                let cell6 = row.insertCell(5);
                let cell7 = row.insertCell(6);
                let cell8 = row.insertCell(7);

                cell1.innerHTML = appointments.appointment.appointmentDate;
                cell2.innerHTML = appointments.appointment.appointmentTime;
                cell3.innerHTML = appointments.fname;
                cell4.innerHTML = appointments.email;
                cell5.innerHTML = appointments.phone;
                cell6.innerHTML = appointments.specCountry;
                cell7.innerHTML = appointments.jobType;
                cell8.innerHTML = appointments.appointment.notes ? appointments.appointment.notes : 'N/A';
            });
        });
    }

    // Fetch appointments when the page loads
    window.onload = fetchAppointments;
</script>
<script>
    <% if (request.getAttribute("message") != null) { %>
    alert('<%= request.getAttribute("message") %>');
    <% } %>
</script>
<a href="${pageContext.request.contextPath}/logoutServlet">Logout</a>
</div>
</body>
</html>
