/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.hotelstay.session.MobileCheckInSessionLocal;
import hms.prepostarrival.session.OnlineCustomerSessionLocal;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.CustomerEntity;
import util.entity.PictureEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.RoomTypeEntity;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("mobilecheckin")
public class MobilecheckinResource {
    OnlineCustomerSessionLocal onlineCustomerSessionLocal = lookupOnlineCustomerSessionLocal();
    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();
    MobileCheckInSessionLocal mobileCheckInSessionLocal = lookupMobileCheckInSessionLocal();
    

    @javax.ws.rs.core.Context
    private ServletContext context;   
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response mobileCheckIn(RoomBookingEntity roomBooking) {
        try{
            RoomBookingEntity booking = mobileCheckInSessionLocal.mobileCheckIn(roomBooking);
            
            CustomerEntity customer = booking.getCustomer();
            customer.setPassword(null);
            RoomTypeEntity roomType = booking.getRoomType();
            roomType.setRooms(null); 
            PictureEntity picture = roomType.getPicture();
            try{
              String image = getEncodedImageString(picture.getPictureFilePath());
              picture.setImage(image);
            }
              catch(IOException ex)
              {
                System.out.println("Error received " + ex.getMessage());
                JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

                return Response.status(404).entity(exception)
                        .type(MediaType.APPLICATION_JSON).build();
                  }                          
            
            return Response.status(Response.Status.CREATED).entity(booking).build();
        }
        catch(Exception ex)
        {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                .add("error", ex.getMessage())
                .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    } //end mobile checkIn
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response roomByRoomUnitNumber(@QueryParam("roomNumber") String roomNumber) {
       int roomNum = Integer.parseInt(roomNumber); 
       
       RoomEntity room = mobileCheckInSessionLocal.retrieveRoomByRoomUnitNumber(roomNum);
       room.getRoomType().setRooms(null);
      
        PictureEntity picture =  room.getRoomType().getPicture();
        try{
          String image = getEncodedImageString(picture.getPictureFilePath());
          picture.setImage(image);
        }
        catch(IOException ex)
        {
          System.out.println("Error received " + ex.getMessage());
          JsonObject exception = Json.createObjectBuilder()
              .add("error", ex.getMessage())
              .build();

          return Response.status(404).entity(exception)
                  .type(MediaType.APPLICATION_JSON).build();
        }  
        
        return Response.status(Response.Status.OK).entity(room).build();

    }
    
    @GET
    @Path("/roomBooking/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomBookingsByCustomerId(@PathParam("customerId") Long customerId){
        System.out.println("customerId " + customerId);
          List<RoomBookingEntity> roomBookings = onlineCustomerSessionLocal.retrieveRoomBookingsByCustomerId(customerId);
         
          Date currentDate = new Date();
          Date closestDate = new Date();
                    
         RoomBookingEntity rm = null;
          for (RoomBookingEntity roomBooking : roomBookings) {
              System.out.println(roomBooking.getCheckInDateTime().toString());
              
              if(roomBooking.getCheckInDateTime().compareTo(currentDate) < 0)
              {
                  continue;
              }
              else if(roomBooking.getCheckInDateTime().compareTo(currentDate) == 0)
              {                                                               
                CustomerEntity customer = roomBooking.getCustomer();
                customer.setPassword(null);
                RoomTypeEntity roomType = roomBooking.getRoomType();
                roomType.setRooms(null); //roomType must not be null
                PictureEntity picture = roomType.getPicture();
                if(picture != null)
                {
                  try{
                  String image = getEncodedImageString(picture.getPictureFilePath());
                  picture.setImage(image);
                }
                  catch(IOException ex)
                  {
                      System.out.println(ex.getMessage());
                  }     
                }
                                
                PictureEntity hotelPicture = roomType.getHotel().getPicture();
                
                if(hotelPicture != null)
                {
                    try{
                  String image = getEncodedImageString(hotelPicture.getPictureFilePath());
                  hotelPicture.setImage(image);
                    }
                    catch(IOException ex)
                    {
                        System.out.println(ex.getMessage());
                    } 
                }
                
                System.out.println(" inside: " +roomBooking.getCheckInDateTime().toString());
                  rm = roomBooking;
                  break;
                   
              }
              else 
              {
                    CustomerEntity customer = roomBooking.getCustomer();
                    customer.setPassword(null);
                    RoomTypeEntity roomType = roomBooking.getRoomType();
                    roomType.setRooms(null); //roomType must not be null
                    PictureEntity picture = roomType.getPicture();
                    if(picture != null)
                    {
                      try{
                      String image = getEncodedImageString(picture.getPictureFilePath());
                      picture.setImage(image);
                    }
                      catch(IOException ex)
                      {
                          System.out.println(ex.getMessage());
                      }     
                    }

                    PictureEntity hotelPicture = roomType.getHotel().getPicture();

                    if(hotelPicture != null)
                    {
                        try{
                      String image = getEncodedImageString(hotelPicture.getPictureFilePath());
                      hotelPicture.setImage(image);
                        }
                        catch(IOException ex)
                        {
                            System.out.println(ex.getMessage());
                        } 
                    }

                    System.out.println(" inside: " +roomBooking.getCheckInDateTime().toString());
                      rm = roomBooking;
                      break;

                  }                  
              }
                      
    
          
          
//        GenericEntity<List<RoomBookingEntity>> entity = new GenericEntity<List<RoomBookingEntity>>(roomBookings) {
//        };
        
//        System.out.println("entity" + entity.toString());
            
        return Response.status(Response.Status.CREATED).entity(rm).build();
                 
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

    private MobileCheckInSessionLocal lookupMobileCheckInSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MobileCheckInSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MobileCheckInSession!hms.hotelstay.session.MobileCheckInSessionLocal");
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

    private OnlineCustomerSessionLocal lookupOnlineCustomerSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OnlineCustomerSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/OnlineCustomerSession!hms.prepostarrival.session.OnlineCustomerSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
