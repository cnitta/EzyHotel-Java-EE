/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import hms.hpm.session.HotelSessionLocal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import util.entity.HotelEntity;
import util.entity.PictureEntity;

/**
 * REST Web Service
 *
 * @author nittayawancharoenkharungrueang
 */
@Path("hotels")
public class HotelsResource {
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();

    @EJB
    private HotelSessionLocal hotelSessionLocal;


    @javax.ws.rs.core.Context
    private ServletContext context;    

    /**
     * Creates a new instance of FacilityResource
     */
    public HotelsResource() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllHotels() {
        
        List<HotelEntity> hotels = hotelSessionLocal.retrieveAllEntities();
        
        for(HotelEntity hotel : hotels) {
            System.out.println("hotel get picture: " + hotel.getPicture());
            if(hotel.getPicture() != null){
                try{            
                PictureEntity picture = hotel.getPicture();

                    String image = getEncodedImageString(picture.getPictureFilePath());
                    picture.setImage(image);

                }
                catch(IOException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }        
            GenericEntity<List<HotelEntity>> entity = new GenericEntity<List<HotelEntity>>(hotels) {
            };
        

        System.out.println("Size of list = " + hotels.size());

        return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();

    } //end retrieve all hotels

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotel(@PathParam("id") Long hId) {
        try {
            HotelEntity h = hotelSessionLocal.retrieveEntityById(hId);
            
            if(h.getPicture() != null){
            PictureEntity picture = h.getPicture();
            
                    String image = getEncodedImageString(picture.getPictureFilePath());
                    picture.setImage(image);
            }   
            
            return Response.status(200).entity(h).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
        catch(IOException ex)
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();            
        }
    }
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotelByAttributes(@QueryParam("name") String name, @QueryParam("address") String address) {
  
            List<HotelEntity> hotels = hotelSessionLocal.retrieveHotelsByAttributes(name, address);
            
            for(HotelEntity hotel : hotels) {
                if(hotel.getPicture() != null){
                    try{
                    PictureEntity picture = hotel.getPicture();

                        String image = getEncodedImageString(picture.getPictureFilePath());
                        picture.setImage(image);

                    }
                    catch(IOException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                
                }

            }        
            GenericEntity<List<HotelEntity>> entity = new GenericEntity<List<HotelEntity>>(hotels) {
            };
        

        System.out.println("Size of list = " + hotels.size());

        return Response.status(200).entity(entity).type(MediaType.APPLICATION_JSON).build();
    }    
    

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHotel(@PathParam("id") Long hId, HotelEntity h) {
        h.setHotelId(hId);
        try {
            hotelSessionLocal.updateEntity(h);
            return Response.status(204).build();
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
    public HotelEntity createHotel(HotelEntity h) {
        hotelSessionLocal.createEntity(h);
        return h;
    } //end createCustomer

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@PathParam("id") Long hId) {
        try {
            hotelSessionLocal.deleteEntity(hId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Hotel not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
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
        //this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");        
        System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
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
