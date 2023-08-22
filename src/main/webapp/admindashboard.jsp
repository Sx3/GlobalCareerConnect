<%--
  Created by IntelliJ IDEA.
  User: Sanjaya
  Date: 8/16/2023
  Time: 10:15 AM
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
    <title>Admin Dashboard</title>
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
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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

        .summary-card {
            background: #FFFFFF;
            padding: 20px;
            margin-top: 20px;
            border-radius: 5px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        }

        .summary-title {
            font-weight: bold;
            font-size: 1.2em;
            border-bottom: 2px solid #4CAF50;
            margin-bottom: 10px;
        }

        .summary-item {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
<h3>All Appointments</h3>
<table border="1">
    <thead>
    <tr>
        <th>Select</th>
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

<div id="summaryDiv"></div>
<button id="deleteAppointment " onclick="deleteAppointment()">Delete Selected Appointment</button>
<script>
    function aggregateData(data) {
        let countryCounts = {};
        let professionCounts = {};

        data.forEach(appointments => {
            let country = appointments.specCountry;
            let profession = appointments.jobType;

            countryCounts[country] = (countryCounts[country] || 0) + 1;
            professionCounts[profession] = (professionCounts[profession] || 0) + 1;
        });
        return {countryCounts, professionCounts};
    }

    function displaySummary(data) {
        let {countryCounts, professionCounts} = aggregateData(data);

        let countrySummary = '<div class="summary-card"><div class="summary-title">Appointments per Country:</div>';
        for (let country in countryCounts) {
            countrySummary += '<div class="summary-item">' + country +": " +countryCounts[country] + '</div>';
        }
        countrySummary += '</div>';  // Close the summary-card div

        let professionSummary = '<div class="summary-card"><div class="summary-title">Appointments per Profession:</div>';
        for (let profession in professionCounts) {
            professionSummary += '<div class="summary-item">' + profession+": " + professionCounts[profession] + '</div>';
        }
        professionSummary += '</div>';  // Close the summary-card div

        let summaryDiv = document.getElementById('summaryDiv');
        summaryDiv.innerHTML = countrySummary + professionSummary;
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
                let cell9 = row.insertCell(8);

                cell1.innerHTML = '<input type="radio" name="appointmentSelection" value="' + appointments.appointment.appointmentId + '">';
                cell2.innerHTML = appointments.appointment.appointmentDate;
                cell3.innerHTML = appointments.appointment.appointmentTime;
                cell4.innerHTML = appointments.fname;
                cell5.innerHTML = appointments.email;
                cell6.innerHTML = appointments.phone;
                cell7.innerHTML = appointments.specCountry;
                cell8.innerHTML = appointments.jobType;
                cell9.innerHTML = appointments.appointment.notes ? appointments.appointment.notes : 'N/A';
            });
            displaySummary(data);
        });
    }

    function deleteAppointment() {
        let selectedAppointment = document.querySelector('input[name="appointmentSelection"]:checked');
        if (!selectedAppointment) {
            alert('Please select an appointment to delete.');
            return;
        }
        let appointmentId = selectedAppointment.value;
        let confirmation = confirm("Are you sure you want to delete this appointment?");
        if (confirmation) {
            let appointmentId = selectedAppointment.value;
            $.ajax({
                url: "${pageContext.request.contextPath}/deleteAppointmentServlet",
                type: "POST",
                data: {appointmentId: appointmentId},
                success: function (response) {
                    if (response === 'success') {
                        alert('Appointment deleted successfully.');
                        fetchAppointments();  // Refresh the list
                    } else {
                        alert('Error deleting appointment. Please try again.');
                    }
                }
            });
        }
    }

    window.onload = fetchAppointments;
</script>
<a href="${pageContext.request.contextPath}/logoutServlet">Logout</a>
</body>
</html>
