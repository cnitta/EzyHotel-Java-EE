/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.SalesCallResultSessionLocal;
import java.time.LocalDate;
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
import util.entity.SalesCallResultEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("salescallresults")
public class SalesCallResultResource {
    
    @EJB
    private SalesCallResultSessionLocal salesCallResultSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SalesCallResultResource
     */
    public SalesCallResultResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SalesCallResultEntity> retrieveAllSalesCallResults()
    {
        return salesCallResultSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalesCallResult(@PathParam("id") Long scrId){
        try{
            SalesCallResultEntity scr = salesCallResultSessionLocal.retrieveEntityById(scrId);
            return Response.status(200).entity(scr).type(MediaType.APPLICATION_JSON).build();
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
    public SalesCallResultEntity createSalesCallResult(SalesCallResultEntity scr){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        salesCallResultSessionLocal.createEntity(scr);
        return scr;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSalesCallResult(@PathParam("id") Long scrId, SalesCallResultEntity scr){
        scr.setSalesCallResultId(scrId);
        try{
            salesCallResultSessionLocal.updateEntity(scr);
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
    public Response deleteSalesCallResult(@PathParam("id") Long scrId){
        try{
            salesCallResultSessionLocal.deleteEntity(scrId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Sales Call Result not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}