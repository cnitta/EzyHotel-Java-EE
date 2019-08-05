/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.prepostarrival.session.LoyaltySessionLocal;
import hms.prepostarrival.session.LoyaltyTransactionSessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.LoyaltyEntity;
import util.entity.LoyaltyTransactionEntity;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("loyaltyTransactions")
public class LoyaltyTransactionsResource {
    LoyaltyTransactionSessionLocal loyaltyTransactionSessionLocal = lookupLoyaltyTransactionSessionLocal();
    LoyaltySessionLocal loyaltySessionLocal = lookupLoyaltySessionLocal();

    @Context
    private UriInfo context;


    public LoyaltyTransactionsResource() {}

    @GET
    @Path("/{loyaltyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveLoyaltyTransactionByLoyaltyId(@PathParam("loyaltyId") Long loyaltyId) {
        try{
            LoyaltyEntity getLoyalty = loyaltySessionLocal.retrieveEntityById(loyaltyId);
            List<LoyaltyTransactionEntity> loyaltyTransactions = loyaltyTransactionSessionLocal.retrieveLoyaltyTransactionByLoyaltyId(getLoyalty.getLoyaltyPointId());
            
            JsonArrayBuilder loyaltyTransactionsByLoyaltyId = Json.createArrayBuilder();

            for(LoyaltyTransactionEntity loyaltyTransaction: loyaltyTransactions){

                loyaltyTransactionsByLoyaltyId.add(Json.createObjectBuilder()
                        .add("loyaltyTransId", loyaltyTransaction.getLoyaltyTransnId())
                        .add("transactionPoint",loyaltyTransaction.getTransactionPoint())
                        .add("remainingPointsForTransaction",loyaltyTransaction.getRemainingPointForTransaction())
                        .add("isFullyRedeemed",loyaltyTransaction.isIsFullyRedeemed())
                        .add("dateCreated", loyaltyTransaction.getDateCreated().toString()));

            }
        
            JsonArray jsonArr = loyaltyTransactionsByLoyaltyId.build();
 
            return Response.status(Response.Status.OK).entity(jsonArr).type(MediaType.APPLICATION_JSON).build();
            
            
        }catch(Exception e){
             return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    
    private LoyaltySessionLocal lookupLoyaltySessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LoyaltySessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LoyaltySession!hms.prepostarrival.session.LoyaltySessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private LoyaltyTransactionSessionLocal lookupLoyaltyTransactionSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LoyaltyTransactionSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LoyaltyTransactionSession!hms.prepostarrival.session.LoyaltyTransactionSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
