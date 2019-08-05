/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.ProgramEntrySessionLocal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import util.entity.ProgramEntryEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("programentries")
public class ProgramEntryResource {
    
    @EJB
    private ProgramEntrySessionLocal programEntrySessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProgramEntryResource
     */
    public ProgramEntryResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProgramEntryEntity> retrieveAllProgramEntrys()
    {
        return programEntrySessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/calendar/meetingroom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProgramEntrysForCalendarMeetingRoom()
    {
        List<ProgramEntryEntity> listOfProgramEntryEntity = programEntrySessionLocal.retrieveAllEntities();

        JsonArrayBuilder programEntryEntityArray = Json.createArrayBuilder();

        for (int i = 0; i < listOfProgramEntryEntity.size(); i++) {
            if (listOfProgramEntryEntity.get(i).getFunctionType().equals("Meeting Room")) {

                String status = listOfProgramEntryEntity.get(i).getStatus();
                String hexColor = "";
                if (status.equals("Definite")) {
                    hexColor = "43A047";
                } else if (status.equals("Tentative")) {
                    hexColor = "EC407A";
                }

                String dateBooked = listOfProgramEntryEntity.get(i).getDateBooked().toString();
                String dateBookedSubString = dateBooked.substring(0, 11);
                String dateBookedYearSubString = dateBooked.substring(24);
                String dateBookedDateTime = dateBookedSubString + dateBookedYearSubString + " 00:00:00 GMT+0800 (Singapore Standard Time)";

                String programDate = listOfProgramEntryEntity.get(i).getDateBooked().toString();
                String programDateSubString = programDate.substring(0, 11);
                String programDateYearSubString = dateBooked.substring(24);
                String programDateDateTime = programDateSubString + programDateYearSubString + " 00:00:00 GMT+0800 (Singapore Standard Time)";

                String startDate = listOfProgramEntryEntity.get(i).getProgramDate().toString();
                String startDateSubString = startDate.substring(0, 11);
                String startDateYearSubString = dateBooked.substring(24);
                String startTime = listOfProgramEntryEntity.get(i).getStartTime().toString();
                String startDateTime = startDateSubString + startDateYearSubString + " " + startTime + " GMT+0800 (Singapore Standard Time)";

                String endDate = listOfProgramEntryEntity.get(i).getProgramDate().toString();
                String endDateSubString = endDate.substring(0, 11);
                String endDateYearSubString = dateBooked.substring(24);
                String endTime = listOfProgramEntryEntity.get(i).getEndTime().toString();
                String endDateTime = endDateSubString + endDateYearSubString + " " + endTime + " GMT+0800 (Singapore Standard Time)";

                programEntryEntityArray.add(Json.createObjectBuilder()
                        .add("programEntryId", listOfProgramEntryEntity.get(i).getProgramEntryId())
                        .add("title", listOfProgramEntryEntity.get(i).getGroupName())
                        .add("start", startDateTime)
                        .add("end", endDateTime)
                        .add("hexColor", hexColor)
                        .add("contactName", listOfProgramEntryEntity.get(i).getContctName())
                        .add("dateBooked", dateBookedDateTime)
                        .add("estNumPerson", listOfProgramEntryEntity.get(i).getEstNumPerson())
                        .add("functionType", listOfProgramEntryEntity.get(i).getFunctionType())
                        .add("personInitial", listOfProgramEntryEntity.get(i).getPersonInitial())
                        .add("programDate", programDateDateTime)
                        .add("remarks", listOfProgramEntryEntity.get(i).getRemarks())
                        .add("status", listOfProgramEntryEntity.get(i).getStatus()));
            }
        }
        
        JsonArray array = programEntryEntityArray.build();        

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/calendar/conferenceroom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProgramEntrysForCalendarConferenceRoom()
    {
        List<ProgramEntryEntity> listOfProgramEntryEntity = programEntrySessionLocal.retrieveAllEntities();

        JsonArrayBuilder programEntryEntityArray = Json.createArrayBuilder();

        for (int i = 0; i < listOfProgramEntryEntity.size(); i++) {
            if (listOfProgramEntryEntity.get(i).getFunctionType().equals("Conference Room")) {

                String status = listOfProgramEntryEntity.get(i).getStatus();
                String hexColor = "";
                if (status.equals("Definite")) {
                    hexColor = "43A047";
                } else if (status.equals("Tentative")) {
                    hexColor = "EC407A";
                }
                
                String dateBooked = listOfProgramEntryEntity.get(i).getDateBooked().toString();
                String dateBookedSubString = dateBooked.substring(0, 11);
                String dateBookedYearSubString = dateBooked.substring(24);
                String dateBookedDateTime = dateBookedSubString + dateBookedYearSubString + " 00:00:00 GMT+0800 (Singapore Standard Time)";

                String programDate = listOfProgramEntryEntity.get(i).getDateBooked().toString();
                String programDateSubString = programDate.substring(0, 11);
                String programDateYearSubString = dateBooked.substring(24);
                String programDateDateTime = programDateSubString + programDateYearSubString + " 00:00:00 GMT+0800 (Singapore Standard Time)";

                String startDate = listOfProgramEntryEntity.get(i).getProgramDate().toString();
                String startDateSubString = startDate.substring(0, 11);
                String startDateYearSubString = dateBooked.substring(24);
                String startTime = listOfProgramEntryEntity.get(i).getStartTime().toString();
                String startDateTime = startDateSubString + startDateYearSubString + " " + startTime + " GMT+0800 (Singapore Standard Time)";

                String endDate = listOfProgramEntryEntity.get(i).getProgramDate().toString();
                String endDateSubString = endDate.substring(0, 11);
                String endDateYearSubString = dateBooked.substring(24);
                String endTime = listOfProgramEntryEntity.get(i).getEndTime().toString();
                String endDateTime = endDateSubString + endDateYearSubString + " " + endTime + " GMT+0800 (Singapore Standard Time)";

                programEntryEntityArray.add(Json.createObjectBuilder()
                        .add("programEntryId", listOfProgramEntryEntity.get(i).getProgramEntryId())
                        .add("title", listOfProgramEntryEntity.get(i).getGroupName())
                        .add("start", startDateTime)
                        .add("end", endDateTime)
                        .add("hexColor", hexColor)
                        .add("contactName", listOfProgramEntryEntity.get(i).getContctName())
                        .add("dateBooked", dateBookedDateTime)
                        .add("estNumPerson", listOfProgramEntryEntity.get(i).getEstNumPerson())
                        .add("functionType", listOfProgramEntryEntity.get(i).getFunctionType())
                        .add("personInitial", listOfProgramEntryEntity.get(i).getPersonInitial())
                        .add("programDate", programDateDateTime)
                        .add("remarks", listOfProgramEntryEntity.get(i).getRemarks())
                        .add("status", listOfProgramEntryEntity.get(i).getStatus()));
            }
        }
        
        JsonArray array = programEntryEntityArray.build();        

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/checkProgram/meetingroom")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkExistingProgramForMeetingRoom(JsonObject request)
    {
        List<ProgramEntryEntity> listOfProgramEntryEntity = programEntrySessionLocal.retrieveAllEntities();

        JsonArrayBuilder checkProgramArray = Json.createArrayBuilder();
        
        Boolean checkProgram = true;
        
        for (int i = 0; i < listOfProgramEntryEntity.size(); i++) {
            if (listOfProgramEntryEntity.get(i).getFunctionType().equals("Meeting Room")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try{
                    Date requestProgramDate = sdf.parse(request.get("programDate").toString().replace("\"", ""));
                    if(requestProgramDate.equals(listOfProgramEntryEntity.get(i).getProgramDate())){
                        String existingProgramEntryStartTime = listOfProgramEntryEntity.get(i).getStartTime().toString();
                        Date existingStartTime = new SimpleDateFormat("HH:mm:ss").parse(existingProgramEntryStartTime);
                        Calendar calendarStartTime = Calendar.getInstance();
                        calendarStartTime.setTime(existingStartTime);
                        calendarStartTime.add(Calendar.DATE, 1);
                        
                        String existingProgramEntryEndTime = listOfProgramEntryEntity.get(i).getEndTime().toString();
                        Date existingEndTime = new SimpleDateFormat("HH:mm:ss").parse(existingProgramEntryEndTime);
                        Calendar calendarEndTime = Calendar.getInstance();
                        calendarEndTime.setTime(existingEndTime);
                        calendarEndTime.add(Calendar.DATE, 1);
                        
                        String requestStartTime = request.get("startTime").toString().replace("\"", "");
                        Date requestProgramStartTime = new SimpleDateFormat("HH:mm:ss").parse(requestStartTime);
                        Calendar calendarRequestStart = Calendar.getInstance();
                        calendarRequestStart.setTime(requestProgramStartTime);
                        calendarRequestStart.add(Calendar.DATE, 1);
                        
                        String requestEndTime = request.get("endTime").toString().replace("\"", "");
                        Date requestProgramEndTime = new SimpleDateFormat("HH:mm:ss").parse(requestEndTime);
                        Calendar calendarRequestEnd = Calendar.getInstance();
                        calendarRequestEnd.setTime(requestProgramEndTime);
                        calendarRequestEnd.add(Calendar.DATE, 1);
                        
                        Date requestDateStart = calendarRequestStart.getTime();
                        Date requestDateEnd = calendarRequestEnd.getTime();
                        if((requestDateStart.after(calendarStartTime.getTime()) && requestDateStart.before(calendarEndTime.getTime())) || requestDateStart.equals(calendarStartTime.getTime())){
                            checkProgram = false;
                        } else if((requestDateEnd.after(calendarStartTime.getTime()) && requestDateEnd.before(calendarEndTime.getTime())) || requestDateEnd.equals(calendarEndTime.getTime())){
                            checkProgram = false;  
                        } else if(requestDateStart.before(calendarStartTime.getTime()) && requestDateEnd.after(calendarEndTime.getTime())){
                            checkProgram = false; 
                        }
                    }                    
                } catch (Exception ex){
                    
                }
            }
        }
        checkProgramArray.add(Json.createObjectBuilder()
                .add("checkProgramEntry", checkProgram));
        
        JsonArray array = checkProgramArray.build();        

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Path("/checkProgram/conferenceroom")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkExistingProgramForConferenceRoom(JsonObject request)
    {
        List<ProgramEntryEntity> listOfProgramEntryEntity = programEntrySessionLocal.retrieveAllEntities();

        JsonArrayBuilder checkProgramArray = Json.createArrayBuilder();
        
        Boolean checkProgram = true;

        for (int i = 0; i < listOfProgramEntryEntity.size(); i++) {
            if (listOfProgramEntryEntity.get(i).getFunctionType().equals("Conference Room")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try{
                    Date requestProgramDate = sdf.parse(request.get("programDate").toString().replace("\"", ""));
                    if(requestProgramDate.equals(listOfProgramEntryEntity.get(i).getProgramDate())){
                        String existingProgramEntryStartTime = listOfProgramEntryEntity.get(i).getStartTime().toString();
                        Date existingStartTime = new SimpleDateFormat("HH:mm:ss").parse(existingProgramEntryStartTime);
                        Calendar calendarStartTime = Calendar.getInstance();
                        calendarStartTime.setTime(existingStartTime);
                        calendarStartTime.add(Calendar.DATE, 1);
                        
                        String existingProgramEntryEndTime = listOfProgramEntryEntity.get(i).getEndTime().toString();
                        Date existingEndTime = new SimpleDateFormat("HH:mm:ss").parse(existingProgramEntryEndTime);
                        Calendar calendarEndTime = Calendar.getInstance();
                        calendarEndTime.setTime(existingEndTime);
                        calendarEndTime.add(Calendar.DATE, 1);
                        
                        String requestStartTime = request.get("startTime").toString().replace("\"", "");
                        Date requestProgramStartTime = new SimpleDateFormat("HH:mm:ss").parse(requestStartTime);
                        Calendar calendarRequestStart = Calendar.getInstance();
                        calendarRequestStart.setTime(requestProgramStartTime);
                        calendarRequestStart.add(Calendar.DATE, 1);
                        
                        String requestEndTime = request.get("endTime").toString().replace("\"", "");
                        Date requestProgramEndTime = new SimpleDateFormat("HH:mm:ss").parse(requestEndTime);
                        Calendar calendarRequestEnd = Calendar.getInstance();
                        calendarRequestEnd.setTime(requestProgramEndTime);
                        calendarRequestEnd.add(Calendar.DATE, 1);
                        
                        Date requestDateStart = calendarRequestStart.getTime();
                        Date requestDateEnd = calendarRequestEnd.getTime();
                        if((requestDateStart.after(calendarStartTime.getTime()) && requestDateStart.before(calendarEndTime.getTime())) || requestDateStart.equals(calendarStartTime.getTime())){
                            checkProgram = false;
                        } else if((requestDateEnd.after(calendarStartTime.getTime()) && requestDateEnd.before(calendarEndTime.getTime())) || requestDateEnd.equals(calendarEndTime.getTime())){
                            checkProgram = false;  
                        } else if(requestDateStart.before(calendarStartTime.getTime()) && requestDateEnd.after(calendarEndTime.getTime())){
                            checkProgram = false; 
                        }
                    }                    
                } catch (Exception ex){
                    
                }
            }
        }
        checkProgramArray.add(Json.createObjectBuilder()
                .add("checkProgramEntry", checkProgram));
        
        JsonArray array = checkProgramArray.build();        

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProgramEntry(@PathParam("id") Long peId){
        try{
            ProgramEntryEntity pe = programEntrySessionLocal.retrieveEntityById(peId);
            return Response.status(200).entity(pe).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ProgramEntryEntity createProgramEntry(ProgramEntryEntity pe){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        programEntrySessionLocal.createEntity(pe);
        return pe;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProgramEntry(@PathParam("id") Long peId, ProgramEntryEntity pe){
        pe.setProgramEntryId(peId);
        try{
            programEntrySessionLocal.updateEntity(pe);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            
            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProgramEntry(@PathParam("id") Long peId){
        try{
            programEntrySessionLocal.deleteEntity(peId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Program Entry not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
