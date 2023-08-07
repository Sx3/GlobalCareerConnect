package lk.gcc.controller;

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
import java.util.List;

@WebServlet(name = "consultantregister", value = "/consultantregister")
public class ConsultantRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Gochchaa");

        String fullName = request.getParameter("fname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String specCountry = request.getParameter("spec_country");
        String jobType = request.getParameter("job_type");
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");

        ConsultantEntity consultantEntity = new ConsultantEntity();
        consultantEntity.setFname(fullName);
        consultantEntity.setEmail(email);
        consultantEntity.setPhone(phone);
        consultantEntity.setSpecCountry(specCountry);
        consultantEntity.setJobType(jobType);
        consultantEntity.setUname(username);
        consultantEntity.setPsw(password);  // remember to hash the password before saving!

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(consultantEntity);
        entityManager.getTransaction().commit();

        List<ConsultantEntity> consultant = entityManager.createQuery("SELECT e FROM ConsultantEntity e").getResultList();
        if (consultant == null) {
            System.out.println("No employee found . ");
        } else {
            for (ConsultantEntity entity : consultant) {
                System.out.println("Name= " + entity.getFname() + "Phone" + entity.getPhone());
            }
        }


        entityManager.close();
        entityManagerFactory.close();
    }
}

