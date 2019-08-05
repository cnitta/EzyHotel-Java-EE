/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.RoomAmenitySessionLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import util.entity.RoomAmenityEntity;
import util.entity.RoomTypeEntity;

/**
 * REST Web Service
 *
 * @author nittayawancharoenkharungrueang
 */
@Path("room-amenities")
public class RoomAmenitiesResource {

    RoomAmenitySessionLocal roomAmenitySessionLocal = lookupRoomAmenitySessionLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomAmenitiesResource
     */
    public RoomAmenitiesResource() {
    }

    private RoomAmenitySessionLocal lookupRoomAmenitySessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomAmenitySessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomAmenitySession!hms.hpm.session.RoomAmenitySessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoomAmenityEntity> retrieveAllRoomTypes() {
        return roomAmenitySessionLocal.retrieveAllEntities();
    } //end retrieve all romm amenities

}
