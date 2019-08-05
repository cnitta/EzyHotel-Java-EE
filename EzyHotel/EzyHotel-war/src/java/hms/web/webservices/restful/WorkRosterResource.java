/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hr.session.StaffSessionLocal;
import hms.hr.session.WorkRosterSessionLocal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;
import util.exception.CustomNotFoundException;
import util.exception.WorkRosterException;
import util.exception.WorkRosterNotFoundException;

/**
 * REST Web Service
 *
 * @author USER
 */
@Path("workrosters")
public class WorkRosterResource {

    @Context
    private UriInfo context;

    @EJB
    private WorkRosterSessionLocal workRosterSessionLocal;
    @EJB
    private StaffSessionLocal staffSessionLocal;

    public WorkRosterResource() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWorkRoster(WorkRosterEntity w) {
        WorkRosterEntity s = workRosterSessionLocal.createEntity(w);
        return Response.status(200).entity(w).type(MediaType.APPLICATION_JSON).build();

    } //end createWorkRoster

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkRosterEntity> getAllWorkRoster() {
        return workRosterSessionLocal.retrieveAllEntities();
    }//Get All work roster 

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkRosterById(@PathParam("id") Long wId) {
        try {
            WorkRosterEntity w = workRosterSessionLocal.retrieveEntityById(wId);
            return Response.status(200).entity(w).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException | NoResultException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }//Single Work roster 

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editWorkRoster(@PathParam("id") Long wId, WorkRosterEntity w) {
        w.setWorkRosterId(wId);
        try {
            WorkRosterEntity roster = workRosterSessionLocal.retrieveEntityById(wId);
            workRosterSessionLocal.updateEntity(w);
            return Response.status(204).build();
        } catch (CustomNotFoundException | NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editWorkRoster

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWorkRoster(@PathParam("id") Long wId) throws CustomNotFoundException, WorkRosterException {
        try {
            WorkRosterEntity rosters = workRosterSessionLocal.retrieveEntityById(wId);
            workRosterSessionLocal.deleteEntity(wId);
            return Response.status(204).build();
        } catch (CustomNotFoundException | NoResultException | WorkRosterException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "unable to delete")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteWorkRoster

    @POST
    @Path("/{workroster_id}/staff/{staff_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStaff(@PathParam("workroster_id") Long wId, @PathParam("staff_id") Long sId) {
        try {
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
            workRosterSessionLocal.addStaffToRoster(wId, staff);
            WorkRosterEntity roster = workRosterSessionLocal.retrieveEntityById(wId);

            return Response.status(204).entity(roster).build();
        } catch (CustomNotFoundException | WorkRosterException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end addStaff

    @DELETE
    @Path("/{workroster_id}/staff/{staff_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeStaff(@PathParam("workroster_id") Long wId, @PathParam("staff_id") Long sId) {
        try {
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
            workRosterSessionLocal.removeStaffFromRoster(wId, staff);
            WorkRosterEntity roster = workRosterSessionLocal.retrieveEntityById(wId);
            return Response.status(204).entity(roster).build();
        } catch (CustomNotFoundException | WorkRosterException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end removeStaff

    @GET
    @Path("/rosterStaff/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkRosterByStaffId(@PathParam("id") Long sId) {
        try {
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
            List<WorkRosterEntity> rosters = workRosterSessionLocal.retrieveWorkRosterBystaffId(sId);
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (WorkRosterEntity w : rosters) {
                builder = createJson(builder, w,staff);
            }
            JsonArray array = builder.build();
            //GenericEntity<List<WorkRosterEntity>> entity = new GenericEntity<List<WorkRosterEntity>>(rosters) {};

            return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
        } catch (CustomNotFoundException | NoResultException | WorkRosterException e) {
            System.out.println("catch error");
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }//Work Roster by staff

    private JsonArrayBuilder createJson(JsonArrayBuilder builder, WorkRosterEntity w, StaffEntity s) {

        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        JsonObjectBuilder object = Json.createObjectBuilder();
        if(s!=null){
            if(s.getStaffId()!=null){
                object.add("staffId", s.getStaffId());
            }
            if(s.getName()!=null){
                object.add("name", s.getName());
            }
            if(s.getDepartment()!=null){
                object.add("department", s.getDepartment().toString());
            }
        }
        if (w.getWorkRosterId() != null) {
            object.add("workRosterId", w.getWorkRosterId());
        }
        if (w.getWorkRosterName() != null) {
            object.add("workRosterName", w.getWorkRosterName());
        }
        if (w.getStartDateTime() != null) {
            object.add("startDateTime", df.format(w.getStartDateTime()));
        }
        if (w.getEndDateTime() != null) {
            object.add("endDateTime", df.format(w.getEndDateTime()));
        }
        if (w.getCreateDateTime() != null) {
            object.add("createDateTime", df.format(w.getCreateDateTime()));
        }
        if (w.getRosterStatus() != null) {
            object.add("rosterStatus", w.getRosterStatus().toString());
        }
        builder.add(object);
        return builder;
    }
}
