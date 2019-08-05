/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.FacilitySessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.FacilityEntity;
import util.entity.PriceRateEntity;
import util.entity.RoomTypeEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("pricerates")
public class PriceRateResource {
    
    @EJB
    private PriceRateSessionLocal priceRateSessionLocal;
    
    @EJB
    private RoomTypeSessionLocal roomTypeSessionLocal;
    
    @EJB
    private RoomSessionLocal roomSessionLocal;
    
    @EJB
    private FacilitySessionLocal facilitySessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PriceRateResource
     */
    public PriceRateResource() {
    }
    
    @GET
    @Path("/roomtype/{roomType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPriceRatesByRoomType(@PathParam("roomType") String roomType)
    {
        RoomTypeEntity roomTypeEntity = roomTypeSessionLocal.retrieveEntity("name", roomType);
        
        List<PriceRateEntity> listOfPriceRateEntity = priceRateSessionLocal.retrieveAllEntitiesByRoomTypeId(roomTypeEntity.getRoomTypeId());
              
        JsonArrayBuilder priceRateByRoomType = Json.createArrayBuilder();
        
        for(int i = 0; i < listOfPriceRateEntity.size(); i++){
            priceRateByRoomType.add(Json.createObjectBuilder()
                    .add("priceRateId", listOfPriceRateEntity.get(i).getPriceRateId())
                    .add("rateTitle", listOfPriceRateEntity.get(i).getRateTitle())
                    .add("roomType", roomTypeEntity.getName())
                    .add("basePrice", listOfPriceRateEntity.get(i).getBasePrice())
                    .add("percentageMarkup", listOfPriceRateEntity.get(i).getPercentageMarkup())
                    .add("markupPrice", listOfPriceRateEntity.get(i).getMarkupPrice())
                    .add("remarks", listOfPriceRateEntity.get(i).getRemarks()));
        }
        
        JsonArray array = priceRateByRoomType.build();

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/facility")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPriceRatesForFacility()
    {
        FacilityEntity facilityMeetingRoomEntity = facilitySessionLocal.retrieveEntity("facilityType", "Meeting Room");
        
        FacilityEntity facilityConferenceRoomEntity = facilitySessionLocal.retrieveEntity("facilityType", "Conference Room");
        
        List<PriceRateEntity> listOfPriceRateMeetingRoomEntity = priceRateSessionLocal.retrieveAllEntitiesByFacilityTypeId(facilityMeetingRoomEntity.getFacilityId());
        
        List<PriceRateEntity> listOfPriceRateConferenceRoomEntity = priceRateSessionLocal.retrieveAllEntitiesByFacilityTypeId(facilityConferenceRoomEntity.getFacilityId());
              
        JsonArrayBuilder priceRateForFacility = Json.createArrayBuilder();
        
        for(int i = 0; i < listOfPriceRateMeetingRoomEntity.size(); i++){
            priceRateForFacility.add(Json.createObjectBuilder()
                    .add("priceRateId", listOfPriceRateMeetingRoomEntity.get(i).getPriceRateId())
                    .add("rateTitle", listOfPriceRateMeetingRoomEntity.get(i).getRateTitle())
                    .add("roomType", facilityMeetingRoomEntity.getFacilityType())
                    .add("basePrice", listOfPriceRateMeetingRoomEntity.get(i).getBasePrice())
                    .add("percentageMarkup", listOfPriceRateMeetingRoomEntity.get(i).getPercentageMarkup())
                    .add("markupPrice", listOfPriceRateMeetingRoomEntity.get(i).getMarkupPrice())
                    .add("remarks", listOfPriceRateMeetingRoomEntity.get(i).getRemarks()));
        }
        
        for(int i = 0; i < listOfPriceRateConferenceRoomEntity.size(); i++){
            priceRateForFacility.add(Json.createObjectBuilder()
                    .add("priceRateId", listOfPriceRateConferenceRoomEntity.get(i).getPriceRateId())
                    .add("rateTitle", listOfPriceRateConferenceRoomEntity.get(i).getRateTitle())
                    .add("roomType", facilityConferenceRoomEntity.getFacilityType())
                    .add("basePrice", listOfPriceRateConferenceRoomEntity.get(i).getBasePrice())
                    .add("percentageMarkup", listOfPriceRateConferenceRoomEntity.get(i).getPercentageMarkup())
                    .add("markupPrice", listOfPriceRateConferenceRoomEntity.get(i).getMarkupPrice())
                    .add("remarks", listOfPriceRateConferenceRoomEntity.get(i).getRemarks()));
        }
        
        JsonArray array = priceRateForFacility.build();

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/rackrate/{roomType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPriceRate(@PathParam("roomType") String roomType){
        try{
            RoomTypeEntity roomTypeEntity = roomTypeSessionLocal.retrieveEntity("name", roomType);
        
            List<PriceRateEntity> listOfPriceRateEntity = priceRateSessionLocal.retrieveAllEntitiesByRoomTypeId(roomTypeEntity.getRoomTypeId());

            JsonArrayBuilder priceRateByRoomType = Json.createArrayBuilder();
            
            for (int i = 0; i < listOfPriceRateEntity.size(); i++) {
                if (listOfPriceRateEntity.get(i).getRateTitle().equals("Rack")) {
                    priceRateByRoomType.add(Json.createObjectBuilder()
                            .add("priceRateId", listOfPriceRateEntity.get(i).getPriceRateId())
                            .add("rateTitle", listOfPriceRateEntity.get(i).getRateTitle())
                            .add("roomType", roomTypeEntity.getName())
                            .add("basePrice", listOfPriceRateEntity.get(i).getBasePrice())
                            .add("percentageMarkup", listOfPriceRateEntity.get(i).getPercentageMarkup())
                            .add("markupPrice", listOfPriceRateEntity.get(i).getMarkupPrice())
                            .add("remarks", listOfPriceRateEntity.get(i).getRemarks()));
                }
            }      
            
            JsonArray array = priceRateByRoomType.build();
            
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/facilityrackrate/{facilityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFacilityRackRate(@PathParam("facilityType") String facilityType){
        try{
            FacilityEntity facilityEntity = facilitySessionLocal.retrieveEntity("facilityType", facilityType);
        
            List<PriceRateEntity> listOfPriceRateEntity = priceRateSessionLocal.retrieveAllEntitiesByFacilityId(facilityEntity.getFacilityId());

            JsonArrayBuilder priceRateByRoomType = Json.createArrayBuilder();
            
            for (int i = 0; i < listOfPriceRateEntity.size(); i++) {
                if (listOfPriceRateEntity.get(i).getRateTitle().equals("Rack")) {
                    priceRateByRoomType.add(Json.createObjectBuilder()
                            .add("priceRateId", listOfPriceRateEntity.get(i).getPriceRateId())
                            .add("rateTitle", listOfPriceRateEntity.get(i).getRateTitle())
                            .add("facilityType", facilityEntity.getFacilityType())
                            .add("basePrice", listOfPriceRateEntity.get(i).getBasePrice())
                            .add("percentageMarkup", listOfPriceRateEntity.get(i).getPercentageMarkup())
                            .add("markupPrice", listOfPriceRateEntity.get(i).getMarkupPrice())
                            .add("remarks", listOfPriceRateEntity.get(i).getRemarks()));
                }
            }      
            
            JsonArray array = priceRateByRoomType.build();
            
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/dynamicroomrate/{roomType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDynamicRoomRate(@PathParam("roomType") String roomType){
        try{
            RoomTypeEntity roomTypeEntity = roomTypeSessionLocal.retrieveEntity("name", roomType);
            
            int roomOccupancy = 0;
            
            double rackRate = 0;
            
            if (roomType.equals("Superior")) {
                roomOccupancy = roomSessionLocal.countSur();
                //System.out.println(roomOccupancy);
            } else if (roomType.equals("Deluxe")){
                roomOccupancy = roomSessionLocal.countDex();
                //System.out.println(roomOccupancy);
            } else if (roomType.equals("Junior Suite")){
                roomOccupancy = roomSessionLocal.countJun();
                //System.out.println(roomOccupancy);
            } else if (roomType.equals("Executive Suite")){
                roomOccupancy = roomSessionLocal.countExe();
                //System.out.println(roomOccupancy);
            } else if (roomType.equals("President Suite")){
                roomOccupancy = roomSessionLocal.countPre();
                //System.out.println(roomOccupancy);
            }
        
            List<PriceRateEntity> listOfPriceRateEntity = priceRateSessionLocal.retrieveAllEntitiesByRoomTypeId(roomTypeEntity.getRoomTypeId());

            JsonArrayBuilder priceRateByRoomType = Json.createArrayBuilder();
            
            for (int i = 0; i < listOfPriceRateEntity.size(); i++) {
                if (listOfPriceRateEntity.get(i).getRateTitle().equals("Rack")) {
                    rackRate = listOfPriceRateEntity.get(i).getMarkupPrice();
                }
            }
            
            for (int i = 0; i < listOfPriceRateEntity.size(); i++) {
                double dynamicPrice = priceRateSessionLocal.dynamicPrice(listOfPriceRateEntity.get(i).getMarkupPrice(), rackRate, roomOccupancy);
                priceRateByRoomType.add(Json.createObjectBuilder()
                        .add("priceRateId", listOfPriceRateEntity.get(i).getPriceRateId())
                        .add("rateTitle", listOfPriceRateEntity.get(i).getRateTitle())
                        .add("roomType", roomTypeEntity.getName())
                        .add("basePrice", listOfPriceRateEntity.get(i).getBasePrice())
                        .add("percentageMarkup", listOfPriceRateEntity.get(i).getPercentageMarkup())
                        .add("markupPrice", listOfPriceRateEntity.get(i).getMarkupPrice())
                        .add("dynamicPrice", dynamicPrice)
                        .add("remarks", listOfPriceRateEntity.get(i).getRemarks()));
            }      
            
            JsonArray array = priceRateByRoomType.build();
            
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/facilityrate/{facilityType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFacilityRate(@PathParam("facilityType") String facilityType){
        try{
            FacilityEntity facilityEntity = facilitySessionLocal.retrieveEntity("facilityType", facilityType);
        
            List<PriceRateEntity> listOfPriceRateEntity = priceRateSessionLocal.retrieveAllEntitiesByFacilityId(facilityEntity.getFacilityId());

            JsonArrayBuilder priceRateByRoomType = Json.createArrayBuilder();
                       
            for (int i = 0; i < listOfPriceRateEntity.size(); i++) {
                priceRateByRoomType.add(Json.createObjectBuilder()
                        .add("priceRateId", listOfPriceRateEntity.get(i).getPriceRateId())
                        .add("rateTitle", listOfPriceRateEntity.get(i).getRateTitle())
                        .add("facilityType", facilityEntity.getFacilityType())
                        .add("basePrice", listOfPriceRateEntity.get(i).getBasePrice())
                        .add("percentageMarkup", listOfPriceRateEntity.get(i).getPercentageMarkup())
                        .add("markupPrice", listOfPriceRateEntity.get(i).getMarkupPrice())
                        .add("remarks", listOfPriceRateEntity.get(i).getRemarks()));
            }      
            
            JsonArray array = priceRateByRoomType.build();
            
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/{priceRateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPriceRateByTitle(@PathParam("priceRateId") Long priceRateId){
        try{
            PriceRateEntity pr = priceRateSessionLocal.retrieveEntityById(priceRateId);
            return Response.status(200).entity(pr).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Path("/roomtype/{roomType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PriceRateEntity createPriceRate(@PathParam("roomType") String roomType, PriceRateEntity pr){
        RoomTypeEntity roomTypeEntity = roomTypeSessionLocal.retrieveEntity("name", roomType);

        PriceRateEntity priceEntity = new PriceRateEntity();
        priceEntity.setRateTitle(pr.getRateTitle());
        priceEntity.setBasePrice(pr.getBasePrice());
        priceEntity.setPercentageMarkup(pr.getPercentageMarkup());
        priceEntity.setMarkupPrice(pr.getMarkupPrice());
        priceEntity.setRemarks(pr.getRemarks());
        priceEntity.setRoomType(roomTypeEntity);
        priceRateSessionLocal.createEntity(priceEntity);

        return priceEntity;
    }
    
    @POST
    @Path("/facilitytype/{facilitytype}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PriceRateEntity createFacilityPriceRate(@PathParam("facilitytype") String facilityType, PriceRateEntity pr){
        FacilityEntity facilityEntity = facilitySessionLocal.retrieveEntity("facilityType", facilityType);

        PriceRateEntity priceEntity = new PriceRateEntity();
        priceEntity.setRateTitle(pr.getRateTitle());
        priceEntity.setBasePrice(pr.getBasePrice());
        priceEntity.setPercentageMarkup(pr.getPercentageMarkup());
        priceEntity.setMarkupPrice(pr.getMarkupPrice());
        priceEntity.setRemarks(pr.getRemarks());
        priceEntity.setFacility(facilityEntity);
        priceRateSessionLocal.createEntity(priceEntity);

        return priceEntity;
    }
    
    @PUT
    @Path("/{priceRateId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePriceRate(@PathParam("priceRateId") Long priceRateId, PriceRateEntity pr){
        PriceRateEntity retrievePriceRate = priceRateSessionLocal.retrieveEntityById(priceRateId);
        retrievePriceRate.setRateTitle(pr.getRateTitle());
        retrievePriceRate.setBasePrice(pr.getBasePrice());
        retrievePriceRate.setPercentageMarkup(pr.getPercentageMarkup());
        retrievePriceRate.setMarkupPrice(pr.getMarkupPrice());
        retrievePriceRate.setRemarks(pr.getRemarks());
        try{
            priceRateSessionLocal.updateEntity(retrievePriceRate);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/{priceRateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePriceRate(@PathParam("priceRateId") Long priceRateId){
        try{
            priceRateSessionLocal.deleteEntity(priceRateId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Price Rate not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
