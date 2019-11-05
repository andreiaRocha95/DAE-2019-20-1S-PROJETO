package ejbs;

import entities.Partner;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "PartnerEJB")
public class PartnerBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int userId, String name, String password, String email) {
        try {
            Partner partner = new Partner(userId, name, password, email);
            em.persist(partner);

        } catch (Exception e) {
            throw new EJBException("ERROR_CREATING_PARTNER", e);
        }
    }

    public Partner findPartner(int userId) {
        try {
            return em.find(Partner.class, userId);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PARTNER", e);
        }
    }

    public List<Partner> all() {
        try {
            return (List<Partner>) em.createNamedQuery("getAllPartners").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PARTNERS", e);
        }
    }
}
