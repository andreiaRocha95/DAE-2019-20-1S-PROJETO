package ejbs;

import entities.Administrador;

import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateful(name = "AdministradorEJB")
public class AdministradorBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int userId, String nome, String password, String email) {
        try {
            // Course course = (Course) em.find(Course.class, courseCode);
            //if(course != null){
            Administrador administrador = new Administrador(userId, nome, password, email);
            em.persist(administrador);
            // course.addStudent(student);
            //}

        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ADMINISTRADOR", e);
        }
    }

 public Administrador findAdmin(String userId) {
        try {
            return em.find(Administrador.class, userId);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ADMINISTRADOR", e);
        }
    }


    public List<Administrador> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Administrador>) em.createNamedQuery("getAllAdministradores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ADMINISTRADORES", e);
        }
    }
}
