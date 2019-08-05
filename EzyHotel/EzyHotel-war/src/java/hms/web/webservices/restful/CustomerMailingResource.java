/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.CustomerMailingSessionLocal;
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
import util.entity.CustomerMailingEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("customermailings")
public class CustomerMailingResource {
    
    @EJB
    private CustomerMailingSessionLocal customerMailingSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerMailingResource
     */
    public CustomerMailingResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerMailingEntity> retrieveAllCustomerMailings()
    {
        return customerMailingSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerMailing(@PathParam("id") Long cmId){
        try{
            CustomerMailingEntity cm = customerMailingSessionLocal.retrieveEntityById(cmId);
            return Response.status(200).entity(cm).type(MediaType.APPLICATION_JSON).build();
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
    public CustomerMailingEntity createCustomerMailing(CustomerMailingEntity cm){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        customerMailingSessionLocal.createEntity(cm);
        return cm;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerMailing(@PathParam("id") Long cmId, CustomerMailingEntity cm){
        cm.setCustomerMailingId(cmId);
        try{
            customerMailingSessionLocal.updateEntity(cm);
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
    public Response deleteCustomerMailing(@PathParam("id") Long cmId){
        try{
            customerMailingSessionLocal.deleteEntity(cmId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Customer Mailing not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
