/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.HotelSessionLocal;
import hms.prepostarrival.session.InformationRetrievalClass;
import hms.prepostarrival.session.InformationSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.HotelEntity;
import util.entity.PriceRateEntity;
import util.entity.RoomTypeEntity;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("information")

public class InformationResource {
    PriceRateSessionLocal priceRateSessionLocal = lookupPriceRateSessionLocal();
    HotelSessionLocal hotelSessionLocal= lookupHotelSessionLocal();
    InformationSessionLocal informationSessionLocal = lookupInformationSessionLocal1();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of InformationResource
     */
    public InformationResource() {}
    
    @POST
    @Path("/roomAvailability/{hotelName}")
    @Produces(MediaType.APPLICATION_JSON)  
    public Response getRoomAvailabilityAndDates(@PathParam("hotelName") String hotelName, JsonObject data) throws Exception{
        String startDateString = data.getString("startDate");
        String endDateString = data.getString("endDate");
        String roomType = data.getString("roomType");
        System.out.println("StartDate: " + startDateString + ", EndDate: " + endDateString + ", Room Type: " + roomType);
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);
        
        
       boolean status = informationSessionLocal.checkVacancyForDateRange(startDate, endDate, roomType, hotelName);
       if(status){
           
           return Response.status(Response.Status.OK).entity("There is vacancy for " + roomType  + " over the selected date range").build();
       }else{
           return Response.status(Response.Status.BAD_REQUEST).entity("There is no vacancy for the selected date range").build();
       }
    }
    
    @GET
    @Path("/{hotelName}")
    @Produces(MediaType.APPLICATION_JSON)    
    public InformationRetrievalClass getInformationByHotel(@PathParam("hotelName") String hotelName) throws Exception{
   
        try{
            return informationSessionLocal.getAllInformationForHotel(hotelName);
        }catch(Exception e){
            throw new Exception ("Information Retrieval Failutre: " + e.getMessage()); 
        }
    }

    @GET
    @Path("/roomTypes/{hotelName}")
    public Response getRoomTypesForHotel(@PathParam("hotelName") String hotelName) throws Exception{
        try{
            HotelEntity hotel = hotelSessionLocal.retrieveEntity("name", hotelName);
            List<RoomTypeEntity> roomTypes = informationSessionLocal. retrieveRoomTypeByHotel("hotel.hotelId", hotel.getHotelId()); 
            JsonArrayBuilder roomTypeJsonArr = Json.createArrayBuilder();
            
            for(RoomTypeEntity rt : roomTypes){
                List<PriceRateEntity> priceRateForRoomType = informationSessionLocal.getPriceRateByDay(rt.getRoomTypeId());
                JsonObjectBuilder buildPriceRateJson = Json.createObjectBuilder();
                for(PriceRateEntity pr : priceRateForRoomType){
                   buildPriceRateJson.add(pr.getRateTitle(), pr.getMarkupPrice());
                }
                
                roomTypeJsonArr.add(Json.createObjectBuilder()
                    .add("roomTypeId",rt.getRoomTypeId())
                    .add("description",rt.getDescription())
                    .add("bedType",rt.getBedType())
                    .add("name",rt.getName())
                    .add("roomTypeCode",rt.getRoomTypecode())
                    .add("priceRates", buildPriceRateJson.build())
                );
            }
            
            JsonArray jsonArr = roomTypeJsonArr.build();
            return Response.status(Response.Status.OK).entity(jsonArr).build();
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    

    private InformationSessionLocal lookupInformationSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (InformationSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/InformationSession!hms.prepostarrival.session.InformationSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private InformationSessionLocal lookupInformationSessionLocal1() {
        try {
            javax.naming.Context c = new InitialContext();
            return (InformationSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/InformationSession!hms.prepostarrival.session.InformationSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private HotelSessionLocal lookupHotelSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (HotelSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/HotelSession!hms.hpm.session.HotelSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private PriceRateSessionLocal lookupPriceRateSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PriceRateSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/PriceRateSession!hms.sales.session.PriceRateSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
