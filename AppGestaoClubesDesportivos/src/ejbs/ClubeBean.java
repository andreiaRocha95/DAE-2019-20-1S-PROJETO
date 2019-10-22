package ejbs;

import dtos.CourseDTO;
import entities.Course;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;

@Stateless
public class CourseBean extends Bean<Course>{

    public void create(int code, String name)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Course.class, code) != null) {
                throw new EntityAlreadyExistsException("A course with that code already exists.");
            }

            em.persist(new Course(code, name));

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<Course> getAll() {
        return em.createNamedQuery("getAllCourses").getResultList();
    }
    
    public Collection<CourseDTO> getAllCourses() {
        try {
            return getAll(CourseDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int code) throws EntityDoesNotExistsException {
        try {
            Course course = em.find(Course.class, code);
            if (course == null) {
                throw new EntityDoesNotExistsException("There is no course with that code");
            }

            em.remove(course);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
