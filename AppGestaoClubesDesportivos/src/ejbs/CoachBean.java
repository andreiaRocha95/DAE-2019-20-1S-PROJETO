package ejbs;

import entities.Club;
import entities.Coach;
import entities.Modality;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "CoachEJB")
public class CoachBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int userId, String name, String password, String email, int clubCode) throws MyEntityExistsException, MyEntityNotFoundException {
        try {
            if (em.find(Coach.class, userId) != null) {
                throw new MyEntityExistsException("A user with that username: (" + userId + ") already exists.");
            }
            Club club = em.find(Club.class, clubCode);
            if (club == null) {
                throw new MyEntityNotFoundException("There is no club with that code:(" + clubCode + ")");
            }
            Coach coach = new Coach(userId, name, password, email, club);
            em.persist(coach);
            club.addCoach(coach);

        } catch (MyEntityExistsException | MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_COACH" + e.getMessage());
        }
    }

    public Coach findCoach(int userId) {
        try {
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

    public void enrollCoachInModality(int userId, int modalityCode) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try {
            Coach coach = em.find(Coach.class, userId);
            if (coach == null) {
                throw new MyEntityNotFoundException("There is no user with that username:(" + userId + ")");
            }
            Modality modality = em.find(Modality.class, modalityCode);
            if (modality == null) {
                throw new MyEntityNotFoundException("There is no modality with that code:(" + modalityCode + ")");
            }

            if (!coach.getClub().getModalities().contains(modality)) {
                throw new MyIllegalArgumentException("Modality is already enrolled in coach whith code: " + modalityCode);
            }

            if (modality.getCoaches().contains(coach)) {
                throw new MyIllegalArgumentException("Student is already enrolled in subject whith code: " + modalityCode);
            }

            coach.addModality(modality);
            modality.addCoach(coach);

        } catch (MyIllegalArgumentException | MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_ENROLL_ATHLETE_IN_MODALITY" + e.getMessage());
        }
    }
}
