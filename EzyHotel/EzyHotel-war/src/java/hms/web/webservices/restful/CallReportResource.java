/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.CallReportSessionLocal;
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
import util.entity.CallReportEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("callreports")
public class CallReportResource {
    
    @EJB
    private CallReportSessionLocal callReportSessionLocal;

    @Context
    private UriInfo context;

    public CallReportResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CallReportEntity> retrieveAllCallReports()
    {
        return callReportSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCallReport(@PathParam("id") Long cpId){
        try{
            CallReportEntity cp = callReportSessionLocal.retrieveEntityById(cpId);
            return Response.status(200).entity(cp).type(MediaType.APPLICATION_JSON).build();
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
    public CallReportEntity createCallReport(CallReportEntity cp){
        Date date = new Date();
        cp.setCallDate(date);
        callReportSessionLocal.createEntity(cp);
        return cp;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCallReport(@PathParam("id") Long cpId, CallReportEntity cp){
        cp.setCallReportId(cpId);
        try{
            callReportSessionLocal.updateEntity(cp);
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
    public Response deleteCallReport(@PathParam("id") Long cpId){
        try{
            callReportSessionLocal.deleteEntity(cpId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Call Report not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
