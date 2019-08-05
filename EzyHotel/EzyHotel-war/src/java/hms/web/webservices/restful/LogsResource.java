/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.commoninfra.session.LogSessionLocal;
import hms.datamodel.ws.RetrieveAllLogsRsp;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.LogEntity;
//import util.entity.HotelEntity;
//import util.entity.LogEntity;
//import util.enumeration.LogStatusEnum;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("logs")
public class LogsResource {
    
    @EJB
    private LogSessionLocal logSessionLocal;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LogResource
     */
    public LogsResource() {
    }

    /**
     * Retrieves representation of an instance of hms.web.webservices.restful.LogsResource
     * @return an instance of java.lang.String
     */
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllLogs() {
         List<LogEntity> logs = logSessionLocal.retrieveAllEntities();
         System.out.println("Logs retrieved size: " + logs.size());
        return Response.status(Response.Status.OK).entity(new RetrieveAllLogsRsp(logs)).build();
    }  
    

}
