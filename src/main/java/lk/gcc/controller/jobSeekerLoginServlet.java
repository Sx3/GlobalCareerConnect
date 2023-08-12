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
import lk.gcc.model.JobseekerEntity;

import java.io.IOException;

@WebServlet(name = "jobSeekerLoginServlet", value = "/jobSeekerLoginServlet")
public class jobSeekerLoginServlet extends HttpServlet {
    private String fName;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");

        boolean isValid = validateCredentials(username, password);

        if (isValid) {
            // Store user data in session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("fname", fName);

            // Redirect to the dashboard
            response.sendRedirect(request.getContextPath() + "/jobseekerdashboard.jsp");
        } else {
            // Redirect back to login page with an error message
            request.setAttribute("login", "Invalid credentials. Please try again.");
            request.getRequestDispatcher("/jobseekerlogin.jsp").forward(request, response);
        }
    }

    private boolean validateCredentials(String username, String password) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            JobseekerEntity seeker = entityManager.createQuery("SELECT e FROM JobseekerEntity e WHERE e.username = :username AND e.password = :password", JobseekerEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            entityManager.close();
            entityManagerFactory.close();

            if (seeker != null) {
                fName = seeker.getFullName();
                return true;
            }
        } catch (Exception e) {
            fName = null;
            e.printStackTrace();
        }
        fName = null;
        return false;
    }
}
