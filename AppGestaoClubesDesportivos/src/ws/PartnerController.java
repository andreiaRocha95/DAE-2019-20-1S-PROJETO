package ws;

import dtos.PartnerDTO;
import ejbs.PartnerBean;
import entities.Partner;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/partners")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class PartnerController {
    @EJB
    private PartnerBean partnerBean;

    PartnerDTO toDTO(Partner partner) {
        return new PartnerDTO(
                partner.getUserId(),
                partner.getName(),
                partner.getPassword(),
                partner.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<PartnerDTO> toDTOs(List<Partner> partners) {
        return partners.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{userId}")
    public Response getPartnerDetails(@PathParam("userId") int userId) {
        String msg;
        try {
            Partner partner = partnerBean.findPartner(userId);
            if (partner != null) {
                return Response.status(Response.Status.OK)
                        .entity(toDTO(partner))
                        .build();
            }
            msg = "ERROR_FINDING_PARTNER";
            System.err.println(msg);
        } catch (Exception e) {
            msg = "ERROR_FETCHING_PARTNER_DETAILS --->" + e.getMessage();
            System.err.println(msg);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(msg)
                .build();
    }


    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/coaches/”
    public List<PartnerDTO> all() {
        try {
            return toDTOs(partnerBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PARTNERS", e);
        }
    }
}
