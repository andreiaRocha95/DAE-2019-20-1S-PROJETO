package ws;

import dtos.AdministradorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administradores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AdministratorController {
    @EJB
    private AdministradorBean adminBean;

    AdministradorDTO toDTO(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getUserId(),
                administrador.getNome(),
                administrador.getPassword(),
                administrador.getEmail()
        );
    }
    // converts an entire list of entities into a list of DTOs
    List<AdministradorDTO> toDTOs(List<Administrador> administradores) {
        return administradores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<AdministradorDTO> all() {
        try {
            return toDTOs(adminBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRADORES", e);
        }
    }
}
