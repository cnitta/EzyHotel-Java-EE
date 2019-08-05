/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.MerchandiseOrderSessionLocal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import util.entity.MerchandiseOrderEntity;
import util.entity.PictureEntity;
import util.entity.RoomEntity;
import util.enumeration.MerchandiseOrderStatusEnum;
import util.exception.MerchandiseOrderNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("merchandiseOrders")
public class MerchandiseOrdersResource {
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    MerchandiseOrderSessionLocal merchandiseOrderSessionLocal = lookupMerchandiseOrderSessionLocal();
    
    

    @javax.ws.rs.core.Context
    private ServletContext context;   
        
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMerchandiseOrder(MerchandiseOrderEntity m) {

            System.out.println("Create MerchandiseOrderEntity at MerchandiseOrderEntityResource");
            
            MerchandiseOrderEntity merchandiseOrder = merchandiseOrderSessionLocal.createMerchandiseOrder(m);
            merchandiseOrder.getRoom().getRoomType().setRooms(null);
            PictureEntity picture = merchandiseOrder.getRoom().getRoomType().getPicture();
            
            try{
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
            return Response.status(Status.OK).entity(merchandiseOrder).build();
        
    } //end createMerchandiseOrderEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMerchandiseOrdersByAttributes(@QueryParam("quantityRedeemed") Integer quantityRedeemed,
            @QueryParam("status") MerchandiseOrderStatusEnum status,
            @QueryParam("roomNumber") Integer roomNumber){             
            
            if(roomNumber != null)
            {
                RoomEntity roomEntity =  new RoomEntity();
                roomEntity.setRoomUnitNumber(roomNumber);
                
                List<MerchandiseOrderEntity> results = merchandiseOrderSessionLocal.retrieveMerchandiseOrderByRoomAttributes(roomEntity);
                for (MerchandiseOrderEntity result : results) {
                    result.getRoom().getRoomType().setRooms(null);
                    PictureEntity picture = result.getRoom().getRoomType().getPicture();

                    try{
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
                    
                    PictureEntity mPicture = result.getMerchandises().get(0).getPicture();
                            try{
                            String image = getEncodedImageString(mPicture.getPictureFilePath());
                            mPicture.setImage(image);        

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
                GenericEntity<List<MerchandiseOrderEntity>> entity = new GenericEntity<List<MerchandiseOrderEntity>>(results) {
                };
                
                return Response.status(200).entity(entity).build();
            }
                        
            MerchandiseOrderEntity merchandiseOrder = new MerchandiseOrderEntity(null, quantityRedeemed, status, null);
            merchandiseOrder.setStatus(status);
         
            List<MerchandiseOrderEntity> results = merchandiseOrderSessionLocal.retrieveMerchandiseOrderByMerchandiseOrderAttributes(merchandiseOrder);
                for (MerchandiseOrderEntity result : results) {
                    result.getRoom().getRoomType().setRooms(null);
                    PictureEntity picture = result.getRoom().getRoomType().getPicture();

                    try{
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
                    
                    if(result.getMerchandises().size() > 0)
                    {
                        PictureEntity mPicture = result.getMerchandises().get(0).getPicture();
                                try{
                                String image = getEncodedImageString(mPicture.getPictureFilePath());
                                mPicture.setImage(image);        

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
            GenericEntity<List<MerchandiseOrderEntity>> entity = new GenericEntity<List<MerchandiseOrderEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchMerchandiseOrderEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllMerchandiseOrders() {
        System.out.println("***Restful retrieveAllMerchandises Started***");
        List<MerchandiseOrderEntity> results = merchandiseOrderSessionLocal.retrieveAllMerchandiseOrders(); 
        System.out.println("***Restful retrieveAllMerchandises Ended***");
        for (MerchandiseOrderEntity result : results) {
            result.getRoom().getRoomType().setRooms(null);
            PictureEntity picture = result.getRoom().getRoomType().getPicture();
            
            try{
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
        
        GenericEntity<List<MerchandiseOrderEntity>> entity = new GenericEntity<List<MerchandiseOrderEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchandiseOrderEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get MerchandiseOrderEntity at MerchandiseOrderEntityResource");
            MerchandiseOrderEntity merchandiseOrder = merchandiseOrderSessionLocal.retrieveMerchandiseOrderById(mId);
            
            merchandiseOrder.getRoom().getRoomType().setRooms(null);
            PictureEntity picture = merchandiseOrder.getRoom().getRoomType().getPicture();
            
            try{
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
            

            System.out.println("MerchandiseOrderEntity e with id" + mId);          

            return Response.status(Status.OK).entity(merchandiseOrder).build();

        } catch (MerchandiseOrderNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandiseOrderEntity Not Found")
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
    } //end getMerchandiseOrderEntity

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editMerchandiseOrder(@PathParam("id") Long mId, MerchandiseOrderEntity m) {        

            merchandiseOrderSessionLocal.updateMerchandiseOrder(m);
            return Response.status(Status.OK).build();
    } //end editMerchandiseOrderEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMerchandiseOrder(@PathParam("id") Long mId) {
        try {
            System.out.println("Delete MerchandiseOrderEntity is triggered at merchandiseOrderResources");
            merchandiseOrderSessionLocal.deleteMerchandiseOrder(mId);
            return Response.status(Status.OK).build();
        } 
        catch (MerchandiseOrderNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandiseOrderEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteMerchandiseOrderEntity      
    
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

    private MerchandiseOrderSessionLocal lookupMerchandiseOrderSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MerchandiseOrderSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MerchandiseOrderSession!hms.hotelstay.session.MerchandiseOrderSessionLocal");
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
