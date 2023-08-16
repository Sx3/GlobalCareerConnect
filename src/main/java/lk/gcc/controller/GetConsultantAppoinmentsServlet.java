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

@WebServlet(name = "getConsultantAppointments", value = "/getConsultantAppointments")
public class GetConsultantAppoinmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user's ID from session or any other means
        int consultantID = (Integer) request.getSession().getAttribute("consultantID");

        // Fetch appointments for the user
        List<Tuple> tuples = fetchAppointmentsForConsultant(consultantID);

        List<Map<String, Object>> mappedData = tuples.stream().map(tuple -> {
            Map<String, Object> map = new HashMap<>();
            map.put("appointment", tuple.get(0, AppointmentEntity.class));
            map.put("fullName", tuple.get(1));
            map.put("email", tuple.get(2));
            map.put("phone", tuple.get(3));
            return map;
        }).collect(Collectors.toList());

        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(mappedData);

        System.out.println(json);

        response.getWriter().write(json);
    }
    private List<Tuple> fetchAppointmentsForConsultant(int consultantId) {
        List<Tuple> result = new ArrayList<>();
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            result = entityManager.createQuery(
                            "SELECT a as appointment, j.fullName as fullName, j.email as email,j.phone as phone " +
                                    "FROM AppointmentEntity a " +
                                    "INNER JOIN JobseekerEntity j " +
                                    "ON a.jobseekerId = j.id WHERE a.consultantId = :consultantID",
                            Tuple.class)
                    .setParameter("consultantID",consultantId)
                    .getResultList();
            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
