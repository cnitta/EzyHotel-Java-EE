/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.frontdesk.session.InvoiceSessionLocal;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.InvoiceEntity;
import util.exception.NoResultException;

/**
 * REST Web Service
 *
 * @author Nicholas
 */
@Path("invoice")
public class InvoiceResource {

    @EJB
    InvoiceSessionLocal invoiceSessionLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createInvoice(InvoiceEntity i) {
        System.out.println("***Create Invoice At InvoiceResource Started ***\n");

        InvoiceEntity invoice = invoiceSessionLocal.createEntity(i);
        System.out.println("***Create Invoice At InvoiceResource Ended ***\n");
        return Response.status(Status.OK).entity(invoice).build();

    } //end createInvoice   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteInvoice(@PathParam("id") Long vId) {
        System.out.println("***Delete Invoice At InvoiceResource Started ***\n");
        invoiceSessionLocal.deleteEntity(vId);
        System.out.println("***Delete Invoice At InvoiceResource Ended ***\n");
        return Response.status(Status.OK).build();
    } //end deleteInvoice  

    @GET
    @Path("/{invoiceNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoiceNumber(@PathParam("invoiceNo") String vId) {
        try {
            System.out.println("***Restful Retrieve Invoice Number Started***");

            InvoiceEntity currInvoice = invoiceSessionLocal.retrieveByInvoiceNumber(vId);
            System.out.println("Retrieve invoice entity successfully with number: " + currInvoice.getInvoiceNo());

            GenericEntity<InvoiceEntity> entity = new GenericEntity<InvoiceEntity>(currInvoice) {
            };
            System.out.println("***Restful  Retrieve Invoice Number Ended***");
            return Response.status(Response.Status.OK).entity(entity).build();

        } catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getInvoiceNumber

    @GET
    @Path("/invoiceList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllUnpaidInvoice() {
        System.out.println("***Restful Retrieve all Unpaid InvoiceList Started***");

        //obtain list of unpaid invoice via InvoiceSession.java
        List<InvoiceEntity> unpaidList = invoiceSessionLocal.retrieveUnpaidInvoice();
        System.out.println("Number of Unpaid invoice: " + unpaidList.size());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < unpaidList.size(); i++) {
            //retrieve unpaid invoice date
            Date unpaidDate = unpaidList.get(i).getInvoiceDate();
            //format to string
            String currStringDate = dateFormat.format(unpaidDate);
            builder.add(Json.createObjectBuilder()
                    .add("invoiceId", unpaidList.get(i).getInvoiceId().toString())
                    .add("invoiceNo", unpaidList.get(i).getInvoiceNo())
                    .add("itemName", unpaidList.get(i).getItemName())
                    .add("itemDesc", unpaidList.get(i).getItemDesc())
                    .add("invoiceDate", currStringDate)
                    .add("status", unpaidList.get(i).getStatus())
                    .add("totalAmount", unpaidList.get(i).getTotalAmount().toString())
            );
        }
        JsonArray array = builder.build();

        System.out.println("***Restful Retrieve all Unpaid InvoiceList Ended***");
        return Response.status(Response.Status.OK).entity(array).build();

    } //end retrieveAllUnpaidInvoice

    @GET
    @Path("/invoice/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecificInvoice(@PathParam("id") Long vId) {
        System.out.println("***Retrieve Specific Invoice at Invoice Resource Started ***\n");
        try {
            InvoiceEntity currInvoice = invoiceSessionLocal.retrieveInvoiceById(vId);

            System.out.println("Specific Invoice number:  " + currInvoice.getInvoiceNo());
            // date to string
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //retrieve checkinDate
            Date invoiceDate = currInvoice.getInvoiceDate();
            //format to string
            String currStringDate = dateFormat.format(invoiceDate);
            //create a json result with require fields
            JsonArrayBuilder builder = Json.createArrayBuilder();
            builder.add(Json.createObjectBuilder()
                    .add("invoiceId", currInvoice.getInvoiceId().toString())
                    .add("invoiceNo", currInvoice.getInvoiceNo())
                    .add("itemName", currInvoice.getItemName())
                    .add("itemDesc", currInvoice.getItemDesc())
                    .add("invoiceDate", currStringDate)
                    .add("status", currInvoice.getStatus())
                    .add("totalAmount", currInvoice.getTotalAmount().toString())
            );

            JsonArray array = builder.build();
            System.out.println("***Retrieve Specific Invoice at Invoice Resource Ended ***\n");
            return Response.status(Status.OK).entity(array).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Invoice  Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    } //end getSpecificInvoice  

    @GET
    @Path("/paymentSucc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInvoiceSuccPayment(@PathParam("id") Long vId) {
        System.out.println("***Retrieve Specific Invoice when payment successful Started ***\n");
        try {
            InvoiceEntity currInvoice = invoiceSessionLocal.retrieveInvoiceById(vId);

            System.out.println("Specific Invoice number:  " + currInvoice.getInvoiceNo());
            // date to string
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //retrieve invoiceDate
            Date invoiceDate = currInvoice.getInvoiceDate();
            //format to string
            String currStringDate = dateFormat.format(invoiceDate);
            //set status to "Paid"
            String currStatus = currInvoice.getStatus();
            System.out.println("Current invoice status is: " + currStatus);
            if (currStatus.equals("Unpaid")) {
                currInvoice.setStatus("Paid");
                //update the status of curr invoice
                invoiceSessionLocal.updateEntity(currInvoice);
            }
            //create a json result with require fields
            JsonArrayBuilder builder = Json.createArrayBuilder();
            builder.add(Json.createObjectBuilder()
                    .add("invoiceId", currInvoice.getInvoiceId().toString())
                    .add("invoiceNo", currInvoice.getInvoiceNo())
                    .add("itemName", currInvoice.getItemName())
                    .add("itemDesc", currInvoice.getItemDesc())
                    .add("invoiceDate", currStringDate)
                    .add("status", currInvoice.getStatus())
                    .add("totalAmount", currInvoice.getTotalAmount().toString())
            );

            JsonArray array = builder.build();
            System.out.println("***Retrieve Specific Invoice when payment is successful Ended ***\n");
            return Response.status(Status.OK).entity(array).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Invoice  Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    } //end getInvoiceSuccPayment  

}
