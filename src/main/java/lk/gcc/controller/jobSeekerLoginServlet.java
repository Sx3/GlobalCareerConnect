package lk.gcc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "jobSeekerLoginServlet", value = "/jobSeekerLoginServlet")
public class jobSeekerLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");

        boolean isValid = validateCredentials(username, password);

        if (isValid) {
            // Store user data in session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Redirect to the dashboard
            response.sendRedirect(request.getContextPath() + "/jobSeekerDashboard.jsp");
        } else {
            // Redirect back to login page with an error message
            request.setAttribute("login", "Invalid credentials. Please try again.");
            request.getRequestDispatcher("/jobSeekerLogin.jsp").forward(request, response);
        }
    }

    private boolean validateCredentials(String username, String password) {

        // Return true if valid, false otherwise
        return true; // Placeholder
    }
}
