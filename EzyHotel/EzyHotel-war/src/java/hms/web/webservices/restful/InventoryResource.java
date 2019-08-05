/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.housekeeping.session.InventorySessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import util.entity.InventoryEntity;

/**
 * REST Web Service
 *
 * @author bryantan
 */
@Path("inventory")
public class InventoryResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of InventoryResource
     */
    public InventoryResource() {
    }
    
    @EJB
    private InventorySessionLocal inventorySessionLocal;


//    @GET
//    @Produces("application/json")
//    public Response getJson() {
//       List<InventoryEntity> inventories = inventorySessionLocal.retrieveAllEntities();
//       
//       JsonArrayBuilder builder = Json.createArrayBuilder();
//       
//       for (int i = 0; i < inventories.size(); i++) {
//            builder.add(Json.createObjectBuilder()
//                    .add("name", inventories.get(i).getName())
//                    .add("description", inventories.get(i).getDescription())
//                    .add("quantity", inventories.get(i).getQuantityInStock())
//                    .add("threshold", inventories.get(i).getThresholdQuantity())
//            );
//       }
//       JsonArray array = builder.build();
//       
//       return Response.status(Response.Status.OK).entity(array).build();
//    }
    
    @GET
    @Produces("application/json")
    public List<InventoryEntity> getJson() {
        return inventorySessionLocal.retrieveAllEntities();
    }

}
