/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.SupplierSessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
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
import util.entity.SupplierEntity;
import util.exception.SupplierNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("suppliers")
public class SuppliersResource {
    SupplierSessionLocal supplierSessionLocal = lookupSupplierSessionLocal();
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSupplier(SupplierEntity s) {

            System.out.println("Create SupplierEntity at SupplierEntityResource");
            
            SupplierEntity supplier = supplierSessionLocal.createSupplier(s);

            return Response.status(Status.OK).entity(supplier).build();
        
    } //end createSupplierEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSuppliersByAttributes(@QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("phoneNum") String phoneNum,
            @QueryParam("company") String company,
            @QueryParam("address") String address){             
            
            SupplierEntity supplier = new SupplierEntity(name, email, phoneNum, company, address);
         
            List<SupplierEntity> results = supplierSessionLocal.retrieveSupplierBySupplierAttributes(supplier);

            GenericEntity<List<SupplierEntity>> entity = new GenericEntity<List<SupplierEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchSupplierEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllSupplierEntity() {
        System.out.println("***Restful retrieveAllSuppliers Started***");
        List<SupplierEntity> results = supplierSessionLocal.retrieveAllSuppliers(); 
        System.out.println("***Restful retrieveAllSuppliers Ended***");

        GenericEntity<List<SupplierEntity>> entity = new GenericEntity<List<SupplierEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierEntityById(@PathParam("id") Long sId) {
        try {
            System.out.println("Get SupplierEntity at SupplierEntityResource");
            SupplierEntity supplier = supplierSessionLocal.retrieveSupplierById(sId);

            System.out.println("SupplierEntity e with id" + sId + " and name " + supplier.getName());          

            return Response.status(Status.OK).entity(supplier).build();

        } catch (SupplierNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "SupplierEntity Not Found")
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
    } //end getSupplierEntity

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editSupplier(@PathParam("id") Long sId, SupplierEntity s) {        

            supplierSessionLocal.updateSupplier(s);
            return Response.status(Status.OK).build();
    } //end editSupplierEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSupplier(@PathParam("id") Long sId) {
        try {
            System.out.println("Delete SupplierEntity is triggered at supplierResources");
            supplierSessionLocal.deleteSupplier(sId);
            return Response.status(Status.OK).build();
        } 
        catch (SupplierNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "SupplierEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteSupplierEntity         

    private SupplierSessionLocal lookupSupplierSessionLocal() {
        try {
            Context c = new InitialContext();
            return (SupplierSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/SupplierSession!hms.hotelstay.session.SupplierSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
