/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.frontdesk.session.CustomerSessionLocal;
import hms.frontdesk.session.RoomBookingSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.CustomerEntity;
import util.entity.PriceRateEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.RoomTypeEntity;
import util.enumeration.RoomStatusEnum;

/**
 * REST Web Service
 *
 * @author Wai Kit
 */
@Path("salesreporting")
public class SalesReportingResource {

    @EJB
    private RoomBookingSessionLocal roomBookingSessionLocal;

    @EJB
    private PriceRateSessionLocal priceRateSessionLocal;
    
    @EJB
    private CustomerSessionLocal customerSessionLocal;
    
    @EJB
    private RoomSessionLocal roomSessionLocal;

    @Context
    private UriInfo context;

    public SalesReportingResource() {
    }

    @GET
    @Path("/salesrevenue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response RetrieveSalesRevenue() {
        List<RoomBookingEntity> listOfRoomBookingEntity = roomBookingSessionLocal.retrieveAllBookingForStaff();

        List<CustomerEntity> listOfCustomersEntity = customerSessionLocal.retrieveAllEntites();
        
        List<RoomEntity> listOfRoomEntity = roomSessionLocal.retrieveAllEntities();
        
        JsonArrayBuilder salesPerformace = Json.createArrayBuilder();

        double totalRevenue = 0;
        int numberOfRoomUnoccupied = 0;

        for (int i = 0; i < listOfRoomBookingEntity.size(); i++) {

            RoomTypeEntity roomTypeEntity = listOfRoomBookingEntity.get(i).getRoomType();

            Date roomBookingDate = listOfRoomBookingEntity.get(i).getCheckInDateTime();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(roomBookingDate);

            List<PriceRateEntity> listOfPriceRateEntity = priceRateSessionLocal.retrieveAllEntitiesByRoomTypeId(roomTypeEntity.getRoomTypeId());

            double roomPrice = 0;

            for (int j = 0; j < listOfPriceRateEntity.size(); j++) {
                if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                    if (listOfPriceRateEntity.get(j).getRateTitle().equals("Leisure")) {
                        roomPrice = listOfPriceRateEntity.get(j).getMarkupPrice();
                        totalRevenue = totalRevenue + roomPrice;
                    }
                } else {
                    if (listOfPriceRateEntity.get(j).getRateTitle().equals("Standard")) {
                        roomPrice = listOfPriceRateEntity.get(j).getMarkupPrice();
                        totalRevenue = totalRevenue + roomPrice;
                    }
                }
            }
        }
        RoomStatusEnum rStatus = RoomStatusEnum.valueOf("UNOCCUPIED");
        for (int k = 0; k < listOfRoomEntity.size(); k++){
            if(listOfRoomEntity.get(k).getStatus().equals(rStatus)){
                numberOfRoomUnoccupied = numberOfRoomUnoccupied + 1;
            }
        }
        double unoccupiedRoomsDouble = numberOfRoomUnoccupied/(double)listOfRoomEntity.size()*100;
        int unoccupiedRoomsPecentage = (int)unoccupiedRoomsDouble;

        salesPerformace.add(Json.createObjectBuilder()
                .add("totalRevenue", totalRevenue)
                .add("totalCustomers", listOfCustomersEntity.size())
                .add("totalBookings", listOfRoomBookingEntity.size())
                .add("totalUnoccupiedRooms", unoccupiedRoomsPecentage));

        JsonArray array = salesPerformace.build();

        return Response.status(200).entity(array).type(MediaType.APPLICATION_JSON).build();
    }
}
