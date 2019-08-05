/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.RoomTypeSessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.RoomTypeEntity;
import javax.ws.rs.core.Response.Status;

/**
 * REST Web Service
 *
 * @author nittayawancharoenkharungrueang
 */
@Path("room-types")
public class TypesRoomResource {

    RoomTypeSessionLocal roomTypeSessionLocal = lookupRoomTypeSessionLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TypesRoomResource
     */
    public TypesRoomResource() {
    }

    private RoomTypeSessionLocal lookupRoomTypeSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomTypeSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomTypeSession!hms.hpm.session.RoomTypeSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoomTypeEntity> retrieveAllRoomTypes() {
        return roomTypeSessionLocal.retrieveAllEntities();

    } //end retrieve all rooms

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomType(@PathParam("id") Long rTId) {
        try {
            RoomTypeEntity rT = roomTypeSessionLocal.retrieveEntityById(rTId);
              System.out.println("RoomType with id: " + rTId + " and its Code is: " + rT.getRoomTypecode());     
               return Response.status(Status.OK).entity(rT).build();
            //return Response.status(200).entity(rT).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RoomTypeEntity createRoomType(RoomTypeEntity rT) {
         System.out.println("***Restful Create Room Type  Started***");
        roomTypeSessionLocal.createEntity(rT);
        return rT;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoomType(@PathParam("id") Long rTId, RoomTypeEntity rT) {
        rT.setRoomTypeId(rTId);
        try {
            roomTypeSessionLocal.updateEntity(rT);
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
    public Response deleteRoomType(@PathParam("id") Long rTId) {
        try {
            roomTypeSessionLocal.deleteEntity(rTId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "RoomType not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

}
