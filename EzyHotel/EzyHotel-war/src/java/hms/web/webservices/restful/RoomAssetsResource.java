/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.hpm.session.RoomAssetSessionLocal;
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
import util.entity.RoomAssetEntity;

/**
 * REST Web Service
 *
 * @author nittayawancharoenkharungrueang
 */
@Path("room-assets")
public class RoomAssetsResource {
    RoomAssetSessionLocal roomAssetSessionLocal = lookupRoomAssetSessionLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoomAssetsResource
     */
    public RoomAssetsResource() {
    }

    private RoomAssetSessionLocal lookupRoomAssetSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomAssetSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomAssetSession!hms.hpm.session.RoomAssetSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoomAssetEntity> retrieveAllHotels() {
        return roomAssetSessionLocal.retrieveAllEntities();
    } //end retrieve all romm assets
}
