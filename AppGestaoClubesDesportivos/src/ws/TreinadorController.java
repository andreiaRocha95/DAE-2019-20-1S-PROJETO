package ws;

import dtos.StudentDTO;
import ejbs.CourseBean;
import ejbs.StudentBean;
import entities.Student;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class StudentController {
    @EJB
    private StudentBean studentBean;


    // Converts an entity Student to a DTO Student class
    StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getCode(),
                student.getCourse().getName()
        );
    }
    // converts an entire list of entities into a list of DTOs
    List<StudentDTO> toDTOs(List<Student> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewStudent (StudentDTO studentDTO){
        try{
            studentBean.create(studentDTO.getUsername(),
                    studentDTO.getPassword(),
                    studentDTO.getName(),
                    studentDTO.getEmail(),
                    studentDTO.getCourseCode());
            Student newStudent = studentBean.findStudent(studentDTO.getUsername());
            if(newStudent!=null)
                return Response.status(Response.Status.CREATED)
                        .entity(toDTO(newStudent))
                        .build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e) {
            throw new EJBException("ERROR_NEW_STUDENT", e);
        }
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<StudentDTO> all() {
        try {
            return toDTOs(studentBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STUDENTS", e);
        }
    }
}
