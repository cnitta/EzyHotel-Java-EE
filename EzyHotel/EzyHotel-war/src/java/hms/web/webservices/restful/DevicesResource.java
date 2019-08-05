/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.FindAssociatedItemsSessionLocal;
import hms.hotelstay.session.DeviceSessionLocal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Path;
import util.entity.DeviceEntity;
import util.entity.RoomEntity;
import util.enumeration.DeviceCategoryEnum;
import util.enumeration.DeviceStateEnum;
import util.enumeration.DeviceStatusEnum;
import util.exception.DeviceNotFoundException;
import util.exception.StaffNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("devices")
public class DevicesResource {
    FindAssociatedItemsSessionLocal findAssociatedItemsSessionLocal = lookupFindAssociatedItemsSessionLocal();
    
    DeviceSessionLocal deviceSessionLocal = lookupDeviceSessionLocal();  
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDevice(DeviceEntity d) {

            System.out.println("Create DeviceEntity at DeviceEntityResource");
            
            DeviceEntity device = deviceSessionLocal.createDevice(d);

            return Response.status(Status.OK).entity(device).build();
        
    } //end createDeviceEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchDevicesByAttributes(@QueryParam("serialNumber") String serialNumber,
            @QueryParam("category") DeviceCategoryEnum category,
            @QueryParam("model") String model,
            @QueryParam("manufacturer") String manufacturer,
            @QueryParam("status") DeviceStatusEnum status,
            @QueryParam("date") String dateString,
            @QueryParam("state") DeviceStateEnum state,
            @QueryParam("roomNumber") Integer roomNumber){             

            if(roomNumber != null)
            {
                RoomEntity roomEntity =  new RoomEntity();
                roomEntity.setRoomUnitNumber(roomNumber);
                
                List<DeviceEntity> results = deviceSessionLocal.retrieveDeviceByRoomAttributes(roomEntity);
                
                GenericEntity<List<DeviceEntity>> entity = new GenericEntity<List<DeviceEntity>>(results) {
                };
                
                return Response.status(200).entity(entity).build();
            }
            
            DeviceEntity device = new DeviceEntity(serialNumber, category, model, manufacturer);
            device.setDeviceState(state);
            device.setDeviceStatus(status);
            
            if(dateString != null)
            {
                try {            
                String[] splited = dateString.split("\\s+");

                Date maintenanceDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                device.setLastMaintenanceDate(maintenanceDate);
                }
                catch (ParseException ex) {
                    Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
            
            List<DeviceEntity> results = deviceSessionLocal.retrieveDeviceByDeviceAttributes(device);

            GenericEntity<List<DeviceEntity>> entity = new GenericEntity<List<DeviceEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchDeviceEntitys
            
    

//    @GET
//    @Path("/query")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response searchDevicesByRoomAttributes(@QueryParam("roomId") Long roomId,
//            @QueryParam("roomNumber") Integer roomNumber){
//            
//            RoomEntity room = new RoomEntity();
//            room.setRoomId(roomId);
//            room.setRoomUnitNumber(roomNumber);                       
//            
//            List<DeviceEntity> results = deviceSessionLocal.retrieveDeviceByRoomAttributes(room);
//
//            GenericEntity<List<DeviceEntity>> entity = new GenericEntity<List<DeviceEntity>>(results) {
//            };
//
//            return Response.status(200).entity(entity).build();                  
//            
//    } //end searchDeviceEntitys    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllDeviceEntity() {
        System.out.println("***Restful retrieveAllDevices Started***");
        List<DeviceEntity> results = deviceSessionLocal.retrieveAllDevices(); 
        System.out.println("***Restful sretrieveAllDevices Ended***");
        
        for (DeviceEntity result : results) {
            if(result.getRoom() != null)
            {
                RoomEntity room = result.getRoom();
                room.getRoomType().setRooms(null);
                room.setRoomType(null);
            }
        }

        GenericEntity<List<DeviceEntity>> entity = new GenericEntity<List<DeviceEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeviceEntityById(@PathParam("id") Long dId) {
        try {
            System.out.println("Get DeviceEntity at DeviceEntityResource");
            DeviceEntity device = deviceSessionLocal.retrieveDeviceById(dId);
            
            if(device.getRoom() != null)
            {
                device.getRoom().getRoomType().setRooms(null);
                device.getRoom().setRoomType(null);
            }

            System.out.println("DeviceEntity d with id" + dId + " and s/n " + device.getSerialNumber());          

            return Response.status(Status.OK).entity(device).build();

        } catch (DeviceNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "DeviceEntity Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
            
        } 
        catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getDeviceEntity
    
    @GET
    @Path("/hotel-rooms/staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomsOfHotelByStaffId(@PathParam("id") Long sId) {
        
        try{
        List<RoomEntity> results = findAssociatedItemsSessionLocal.findHotelRoomsByStaffId(sId);

        for (RoomEntity result : results) {
            result.getRoomType().setRooms(null);
            result.setRoomType(null);
        }
        
        GenericEntity<List<RoomEntity>> entity = new GenericEntity<List<RoomEntity>>(results) {
        };
        
        return Response.status(Status.OK).entity(entity).build();
        }
        catch(StaffNotFoundException ex)
        {
                        System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        
    } //end getRoomsOfHotelByStaffId
    
    @GET
    @Path("/hotel-rooms-without-devices/staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomsOfHotelWithoutDeviceByStaffId(@PathParam("id") Long sId) {
        
        try{
        List<RoomEntity> results = findAssociatedItemsSessionLocal.findHotelRoomsByStaffId(sId); 
            
        List<RoomEntity> filters = deviceSessionLocal.retrieveRoomsWithDevice();

        results.removeAll(filters);
        
        for (RoomEntity result : results) {
            result.getRoomType().setRooms(null);
            result.setRoomType(null);
        }
        
        GenericEntity<List<RoomEntity>> entity = new GenericEntity<List<RoomEntity>>(results) {
        };
        
        return Response.status(Status.OK).entity(entity).build();
        }
        catch(StaffNotFoundException ex)
        {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
        
    } //end getRoomsOfHotelByStaffId    
    
    

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editDevice(@PathParam("id") Long dId, DeviceEntity d) {        

//        try {
            deviceSessionLocal.updateDevice(d);
            return Response.status(Status.OK).build();
//        } 
//        catch (Exception e) {
//            JsonObject exception = Json.createObjectBuilder()
//                    .add("error", "DeviceEntity Not Found")
//                    .build();
//
//            return Response.status(404).entity(exception)
//                    .type(MediaType.APPLICATION_JSON).build();
//        }
    } //end editDeviceEntity   
    
        //Not working
    @PUT
    @Path("/{id}/room/{rId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignDeviceWithRoom(@PathParam("id") Long dId, @PathParam("rId")Long rId) {        

        try{
          deviceSessionLocal.assignDeviceWithRoom(dId, rId);  
          return Response.status(Status.ACCEPTED).build();
        }
        catch(Exception ex)
        {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.NOT_FOUND).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
               
    }
    
    @DELETE
    @Path("/{id}/room")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unassignDeviceWithRoom(@PathParam("id") Long dId) {        

        try{
          deviceSessionLocal.unassignDeviceWithRoom(dId);  
          return Response.status(Status.OK).build();
        }
        catch(Exception ex)
        {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.NOT_FOUND).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
               
    }    

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDevice(@PathParam("id") Long dId) {
        try {
            System.out.println("Delete DeviceEntity is triggered at deviceResources");
            deviceSessionLocal.deleteDevice(dId);
            return Response.status(Status.OK).build();
        } 
        catch (DeviceNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "DeviceEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteDeviceEntity  

    private DeviceSessionLocal lookupDeviceSessionLocal() {
        try {
            Context c = new InitialContext();
            return (DeviceSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/DeviceSession!hms.hotelstay.session.DeviceSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private FindAssociatedItemsSessionLocal lookupFindAssociatedItemsSessionLocal() {
        try {
            Context c = new InitialContext();
            return (FindAssociatedItemsSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/FindAssociatedItemsSession!hms.common.session.FindAssociatedItemsSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
    
}
