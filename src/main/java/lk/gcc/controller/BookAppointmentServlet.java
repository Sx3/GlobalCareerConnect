package lk.gcc.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.gcc.model.AppointmentEntity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "bookAppointmentServlet", value = "/bookAppointmentServlet")
public class BookAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int jobseeker_id = (Integer) request.getSession().getAttribute("jobSeekerId");
        int consultant_id = Integer.valueOf(request.getParameter("consultant_Id"));
        String appointment_date = request.getParameter("date");
        String appointment_time = request.getParameter("timeSlot");
        String notes = request.getParameter("note");

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setJobseekerId(jobseeker_id);
        appointmentEntity.setConsultantId(consultant_id);
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointment_date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Convert util.Date to sql.Date
            appointmentEntity.setAppointmentDate(sqlDate);

            java.util.Date utilTime = new SimpleDateFormat("HH:mm:ss").parse(appointment_time + ":00");  // Assuming you're missing seconds in the time format
            java.sql.Time sqlTime = new java.sql.Time(utilTime.getTime());  // Convert util.Date to sql.Time
            appointmentEntity.setAppointmentTime(sqlTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        appointmentEntity.setNotes(notes);
        appointmentEntity.setStatus("Open");


        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(appointmentEntity);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();

            request.setAttribute("message", "Appointment successfully booked! \uD83C\uDF89");
            request.getRequestDispatcher("/jobseekerdashboard.jsp").forward(request, response);
        }catch (Exception e) {
            e.printStackTrace();

            // Handle the exception, maybe redirect to an error page or show an error message
            request.setAttribute("message", "There was an error during booking.");
            request.getRequestDispatcher("/jobseekerdashboard.jsp").forward(request, response);
        }
    }
}
