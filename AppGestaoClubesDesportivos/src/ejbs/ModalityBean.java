package ejbs;

import entities.Club;
import entities.Modality;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Stateless(name = "SubjectEJB")
public class ModalityBean {


    @PersistenceContext
    private EntityManager em;

    public void create(int modalityCode, String name, int clubCode) throws MyEntityExistsException, MyEntityNotFoundException {
        try {
            if (em.find(Modality.class, modalityCode) != null) {
                throw new MyEntityExistsException("A subject with that code: (" + modalityCode + ") already exists.");
            }
            Club club = em.find(Club.class, clubCode);
            if (club == null) {
                throw new MyEntityNotFoundException("There is no course with that code:(" + clubCode + ")");
            }


            Modality modality = new Modality(modalityCode, name, club);
            em.persist(modality);
            club.addModality(modality);

        } catch (MyEntityExistsException | MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_MODALITY" + e.getMessage());
        }

    }

    public Modality findModality(int modatityCode) {
        try {
            return em.find(Modality.class, modatityCode);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_MODALITY" + e.getMessage());
        }
    }

    public Set<Modality> all() {
        try {
            // remember, maps to: “SELECT s FROM Subject s ORDER BY s.name”
            return (Set<Modality>) em.createNamedQuery("getAllModalities").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_MODALITIES" + e.getMessage());
        }
    }

    public void update(int modalityCode, String name) throws MyEntityNotFoundException {
        try {
            Modality modality = em.find(Modality.class, modalityCode);
            if (modality == null) {
                throw new MyEntityNotFoundException("There is no subject with that code:(" + modalityCode + ")");
            }
            modality.setName(name);
        } catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATE_MODALITY" + e.getMessage());
        }
    }


}
