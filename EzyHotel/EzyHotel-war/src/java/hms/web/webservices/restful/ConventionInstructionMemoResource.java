/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.ConventionInstructionMemoSessionLocal;
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
import util.entity.ConventionInstructionMemoEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("conventioninstructionmemos")
public class ConventionInstructionMemoResource {
    
    @EJB
    private ConventionInstructionMemoSessionLocal conventionInstructionMemoSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConventionInstructionMemoResource
     */
    public ConventionInstructionMemoResource() {
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConventionInstructionMemoEntity> retrieveAllConventionInstructionMemos()
    {
        return conventionInstructionMemoSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConventionInstructionMemo(@PathParam("id") Long cimId){
        try{
            ConventionInstructionMemoEntity cim = conventionInstructionMemoSessionLocal.retrieveEntityById(cimId);
            return Response.status(200).entity(cim).type(MediaType.APPLICATION_JSON).build();
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
    public ConventionInstructionMemoEntity createConventionInstructionMemo(ConventionInstructionMemoEntity cim){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        conventionInstructionMemoSessionLocal.createEntity(cim);
        return cim;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConventionInstructionMemo(@PathParam("id") Long cimId, ConventionInstructionMemoEntity cim){
        cim.setConventionInstructionMemoId(cimId);
        try{
            conventionInstructionMemoSessionLocal.updateEntity(cim);
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
    public Response deleteConventionInstructionMemo(@PathParam("id") Long cimId){
        try{
            conventionInstructionMemoSessionLocal.deleteEntity(cimId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Convention Instruction Memo not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
