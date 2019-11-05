package ejbs;

import entities.Course;
import entities.Subject;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "SubjectEJB")
public class SubjectBean {


    @PersistenceContext
    private EntityManager em;

    public void create(int code, String name, int courseCode, int courseYear, String scholarYear) throws MyEntityExistsException, MyEntityNotFoundException {
        try{
            if (em.find(Subject.class, code) != null) {
                throw new MyEntityExistsException("A subject with that code: ("+code+") already exists.");
            }
            Course course = (Course) em.find(Course.class, courseCode);
            if(course == null) {
                throw new MyEntityNotFoundException("There is no course with that code:("+courseCode+")");

            }
                Subject subject = new Subject(code, name, course, courseYear, scholarYear);
                em.persist(subject);
                course.addSubject(subject);

        } catch (MyEntityExistsException | MyEntityNotFoundException e) {
            throw e;
        }catch(Exception e){
            throw new EJBException("ERROR_CREATING_SUBJECT" +e.getMessage());
        }

    }

    public Course findCourse(int courseCode) {
        try{
            return em.find(Course.class, courseCode);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_COURSE" +e.getMessage());
        }
    }

    public Set<Subject> all() {
        try {
            // remember, maps to: “SELECT s FROM Subject s ORDER BY s.name”
            return (Set<Subject>) em.createNamedQuery("getAllSubjects").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_SUBJECTS" +e.getMessage());
        }
    }

    public void update(int code, String name) throws MyEntityNotFoundException {
        try {
            Subject subject = em.find(Subject.class, code);
            if (subject == null) {
                throw new MyEntityNotFoundException("There is no subject with that code:("+code+")");
            }
            subject.setName(name);
        } catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATE_Subject" +e.getMessage());
        }
    }


}
