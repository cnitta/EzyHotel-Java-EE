/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.MerchandiseOrderItemSessionLocal;
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
import util.entity.MerchandiseOrderItemEntity;
import util.exception.MerchandiseOrderItemNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("merchandiseorderitems")
public class MerchandiseOrderItemsResource {
    MerchandiseOrderItemSessionLocal merchandiseOrderItemSessionLocal = lookupMerchandiseOrderItemSessionLocal();   
    
//    @Context
//    private UriInfo context;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMerchandiseOrderItem(MerchandiseOrderItemEntity m) {

            System.out.println("Create MerchandiseOrderItemEntity at MerchandiseOrderItemEntityResource");
            
            MerchandiseOrderItemEntity merchandiseOrderItem = merchandiseOrderItemSessionLocal.createMerchandiseOrderItem(m);

            return Response.status(Status.OK).entity(merchandiseOrderItem).build();
        
    } //end createMerchandiseOrderItemEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMerchandiseOrderItemsByAttributes(@QueryParam("name") String name,
            @QueryParam("description") String description,
            @QueryParam("unitPrice") String unitPrice){             

            Double uPrice = 0.00;
            
            if(unitPrice != null)
            {
                uPrice = Double.valueOf(unitPrice);
            }
                    
            MerchandiseOrderItemEntity merchandiseOrderItem = new MerchandiseOrderItemEntity(name, description, uPrice, null);
         
            List<MerchandiseOrderItemEntity> results = merchandiseOrderItemSessionLocal.retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes(merchandiseOrderItem);

            GenericEntity<List<MerchandiseOrderItemEntity>> entity = new GenericEntity<List<MerchandiseOrderItemEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchMerchandiseOrderItemEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllMerchandiseOrderItemEntity() {
        System.out.println("***Restful retrieveAllMerchandiseOrderItems Started***");
        List<MerchandiseOrderItemEntity> results = merchandiseOrderItemSessionLocal.retrieveAllMerchandiseOrderItems(); 
        System.out.println("***Restful retrieveAllMerchandiseOrderItems Ended***");

        GenericEntity<List<MerchandiseOrderItemEntity>> entity = new GenericEntity<List<MerchandiseOrderItemEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMerchandiseOrderItemEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get MerchandiseOrderItemEntity at MerchandiseOrderItemEntityResource");
            MerchandiseOrderItemEntity merchandiseOrderItem = merchandiseOrderItemSessionLocal.retrieveMerchandiseOrderItemById(mId);

            System.out.println("MerchandiseOrderItemEntity e with id" + mId + " and name " + merchandiseOrderItem.getName());          

            return Response.status(Status.OK).entity(merchandiseOrderItem).build();

        } catch (MerchandiseOrderItemNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandiseOrderItemEntity Not Found")
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
    } //end getMerchandiseOrderItemEntity

    //Not working
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editMerchandiseOrderItem(@PathParam("id") Long dId, MerchandiseOrderItemEntity d) {        

            merchandiseOrderItemSessionLocal.updateMerchandiseOrderItem(d);
            return Response.status(Status.OK).build();
    } //end editMerchandiseOrderItemEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMerchandiseOrderItem(@PathParam("id") Long dId) {
        try {
            System.out.println("Delete MerchandiseOrderItemEntity is triggered at merchandiseOrderItemResources");
            merchandiseOrderItemSessionLocal.deleteMerchandiseOrderItem(dId);
            return Response.status(Status.OK).build();
        } 
        catch (MerchandiseOrderItemNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "MerchandiseOrderItemEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteMerchandiseOrderItemEntity      

    private MerchandiseOrderItemSessionLocal lookupMerchandiseOrderItemSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MerchandiseOrderItemSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/MerchandiseOrderItemSession!hms.hotelstay.session.MerchandiseOrderItemSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
