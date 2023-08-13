package lk.gcc.controller;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.gcc.model.AppointmentEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "getUserAppointments", value = "/getUserAppointments")
public class GetUserAppointmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user's ID from session or any other means
        int userId = (Integer) request.getSession().getAttribute("jobSeekerId");

        // Fetch appointments for the user
        List<Tuple> tuples = fetchAppointmentsForUser(userId);

        List<Map<String, Object>> mappedData = tuples.stream().map(tuple -> {
            Map<String, Object> map = new HashMap<>();
            map.put("appointment", tuple.get(0, AppointmentEntity.class));
            map.put("fname", tuple.get(1));
            map.put("email", tuple.get(2));
            map.put("phone", tuple.get(3));
            map.put("specCountry", tuple.get(4));
            map.put("jobType", tuple.get(5));
            return map;
        }).collect(Collectors.toList());

        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(mappedData);

        System.out.println(json);

        response.getWriter().write(json);
    }


    private List<Tuple> fetchAppointmentsForUser(int userId) {
        List<Tuple> result = new ArrayList<>();
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            result = entityManager.createQuery(
                            "SELECT a as appointment, c.fname as fname, c.email as email,c.phone as phone, c.specCountry as specCountry,c.jobType as jobType " +
                                    "FROM AppointmentEntity a " +
                                    "INNER JOIN ConsultantEntity c " +
                                    "ON a.consultantId = c.id WHERE a.jobseekerId = :userId",
                            Tuple.class)
                    .setParameter("userId", userId)
                    .getResultList();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
