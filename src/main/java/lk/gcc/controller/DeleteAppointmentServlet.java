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

@WebServlet(name = "deleteAppointmentServlet", value = "/deleteAppointmentServlet")
public class DeleteAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");

        if (deleteAppointment(appointmentId)) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("error");
        }
    }

    private boolean deleteAppointment(String appointmentId) {

        System.out.println(appointmentId);

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            AppointmentEntity appointment = entityManager.find(AppointmentEntity.class, Long.valueOf(appointmentId));
            if (appointment != null) {
                entityManager.remove(appointment);
                entityManager.getTransaction().commit();
                return true;
            }
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
