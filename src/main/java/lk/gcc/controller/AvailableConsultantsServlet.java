package lk.gcc.controller;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.gcc.model.ConsultantEntity;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "getAvailableConsultants", value = "/getAvailableConsultants")
public class AvailableConsultantsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String country = request.getParameter("country");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        String timeSlot = request.getParameter("time");

        List<ConsultantEntity> availableConsultants = fetchConsultantsNotBookedOn(country, date, timeSlot);

        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(availableConsultants);
        response.getWriter().write(json);
    }

    private List<ConsultantEntity> fetchConsultantsNotBookedOn(String country, LocalDate date, String timeSlot) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            Time time = Time.valueOf(timeSlot + ":00");
            List<ConsultantEntity> availableConsultants = entityManager
                    .createQuery("SELECT c FROM ConsultantEntity c WHERE c.specCountry LIKE :selectedCountry AND c.id NOT IN (" +
                                    "SELECT a.consultantId FROM AppointmentEntity a WHERE a.appointmentDate = :selectedDate AND a.appointmentTime = :selectedTime)",
                            ConsultantEntity.class)
                    .setParameter("selectedCountry", country)
                    .setParameter("selectedDate", date)
                    .setParameter("selectedTime", time)
                    .getResultList();
            entityManager.close();
            entityManagerFactory.close();

            if (availableConsultants != null) {
                return availableConsultants;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}