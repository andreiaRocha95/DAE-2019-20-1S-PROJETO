package ejbs;



import entities.Administrador;

import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Stateful(name = "AdministadorEJB")
public class AdministadorBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int userId, String password, String name, String email) {
        try{
            Administrador administrador = new Administrador(userId, password, name, email);
            em.persist(administrador);

        }catch(Exception e){
            throw new EJBException("ERROR_CREATING_administrador", e);
        }
    }

    public Administrador findAdministrador(int userId) {
        try{
            return em.find(Administrador.class, userId);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_administrador", e);
        }
    }

    public List<Administrador> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Administrador>) em.createNamedQuery("getAllAdministradores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_Administradores", e);
        }
    }


}
