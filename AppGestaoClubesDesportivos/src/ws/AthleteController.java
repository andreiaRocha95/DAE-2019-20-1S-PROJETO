package ws;

import dtos.CoachDTO;
import ejbs.CoachBean;
import entities.Coach;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/coaches")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class CoachController {
    @EJB
    private CoachBean coachBean;

    CoachDTO toDTO(Coach coach) {
        return new CoachDTO(
                coach.getUserId(),
                coach.getName(),
                coach.getPassword(),
                coach.getEmail()
        );
    }
    // converts an entire list of entities into a list of DTOs
    List<CoachDTO> toDTOs(List<Coach> coaches) {
        return coaches.stream().map(this::toDTO).collect(Collectors.toList());
    }
    @GET
    @Path("{userId}")
    public Response getCoachDetails(@PathParam("userId") int userId)
    {
        String msg;
        try {
            Coach coach = coachBean.findCoach(userId);
            if (coach != null) {
                return Response.status(Response.Status.OK)
                        .entity(toDTO(coach))
                        .build();
            }
            msg = "ERROR_FINDING_COACH";
            System.err.println(msg);
        } catch (Exception e) {
            msg = "ERROR_FETCHING_COACH_DETAILS --->" + e.getMessage();
            System.err.println(msg);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(msg)
                .build();
    }


    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/coaches/”
    public List<CoachDTO> all() {
        try {
            return toDTOs(coachBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_COACHES", e);
        }
    }
}
