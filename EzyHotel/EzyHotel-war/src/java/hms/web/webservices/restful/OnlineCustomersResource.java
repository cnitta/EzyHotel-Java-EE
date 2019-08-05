/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.session.PictureSessionLocal;
import hms.datamodel.ws.RetrieveAllRoomBookingsRsp;
import hms.prepostarrival.session.OnlineCustomerSessionLocal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.faces.bean.RequestScoped;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.CustomerEntity;
import util.entity.LoyaltyEntity;
import util.entity.PictureEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomTypeEntity;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("customers")
@RequestScoped
public class OnlineCustomersResource {

    PictureSessionLocal pictureSessionLocal = lookupPictureSessionLocal();

    OnlineCustomerSessionLocal onlineCustomerSessionLocal = lookupOnlineCustomerSessionLocal();

    @javax.ws.rs.core.Context
    private ServletContext context;

    /**
     * Creates a new instance of CustomersResource
     */
    public OnlineCustomersResource() {
    }

    /**
     * Retrieves representation of an instance of
     * hms.web.webservices.restful.OnlineCustomersResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("customerId") Long customerId) {
        try {
            CustomerEntity getCustomer = onlineCustomerSessionLocal.retrieveEntityById(customerId);
            getCustomer.setPassword(null);
            return Response.status(Response.Status.OK).entity(getCustomer).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/roomBookings/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomBookingsByCustomerId(@PathParam("customerId") Long customerId) {
        System.out.println("customerId " + customerId);
        List<RoomBookingEntity> roomBookings = onlineCustomerSessionLocal.retrieveRoomBookingsByCustomerId(customerId);

        for (RoomBookingEntity roomBooking : roomBookings) {
            CustomerEntity customer = roomBooking.getCustomer();
            customer.setPassword(null);
            RoomTypeEntity roomType = roomBooking.getRoomType();
            roomType.setRooms(null); // roomType must not be null

            if (roomType.getPicture() != null) {
                PictureEntity picture = roomType.getPicture();
                try {
                    String image = getEncodedImageString(picture.getPictureFilePath());
                    picture.setImage(image);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                PictureEntity hotelPicture = roomType.getHotel().getPicture();
                try {
                    String image = getEncodedImageString(hotelPicture.getPictureFilePath());
                    hotelPicture.setImage(image);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }

        GenericEntity<List<RoomBookingEntity>> entity = new GenericEntity<List<RoomBookingEntity>>(roomBookings) {
        };

        System.out.println("entity" + entity.toString());

        return Response.status(Response.Status.CREATED).entity(entity).build();

    }

    private String getEncodedImageString(String filePath) throws IOException {
        String UPLOAD_DIRECTORY = getUploadDirectory();
        try {
            String image = pictureSessionLocal.getPictureBase64String(UPLOAD_DIRECTORY + filePath);
            // System.out.println("image: " + image);
            return image;
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    // UPLOAD_DIRECTORY + filePath for new file()
    private String getUploadDirectory() {
        String substring = "/EzyHotel-J2E/EzyHotel/dist/gfdeploy/EzyHotel/EzyHotel-war_war/";
        // System.out.println("Real Path " + context.getRealPath("/"));
        // this is the file path that is for different local server
        String UPLOAD_DIRECTORY = context.getRealPath("/").replace(substring, "");
        // System.out.println("#UPLOAD_DIRECTORY : " + UPLOAD_DIRECTORY);
        return UPLOAD_DIRECTORY;
    }

    /**
     * PUT method for updating or creating an instance of OnlineCustomersResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    private OnlineCustomerSessionLocal lookupOnlineCustomerSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OnlineCustomerSessionLocal) c.lookup(
                    "java:global/EzyHotel/EzyHotel-ejb/OnlineCustomerSession!hms.prepostarrival.session.OnlineCustomerSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PictureSessionLocal lookupPictureSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PictureSessionLocal) c
                    .lookup("java:global/EzyHotel/EzyHotel-ejb/PictureSession!hms.common.session.PictureSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
