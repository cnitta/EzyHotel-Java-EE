/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.MerchandiseSessionLocal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.MerchandiseEntity;
import util.entity.PictureEntity;
import util.enumeration.MerchandiseStatusEnum;
import util.exception.MerchandiseNotFoundException;



/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("merchandises")
public class MerchandisesResource {
    
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    MerchandiseSessionLocal merchandiseSessionLocal = lookupMerchandiseSessionLocal();

    @javax.ws.rs.core.Context
    private ServletContext context;    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMerchandise(MerchandiseEntity a) {

            System.out.println("Create MerchandiseEntity at MerchandiseEntityResource");
            
            MerchandiseEntity merchandise = merchandiseSessionLocal.createMerchandise(a);

            return Response.status(Status.OK).entity(merchandise).build();
        
    } //end createMerchandiseEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMerchandisesByAttributes(@QueryParam("merchandiseName") String merchandiseName,
            @QueryParam("description") String description,
            @QueryParam("costPoints") Integer costPoints,
            @QueryParam("maxCostPriceLimit") Integer maxCostPriceLimit,
            @QueryParam("quantityOnHand") Integer quantityOnHand,
            @QueryParam("merchandiseStatus") MerchandiseStatusEnum merchandiseStatus,
            @QueryParam("poTriggerLevel") Integer poTriggerLevel,
            @QueryParam("isTriggerOn") Boolean isTriggerOn){             

            
            MerchandiseEntity merchandise = new MerchandiseEntity(merchandiseName, description, costPoints, maxCostPriceLimit, quantityOnHand, poTriggerLevel, isTriggerOn);
            merchandise.setMerchandiseStatus(merchandiseStatus);
         
            List<MerchandiseEntity> results = merchandiseSessionLocal.retrieveMerchandiseByMerchandiseAttributes(merchandise);
            
            for (MerchandiseEntity m : results) {
                try{
                    PictureEntity picture = m.getPicture();

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

            GenericEntity<List<MerchandiseEntity>> entity = new GenericEntity<List<MerchandiseEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchMerchandiseEntitys           

    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllMerchandiseEntity() {
        System.out.println("***Restful retrieveAllMerchandises Started***");
        
        List<MerchandiseEntity> results = merchandiseSessionLocal.retrieveAllMerchandises(); 
        
        for (MerchandiseEntity merchandise : results) {
            try{
                PictureEntity picture = merchandise.getPicture();
            
//                if(picture.getImage() == null)
//                {
                    String image = getEncodedImageString(picture.getPictureFilePath());
                    picture.setImage(image);
//                    pictureSessionLocal.updatePicture(picture);  //currently cannot store the base 64 string into database - so do it as on demand                 
//                }
            
//                System.out.println("MerchandiseEntity e with id" + merchandise.getMerchandiseId() + " and name " + merchandise.getName());          

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
        
        System.out.println("***Restful retrieveAllMerchandises Ended***");
        
        GenericEntity<List<MerchandiseEntity>> entity = new GenericEntity<List<MerchandiseEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchandiseEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get MerchandiseEntity at MerchandiseEntityResource");
            MerchandiseEntity merchandise = merchandiseSessionLocal.retrieveMerchandiseById(mId);
            PictureEntity picture = merchandise.getPicture();
            
            String image = getEncodedImageString(picture.getPictureFilePath());
            picture.setImage(image);
            
            System.out.println("MerchandiseEntity e with id" + mId + " and name " + merchandise.getName());          
                                    
            return Response.status(Status.OK).entity(merchandise).build();

        } catch (MerchandiseNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandiseEntity Not Found")
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
    } //end getMerchandiseEntity

    
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
//        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    } 
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editMerchandise(@PathParam("id") Long mId, MerchandiseEntity m) {        

            merchandiseSessionLocal.updateMerchandise(m);
            return Response.status(Status.OK).build();
    } //end editMerchandiseEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMerchandise(@PathParam("id") Long mId) {
        try {
            System.out.println("Delete MerchandiseEntity is triggered at merchandiseResources");
            merchandiseSessionLocal.deleteMerchandise(mId);
            return Response.status(Status.OK).build();
        } 
        catch (MerchandiseNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandiseEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteMerchandiseEntity     
    
    private MerchandiseSessionLocal lookupMerchandiseSessionLocal() {
        try {
            Context c = new InitialContext();
            return (MerchandiseSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MerchandiseSession!hms.hotelstay.session.MerchandiseSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PictureSessionLocal lookupPictureSessionLocal() {
        try {
            Context c = new InitialContext();
            return (PictureSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/PictureSession!hms.common.session.PictureSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
