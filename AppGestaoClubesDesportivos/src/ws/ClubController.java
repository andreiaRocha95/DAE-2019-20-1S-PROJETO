package ws;

import dtos.CourseDTO;
import dtos.StudentDTO;
import ejbs.CourseBean;
import ejbs.StudentBean;
import entities.Course;
import entities.Student;
import exceptions.MyEntityExistsException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/courses") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class CourseController {

    @EJB
    private CourseBean courseBean;


    // Converts an entity Student to a DTO Student class
    CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getCode(),
                course.getName()
        );
    }
    // converts an entire list of entities into a list of DTOs
    List<CourseDTO> toDTOs(List<Course> courses) {
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewStudent (CourseDTO courseDTO)
    throws MyEntityExistsException {
            courseBean.create(courseDTO.getCode(),
                    courseDTO.getName());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<CourseDTO> all() {
            return toDTOs(courseBean.all());
    }
}


