package ejbs;

import entities.Club;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ClubEJB")
public class ClubBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int clubCode, String name) {
        try {
            Club club = new Club(clubCode, name);
            em.persist(club);

        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_CLUB" + e.getMessage());
        }

    }

    public Club findClub(int clubCode) {
        try {
            return em.find(Club.class, clubCode);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_CLUB" + e.getMessage());
        }
    }

    public List<Club> all() {
        try {
            return (List<Club>) em.createNamedQuery("getAllClubs").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_CLUBS" + e.getMessage());
        }
    }

    public void update(int clubCode, String name) throws MyEntityNotFoundException {
        try {
            Club club = em.find(Club.class, clubCode);
            if (club == null) {
                throw new MyEntityNotFoundException("There is no course with that code:(" + clubCode + ")");
            }
            club.setName(name);
        } catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATE_CLUB" + e.getMessage());
        }
    }

}
