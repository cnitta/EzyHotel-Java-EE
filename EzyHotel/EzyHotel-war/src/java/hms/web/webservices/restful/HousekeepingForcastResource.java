/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.housekeeping.session.ForcastSessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import util.entity.HousekeepingForcastEntity;

/**
 * REST Web Service
 *
 * @author bryantan
 */
@Path("housekeepingForcast")
public class HousekeepingForcastResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HousekeepingForcastResource
     */
    public HousekeepingForcastResource() {
    }
    
    @EJB
    private ForcastSessionLocal forcastSessionLocal;

    /**
     * Retrieves representation of an instance of hms.web.webservices.restful.HousekeepingForcastResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<HousekeepingForcastEntity  > getAllForcasts() {
        return forcastSessionLocal.retrieveAllEntities();
    }

    /**
     * PUT method for updating or creating an instance of HousekeepingForcastResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
