package ejbs;

import entities.Course;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;


import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "CourseEJB")
public class CourseBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int courseCode, String name) {
        try{
            Course course = new Course(courseCode, name);
            em.persist(course);

        }catch(Exception e){
            throw new EJBException("ERROR_CREATING_COURSE" +e.getMessage());
        }

    }

    public Course findCourse(int courseCode) {
        try{
            return em.find(Course.class, courseCode);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_COURSE" +e.getMessage());
        }
    }

    public List<Course> all() {
        try {
            // remember, maps to: “SELECT c FROM Course c ORDER BY c.name”
            return (List<Course>) em.createNamedQuery("getAllCourses").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_COURSES" +e.getMessage());
        }
    }

    public void update(int courseCode, String name) throws MyEntityNotFoundException{
        try {
            Course course = em.find(Course.class, courseCode);
            if (course == null) {
                throw new MyEntityNotFoundException("There is no course with that code:("+courseCode+")");
            }
            course.setName(name);
        } catch (MyEntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException("ERROR_UPDATE_Course" +e.getMessage());
        }
    }

}
