package lk.gcc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "getAvailableSlots", value = "/getAvailableSlots")
public class AvailableSlotsServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Sample logic to get available slots for a particular date
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        List<LocalTime> availableSlots = getAvailableSlotsForDate(date);

        // Manually building the JSON string
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < availableSlots.size(); i++) {
            json.append("\"").append(availableSlots.get(i).toString()).append("\"");
            if (i < availableSlots.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }

    private List<LocalTime> getAvailableSlotsForDate(LocalDate date) {
        // Logic to fetch available slots from the database
        // This is a dummy implementation
        return Arrays.asList(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(14, 0));
    }
}
