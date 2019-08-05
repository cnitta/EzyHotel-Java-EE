/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.ListMenuItemSessionLocal;
import hms.hotelstay.session.RoomServiceOrderSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.ListMenuItemEntity;
import util.entity.PictureEntity;
import util.entity.RoomEntity;
import util.entity.RoomServiceOrderEntity;
import util.enumeration.RoomServiceOrderStatusEnum;
import util.exception.RoomServiceOrderNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("roomserviceorders")
public class RoomServiceOrdersResource {
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    ListMenuItemSessionLocal listMenuItemSessionLocal = lookupListMenuItemSessionLocal();
    
    RoomSessionLocal roomSessionLocal = lookupRoomSessionLocal();        

    RoomServiceOrderSessionLocal roomServiceOrderSessionLocal = lookupRoomServiceOrderSessionLocal();
    
    
    
    @javax.ws.rs.core.Context
    private ServletContext context;    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoomServiceOrder(RoomServiceOrderEntity r) {
    //to create and persist the listMenuItems
    List<ListMenuItemEntity> listMenuItems = r.getListMenuItems();
    
        for (ListMenuItemEntity listMenuItem : listMenuItems) {
            listMenuItemSessionLocal.createListMenuItem(listMenuItem);
        }
        
        Long roomId = r.getRoomEntity().getRoomId();
        System.out.println("RoomId " + roomId);
        
    //to retrieve RoomEntity
        RoomEntity room = roomSessionLocal.retrieveEntityById(roomId);
        
        r.setRoomEntity(room);
        r.setOrderDatePlaced(new Date());
    //assign to the roomServiceOrder       
        System.out.println("Create RoomServiceOrderEntity at RoomServiceOrderEntityResource");

        RoomServiceOrderEntity roomServiceOrder = roomServiceOrderSessionLocal.createRoomServiceOrder(r);

        return Response.status(Status.OK).entity(roomServiceOrder).build();
        
    } //end createRoomServiceOrderEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRoomServiceOrdersByAttributes(@QueryParam("totalPrice") Double totalPrice,
            @QueryParam("roomNumber") Integer roomNumber,
            @QueryParam("status") RoomServiceOrderStatusEnum status,
            @QueryParam("comments") String comments){             
        
            if(roomNumber != null)
            {
                RoomEntity room = new RoomEntity();
                room.setRoomUnitNumber(roomNumber);

                List<RoomServiceOrderEntity> results = roomServiceOrderSessionLocal.retrieveRoomServiceOrderByRoomAttributes(room);

                GenericEntity<List<RoomServiceOrderEntity>> entity = new GenericEntity<List<RoomServiceOrderEntity>>(results) {
                };

                return Response.status(200).entity(entity).build();
            }
        
            RoomServiceOrderEntity menuItem = new RoomServiceOrderEntity(totalPrice, status, comments, null, null);

            List<RoomServiceOrderEntity> results = roomServiceOrderSessionLocal.retrieveRoomServiceOrderByRoomServiceOrderAttributes(menuItem);

            for (RoomServiceOrderEntity result : results) {
                result.getRoomEntity().setRoomType(null);

                for (ListMenuItemEntity lm : result.getListMenuItems()) {
                    try
                    {
                        PictureEntity picture = lm.getMenuItem().getPicture();
                        String image = getEncodedImageString(picture.getPictureFilePath());
                        picture.setImage(image);
                    }
                    catch(IOException ex)
                    {
                        System.out.println("Error received " + ex.getMessage());
                        JsonObject exception = Json.createObjectBuilder()
                            .add("error", "MerchandiseEntity Not Found")
                            .build();

                    return Response.status(404).entity(exception)
                            .type(MediaType.APPLICATION_JSON).build();
                    }

                }
            }
            System.out.println("***Restful retrieveAllRoomServiceOrders Ended***");            
            

            GenericEntity<List<RoomServiceOrderEntity>> entity = new GenericEntity<List<RoomServiceOrderEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchRoomServiceOrderEntitys           
        
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllRoomServiceOrderEntity() {
        System.out.println("***Restful retrieveAllRoomServiceOrders Started***");
        List<RoomServiceOrderEntity> results = roomServiceOrderSessionLocal.retrieveAllRoomServiceOrders(); 
        
        for (RoomServiceOrderEntity result : results) {
            result.getRoomEntity().setRoomType(null);
            
            for (ListMenuItemEntity lm : result.getListMenuItems()) {
                try
                {
                    PictureEntity picture = lm.getMenuItem().getPicture();
                    String image = getEncodedImageString(picture.getPictureFilePath());
                    picture.setImage(image);
                }
                catch(IOException ex)
                {
                    System.out.println("Error received " + ex.getMessage());
                    JsonObject exception = Json.createObjectBuilder()
                        .add("error", "MerchandiseEntity Not Found")
                        .build();

                return Response.status(404).entity(exception)
                        .type(MediaType.APPLICATION_JSON).build();
                }
            
            }
        }
        System.out.println("***Restful retrieveAllRoomServiceOrders Ended***");

        GenericEntity<List<RoomServiceOrderEntity>> entity = new GenericEntity<List<RoomServiceOrderEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomServiceOrderEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get RoomServiceOrderEntity at RoomServiceOrderEntityResource");
            RoomServiceOrderEntity roomServiceOrder = roomServiceOrderSessionLocal.retrieveRoomServiceOrderById(mId);
            
            for (ListMenuItemEntity lm : roomServiceOrder.getListMenuItems()) {
                PictureEntity picture = lm.getMenuItem().getPicture();
                String image = getEncodedImageString(picture.getPictureFilePath());
                picture.setImage(image);
            }

            
            System.out.println("RoomServiceOrderEntity e with id" + mId);          

            return Response.status(Status.OK).entity(roomServiceOrder).build();

        } catch (RoomServiceOrderNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "RoomServiceOrderEntity Not Found")
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
    } //end getRoomServiceOrderEntity
    
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
        
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");
        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editRoomServiceOrder(@PathParam("id") Long rId, RoomServiceOrderEntity r) {
        System.out.println(r.toString());
        if(r.getOrderStatus() != RoomServiceOrderStatusEnum.PENDING)
        {
            r.setOrderDateCompleteOrCancelled(new Date());
        }
            roomServiceOrderSessionLocal.updateRoomServiceOrder(r);
            
            return Response.status(Status.OK).build();
    } //end editMerchandiseEntity     

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoomServiceOrder(@PathParam("id") Long mId) {
        try {
            System.out.println("Delete RoomServiceOrderEntity is triggered at menuItemResources");
            roomServiceOrderSessionLocal.deleteRoomServiceOrder(mId);
            return Response.status(Status.OK).build();
        } 
        catch (RoomServiceOrderNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "RoomServiceOrderEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteRoomServiceOrderEntity       

    private RoomServiceOrderSessionLocal lookupRoomServiceOrderSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomServiceOrderSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomServiceOrderSession!hms.hotelstay.session.RoomServiceOrderSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomSessionLocal lookupRoomSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomSession!hms.hpm.session.RoomSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ListMenuItemSessionLocal lookupListMenuItemSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ListMenuItemSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/ListMenuItemSession!hms.hotelstay.session.ListMenuItemSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
