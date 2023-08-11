package lk.gcc.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.gcc.model.JobseekerEntity;

import java.io.IOException;

@WebServlet(name = "jobSeekerRegister", value = "/jobSeekerRegister")
public class JobSeekerRegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract parameters from request
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input data
        if (!password.equals(confirmPassword)) {
            // Redirect to registration page with an error message
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("/jobSeekerRegister.jsp").forward(request, response);
            return;
        }

        // Create a job seeker object
        JobseekerEntity jobSeeker = new JobseekerEntity();
        jobSeeker.setFullName(fullName);
        jobSeeker.setEmail(email);
        jobSeeker.setPhone(phone);
        jobSeeker.setUsername(username);
        jobSeeker.setPassword(password);

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(jobSeeker);
            entityManager.getTransaction().commit();
            entityManager.close();
            entityManagerFactory.close();

            request.setAttribute("login", "You are sucessfully registered! now you can login.");
            request.getRequestDispatcher("/jobSeekerLogin.jsp").forward(request, response);
        }catch (Exception e) {
            e.printStackTrace();

            // Handle the exception, maybe redirect to an error page or show an error message
            request.setAttribute("error", "There was an error during registration.");
            request.getRequestDispatcher("/jobSeekerRegister.jsp").forward(request, response);
        }
    }

}
