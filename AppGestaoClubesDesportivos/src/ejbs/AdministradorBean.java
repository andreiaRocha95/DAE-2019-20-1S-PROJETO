package ejbs;

import entities.Administrator;

import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateful(name = "AdministratorEJB")
public class AdministradorBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int userId, String name, String password, String email) {
        try {
            Administrator administrator = new Administrator(userId, name, password, email);
            em.persist(administrator);

        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ADMINISTRATOR", e);
        }
    }

    public Administrator findAdministrador(int userId) {
        try {
            return em.find(Administrator.class, userId);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ADMINISTRATOR", e);
        }
    }

    public List<Administrator> all() {
        try {
            return (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ADMINISTRATORS", e);
        }
    }

    public void delete(int userId) {
        try {
            Administrator administrator = em.find(Administrator.class, userId);
            if (administrator == null) {
                return;
            }
            em.remove(administrator);
        } catch (Exception e) {
            e.getMessage();
        }


    }

}
