import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lk.gcc.model.ConsultantEntity;

import java.util.List;

public class ConsultantTest {
    public static void main(String a[]){
        ConsultantEntity consultantEntity = new ConsultantEntity();
        consultantEntity.setFname("SAM");
        consultantEntity.setUname("ssam");
        consultantEntity.setEmail("sam@g.com");
        consultantEntity.setPsw("123");
        consultantEntity.setPhone("1110");
        consultantEntity.setSpecCountry("USA");
        consultantEntity.setJobType("Dev");

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
