package ws;

import dtos.ClubDTO;
import ejbs.ClubBean;
import entities.Club;
import exceptions.MyEntityExistsException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/courses") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class ClubController {

    @EJB
    private ClubBean clubBean;


    // Converts an entity Student to a DTO Student class
    ClubDTO toDTO(Club club) {
        return new ClubDTO(
                club.getClubCode(),
                club.getName()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<ClubDTO> toDTOs(List<Club> clubs) {
        return clubs.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/")
    public Response createNewClub(ClubDTO clubDTO)
            throws MyEntityExistsException {
        clubBean.create(clubDTO.getClubCode(),
                clubDTO.getName());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<ClubDTO> all() {
        return toDTOs(clubBean.all());
    }
}


