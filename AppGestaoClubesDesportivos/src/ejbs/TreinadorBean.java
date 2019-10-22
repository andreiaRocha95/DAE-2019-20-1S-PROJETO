package ejbs;

import dtos.DocumentDTO;
import dtos.StudentDTO;
import entities.Course;
import entities.Document;
import entities.Student;
import entities.Subject;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.StudentEnrolledException;
import exceptions.SubjectNotInCourseException;
import exceptions.Utils;
import exceptions.StudentNotEnrolledException;
import java.util.Collection;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/students")
public class StudentBean extends Bean<Student> {

    @EJB
    EmailBean emailBean;

    public void create(String username, String password, String name, String email, int courseCode)
            throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Student.class, username) != null) {
                throw new EntityAlreadyExistsException("A user with that username already exists.");
            }
            Course course = em.find(Course.class, courseCode);
            if (course == null) {
                throw new EntityDoesNotExistsException("There is no course with that code.");
            }
            Student student = new Student(username, password, name, email, course);
            course.addStudent(student);
            em.persist(student);
        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public Collection<StudentDTO> gettAllStudents() {
        try {
            return getAll(StudentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<Student> getAll() {
        return em.createNamedQuery("getAllStudents").getResultList();
    }

    @GET
    @RolesAllowed({"Student"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findStudent/{username}")
    public StudentDTO findStudent(@PathParam("username") String username)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no user with such username.");
            }
            return toDTO(student, StudentDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("updateREST1/{username}/{password}/{name}/{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST1(
            @PathParam("username") String username,
            @PathParam("password") String password,
            @PathParam("name") String name,
            @PathParam("email") String email,
            String courseCode)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        try {
            update(
                    username,
                    password,
                    name,
                    email,
                    Integer.valueOf(courseCode));
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/updateREST2")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST2(StudentDTO student)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        try {
            update(
                    student.getUsername(),
                    student.getPassword(),
                    student.getName(),
                    student.getEmail(),
                    student.getCourseCode());
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(String username, String password, String name, String email, int courseCode)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            Course course = em.find(Course.class, courseCode);
            if (course == null) {
                throw new EntityDoesNotExistsException("There is no course with that code.");
            }

            student.setPassword(password);
            student.setName(name);
            student.setEmail(email);
            student.getCourse().removeStudent(student);
            student.setCourse(course);
            course.addStudent(student);
            em.merge(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @RolesAllowed({"Student"})
    @Path("/addDocument/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addDocument(
            @PathParam("username") String username,
            DocumentDTO doc)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with such username.");
            }

            Document document = new Document(doc.getFilepath(), doc.getDesiredName(), doc.getMimeType(), student);
            em.persist(document);
            student.addDocument(document);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public DocumentDTO getDocument(int id) throws EntityDoesNotExistsException {
        Document doc = em.find(Document.class, id);
            
        if (doc == null)
            throw new EntityDoesNotExistsException();

        return toDTO(doc, DocumentDTO.class);
    }
    
    public Collection<DocumentDTO> getDocuments(String username) throws EntityDoesNotExistsException {
        try {
            List<Document> docs = em.createNamedQuery("getDocumentsOfStudent", Document.class).setParameter("username", username).getResultList();
            return toDTOs(docs, DocumentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public boolean hasDocuments(String username)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no user with such username.");
            }
            return !student.getDocuments().isEmpty();
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(String username) throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }
            student.getCourse().removeStudent(student);

            for (Subject subject : student.getSubjects()) {
                subject.removeStudent(student);
            }

            em.remove(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollStudent(String username, int subjectCode)
            throws EntityDoesNotExistsException, SubjectNotInCourseException, StudentEnrolledException {
        try {

            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            Subject subject = em.find(Subject.class, subjectCode);
            if (subject == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }

            if (!student.getCourse().getSubjects().contains(subject)) {
                throw new SubjectNotInCourseException("Student's course has no such subject.");
            }

            if (subject.getStudents().contains(student)) {
                throw new StudentEnrolledException("Student is already enrolled in that subject.");
            }

            subject.addStudent(student);
            student.addSubject(subject);

        } catch (EntityDoesNotExistsException | SubjectNotInCourseException | StudentEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void unrollStudent(String username, int subjectCode)
            throws EntityDoesNotExistsException, StudentNotEnrolledException {
        try {
            Subject subject = em.find(Subject.class, subjectCode);
            if (subject == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }

            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            if (!subject.getStudents().contains(student)) {
                throw new StudentNotEnrolledException();
            }

            subject.removeStudent(student);
            student.removeSubject(subject);

        } catch (EntityDoesNotExistsException | StudentNotEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Collection<StudentDTO> getEnrolledStudents(int subjectCode) throws EntityDoesNotExistsException {
        try {
            Subject subject = em.find(Subject.class, subjectCode);

            if (subject == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }

            return toDTOs(subject.getStudents(), StudentDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Collection<StudentDTO> getUnrolledStudents(int subjectCode) throws EntityDoesNotExistsException {
        try {
            Subject subject = em.find(Subject.class, subjectCode);
            if (subject == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }
            List<Student> students = (List<Student>) em.createNamedQuery("getAllCourseStudents")
                    .setParameter("courseCode", subject.getCourse().getCode())
                    .getResultList();

            List<Student> enrolled = em.find(Subject.class, subjectCode).getStudents();

            students.removeAll(enrolled);

            return toDTOs(students, StudentDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void sendEmailToStudent(String username) throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            emailBean.send(
                    student.getEmail(),
                    "Subject",
                    "Hello " + student.getName());
        
        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }

}
