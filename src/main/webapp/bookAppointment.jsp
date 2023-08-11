<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Appointment Booking</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>Book an Appointment</h2>

<form action="${pageContext.request.contextPath}/bookAppointment method="post">
    <label for="date">Select Date:</label>
    <input type="date" id="date" name="date" onchange="fetchSlots()"><br>

    <label for="timeSlot">Available Time Slots:</label><br>
    <select id="timeSlot" name="timeSlot"></select>

    <input type="submit" value="Book Appointment">
</form>
<script>
    function fetchSlots() {
        let selectedDate = document.getElementById('date').value;
        $.get("${pageContext.request.contextPath}/getAvailableSlots?date=" + selectedDate, function(data) {
            let slots = data;
            let slotDropdown = document.getElementById('timeSlot');
            slotDropdown.innerHTML = ""; // Clear existing options
            slots.forEach(slot => {
                let option = document.createElement('option');
                option.value = slot;
                option.text = slot;
                slotDropdown.appendChild(option);
            });
        });
    }
    document.getElementById('date').addEventListener('change', fetchSlots);
</script>
</body>
</html>
