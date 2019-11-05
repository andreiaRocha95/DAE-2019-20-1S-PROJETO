package ws;

import dtos.AthleteDTO;
import ejbs.AthleteBean;
import entities.Athlete;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/athletes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class AthleteController {
    @EJB
    private AthleteBean athleteBean;

    AthleteDTO toDTO(Athlete athlete) {
        return new AthleteDTO(
                athlete.getUserId(),
                athlete.getName(),
                athlete.getPassword(),
                athlete.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<AthleteDTO> toDTOs(List<Athlete> athletes) {
        return athletes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{userId}")
    public Response getAthleteDetails(@PathParam("userId") int userId) {
        String msg;
        try {
            Athlete athlete = athleteBean.findAthlete(userId);
            if (athlete != null) {
                return Response.status(Response.Status.OK)
                        .entity(toDTO(athlete))
                        .build();
            }
            msg = "ERROR_FINDING_ATHLETE";
            System.err.println(msg);
        } catch (Exception e) {
            msg = "ERROR_FETCHING_ATHLETE_DETAILS --->" + e.getMessage();
            System.err.println(msg);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(msg)
                .build();
    }


    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/coaches/”
    public List<AthleteDTO> all() {
        try {
            return toDTOs(athleteBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ATHLETES", e);
        }
    }
}
