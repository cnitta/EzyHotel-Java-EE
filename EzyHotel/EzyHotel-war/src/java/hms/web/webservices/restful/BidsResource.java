/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.BidSessionLocal;
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
import util.entity.BidEntity;
import util.exception.BidNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("bids")
public class BidsResource {
    
    @Context
    private UriInfo context;
    
    BidSessionLocal bidSessionLocal = lookupBidSessionLocal();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBid(BidEntity b) {

            System.out.println("Create BidEntity at BidEntityResource");
            
            BidEntity bid = bidSessionLocal.createBid(b);

            return Response.status(Status.OK).entity(bid).build();
        
    } //end createBidEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBidsByAttributes(@QueryParam("bidAmount") double bidAmount,
            @QueryParam("bidDateTime") String bidDateTimeString,
            @QueryParam("startDateTime") String startDateTimeString,
            @QueryParam("endDateTime") String endDateTimeString){             
            
            BidEntity bid = new BidEntity(bidAmount, null, null, null, null);
            
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
         
            List<BidEntity> results = bidSessionLocal.retrieveBidByBidAttributes(bid);

            GenericEntity<List<BidEntity>> entity = new GenericEntity<List<BidEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchBidEntitys           

    @POST
    @Path("/affiliateContent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBidsByAffiliateContentAttributes(AffiliateContentEntity affiliateContent){             
                   
        List<BidEntity> results = bidSessionLocal.retrieveBidByAffiliateContentAttributes(affiliateContent);

        GenericEntity<List<BidEntity>> entity = new GenericEntity<List<BidEntity>>(results) {
        };
            
            return Response.status(200).entity(entity).build();
        } //end searchBidEntitys          
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllBidEntity() {
        System.out.println("***Restful retrieveAllBids Started***");
        List<BidEntity> results = bidSessionLocal.retrieveAllBids(); 
        System.out.println("***Restful retrieveAllBids Ended***");

        GenericEntity<List<BidEntity>> entity = new GenericEntity<List<BidEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBidEntityById(@PathParam("id") Long bId) {
        try {
            System.out.println("Get BidEntity at BidEntityResource");
            BidEntity bid = bidSessionLocal.retrieveBidById(bId);

            System.out.println("BidEntity e with id" + bId);          

            return Response.status(Status.OK).entity(bid).build();

        } catch (BidNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "BidEntity Not Found")
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
    } //end getBidEntity

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBid(@PathParam("id") Long bId) {
        try {
            System.out.println("Delete BidEntity is triggered at bidResources");
            bidSessionLocal.deleteBid(bId);
            return Response.status(Status.OK).build();
        } 
        catch (BidNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "BidEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteBidEntity      
    


    private BidSessionLocal lookupBidSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BidSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/BidSession!hms.hotelstay.session.BidSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
