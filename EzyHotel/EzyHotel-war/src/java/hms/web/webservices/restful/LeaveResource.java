/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hr.session.LeaveSessionLocal;
import hms.hr.session.StaffSessionLocal;
import java.text.SimpleDateFormat;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.LeaveEntity;
import util.entity.StaffEntity;
import util.enumeration.LeaveStatusEnum;
import util.exception.CustomNotFoundException;
import util.exception.LeaveException;

/**
 * REST Web Service
 *
 * @author USER
 */
@Path("leaves")
public class LeaveResource {

    LeaveSessionLocal leaveSessionLocal = lookupLeaveSessionLocal();
    StaffSessionLocal staffSessionLocal = lookupStaffSessionLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LeaveResource
     */
    public LeaveResource() {
    }

    @POST
    @Path("/staff/{staffId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLeave(@PathParam("staffId") Long sId, LeaveEntity l) {
        try {
            System.out.println("in");
            StaffEntity s = staffSessionLocal.retrieveEntityById(sId);
            System.out.println("staff");
            boolean validate = leaveSessionLocal.validateDates(l.getStartDateTime(), l.getEndDateTime());
            System.out.println("validate");
            boolean status = leaveSessionLocal.checkOverlappedLeaves(l, s);
            boolean checkQuota = leaveSessionLocal.checkExceedAnnualLeave(l, s);
            System.out.println("quota");
            if (validate) {
                System.out.println("check1"+validate);
                throw new LeaveException("Invalid input");
            }
            if (status) {
                System.out.println("check2");
                throw new LeaveException("Leave has been created");
            }
            if (checkQuota) {
                System.out.println("check3");
                throw new LeaveException("Leave Quota exceeded");
            }
             System.out.println("check");
            LeaveEntity leave = leaveSessionLocal.createEntity(l);
            System.out.println("create");
            leave.setLeaveStatus(LeaveStatusEnum.PENDING);
            staffSessionLocal.addLeave(sId, leave);
            System.out.println("add");
            return Response.status(200).entity(s).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException | LeaveException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end createLeave

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLeaves() {
        try {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            List<LeaveEntity> leaves = leaveSessionLocal.retrieveAllEntities();
            for (LeaveEntity l : leaves) {
                builder = createJson(builder, l);
            }
            JsonArray array = builder.build();
            //return leaveSessionLocal.retrieveAllEntities();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }//Get All Leaves

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeaveById(@PathParam("id") Long lId) {
        try {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            LeaveEntity leave = leaveSessionLocal.retrieveEntityById(lId);
            builder = createJson(builder, leave);
            JsonArray array = builder.build();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }//Single Leave 

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editLeave(@PathParam("id") Long lId, LeaveEntity l) {
        l.setLeaveId(lId);
        try {
            LeaveEntity leave = leaveSessionLocal.retrieveEntityById(lId);
            StaffEntity s = leaveSessionLocal.retrieveStaffEntityfromLeave(leave);
            boolean validate = leaveSessionLocal.validateDates(l.getStartDateTime(), l.getEndDateTime());
            boolean status = leaveSessionLocal.checkOverlappedLeaves(l, s);
            boolean checkQuota = leaveSessionLocal.checkExceedAnnualLeave(l, s);
            if (validate) {
                throw new LeaveException("Invalid input");
            }
            /*
            if (status) {
                throw new LeaveException("Leave has been created");
            }*/
            //approve of leave 
            if ((leave.getLeaveStatus().equals(LeaveStatusEnum.PENDING)) && (l.getLeaveStatus().equals(LeaveStatusEnum.APPROVED))) {
                if (checkQuota) {
                    throw new LeaveException("Leave Quota exceeded");
                }
                s = leaveSessionLocal.minusLeaveQuota(leave, s);
                staffSessionLocal.updateEntity(s);
            }
            //change from approve to reject 
            if (leave.getLeaveStatus().equals(LeaveStatusEnum.APPROVED) &&
                    ((l.getLeaveStatus().equals(LeaveStatusEnum.PENDING)||(l.getLeaveStatus().equals(LeaveStatusEnum.REJECTED))))) {
                s = leaveSessionLocal.addLeaveQuota(leave, s);
                staffSessionLocal.updateEntity(s);
            }
            leaveSessionLocal.updateEntity(l);
            return Response.status(204).build();
        } catch (CustomNotFoundException | LeaveException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editLeave

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLeave(@PathParam("id") Long lId) {
        try {
            LeaveEntity leave = leaveSessionLocal.retrieveEntityById(lId);
            StaffEntity staff = leaveSessionLocal.retrieveStaffEntityfromLeave(leave);
            if (staff != null) {
                staffSessionLocal.removeLeave(staff.getStaffId(), leave);
            } else {
                leaveSessionLocal.deleteEntity(lId);
            }
            return Response.status(204).build();
        } catch (CustomNotFoundException | NotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteLeave

    @GET
    @Path("/unapprovedLeave/staff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewUnapprovedLeave(@PathParam("id") Long sId) {
        try {
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId); 
            JsonArrayBuilder builder = Json.createArrayBuilder();
            List<LeaveEntity> leaves = leaveSessionLocal.viewUnapprovedLeave(sId);
            for (LeaveEntity l : leaves) {
                builder = createJson(builder, l);
            }
            JsonArray array = builder.build();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException | NotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }//Get All Leaves

    @GET
    @Path("/staff/{staffid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllLeaveByStaffId(@PathParam("staffid") Long sId) {
        try {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            List<LeaveEntity> leaves = leaveSessionLocal.viewAllLeaveByStaffId(sId);
            for (LeaveEntity l : leaves) {
                builder = createJson(builder, l);
            }
            JsonArray array = builder.build();
            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException | NotFoundException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }//Get All Leaves

    private StaffSessionLocal lookupStaffSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StaffSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/StaffSession!hms.hr.session.StaffSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private LeaveSessionLocal lookupLeaveSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LeaveSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LeaveSession!hms.hr.session.LeaveSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private JsonArrayBuilder createJson(JsonArrayBuilder builder, LeaveEntity l) throws CustomNotFoundException {

        JsonObjectBuilder object = Json.createObjectBuilder();
        if (l.getLeaveId() != null) {
            object.add("leaveId", l.getLeaveId());
        }
        if (l.getDescription() != null) {
            object.add("description", l.getDescription());
        }
        if (l.getStartDateTime() != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            object.add("startDateTime", df.format(l.getStartDateTime()));
        }
        if (l.getEndDateTime() != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            object.add("endDateTime", df.format(l.getEndDateTime()));
        }
        if (l.getLeaveCategory() != null) {
            object.add("leaveCategory", l.getLeaveCategory().toString());
        }
        if (l.getLeaveStatus() != null) {
            object.add("leaveStatus", l.getLeaveStatus().toString());
        }
        StaffEntity staff = leaveSessionLocal.retrieveStaffEntityfromLeave(l);
        if (staff != null) {
            object.add("staffId", staff.getStaffId());
            if (staff.getName() != null) {
                object.add("staffName", staff.getName());
            }
            if (staff.getDepartment() != null) {
                object.add("department", staff.getDepartment().toString());
            }
        }
        builder.add(object);
        return builder;
    }

}
