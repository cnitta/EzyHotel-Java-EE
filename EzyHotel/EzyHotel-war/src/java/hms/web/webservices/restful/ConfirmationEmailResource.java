/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.ConfirmationEmailSession;
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
import util.entity.ConfirmationEmailEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("confirmationemails")
public class ConfirmationEmailResource {

    @EJB
    private ConfirmationEmailSession confirmationEmailSessionLocal;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConfirmationEmailResource
     */
    public ConfirmationEmailResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConfirmationEmailEntity> retrieveAllConfirmationEmails()
    {
        return confirmationEmailSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfirmationEmail(@PathParam("id") Long ceId){
        try{
            ConfirmationEmailEntity ce = confirmationEmailSessionLocal.retrieveEntityById(ceId);
            return Response.status(200).entity(ce).type(MediaType.APPLICATION_JSON).build();
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
    public ConfirmationEmailEntity createConfirmationEmail(ConfirmationEmailEntity ce){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        confirmationEmailSessionLocal.createEntity(ce);
        return ce;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConfirmationEmail(@PathParam("id") Long ceId, ConfirmationEmailEntity ce){
        ce.setConfirmationEmailId(ceId);
        try{
            confirmationEmailSessionLocal.updateEntity(ce);
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
    public Response deleteConfirmationEmail(@PathParam("id") Long ceId){
        try{
            confirmationEmailSessionLocal.deleteEntity(ceId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Confirmation Email not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}