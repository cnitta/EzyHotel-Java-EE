/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.AffiliateContentSessionLocal;
import java.io.IOException;
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
import javax.servlet.ServletContext;
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
import util.entity.AffiliateContentEntity;
import util.entity.PictureEntity;
import util.enumeration.AffiliateContentCategoryEnum;
import util.enumeration.AffiliateContentStateEnum;
import util.enumeration.AffiliateContentStatusEnum;
import util.exception.AffiliateContentNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("affiliateContents")
public class AffiliateContentsResource {
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    AffiliateContentSessionLocal affiliateContentSessionLocal = lookupAffiliateContentSessionLocal();
   
    @javax.ws.rs.core.Context
    private ServletContext context;    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAffiliateContent(AffiliateContentEntity a) {

            System.out.println("Create AffiliateContentEntity at AffiliateContentEntityResource");
            
            AffiliateContentEntity affiliate = affiliateContentSessionLocal.createAffiliateContent(a);

            return Response.status(Status.OK).entity(affiliate).build();
        
    } //end createAffiliateContentEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAffiliateContentsByAttributes(@QueryParam("affiliateContentState") AffiliateContentStateEnum affiliateContentState,
            @QueryParam("affiliateContentStatus") AffiliateContentStatusEnum affiliateContentStatus,
            @QueryParam("category") AffiliateContentCategoryEnum category,
            @QueryParam("representativeName") String representativeName,
            @QueryParam("promoCode") String promoCode,
            @QueryParam("promoDescription") String promoDescription,
            @QueryParam("promotionEndDate") String promotionEndDateString,
            @QueryParam("promotionStartDate") String promotionStartDateString,
            @QueryParam("title") String title){             
           
            
            AffiliateContentEntity affiliateContent = new AffiliateContentEntity(title, category, promoDescription, promoCode, null, null, affiliateContentStatus, affiliateContentState, null);
           
         
            if(promotionEndDateString != null)
            {
                try {            
                String[] splited = promotionEndDateString.split("\\s+");

                Date promotionEndDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                    affiliateContent.setPromotionEndDate(promotionEndDate);
                }
                catch (ParseException ex) {
                    Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
             
            if(promotionStartDateString != null)
            {
                try {            
                String[] splited = promotionStartDateString.split("\\s+");

                Date promotionStartDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                    affiliateContent.setPromotionEndDate(promotionStartDate);
                }
                catch (ParseException ex) {
                    Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }              
            
            List<AffiliateContentEntity> results = affiliateContentSessionLocal.retrieveAffiliateContentByAffiliateContentAttributes(affiliateContent);
            
            for (AffiliateContentEntity result : results) {
                try{
                PictureEntity picture = result.getPicture();

                String image = getEncodedImageString(picture.getPictureFilePath());
                
                picture.setImage(image);
                }
                catch(IOException ex)
                {
 
                    System.out.println("Error received " + ex.getMessage());
                    JsonObject exception = Json.createObjectBuilder()
                            .add("error", "MenuItemEntity Not Found")
                            .build();

                    return Response.status(404).entity(exception)
                            .type(MediaType.APPLICATION_JSON).build();

                }
            }

            GenericEntity<List<AffiliateContentEntity>> entity = new GenericEntity<List<AffiliateContentEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchAffiliateContentEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllAffiliateContentEntity() {
        System.out.println("***Restful retrieveAllAffiliateContents Started***");
        List<AffiliateContentEntity> results = affiliateContentSessionLocal.retrieveAllAffiliateContents(); 
        
            for (AffiliateContentEntity result : results) {
                try{
                PictureEntity picture = result.getPicture();

                String image = getEncodedImageString(picture.getPictureFilePath());
                
                picture.setImage(image);
                }
                catch(IOException ex)
                {
 
                    System.out.println("Error received " + ex.getMessage());
                    JsonObject exception = Json.createObjectBuilder()
                            .add("error", "MenuItemEntity Not Found")
                            .build();

                    return Response.status(404).entity(exception)
                            .type(MediaType.APPLICATION_JSON).build();

                }
            }        
        System.out.println("***Restful retrieveAllAffiliateContents Ended***");

        GenericEntity<List<AffiliateContentEntity>> entity = new GenericEntity<List<AffiliateContentEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAffiliateContentEntityById(@PathParam("id") Long aId) {
        try {
            System.out.println("Get AffiliateContentEntity at AffiliateContentEntityResource");
            AffiliateContentEntity affiliateContent = affiliateContentSessionLocal.retrieveAffiliateContentById(aId);
            PictureEntity picture = affiliateContent.getPicture();

            String image = getEncodedImageString(picture.getPictureFilePath());
            picture.setImage(image);

            System.out.println("AffiliateContentEntity e with id" + aId + " and title " + affiliateContent.getTitle());          

            return Response.status(Status.OK).entity(affiliateContent).build();

        } catch (AffiliateContentNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "AffiliateContentEntity Not Found")
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
    } //end getAffiliateContentEntity
    
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
    

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAffiliateContent(@PathParam("id") Long dId, AffiliateContentEntity d) {        

            affiliateContentSessionLocal.updateAffiliateContent(d);
            return Response.status(Status.OK).build();
    } //end editAffiliateContentEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAffiliateContent(@PathParam("id") Long dId) {
        try {
            System.out.println("Delete AffiliateContentEntity is triggered at affiliateResources");
            affiliateContentSessionLocal.deleteAffiliateContent(dId);
            return Response.status(Status.OK).build();
        } 
        catch (AffiliateContentNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "AffiliateContentEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteAffiliateContentEntity  

    private AffiliateContentSessionLocal lookupAffiliateContentSessionLocal() {
        try {
            Context c = new InitialContext();
            return (AffiliateContentSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/AffiliateContentSession!hms.hotelstay.session.AffiliateContentSessionLocal");
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
