package ejbs;

import entities.Coach;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "CoachEJB")
public class CoachBean {
    @PersistenceContext
    private EntityManager em;
    public void create(int userId, String name, String password, String email) {
        try{
            Coach coach = new Coach(userId,name, password, email);
            em.persist(coach);

        }catch(Exception e){
            throw new EJBException("ERROR_CREATING_COACH", e);
        }
    }

    public Coach findCoach(int userId) {
        try{
            return em.find(Coach.class, userId);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_COACH", e);
        }
    }

    public List<Coach> all() {
        try {
            return (List<Coach>) em.createNamedQuery("getAllCoaches").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COACHES", e);
        }
    }
}
