/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.sales.session.PromotionSessionLocal;
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
import util.entity.PromotionEntity;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("promotions")
public class PromotionResource {
    
    @EJB
    private PromotionSessionLocal promotionSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PromotionResource
     */
    public PromotionResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PromotionEntity> retrieveAllPromotions()
    {
        return promotionSessionLocal.retrieveAllEntities();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPromotion(@PathParam("id") Long pId){
        try{
            PromotionEntity p = promotionSessionLocal.retrieveEntityById(pId);
            return Response.status(200).entity(p).type(MediaType.APPLICATION_JSON).build();
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
    public PromotionEntity createPromotion(PromotionEntity p){
        LocalDate localDate = LocalDate.now();
        //cp.setCallDate(localDate);
        promotionSessionLocal.createEntity(p);
        return p;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePromotion(@PathParam("id") Long pId, PromotionEntity p){
        p.setPromotionId(pId);
        try{
            promotionSessionLocal.updateEntity(p);
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
    public Response deletePromotion(@PathParam("id") Long pId){
        try{
            promotionSessionLocal.deleteEntity(pId);
            return Response.status(204).build();
        } catch (NoResultException e){
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Promotion not found")
                    .build();
            
            return Response.status(404).entity(exception).build();
        }
    }
}
