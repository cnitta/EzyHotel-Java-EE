/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hr.session.StaffSessionLocal;
import hms.hr.session.WorkRosterSessionLocal;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;
import util.exception.CustomNotFoundException;
import util.exception.WorkRosterException;

/**
 * REST Web Service
 *
 * @author bryantan
 */
@Path("workRoster2")
public class WorkRoster2Resource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WorkRoster2Resource
     */
    public WorkRoster2Resource() {
    }

    @EJB
    private WorkRosterSessionLocal workRosterSessionLocal; 
    
    @EJB
    private StaffSessionLocal staffSessionLocal; 
    
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkRoster() {
        List<WorkRosterEntity> workRosters = workRosterSessionLocal.retrieveAllEntities();
        
        
        JsonArrayBuilder builder1 = Json.createArrayBuilder();
        
        for (int i = 0; i < workRosters.size(); i++) {
            JsonArrayBuilder builder2 = Json.createArrayBuilder();
            
            for (int j = 0; j < workRosters.get(i).getStaffs().size(); j++) {
                builder2.add(Json.createObjectBuilder()
                        .add("name", workRosters.get(i).getStaffs().get(j).getName())
                        .add("staffId", workRosters.get(i).getStaffs().get(j).getStaffId()));
            };
            
             builder1.add(Json.createObjectBuilder()
            .add("workRosterId", workRosters.get(i).getWorkRosterId())
            .add("staffs", builder2)
             );
                     
        }
        JsonArray array = builder1.build();
        
        return Response.status(Response.Status.OK).entity(array).build();
    }
    
    @DELETE
    @Path("/{workroster_id}/staff/{staff_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeStaff(@PathParam("workroster_id") Long wId, @PathParam("staff_id") Long sId) {
        try 
        {
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId); 
            workRosterSessionLocal.removeStaffFromRoster(wId, staff);
            WorkRosterEntity roster = workRosterSessionLocal.retrieveEntityById(wId); 
            return Response.status(204).entity(roster).build();
        } 
        catch (CustomNotFoundException| WorkRosterException e) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end removeStaff
}
