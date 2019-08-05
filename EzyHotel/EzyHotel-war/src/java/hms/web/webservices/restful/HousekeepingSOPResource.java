/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.housekeeping.session.HousekeepingSOPSessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.HousekeepingSOPEntity;

/**
 * REST Web Service
 *
 * @author bryantan
 */
@Path("housekeepingSOP")
public class HousekeepingSOPResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HousekeepingSOPResource
     */
    public HousekeepingSOPResource() {
    }

    /**
     * Retrieves representation of an instance of hms.web.webservices.restful.HousekeepingSOPResource
     * @return an instance of java.lang.String
     */
    
    @EJB
    private HousekeepingSOPSessionLocal housekeepingSOPSessionLocal;
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSOPs() {
        
        List<HousekeepingSOPEntity> sops = housekeepingSOPSessionLocal.retrieveAllEntities();
        
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        for (int i = 0; i < sops.size(); i++) {
             builder.add(Json.createObjectBuilder()
            .add("housekeepingSOPId", sops.get(i).getHousekeepingSopId())
            .add("estimatedCleaningTime", sops.get(i).getEstimatedCleaningTime().toString())
            .add("name", sops.get(i).getRoomType().getName())
            .add("roomTypecode",sops.get(i).getRoomType().getRoomTypecode())
            .add("roomType", Json.createObjectBuilder()
                     .add("roomTypeId", sops.get(i).getRoomType().getRoomTypeId()))
                     );
        }
        
        JsonArray array = builder.build();
  
        return Response.status(Status.OK).entity(array).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editSOP(@PathParam("id") Long hId, HousekeepingSOPEntity h) {
        h.setHousekeepingSopId(hId);
        try {
            housekeepingSOPSessionLocal.updateEntity(h);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSOP(@PathParam("id") Long hId) {
        try {
            housekeepingSOPSessionLocal.deleteEntity(hId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public HousekeepingSOPEntity createSOP(JsonObject request) {
        
        String time = request.getString("estimatedCleaningTime");
        String roomTypeName = request.getString("name");
        HousekeepingSOPEntity h = new HousekeepingSOPEntity();
        h.setEstimatedCleaningTime(java.sql.Time.valueOf(time));
        
        housekeepingSOPSessionLocal.createEntityRT(h, roomTypeName);
        return h;
    } 

}
