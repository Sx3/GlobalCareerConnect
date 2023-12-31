<%--
  Created by IntelliJ IDEA.
  User: Sanjaya
  Date: 8/11/2023
  Time: 3:51 PM
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
    <title>Consultant Dashboard</title>
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
            padding: 10px 20px;  /* Adjust padding */
            margin: 10px 0;  /* Adjust margin */
            border: none;
            cursor: pointer;
            width: auto;  /* Reset the width */
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
<h3>Your Appointments</h3>
    <button id="closeAppointmentBtn">Close Appointment</button> <!-- Close appointment button -->
<table border="1">
    <thead>
    <tr>
        <th></th>
        <th>Date</th>
        <th>Time Slot</th>
        <th>JobSeeker Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Status</th>
        <th>Note</th>
    </tr>
    </thead>
    <tbody id="appointmentsTable">
    </tbody>
</table>

<script>
    function fetchAppointments() {
        // Assume there's an endpoint that fetches appointments for the current user
        $.get("${pageContext.request.contextPath}/getConsultantAppointments", function (data) {
            let tableBody = document.getElementById('appointmentsTable');
            tableBody.innerHTML = ''; // Clear the table

            data.forEach(appointments => {
                let row = tableBody.insertRow();
                let cell0 = row.insertCell(0);
                let cell1 = row.insertCell(1);
                let cell2 = row.insertCell(2);
                let cell3 = row.insertCell(3);
                let cell4 = row.insertCell(4);
                let cell5 = row.insertCell(5);
                let cell6 = row.insertCell(6);
                let cell7 = row.insertCell(7);

                cell0.innerHTML = '<input type="radio" name="selectedAppointment" value="' + appointments.appointment.appointmentId + '">';
                cell1.innerHTML = appointments.appointment.appointmentDate;
                cell2.innerHTML = appointments.appointment.appointmentTime;
                cell3.innerHTML = appointments.fullName;
                cell4.innerHTML = appointments.email;
                cell5.innerHTML = appointments.phone;
                cell6.innerHTML = appointments.appointment.status;
                cell7.innerHTML = appointments.appointment.notes ? appointments.appointment.notes : 'N/A';
            });
        });
    }

    document.getElementById('closeAppointmentBtn').addEventListener('click', function() {
        let selectedAppointment = document.querySelector('input[name="selectedAppointment"]:checked');
        if (selectedAppointment) {
            let confirmation = confirm("Are you sure you want to mark this appoinment as Closed?");
            if (confirmation) {
                let appointmentId = selectedAppointment.value;

                $.ajax({
                    url: "${pageContext.request.contextPath}/closeAppointment",
                    type: "POST",
                    data: {appointmentId: appointmentId},
                    success: function(response) {
                        if(response === 'success') {
                            alert('Appointment closed successfully!');
                            fetchAppointments();  // Refresh the list
                        } else {
                            alert('Failed to close the appointment.');
                        }
                    }
                });
            }

        } else {
            alert('Please select an appointment to close.');
        }
    });

    // Fetch appointments when the page loads
    window.onload = fetchAppointments;
</script>
<a href="${pageContext.request.contextPath}/logoutServlet">Logout</a>
</div>
</body>
</html>
