/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.AffiliateSessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Path;
import util.entity.AffiliateEntity;
import util.enumeration.AffiliateStatusEnum;
import util.enumeration.AffiliateTypeEnum;
import util.exception.AffiliateNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("affiliates")
public class AffiliatesResource {
    AffiliateSessionLocal affiliateSessionLocal = lookupAffiliateSessionLocal();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAffiliate(AffiliateEntity a) {

            System.out.println("Create AffiliateEntity at AffiliateEntityResource");
            
            AffiliateEntity affiliate = affiliateSessionLocal.createAffiliate(a);

            return Response.status(Status.OK).entity(affiliate).build();
        
    } //end createAffiliateEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAffiliatesByAttributes(@QueryParam("affiliateName") String affiliateName,
            @QueryParam("organizationEntityNumber") String organizationEntityNumber,
            @QueryParam("businessAddress") String businessAddress,
            @QueryParam("representativeName") String representativeName,
            @QueryParam("email") String email,
            @QueryParam("contactNumber") String contactNumber,
            @QueryParam("type") AffiliateTypeEnum type,
//            @QueryParam("isPremium") Boolean isPremium,
            @QueryParam("status") AffiliateStatusEnum status){             

            
            AffiliateEntity affiliate = new AffiliateEntity(affiliateName, organizationEntityNumber, businessAddress, representativeName, email, contactNumber, type);
            affiliate.setAffiliateStatus(status);
         
            List<AffiliateEntity> results = affiliateSessionLocal.retrieveAffiliateByAffiliateAttributes(affiliate);

            GenericEntity<List<AffiliateEntity>> entity = new GenericEntity<List<AffiliateEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchAffiliateEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllAffiliateEntity() {
        System.out.println("***Restful retrieveAllAffiliates Started***");
        List<AffiliateEntity> results = affiliateSessionLocal.retrieveAllAffiliates(); 
        System.out.println("***Restful retrieveAllAffiliates Ended***");

        GenericEntity<List<AffiliateEntity>> entity = new GenericEntity<List<AffiliateEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAffiliateEntityById(@PathParam("id") Long aId) {
        try {
            System.out.println("Get AffiliateEntity at AffiliateEntityResource");
            AffiliateEntity affiliate = affiliateSessionLocal.retrieveAffiliateById(aId);

            System.out.println("AffiliateEntity e with id" + aId + " and email " + affiliate.getEmail());          

            return Response.status(Status.OK).entity(affiliate).build();

        } catch (AffiliateNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "AffiliateEntity Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
            
        } 
        catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getAffiliateEntity

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editAffiliate(@PathParam("id") Long dId, AffiliateEntity d) {        

            affiliateSessionLocal.updateAffiliate(d);
            return Response.status(Status.OK).build();
    } //end editAffiliateEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAffiliate(@PathParam("id") Long dId) {
        try {
            System.out.println("Delete AffiliateEntity is triggered at affiliateResources");
            affiliateSessionLocal.deleteAffiliate(dId);
            return Response.status(Status.OK).build();
        } 
        catch (AffiliateNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "AffiliateEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteAffiliateEntity  
    
    private AffiliateSessionLocal lookupAffiliateSessionLocal() {
        try {
            Context c = new InitialContext();
            return (AffiliateSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/AffiliateSession!hms.hotelstay.session.AffiliateSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
