package lk.gcc.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.gcc.model.ConsultantEntity;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "consultantLoginServlet", value = "/consultantLoginServlet")
public class ConsultantLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");

        boolean isValid = validateCredentials(username, password);

        if (isValid) {
            // Store user data in session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Redirect to the dashboard
            response.sendRedirect(request.getContextPath() + "/consultantdashboard.jsp");
        } else {
            // Redirect back to login page with an error message
            request.setAttribute("login", "Invalid credentials. Please try again.");
            request.getRequestDispatcher("/consultantlogin.jsp").forward(request, response);
        }
    }

    private boolean validateCredentials(String username, String password) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            List<ConsultantEntity> consultant = entityManager.createQuery("SELECT e FROM ConsultantEntity e WHERE e.uname = :username AND e.psw = :password", ConsultantEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
            entityManager.close();
            entityManagerFactory.close();

            if (consultant != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
