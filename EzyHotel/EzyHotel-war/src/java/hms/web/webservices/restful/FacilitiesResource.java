/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.FacilitySessionLocal;
import hms.hpm.session.HotelSessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.FacilityEntity;
import util.entity.HotelEntity;
import util.enumeration.FacilityStatusEnum;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("facilities")

public class FacilitiesResource {

    FacilitySessionLocal facilitySession = lookupFacilitySessionLocal();

    @EJB
    private FacilitySessionLocal facilitySessionLocal;

    @EJB
    private HotelSessionLocal hotelSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FacilityResource
     */
    public FacilitiesResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllFacilites() {

        List<FacilityEntity> facilities = facilitySessionLocal.retrieveAllEntities();

        for (FacilityEntity f : facilities) {
            System.out.println(f);
            f.setHotel(null);
        }
        GenericEntity<List<FacilityEntity>> entity = new GenericEntity<List<FacilityEntity>>(facilities) {
        };

        return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFacility(@PathParam("id") Long fId) {
        try {
            FacilityEntity f = facilitySessionLocal.retrieveEntityById(fId);
            f.setHotel(null);
            return Response.status(200).entity(f).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFacilitiesByHotelId(@QueryParam("hotelId") Long hId) {
        System.out.println("### Im at search facilities by hotel Id: " + hId);
        try {
            List<FacilityEntity> facilities = facilitySessionLocal.retrieveFacilitiesByHotelId(hId);

            System.out.println("### facilities:");
            System.out.println(facilities.toString());

            for (FacilityEntity f : facilities) {
                System.out.println(f);
            }

            GenericEntity<List<FacilityEntity>> fac = new GenericEntity<List<FacilityEntity>>(facilities) {
            };

            return Response.status(200).entity(fac).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Facilities Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FacilityEntity createFacility(FacilityEntity f) {
        facilitySessionLocal.createEntity(f);
        return f;
    }

    @POST
    @Path("/hotel/{hotelId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFacilityByHotelId(@PathParam("hotelId") Long hId, FacilityEntity f) {
        try {
            HotelEntity hotel = hotelSessionLocal.retrieveEntityById(hId);

            FacilityEntity facility = facilitySessionLocal.createEntity(f);

            facilitySessionLocal.addHotel(facility.getFacilityId(), hotel);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

//    @PUT
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateFacility(@PathParam("id") Long fId, FacilityEntity f) {
//        f.setFacilityId(fId);
//        try {
//            facilitySessionLocal.updateEntity(f);
//            return Response.status(204).build();
//        } catch (NoResultException e) {
//            JsonObject exception = Json.createObjectBuilder()
//                    .add("error", "Not found")
//                    .build();
//
//            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
//        }
//    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFacility(@PathParam("id") Long fId, JsonObject json) {

        try {

            FacilityEntity newFac = new FacilityEntity();

            String name = json.getString("name");
            String description = json.getString("description");
            String facilityType = json.getString("facilityType");
            String capacity = json.getString("capacity");
            double area = json.getJsonNumber("area").doubleValue();
            String facFeature = json.getString("facFeature");
            FacilityStatusEnum facStatus = FacilityStatusEnum.valueOf(json.getString("facStatus"));
            Long hotelId = json.getJsonNumber("hotelId").longValue();

            HotelEntity hotel = hotelSessionLocal.retrieveEntityById(hotelId);

            newFac.setFacilityId(fId);
            newFac.setName(name);
            newFac.setDescription(description);
            newFac.setFacilityType(facilityType);
            newFac.setCapacity(capacity);
            newFac.setArea(area);
            newFac.setFacFeature(facFeature);
            newFac.setFacStatus(facStatus);
            newFac.setHotel(hotel);
            
            facilitySessionLocal.updateEntity(newFac);
            
            return Response.status(204).build();

        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFacility(@PathParam("id") Long fId) {
        try {
            facilitySessionLocal.deleteEntity(fId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Facility not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    private FacilitySessionLocal lookupFacilitySessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (FacilitySessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/FacilitySession!hms.hpm.session.FacilitySessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
