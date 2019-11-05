package ejbs;

import entities.Athlete;
import entities.Club;
import entities.Modality;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AtheleteEJB")
public class AthleteBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int userId, String name, String password, String email, int clubCode) throws MyEntityExistsException, MyEntityNotFoundException {
        try {
            if (em.find(Athlete.class, userId) != null) {
                throw new MyEntityExistsException("A user with that username: (" + userId + ") already exists.");
            }
            Club club = em.find(Club.class, clubCode);
            if (club == null) {
                throw new MyEntityNotFoundException("There is no course with that code:(" + clubCode + ")");
            }
            Athlete athlete = new Athlete(userId, name, password, email, club);
            em.persist(athlete);
            club.addAthlete(athlete);

        } catch (MyEntityExistsException | MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_ATHLETE" + e.getMessage());
        }
    }

    public Athlete findAthlete(int userId) {
        try {
            return em.find(Athlete.class, userId);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ATHLETE", e);
        }
    }

    public List<Athlete> all() {
        try {
            return (List<Athlete>) em.createNamedQuery("getAllAthletes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATHLETES", e);
        }
    }

    public void enrollAthleteInModality(int userId, int modalityCode) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try {
            Athlete athlete = em.find(Athlete.class, userId);
            if (athlete == null) {
                throw new MyEntityNotFoundException("There is no student with that username:(" + userId + ")");
            }
            Modality modality = em.find(Modality.class, modalityCode);
            if (modality == null) {
                throw new MyEntityNotFoundException("There is no subject with that code:(" + modalityCode + ")");
            }

            if (!athlete.getClub().getModalities().contains(modality)) {
                throw new MyIllegalArgumentException("Subject is already enrolled in course whith code: " + modalityCode);
            }

            if (modality.getAthletes().contains(athlete)) {
                throw new MyIllegalArgumentException("Student is already enrolled in subject whith code: " + modalityCode);
            }

            modality.addAthlete(athlete);
            athlete.addModality(modality);

        } catch (MyIllegalArgumentException | MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_ENROLL_ATHLETE_IN_MODALITY" + e.getMessage());
        }
    }


}
