/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.MerchandisePurchaseOrderSessionLocal;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.MerchandisePurchaseOrderEntity;
import util.exception.MerchandisePurchaseOrderNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("merchandisePurchaseOrders")
public class MerchandisePurchaseOrdersResource {
    MerchandisePurchaseOrderSessionLocal merchandisePurchaseOrderSessionLocal = lookupMerchandisePurchaseOrderSessionLocal();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMerchandisePurchaseOrder(MerchandisePurchaseOrderEntity a) {

            System.out.println("Create MerchandisePurchaseOrderEntity at MerchandisePurchaseOrderEntityResource");
            
            MerchandisePurchaseOrderEntity merchandisePurchaseOrder = merchandisePurchaseOrderSessionLocal.createMerchandisePurchaseOrder(a);

            return Response.status(Status.OK).entity(merchandisePurchaseOrder).build();
        
    } //end createMerchandisePurchaseOrderEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMerchandisePurchaseOrdersByAttributes(@QueryParam("date") String dateString){             

        MerchandisePurchaseOrderEntity merchandisePurchaseOrder = new MerchandisePurchaseOrderEntity();
        
            if(dateString != null)
            {
                try {            
                String[] splited = dateString.split("\\s+");

                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(splited[0] + "+" + splited[1]);
                merchandisePurchaseOrder.setOrderDate(date);
                
                }
                catch (ParseException ex) {
                    Logger.getLogger(DevicesResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
                    
            List<MerchandisePurchaseOrderEntity> results = merchandisePurchaseOrderSessionLocal.retrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes(merchandisePurchaseOrder);

            GenericEntity<List<MerchandisePurchaseOrderEntity>> entity = new GenericEntity<List<MerchandisePurchaseOrderEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchMerchandisePurchaseOrderEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllMerchandisePurchaseOrderEntity() {
        System.out.println("***Restful retrieveAllMerchandisePurchaseOrders Started***");
        List<MerchandisePurchaseOrderEntity> results = merchandisePurchaseOrderSessionLocal.retrieveAllMerchandisePurchaseOrders(); 
        System.out.println("***Restful retrieveAllMerchandisePurchaseOrders Ended***");

        GenericEntity<List<MerchandisePurchaseOrderEntity>> entity = new GenericEntity<List<MerchandisePurchaseOrderEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchandisePurchaseOrderEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get MerchandisePurchaseOrderEntity at MerchandisePurchaseOrderEntityResource");
            MerchandisePurchaseOrderEntity merchandisePurchaseOrder = merchandisePurchaseOrderSessionLocal.retrieveMerchandisePurchaseOrderById(mId);

            System.out.println("MerchandisePurchaseOrderEntity e with id" + mId);          

            return Response.status(Status.OK).entity(merchandisePurchaseOrder).build();

        } catch (MerchandisePurchaseOrderNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandisePurchaseOrderEntity Not Found")
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
    } //end getMerchandisePurchaseOrderEntity

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editMerchandisePurchaseOrder(@PathParam("id") Long dId, MerchandisePurchaseOrderEntity d) {        

            merchandisePurchaseOrderSessionLocal.updateMerchandisePurchaseOrder(d);
            return Response.status(Status.OK).build();
    } //end editMerchandisePurchaseOrderEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMerchandisePurchaseOrder(@PathParam("id") Long dId) {
        try {
            System.out.println("Delete MerchandisePurchaseOrderEntity is triggered at merchandisePurchaseOrderResources");
            merchandisePurchaseOrderSessionLocal.deleteMerchandisePurchaseOrder(dId);
            return Response.status(Status.OK).build();
        } 
        catch (MerchandisePurchaseOrderNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandisePurchaseOrderEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteMerchandisePurchaseOrderEntity      
    
    private MerchandisePurchaseOrderSessionLocal lookupMerchandisePurchaseOrderSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MerchandisePurchaseOrderSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MerchandisePurchaseOrderSession!hms.hotelstay.session.MerchandisePurchaseOrderSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
