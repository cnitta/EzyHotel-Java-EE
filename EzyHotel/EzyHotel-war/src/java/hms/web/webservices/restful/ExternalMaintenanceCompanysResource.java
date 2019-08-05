/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hotelstay.session.ExternalMaintenanceCompanySessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.entity.ExternalMaintenanceCompanyEntity;
import util.exception.ExternalMaintenanceCompanyNotFoundException;

/**
 * REST Web Service
 *
 * @author vincentyeo
 */
@Path("externalMaintenanceCompanys")
public class ExternalMaintenanceCompanysResource {
    ExternalMaintenanceCompanySessionLocal externalMaintenanceCompanySessionLocal = lookupExternalMaintenanceCompanySessionLocal();   

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createExternalMaintenanceCompany(ExternalMaintenanceCompanyEntity a) {

            System.out.println("Create ExternalMaintenanceCompanyEntity at ExternalMaintenanceCompanyEntityResource");
            
            ExternalMaintenanceCompanyEntity externalMaintenanceCompany = externalMaintenanceCompanySessionLocal.createExternalMaintenanceCompany(a);

            return Response.status(Status.OK).entity(externalMaintenanceCompany).build();
        
    } //end createExternalMaintenanceCompanyEntity 

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchExternalMaintenanceCompanysByAttributes(@QueryParam("companyName") String companyName,
                    @QueryParam("address") String address,
                    @QueryParam("pocName") String pocName,
                    @QueryParam("contactNum")String contactNum,
                    @QueryParam("email") String email,
                    @QueryParam("businessEntityNumber") String businessEntityNumber){             

            
            ExternalMaintenanceCompanyEntity externalMaintenanceCompany = new ExternalMaintenanceCompanyEntity();
            externalMaintenanceCompany.setAddress(address);
            externalMaintenanceCompany.setBusinessEntityNumber(businessEntityNumber);
            externalMaintenanceCompany.setCompanyName(companyName);
            externalMaintenanceCompany.setContactNum(contactNum);
            externalMaintenanceCompany.setEmail(email);
            externalMaintenanceCompany.setPocName(pocName);
         
            List<ExternalMaintenanceCompanyEntity> results = externalMaintenanceCompanySessionLocal.retrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes(externalMaintenanceCompany);

            GenericEntity<List<ExternalMaintenanceCompanyEntity>> entity = new GenericEntity<List<ExternalMaintenanceCompanyEntity>>(results) {
            };
            
            return Response.status(200).entity(entity).build();
        } //end searchExternalMaintenanceCompanyEntitys           
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllExternalMaintenanceCompanyEntity() {
        System.out.println("***Restful retrieveAllExternalMaintenanceCompanys Started***");
        List<ExternalMaintenanceCompanyEntity> results = externalMaintenanceCompanySessionLocal.retrieveAllExternalMaintenanceCompanys(); 
        System.out.println("***Restful retrieveAllExternalMaintenanceCompanys Ended***");

        GenericEntity<List<ExternalMaintenanceCompanyEntity>> entity = new GenericEntity<List<ExternalMaintenanceCompanyEntity>>(results) {
        };

        return Response.status(Status.OK).entity(entity).build();
    }  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExternalMaintenanceCompanyEntityById(@PathParam("id") Long mId) {
        try {
            System.out.println("Get ExternalMaintenanceCompanyEntity at ExternalMaintenanceCompanyEntityResource");
            ExternalMaintenanceCompanyEntity externalMaintenanceCompany = externalMaintenanceCompanySessionLocal.retrieveExternalMaintenanceCompanyById(mId);

            System.out.println("ExternalMaintenanceCompanyEntity e with id" + mId + " and name " + externalMaintenanceCompany.getCompanyName());          

            return Response.status(Status.OK).entity(externalMaintenanceCompany).build();

        } catch (ExternalMaintenanceCompanyNotFoundException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "ExternalMaintenanceCompanyEntity Not Found")
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
    } //end getExternalMaintenanceCompanyEntity

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editExternalMaintenanceCompany(@PathParam("id") Long mId, ExternalMaintenanceCompanyEntity m) {        

            externalMaintenanceCompanySessionLocal.updateExternalMaintenanceCompany(m);
            return Response.status(Status.OK).build();
    } //end editExternalMaintenanceCompanyEntity   

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExternalMaintenanceCompany(@PathParam("id") Long mId) {
        try {
            System.out.println("Delete ExternalMaintenanceCompanyEntity is triggered at externalMaintenanceCompanyResources");
            externalMaintenanceCompanySessionLocal.deleteExternalMaintenanceCompany(mId);
            return Response.status(Status.OK).build();
        } 
        catch (ExternalMaintenanceCompanyNotFoundException ex) 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "ExternalMaintenanceCompanyEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteExternalMaintenanceCompanyEntity    
    
    private ExternalMaintenanceCompanySessionLocal lookupExternalMaintenanceCompanySessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ExternalMaintenanceCompanySessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/ExternalMaintenanceCompanySession!hms.hotelstay.session.ExternalMaintenanceCompanySessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
