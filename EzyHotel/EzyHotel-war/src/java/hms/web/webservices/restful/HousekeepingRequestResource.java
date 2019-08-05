/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.RoomServiceOrderSessionLocal;
import hms.housekeeping.session.HousekeepingRequestSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.hr.session.StaffSessionLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
import javax.ws.rs.core.Response.Status;
import util.entity.HousekeepingRequestEntity;
import util.entity.PictureEntity;
import util.entity.RoomServiceOrderEntity;
import util.enumeration.RoomServiceOrderStatusEnum;

/**
 * REST Web Service
 *
 * @author bryantan
 */
@Path("housekeepingRequest")
public class HousekeepingRequestResource {
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();

    @javax.ws.rs.core.Context
    private ServletContext context; 
    
    @EJB
    private HousekeepingRequestSessionLocal housekeepingRequestSessionLocal;
    
    @EJB
    private RoomServiceOrderSessionLocal roomServiceOrderSessionLocal;
    
    @EJB
    private RoomSessionLocal roomSessionLocal;
    
    @EJB
    private StaffSessionLocal staffSessionLocal;
    /**
     * Creates a new instance of HousekeepingRequestResource
     */
    public HousekeepingRequestResource() {
    }

    
    @GET
    @Produces("application/json")
    public Response getAllRequests() {
        List<HousekeepingRequestEntity> list = housekeepingRequestSessionLocal.retrieveAllEntities();
        //        return list; 
        
        for (HousekeepingRequestEntity h : list) {
            h.getRoom().getRoomType().setRooms(null);
        }         
        
        GenericEntity<List<HousekeepingRequestEntity>> entity = new GenericEntity<List<HousekeepingRequestEntity>>(list) {
        };        
        
        return Response.status(Status.OK).entity(entity).build();       
    }
    
    @GET
    @Path("/roomservicedelivery")
     public Response getAllRoomServiceDelivery() {
          List<HousekeepingRequestEntity> list = housekeepingRequestSessionLocal.retrieveAllEntities();
          List<HousekeepingRequestEntity> list1 = new ArrayList<>();
          for (HousekeepingRequestEntity h : list) {
              if ("Room Service".equals(h.getRequestType()) && "In Progress".equals(h.getStatus())) {
                  h.getRoom().getRoomType().setRooms(null);
                  list1.add(h);
              }
          }
           GenericEntity<List<HousekeepingRequestEntity>> entity = new GenericEntity<List<HousekeepingRequestEntity>>(list1) {
        };
           return Response.status(Status.OK).entity(entity).build();     
     }
    
    @GET
    @Path("/query")
    @Produces("application/json")
    public Response getAllRequestsByRoomNumber(@QueryParam("roomNumber") String roomNumber) {

        List<HousekeepingRequestEntity> list = housekeepingRequestSessionLocal.retrieveAllEntitiesByRoomUnitNumber(roomNumber);
        
        for (HousekeepingRequestEntity h : list) {
            h.getRoom().getRoomType().setRooms(null);
//            h.getRoom().getRoomType().setPicture(null);
        
            PictureEntity picture = h.getRoom().getRoomType().getPicture();
        
            try{
              String image = getEncodedImageString(picture.getPictureFilePath());
              picture.setImage(image);
            }
            catch(IOException ex)
            {
                System.out.println(ex.getMessage());
            } 
                h.setStaff(null);
            }
        
        GenericEntity<List<HousekeepingRequestEntity>> entity = new GenericEntity<List<HousekeepingRequestEntity>>(list) {
        };        
        
        return Response.status(Status.OK).entity(entity).build();        
    }    
    
    @GET
    @Path("/staff/{id}")
    @Produces("application/json")
    public Response getStaffRequests(@PathParam("id") Long hId) {
        List<HousekeepingRequestEntity> requests = housekeepingRequestSessionLocal.retrieveStaffOngoingRequests(hId);
        
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (int i =0; i < requests.size(); i++) {
            System.out.println(requests.get(i).getRequestType());
            if ("Room Service".equals(requests.get(i).getRequestType())) {
                
             builder.add(Json.createObjectBuilder()
                     .add("requestId", requests.get(i).getRequestId())
                     .add("status", requests.get(i).getStatus())
                     .add("message", requests.get(i).getMessage())
                     .add("dateCreated", requests.get(i).getDateCreated().toString())
                     .add("requestType", requests.get(i).getRequestType())
                     .add("roomUnitNumber", requests.get(i).getRoom().getRoomUnitNumber())
                     .add("roomServiceDelivery", requests.get(i).getRoomServiceDelivery())
             );
            } else {
                builder.add(Json.createObjectBuilder()
                     .add("requestId", requests.get(i).getRequestId())
                     .add("status", requests.get(i).getStatus())
                     .add("message", requests.get(i).getMessage())
                     .add("dateCreated", requests.get(i).getDateCreated().toString())
                     .add("requestType", requests.get(i).getRequestType())
                     .add("roomUnitNumber", requests.get(i).getRoom().getRoomUnitNumber())
             );
            }
        }
        JsonArray array = builder.build();
        return Response.status(Status.OK).entity(array).build();
    }
    
//    @GET
//    @Produces("application/json")
//    public Response getAllRequests() {
//        List<HousekeepingRequestEntity> requests = housekeepingRequestSessionLocal.retrieveAllEntities();
//        
//        JsonArrayBuilder builder = Json.createArrayBuilder();
//        
//        for (int i = 0; i < requests.size(); i++) {
//            builder.add(Json.createObjectBuilder()
//                    .add("requestId", requests.get(i).getRequestId())
//                    .add("dateCreated", requests.get(i).getDateCreated().toString())
//                    .add("requestType", requests.get(i).getRequestType())
//                    .add("message", requests.get(i).getMessage())
//                    .add("roomUnitNumber", requests.get(i).getRoom().getRoomUnitNumber())
//                    .add("status", requests.get(i).getStatus())
//                    .add("staff", requests.get(i).getStaff().getName())
//            );
//        }
//        JsonArray array = builder.build();
//        return Response.status(Status.OK).entity(array).build();
//    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editRequest(@PathParam("id") Long hId, HousekeepingRequestEntity h) {
        h.setRequestId(hId);
        try {
            housekeepingRequestSessionLocal.updateEntity(h);
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
    public Response deleteRequest(@PathParam("id") Long hId) {
        try {
            housekeepingRequestSessionLocal.deleteEntity(hId);
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
    public HousekeepingRequestEntity createRequest(JsonObject request) {
        
        String roomNumber = request.getString("roomNumber");
        String staffName = request.getString("staff");
        String requestType = request.getString("requestType");
        String message = request.getString("message");
        
        HousekeepingRequestEntity h = new HousekeepingRequestEntity();
        return housekeepingRequestSessionLocal.createRequest(h, roomNumber, staffName, requestType, message);
    }
    
    @POST
    @Path("/roomservice/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoomServiceDelivery(@PathParam("id") Long rId) {
        try {
        RoomServiceOrderEntity roomService = roomServiceOrderSessionLocal.retrieveRoomServiceOrderById(rId);
        HousekeepingRequestEntity request = new HousekeepingRequestEntity();
        request.setRoomServiceDelivery(rId);
        request.setRequestType("Room Service");
        request.setMessage("Deliver room service");
        request.setRoom(roomSessionLocal.retrieveEntityById(roomService.getRoomEntity().getRoomId()));
        
        //set as staff 1 (hkstaff1x)
        long i = 4L;
        request.setStaff(staffSessionLocal.retrieveEntityById(i));
        housekeepingRequestSessionLocal.createEntity(request);
        return Response.status(200).build();
        } catch(Exception e) {
             JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @POST
    @Path("/roomservice/complete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response completeRoomServiceDelivery(@PathParam("id") Long rId) {
        try {
        RoomServiceOrderEntity roomService = roomServiceOrderSessionLocal.retrieveRoomServiceOrderById(rId);
        roomService.setOrderStatus(RoomServiceOrderStatusEnum.FULFILLED);
        roomServiceOrderSessionLocal.updateRoomServiceOrder(roomService);
        return Response.status(200).build();
        } catch(Exception e) {
             JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @POST
    @Path("/customer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRequestCustomer(JsonObject request) {
        
        String roomNumber = request.getString("roomNumber");
        String requestType = request.getString("requestType");
        String message = request.getString("message");
        
        HousekeepingRequestEntity h = new HousekeepingRequestEntity();
        HousekeepingRequestEntity hRequest = housekeepingRequestSessionLocal.createRequest(h, roomNumber, requestType, message);
        hRequest.getRoom().getRoomType().setRooms(null);
        hRequest.getRoom().getRoomType().setPicture(null);
        return Response.status(200).entity(hRequest).build();
    }
    
    private String getEncodedImageString(String filePath) throws IOException
    {
        String UPLOAD_DIRECTORY = getUploadDirectory();
        try{
            String image = pictureSessionLocal.getPictureBase64String(UPLOAD_DIRECTORY + filePath);
//            System.out.println("image: " + image);
            return image;
        }
        
        catch(IOException ex)
        {
            throw new IOException(ex.getMessage());
        }       
    }
    
    //UPLOAD_DIRECTORY + filePath for new file()
    private String getUploadDirectory()
    {
        String substring = "/EzyHotel-J2E/EzyHotel/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
//        System.out.println("Real Path " + context.getRealPath("/"));
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");        
//        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }     

    private PictureSessionLocal lookupPictureSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PictureSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/PictureSession!hms.common.session.PictureSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
