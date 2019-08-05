/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.frontdesk.session.RoomOrderItemSessionLocal;
import hms.frontdesk.session.RoomOrderSessionLocal;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.InvoiceEntity;
import util.entity.RoomOrderEntity;
import util.entity.RoomOrderItemEntity;

/**
 * REST Web Service
 *
 * @author Nicholas
 */
@Path("RoomOrder")
public class RoomOrderResource {

    @EJB
    RoomOrderSessionLocal roomOrderSessionLocal;
    
    @EJB 
    RoomOrderItemSessionLocal roomOrderItemSessionLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoomOrder(RoomOrderEntity r) {
        System.out.println("***Create Room Order At RoomOrderResource Started ***\n");

        RoomOrderEntity roomOrder = roomOrderSessionLocal.createEntity(r);
        System.out.println("***Create Room Order At RoomOrderResource Ended ***\n");
        return Response.status(Response.Status.OK).entity(roomOrder).build();

    } //end createRoomOrder   

    @GET
    @Path("/roomOrderList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUnpaidRoomOrder() {
        System.out.println("***Restful Retrieve all Unpaid room order list Started***");

        //obtain list of unpaid room orders via RoomOrderSession.java
        List<RoomOrderEntity> unpaidList = roomOrderSessionLocal.retrieveUnpaidRoomOrders();
        System.out.println("Number of Unpaid roomOrders: " + unpaidList.size());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < unpaidList.size(); i++) {
            //retrieve unpaid invoice date
            Date unpaidDate = unpaidList.get(i).getRoomOrderDate();
            //format to string
            String currStringDate = dateFormat.format(unpaidDate);
            builder.add(Json.createObjectBuilder()
                    .add("roomOrderId", unpaidList.get(i).getRoomOrderId().toString())
                    .add("roomOrderName", unpaidList.get(i).getRoomOrderName())
                    .add("roomOrderDesc", unpaidList.get(i).getRoomOrderDesc())
                    .add("roomOrderDate", currStringDate)
                    .add("roomNumber", unpaidList.get(i).getRoomNumber())
                    .add("status", unpaidList.get(i).getStatus())
                    .add("totalAmount", unpaidList.get(i).getTotalAmount().toString())
            );
        }
        JsonArray array = builder.build();

        System.out.println("***Restful Retrieve all Unpaid room order list Ended***");
        return Response.status(Response.Status.OK).entity(array).build();

    } //end retrieveAllUnpaidRoomOrder

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecificRoomOrders(@PathParam("id") Long vId) {
        System.out.println("***Retrieve Specific Room Orders at RoomOrder Resource Started ***\n");
        try {
            RoomOrderEntity currRoomOrder = roomOrderSessionLocal.retrieveRoomOrderById(vId);

            System.out.println("Specific Room Order number:  " + currRoomOrder.getRoomNumber());
            // date to string
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //retrieve roomOrderDate
            Date roomOrderDate = currRoomOrder.getRoomOrderDate();
            //format to string
            String currStringDate = dateFormat.format(roomOrderDate);
            //create a json result with require fields
            JsonArrayBuilder builder = Json.createArrayBuilder();
            builder.add(Json.createObjectBuilder()
                    .add("roomOrderId", currRoomOrder.getRoomOrderId().toString())
                    .add("roomOrderName", currRoomOrder.getRoomOrderName())
                    .add("roomOrderDesc", currRoomOrder.getRoomOrderDesc())
                    .add("roomOrderDate", currStringDate)
                    .add("roomNumber", currRoomOrder.getRoomNumber())
                    .add("status", currRoomOrder.getStatus())
                    .add("totalAmount", currRoomOrder.getTotalAmount().toString())
            );

            JsonArray array = builder.build();
            System.out.println("***Retrieve Specific Room Orders at RoomOrder Resource Ended ***\n");
            return Response.status(Response.Status.OK).entity(array).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "RoomOrder  Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    } //end getSpecificRoomOrders  

    @GET
    @Path("/paymentSucc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomOrderSuccPayment(@PathParam("id") Long vId) {
        System.out.println("***Retrieve Specific RoomOrders when payment successful Started ***\n");
        try {
            RoomOrderEntity currRoomOrder = roomOrderSessionLocal.retrieveRoomOrderById(vId);

            System.out.println("Specific Room Order number:  " + currRoomOrder.getRoomNumber());
            // date to string
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //retrieve roomOrderDate
            Date roomOrderDate = currRoomOrder.getRoomOrderDate();
            //format to string
            String currStringDate = dateFormat.format(roomOrderDate);
            //set status to "Paid"
            String currStatus = currRoomOrder.getStatus();
            System.out.println("Current room order status is: " + currStatus);
            if (currStatus.equals("Unpaid")) {
                currRoomOrder.setStatus("Paid");
                //update the status of curr invoice
                roomOrderSessionLocal.updateEntity(currRoomOrder);
            }
            //create a json result with require fields
            JsonArrayBuilder builder = Json.createArrayBuilder();
            builder.add(Json.createObjectBuilder()
                    .add("roomOrderId", currRoomOrder.getRoomOrderId().toString())
                    .add("roomOrderName", currRoomOrder.getRoomOrderName())
                    .add("roomOrderDesc", currRoomOrder.getRoomOrderDesc())
                    .add("roomOrderDate", currStringDate)
                    .add("roomNumber", currRoomOrder.getRoomNumber())
                    .add("status", currRoomOrder.getStatus())
                    .add("totalAmount", currRoomOrder.getTotalAmount().toString())
            );

            JsonArray array = builder.build();
            System.out.println("***Retrieve Specific RoomOrders when payment is successful Ended ***\n");
            return Response.status(Response.Status.OK).entity(array).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "RoomOrders  Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    } //end getRoomOrderSuccPayment  
    
    @POST
    @Path("/addRoomOrderItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoomOrderItem(JsonObject request) {
        RoomOrderItemEntity orderItem = new RoomOrderItemEntity();
        orderItem.setSubItemName(request.getString("name"));
        orderItem.setSubItemDesc(request.getString("description"));
        orderItem.setSubAmount(new BigDecimal(request.getString("amount")));
        
        //get room order entitty based on roomnumber
        int roomNumber = request.getInt("roomNumber");
        orderItem.setRoomOrder(roomOrderSessionLocal.getRoomOrderByRoomNumber(String.valueOf(roomNumber)));
        roomOrderItemSessionLocal.createEntity(orderItem);
        return Response.status(Response.Status.OK).entity(orderItem).build();
    }
}
