/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template rile, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.RoomSessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.RoomEntity;
import util.enumeration.RoomStatusEnum;

/**
 * REST Web Service
 *
 * @author nittayawancharoenkharungrueang
 */
@Path("rooms")
public class RoomsResource {

    @EJB
    private RoomSessionLocal roomSessionLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRooms() {

        List<RoomEntity> rooms = roomSessionLocal.retrieveAllEntities();
        for (RoomEntity rm : rooms) {
            System.out.println("Each Rooms: " + rm);
            rm.getRoomType().setHotel(null);
            rm.getRoomType().setRooms(null);
        }

        GenericEntity<List<RoomEntity>> entity = new GenericEntity<List<RoomEntity>>(rooms) {
        };

        System.out.println("Size of list = " + rooms.size());
        return Response.status(200).entity(entity).build();
    } //end retrieve all rooms

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("id") Long rId) {
        try {
            RoomEntity r = roomSessionLocal.retrieveEntityById(rId);
            r.getRoomType().setRooms(null);
            return Response.status(200).entity(r).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Room Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomsByHotelId(@QueryParam("hotelId") Long hId) {
        System.out.println("### Im at get room by hotel Id: " + hId);
        try {
            List<RoomEntity> rms = roomSessionLocal.retrieveAllRoomsByHotelId(hId);

            //TODO: Check if the RESTful display what's required
            for (RoomEntity rm : rms) {
                System.out.println("Each Rooms: " + rm);
                rm.getRoomType().setHotel(null);
                rm.getRoomType().setRooms(null);
            }

            JsonArrayBuilder builder = Json.createArrayBuilder();

            for (int i = 0; i < rms.size(); i++) {
                builder.add(Json.createObjectBuilder()
                        .add("roomId", rms.get(i).getRoomId())
                        .add("roomUnitNumber", rms.get(i).getRoomUnitNumber())
                        .add("roomStatus", rms.get(i).getStatus().toString())
                        .add("roomTypeCode", rms.get(i).getRoomType().getRoomTypecode())
                        .add("name", rms.get(i).getRoomType().getName())
                );
            }

            JsonArray array = builder.build();

            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Rooms Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public RoomEntity createRoom(RoomEntity r) {
//        
//        roomSessionLocal.createEntity(r);
//        return r;
//    }
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(@PathParam("id") Long hId, RoomEntity r) {

        boolean isExist = false;
        try {

            List<RoomEntity> rms = roomSessionLocal.retrieveAllRoomsByHotelId(hId);

            for (RoomEntity rm : rms) {
                if (rm.getRoomUnitNumber().equals(r.getRoomUnitNumber())) {
                    isExist = true;
                }
            }

            if (isExist) {
                throw new Exception("Error");
            } else {
                roomSessionLocal.createEntity(r);
            }

            return Response.status(204).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Cannot create room!")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoom(@PathParam("id") Long rId, RoomEntity r) {
        r.setRoomId(rId);
        try {
            roomSessionLocal.updateEntity(r);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Room Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") Long rId) {
        try {
            roomSessionLocal.deleteEntity(rId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Room not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }
}
