/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.housekeeping.session.HousekeepingRecordSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.hr.session.StaffSessionLocal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.HousekeepingRecordEntity;

/**
 * REST Web Service
 *
 * @author bryantan
 */
@Path("housekeepingRecords")
public class HousekeepingRecordsResource {

    @Context
    private UriInfo context;
    
    @EJB
    private HousekeepingRecordSessionLocal housekeepingRecordSessionLocal;
    
    @EJB
    private RoomSessionLocal roomSessionLocal;

    /**
     * Creates a new instance of HousekeepingRecordsResource
     */
    public HousekeepingRecordsResource() {
    }

    /**
     * Retrieves representation of an instance of hms.web.webservices.restful.HousekeepingRecordsResource
     * @return an instance of java.lang.String
     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<HousekeepingRecordEntity> getAllStaff() {
//        return housekeepingRecordSessionLocal.retrieveAllEntities();
//    }//Get All Staff 
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHousekeepingRecords() {
        
        List<HousekeepingRecordEntity> records = housekeepingRecordSessionLocal.retrieveAllEntities();
        
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        for (int i = 0; i < records.size(); i++) {
            
            JsonArrayBuilder builder2 = Json.createArrayBuilder();
            
            for (int j = 0; j < records.get(i).getRooms().size(); j++) {
                builder2.add(Json.createObjectBuilder()
                        .add("roomId", records.get(i).getRooms().get(j).getRoomId())
                        .add("roomUnitNumber", records.get(i).getRooms().get(j).getRoomUnitNumber())
                        .add("status", records.get(i).getRooms().get(j).getStatus().toString())
                        .add("roomTypeName", records.get(i).getRooms().get(j).getRoomType().getName())
                );
            }
            
            
            builder.add(Json.createObjectBuilder()
                    .add("housekeepingRecordId", records.get(i).getHousekeepingRecordId())
                    .add("housekeepingStaff", Json.createObjectBuilder()
                        .add("staffId", records.get(i).getHousekeepingStaff().getStaffId())
                        .add("name", records.get(i).getHousekeepingStaff().getName())
                    )
                    .add("rooms", builder2)
                    .add("inspectorName", records.get(i).getInspectorName())
                    .add("recordDate", records.get(i).getRecordDate().toString())
                    .add("inspectorRating", records.get(i).getInspectorRating())
            );
        }
        JsonArray array = builder.build();
  
        return Response.status(Status.OK).entity(array).build();
    }
    
    @GET
    @Path("/staff/{staff_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStaffRecord(@PathParam("staff_id") Long sId) {
        
       List<HousekeepingRecordEntity> records = housekeepingRecordSessionLocal.retrieveStaffRecord(sId);
       
       JsonArrayBuilder builder = Json.createArrayBuilder();
        
        for (int i = 0; i < records.size(); i++) {
            
            JsonArrayBuilder builder2 = Json.createArrayBuilder();
            
            for (int j = 0; j < records.get(i).getRooms().size(); j++) {
                builder2.add(Json.createObjectBuilder()
                        .add("roomId", records.get(i).getRooms().get(j).getRoomId())
                        .add("roomUnitNumber", records.get(i).getRooms().get(j).getRoomUnitNumber())
                        .add("status", records.get(i).getRooms().get(j).getStatus().toString())
                        .add("roomTypeName", records.get(i).getRooms().get(j).getRoomType().getName())
                        .add("cleaningStatus", records.get(i).getRooms().get(j).getCleaningStatus())
                        .add("DND", records.get(i).getRooms().get(j).getIsDND())
                );
            }
            
            if (records.get(i).getFacility() != null) {
            builder.add(Json.createObjectBuilder()
                    .add("housekeepingRecordId", records.get(i).getHousekeepingRecordId())
                    .add("housekeepingStaff", Json.createObjectBuilder()
                        .add("staffId", records.get(i).getHousekeepingStaff().getStaffId())
                        .add("name", records.get(i).getHousekeepingStaff().getName())
                    )
                    .add("rooms", builder2)
                    .add("facility", Json.createObjectBuilder()
                        .add("facilityId", records.get(i).getFacility().getFacilityId())
                        .add("name", records.get(i).getFacility().getName())
                        .add("facilityType", records.get(i).getFacility().getFacilityType())
                        .add("cleaningStatus", records.get(i).getFacility().getCleaningStatus())
                    )
                    .add("inspectorName", records.get(i).getInspectorName())
                    .add("recordDate", records.get(i).getRecordDate().toString())
                    .add("inspectorRating", records.get(i).getInspectorRating())
            );
            } else {
                builder.add(Json.createObjectBuilder()
                    .add("housekeepingRecordId", records.get(i).getHousekeepingRecordId())
                    .add("housekeepingStaff", Json.createObjectBuilder()
                        .add("staffId", records.get(i).getHousekeepingStaff().getStaffId())
                        .add("name", records.get(i).getHousekeepingStaff().getName())
                    )
                    .add("rooms", builder2)
                    .add("inspectorName", records.get(i).getInspectorName())
                    .add("recordDate", records.get(i).getRecordDate().toString())
                    .add("inspectorRating", records.get(i).getInspectorRating())
            );
            }
        }
        JsonArray array = builder.build();
  
        return Response.status(Status.OK).entity(array).build();
       
    }

    @GET
    @Path("/morning")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMorningRecords() {
        
        List<HousekeepingRecordEntity> records = housekeepingRecordSessionLocal.retrieveMorningRecords();
        
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        for (int i = 0; i < records.size(); i++) {
            
            JsonArrayBuilder builder2 = Json.createArrayBuilder();
            
            for (int j = 0; j < records.get(i).getRooms().size(); j++) {
                builder2.add(Json.createObjectBuilder()
                        .add("roomId", records.get(i).getRooms().get(j).getRoomId())
                        .add("roomUnitNumber", records.get(i).getRooms().get(j).getRoomUnitNumber())
                        .add("status", records.get(i).getRooms().get(j).getStatus().toString())
                        .add("roomTypeName", records.get(i).getRooms().get(j).getRoomType().getName())
                        .add("cleaningStatus", records.get(i).getRooms().get(j).getCleaningStatus())
                );
            }
            
            
            builder.add(Json.createObjectBuilder()
                    .add("housekeepingRecordId", records.get(i).getHousekeepingRecordId())
                    .add("housekeepingStaff", Json.createObjectBuilder()
                        .add("staffId", records.get(i).getHousekeepingStaff().getStaffId())
                        .add("name", records.get(i).getHousekeepingStaff().getName())
                    )
                    .add("rooms", builder2)
                    .add("inspectorName", records.get(i).getInspectorName())
                    .add("recordDate", records.get(i).getRecordDate().toString())
                    .add("inspectorRating", records.get(i).getInspectorRating())
            );
        }
        JsonArray array = builder.build();
  
        return Response.status(Status.OK).entity(array).build();
        
    }
    
    @GET
    @Path("/evening")
    public Response getAllEveningRecords() {
        List<HousekeepingRecordEntity> records = housekeepingRecordSessionLocal.retrieveEveningRecords();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (int i = 0; i < records.size(); i++) {
            
            
            
            JsonArrayBuilder builder2 = Json.createArrayBuilder();
            for (int j = 0; j < records.get(i).getRooms().size(); j++) {
                builder2.add(Json.createObjectBuilder()
                        .add("roomId", records.get(i).getRooms().get(j).getRoomId())
                        .add("roomUnitNumber", records.get(i).getRooms().get(j).getRoomUnitNumber())
                        .add("status", records.get(i).getRooms().get(j).getStatus().toString())
                        .add("roomTypeName", records.get(i).getRooms().get(j).getRoomType().getName())
                );
            }
            
            if (records.get(i).getFacility() != null) {
            
            builder.add(Json.createObjectBuilder()
                    .add("housekeepingRecordId", records.get(i).getHousekeepingRecordId())
                    .add("housekeepingStaff", Json.createObjectBuilder()
                        .add("staffId", records.get(i).getHousekeepingStaff().getStaffId())
                        .add("name", records.get(i).getHousekeepingStaff().getName())
                    )
                    .add("rooms", builder2)
                    .add("facility", Json.createObjectBuilder()
                        .add("facilityId", records.get(i).getFacility().getFacilityId())
                        .add("name", records.get(i).getFacility().getName())
                        .add("facilityType", records.get(i).getFacility().getFacilityType())
                    )
                    .add("inspectorName", records.get(i).getInspectorName())
                    .add("recordDate", records.get(i).getRecordDate().toString())
                    .add("inspectorRating", records.get(i).getInspectorRating())
            );
            } else {
                builder.add(Json.createObjectBuilder()
                    .add("housekeepingRecordId", records.get(i).getHousekeepingRecordId())
                    .add("housekeepingStaff", Json.createObjectBuilder()
                        .add("staffId", records.get(i).getHousekeepingStaff().getStaffId())
                        .add("name", records.get(i).getHousekeepingStaff().getName())
                    )
                    .add("rooms", builder2)
                    .add("inspectorName", records.get(i).getInspectorName())
                    .add("recordDate", records.get(i).getRecordDate().toString())
                    .add("inspectorRating", records.get(i).getInspectorRating())
            );
            }
        }
        JsonArray array = builder.build();
        return Response.status(Status.OK).entity(array).build();
    }
    
    @POST
    @Path("/cleanRoom/{room_number}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setRoomToClean(@PathParam("room_number") Integer roomNumber) {
         try {
            roomSessionLocal.setRoomToClean(roomNumber);
            HousekeepingRecordEntity record = housekeepingRecordSessionLocal.findRecordWithRoomNumber(roomNumber);
            List<String> recentActivity = record.getRecentActivity();
            Date now = new java.util.Date();
            if (recentActivity == null) {
                List<String> newList = new ArrayList<>();
                newList.add(now.toString() + ",room," + roomNumber );
                record.setRecentActivity(newList);
            } else {
                if (recentActivity.size() < 5) {
                    recentActivity.add(now.toString() + ",room," + roomNumber );
                } else {
                    recentActivity.remove(4);
                    recentActivity.add(0, now.toString() + ",room," + roomNumber );
                }
            }
            housekeepingRecordSessionLocal.updateEntity(record);

            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Path("/completion/{staff_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateCompletion(@PathParam("staff_id") Long sId) {
        List<HousekeepingRecordEntity> records = housekeepingRecordSessionLocal.retrieveStaffRecord(sId);
        HousekeepingRecordEntity record = records.get(0);
        JsonArrayBuilder builder = Json.createArrayBuilder();
         builder.add(Json.createObjectBuilder()
                 .add("completion", housekeepingRecordSessionLocal.calculateCompletion(record))
         );
        JsonArray array = builder.build(); 
        return Response.status(Status.OK).entity(array).build();
    }
    
    @GET
    @Path("/recentActivity/{staff_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentActivity(@PathParam("staff_id") Long sId) {
        List<HousekeepingRecordEntity> records = housekeepingRecordSessionLocal.retrieveStaffRecord(sId);
        HousekeepingRecordEntity record = records.get(0);
        List<String> recentActivity = housekeepingRecordSessionLocal.getRecentActivity(record);

        JsonArrayBuilder builder = Json.createArrayBuilder();
        if (recentActivity != null) {
            JsonArrayBuilder builder2 = Json.createArrayBuilder();
            for (String activity: recentActivity) {
                builder2.add(Json.createObjectBuilder()
                        .add("activity", activity)
                );
            }
            builder.add(Json.createObjectBuilder()
                 .add("activities", builder2)
         );
        }
        JsonArray array = builder.build(); 
        return Response.status(Status.OK).entity(array).build();
    }
}
