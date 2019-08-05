/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.frontdesk.session.CustomerSessionLocal;
import hms.hpm.session.FacilitySessionLocal;
import hms.sales.session.ProgramEntrySessionLocal;
import hms.sales.session.ReserveFacilitySessionLocal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
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
import util.entity.CustomerEntity;
import util.entity.FacilityEntity;
import util.entity.ProgramEntryEntity;
import util.entity.ReserveFacilityEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("reservefacilities")
public class ReserveFacilityResource {

    @EJB
    private ReserveFacilitySessionLocal reserveFacilitySessionLocal;
    
    @EJB
    private ProgramEntrySessionLocal programEntrySessionLocal;
    
    @EJB
    private FacilitySessionLocal facilitySessionLocal;

    @EJB
    private CustomerSessionLocal customerSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ReserveFacilityResource
     */
    public ReserveFacilityResource() {
    }

    @GET
    @Path("/customer/phonenumber/{customerphonenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerDetails(@PathParam("customerphonenumber") String customerPhoneNumber) throws NoResultException {
        try {
            CustomerEntity customerEntity = customerSessionLocal.retrieveEntityByPhoneNumber(customerPhoneNumber);
            return Response.status(200).entity(customerEntity).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReserveFacilityEntity> retrieveAllReserveFacilites() {
        return reserveFacilitySessionLocal.retrieveAllEntities();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReserveFacility(@PathParam("id") Long rfId) {
        try {
            ReserveFacilityEntity rf = reserveFacilitySessionLocal.retrieveEntityById(rfId);
            return Response.status(200).entity(rf).type(MediaType.APPLICATION_JSON).build();
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
    public ReserveFacilityEntity createReserveFacility(ReserveFacilityEntity rf) {
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        reserveFacilitySessionLocal.createEntity(rf);
        return rf;
    }

    @POST
    @Path("/createReserveFacilityWithProgram")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ReserveFacilityEntity createReserveFacilityWithProgram(JsonObject request) {

        String strDateBooked = request.get("dateBooked").toString().replace("\"", "");
        String strReserveDate = request.get("reserveDate").toString().replace("\"", "");
        String strStartTime = request.get("startTime").toString().replace("\"", "");
        String strStartTimeArray[] = strStartTime.split(":");
        String strEndTime = request.get("endTime").toString().replace("\"", "");
        String strEndTimeArray[] = strEndTime.split(":");
        
        ReserveFacilityEntity reserveFacilityEntity = new ReserveFacilityEntity();
        
        ProgramEntryEntity programEntryEntity = new ProgramEntryEntity();
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateBooked = formatter.parse(strDateBooked);
            Date reserveDate = formatter.parse(strReserveDate);
            
            Time startTime = new Time(Integer.parseInt(strStartTimeArray[0]),Integer.parseInt(strStartTimeArray[1]),Integer.parseInt("0"));
            Time endTime = new Time(Integer.parseInt(strEndTimeArray[0]),Integer.parseInt(strEndTimeArray[1]),Integer.parseInt("0"));
                       
            programEntryEntity.setContctName(request.get("personInCharge").toString().replace("\"", ""));
            programEntryEntity.setDateBooked(dateBooked);
            programEntryEntity.setEndTime(endTime);
            programEntryEntity.setEstNumPerson(Integer.parseInt(request.get("estimatedNumber").toString().replace("\"", "")));
            programEntryEntity.setFunctionType(request.get("functionType").toString().replace("\"", ""));
            programEntryEntity.setGroupName(request.get("groupName").toString().replace("\"", ""));
            programEntryEntity.setPersonInitial(request.get("personInitial").toString().replace("\"", ""));
            programEntryEntity.setPhoneNum(request.get("inChargePhoneNumber").toString().replace("\"", ""));
            programEntryEntity.setProgramDate(reserveDate);
            programEntryEntity.setRemarks(request.get("programRemark").toString().replace("\"", ""));
            programEntryEntity.setStartTime(startTime);
            programEntryEntity.setStatus(request.get("programStatus").toString().replace("\"", ""));
            
            programEntrySessionLocal.createEntity(programEntryEntity);
            
            FacilityEntity facilityEntity = facilitySessionLocal.retrieveEntity("facilityType", request.get("functionType").toString().replace("\"", ""));
            reserveFacilityEntity.setDateBooked(dateBooked);
            reserveFacilityEntity.setFunctionType(request.get("functionType").toString().replace("\"", ""));
            reserveFacilityEntity.setPersonContacted(request.get("personContacted").toString().replace("\"", ""));
            reserveFacilityEntity.setPersonInitial(request.get("personInitial").toString().replace("\"", ""));
            reserveFacilityEntity.setPhoneNum(request.get("phoneNumber").toString().replace("\"", ""));
            reserveFacilityEntity.setReservedDate(reserveDate);
            reserveFacilityEntity.setStatus(request.get("statusType").toString().replace("\"", ""));
            reserveFacilityEntity.setProgramEntry(programEntryEntity);
            reserveFacilityEntity.setFacility(facilityEntity);
            
            reserveFacilitySessionLocal.createEntity(reserveFacilityEntity);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return reserveFacilityEntity;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReserveFacility(@PathParam("id") Long rfId, ReserveFacilityEntity rf) {
        rf.setReserveFacilityId(rfId);
        try {
            reserveFacilitySessionLocal.updateEntity(rf);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReserveFacility(@PathParam("id") Long rfId) {
        try {
            reserveFacilitySessionLocal.deleteEntity(rfId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Reserve Facility not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }
}
