/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.ProxyBidSessionLocal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.AffiliateContentEntity;
import util.entity.ProxyBidEntity;
import util.exception.ProxyBidNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("proxybids")
public class ProxyBidsResource {
    ProxyBidSessionLocal proxyBidSessionLocal = lookupProxyBidSessionLocal();
    
    @Context
    private UriInfo context;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProxyBid(ProxyBidEntity b) {

            System.out.println("Create ProxyBidEntity at ProxyBidEntityResource");
            
            ProxyBidEntity bid = proxyBidSessionLocal.createProxyBid(b);

            return Response.status(Status.OK).entity(bid).build();
        
    } //end createProxyBidEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProxyBidsByAttributes(@QueryParam("bidAmount") double bidAmount,
            @QueryParam("bidDateTime") String bidDateTimeString,
            @QueryParam("startDateTime") String startDateTimeString,
            @QueryParam("endDateTime") String endDateTimeString){             
            
            ProxyBidEntity bid = new ProxyBidEntity(bidAmount, bidAmount, bidAmount, null, null, null, null);
            
            if(bidDateTimeString != null)
            {
                try {  
                    String[] splited = bidDateTimeString.split("\\s+");

                    Date bidDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                    bid.setBidDateTime(bidDateTime);
                }
                catch (ParseException ex) {
                    Logger.getLogger(BidsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(startDateTimeString != null)
            {
                try {  
                    String[] splited = bidDateTimeString.split("\\s+");

                    Date startDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                    bid.setBidDateTime(startDateTime);
                }
                catch (ParseException ex) {
                    Logger.getLogger(BidsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            if(endDateTimeString != null)
            {
                try {  
                    String[] splited = bidDateTimeString.split("\\s+");

                    Date endDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                    bid.setBidDateTime(endDateTime);
                }
                catch (ParseException ex) {
                    Logger.getLogger(BidsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }               
         
            List<ProxyBidEntity> results = proxyBidSessionLocal.retrieveProxyBidByProxyBidAttributes(bid);

            GenericEntity<List<ProxyBidEntity>> entity = new GenericEntity<List<ProxyBidEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchProxyBidEntitys           

    @POST
    @Path("/affiliateContent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProxyBidsByAffiliateContentAttributes(AffiliateContentEntity affiliateContent){             
                   
        List<ProxyBidEntity> results = proxyBidSessionLocal.retrieveProxyBidByAffiliateContentAttributes(affiliateContent);

        GenericEntity<List<ProxyBidEntity>> entity = new GenericEntity<List<ProxyBidEntity>>(results) {
        };
            
            return Response.status(200).entity(entity).build();
        } //end searchProxyBidEntitys          
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllProxyBidEntity() {
        System.out.println("***Restful retrieveAllBids Started***");
        List<ProxyBidEntity> results = proxyBidSessionLocal.retrieveAllProxyBids(); 
        System.out.println("***Restful retrieveAllBids Ended***");

        GenericEntity<List<ProxyBidEntity>> entity = new GenericEntity<List<ProxyBidEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProxyBidEntityById(@PathParam("id") Long bId) {
        try {
            System.out.println("Get ProxyBidEntity at ProxyBidEntityResource");
            ProxyBidEntity bid = (ProxyBidEntity) proxyBidSessionLocal.retrieveProxyBidById(bId);

            System.out.println("ProxyBidEntity e with id" + bId);          

            return Response.status(Status.OK).entity(bid).build();

        } catch (ProxyBidNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "ProxyBidEntity Not Found")
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
    } //end getProxyBidEntity

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProxyBid(@PathParam("id") Long bId) {
        try {
            System.out.println("Delete ProxyBidEntity is triggered at bidResources");
            proxyBidSessionLocal.deleteProxyBid(bId);
            return Response.status(Status.OK).build();
        } 
        catch (ProxyBidNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "ProxyBidEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteProxyBidEntity           

    private ProxyBidSessionLocal lookupProxyBidSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProxyBidSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/ProxyBidSession!hms.hotelstay.session.ProxyBidSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
