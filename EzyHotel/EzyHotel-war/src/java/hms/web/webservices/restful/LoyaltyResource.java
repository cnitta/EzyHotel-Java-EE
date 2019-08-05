/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.frontdesk.session.CustomerSessionLocal;
import hms.prepostarrival.session.LoyaltySessionLocal;
import hms.prepostarrival.session.LoyaltyTransactionSessionLocal;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.CustomerEntity;
import util.entity.LoyaltyEntity;
import util.enumeration.CustomerMembershipEnum;
import util.exception.LoyaltyException;
import util.exception.NoResultException;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("loyalties")
public class LoyaltyResource {

    CustomerSessionLocal customerSessionLocal = lookupCustomerSessionLocal();
    LoyaltySessionLocal loyaltySessionLocal = lookupLoyaltySessionLocal();

    @Context
    private UriInfo context;

    public LoyaltyResource() {
    }

    /**
     * Retrieves representation of an instance of
     * hms.web.webservices.restful.LoyaltyResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/retrieveLoyaltyPrograms")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllLoyaltyProgram() {
        try {
            List<String> loyaltyPrograms = loyaltySessionLocal.retrieveAllLoyaltyProgram();
            JsonArrayBuilder loyaltyProgramJson = Json.createArrayBuilder();

            for (String loyaltyProgram : loyaltyPrograms) {
                loyaltyProgramJson.add(Json.createObjectBuilder().add("loyaltyProgramType", loyaltyProgram));
            }

            JsonArray jsonArr = loyaltyProgramJson.build();
            return Response.status(Response.Status.OK).entity(jsonArr).type(MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/memberships")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCustomersMembership() throws LoyaltyException {
        List<CustomerEntity> customers = customerSessionLocal.retrieveAllEntites();
        JsonArrayBuilder customerMemberships = Json.createArrayBuilder();
        for (CustomerEntity customer : customers) {
            if (customer.getMembershipStatus().equals(CustomerMembershipEnum.MEMBER)) {
                LoyaltyEntity loyalty = new LoyaltyEntity();
                try {
                    loyalty = loyaltySessionLocal.retrieveLoyaltyByCustomerId(customer.getCustomerId());
                    customerMemberships.add(Json.createObjectBuilder()
                            .add("customerId", customer.getCustomerId())
                            .add("firstName", customer.getFirstName())
                            .add("lastName", customer.getLastName())
                            .add("custIdentity", customer.getCustIdentity())
                            .add("email", customer.getEmail())
                            .add("membershipType", loyalty.getMembershipType())
                            .add("currentPoints", loyalty.getCurrentPoint())
                            .add("maxPoints", loyalty.getMaxPoint())         
                    );
                } catch (LoyaltyException e) {
                    //Customer might not have an Loyalty yet cause newly joined, so create one
                    loyalty.setCustomer(customer);
                    loyalty.setStartDate(customer.getFirstJoined());
                    loyaltySessionLocal.createEntity(loyalty);

                    customerMemberships.add(Json.createObjectBuilder()
                            .add("customerId", customer.getCustomerId())
                            .add("firstName", customer.getFirstName())
                            .add("lastName", customer.getLastName())
                            .add("custIdentity", customer.getCustIdentity())
                            .add("email", customer.getEmail())
                            .add("membershipType", loyalty.getMembershipType())
                            .add("currentPoints", loyalty.getCurrentPoint())
                            .add("maxPoints", loyalty.getMaxPoint())
                    );

                }
            }
//            } else { //NON-MEMBER
//                customerMemberships.add(Json.createObjectBuilder()
//                        .add("firstName", customer.getFirstName())
//                        .add("lastName", customer.getLastName())
//                        .add("custIdentity", customer.getCustIdentity())
//                        .add("email", customer.getEmail())
//                        .add("membershipType", "NON-MEMBER")
//                );
//            }
        }
        JsonArray jsonArr = customerMemberships.build();
        return Response.status(Response.Status.OK).entity(jsonArr).type(MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/retrieveCustomers/{loyaltyProgram}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomersByLoyaltyProgram(@PathParam("loyaltyProgram") String membershipType) throws NoResultException {
        //Retrieve all the Loyalties By LoyaltyProgram

        try {
            //Safeguard: Make the memebership string to be in uppercase
            membershipType = membershipType.toUpperCase();
            List<LoyaltyEntity> loyalties = loyaltySessionLocal.retrieveAllLoyaltyByLoyaltyProgram(membershipType);

            JsonArrayBuilder customerLoyalties = Json.createArrayBuilder();

            for (LoyaltyEntity loyalty : loyalties) {
                //Get the customer entity by customerId
                CustomerEntity customer = customerSessionLocal.retrieveEntityById(loyalty.getCustomer().getCustomerId());

                //Compile the necessary information for Customer
                customerLoyalties.add(Json.createObjectBuilder()
                        .add("customerId", customer.getCustomerId())
                        .add("firstName", customer.getFirstName())
                        .add("lastName", customer.getLastName())
                        .add("custIdentity", customer.getCustIdentity())
                        .add("email", customer.getEmail())
                        .add("loyaltyPointId", loyalty.getLoyaltyPointId())
                        .add("membershipType", loyalty.getMembershipType())
                        .add("maxPoint", loyalty.getMaxPoint())
                        .add("currentPoint", loyalty.getCurrentPoint())
                        .add("startDate", loyalty.getStartDate().toString())
                );

            }

            JsonArray jsonArr = customerLoyalties.build();
            return Response.status(Response.Status.OK).entity(jsonArr).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.OK).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveLoyaltyByCustomerId(@PathParam("customerId") Long customerId) {
        try {
            LoyaltyEntity customerLoyalty = loyaltySessionLocal.retrieveLoyaltyByCustomerId(customerId);
            customerLoyalty.getCustomer().setPassword(null);
            return Response.status(Response.Status.OK).entity(customerLoyalty).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Customer cannot be found").build();
        }
    }

    @PUT
    @Path("/deductLoyaltyPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deductLoyaltyPointsByCustomerId(JsonObject details) throws LoyaltyException {
        Long customerId = new Long(details.getInt("customerId"));
        int pointsToDeduct = details.getInt("pointsToDeduct");

        boolean status = loyaltySessionLocal.deductLoyaltyPointsByCustomerId(customerId, pointsToDeduct);

        if (status) {
            return Response.status(Response.Status.OK).entity("Loyalty points have been deducted.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Loyalty point have not been deducted successfully.").build();
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

    private CustomerSessionLocal lookupCustomerSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/CustomerSession!hms.frontdesk.session.CustomerSessionLocal");
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
