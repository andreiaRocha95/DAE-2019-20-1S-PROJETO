package ws;

import dtos.AdministratorDTO;
import ejbs.AdministradorBean;
import entities.Administrator;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administrators")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class AdministratorController {
    @EJB
    private AdministradorBean adminBean;

    AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUserId(),
                administrator.getName(),
                administrator.getPassword(),
                administrator.getEmail()
        );
    }
    // converts an entire list of entities into a list of DTOs
    List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/administrators/”
    public List<AdministratorDTO> all() {
        try {
            return toDTOs(adminBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORS", e);
        }
    }

    @GET
    @Path("{userId}")
    public Response getAdministratorDetails(@PathParam("userId") int userId) {
        String msg;
        try {
            Administrator administrator = adminBean.findAdministrador(userId);
            if (administrator != null) {
                return Response.status(Response.Status.OK)
                        .entity(toDTO(administrator))
                        .build();
            }
            msg = "ERROR_FINDING_ADMINISTRATOR";
            System.err.println(msg);
        } catch (Exception e) {
            msg = "ERROR_FETCHING_ADMINISTRATOR_DETAILS --->" + e.getMessage();
            System.err.println(msg);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(msg)
                .build();
    }

}
