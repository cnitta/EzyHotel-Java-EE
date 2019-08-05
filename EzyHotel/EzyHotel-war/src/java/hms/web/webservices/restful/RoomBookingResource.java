/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import hms.frontdesk.session.RoomBookingSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response.Status;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;

/**
 * REST Web Service
 *
 * @author Nicholas Nah
 */
@Path("RoomBooking")
public class RoomBookingResource {

    @EJB
    private RoomSessionLocal roomSessionLocal;

    @EJB
    private RoomBookingSessionLocal roomBookingSessionLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(RoomBookingEntity rb) {
        System.out.println("***Create Booking at RoomBooking Resource Started ***\n");

        RoomBookingEntity booking = roomBookingSessionLocal.createBooking(rb);
        //Long custId = booking.getCustomer().getCustomerId();
        //System.out.println("Customer id: " + custId);
        try {

        } catch (Exception ex) {
            Logger.getLogger(RoomBookingResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("***Create Booking at RoomBooking Resource Ended ***\n");
        return Response.status(Response.Status.OK).entity(booking).build();

    } //end createBooking 

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") Long bId) {
        try {
            System.out.println("***Retrieve Booking by Id at RoomBooking Resource Started ***\n");
            RoomBookingEntity booking = roomBookingSessionLocal.retrieveBookingById(bId);
            //bookingRsp book = wrapBooking(booking);
            System.out.println("***Retrieve Booking by Id at RoomBooking Resource Ended ***\n");
            booking.getRoomType().setRooms(null);
            booking.getRoomType().setPicture(null);
            
            return Response.status(200).entity(booking).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Booking Not Found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end getBookingById 

    @GET
    @Path("/custBooking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecificBooking(@PathParam("id") Long bId) {
        System.out.println("***Retrieve Specific Booking at RoomBooking Resource Started ***\n");
        try {
            RoomBookingEntity booking = roomBookingSessionLocal.retrieveBookingById(bId);

            System.out.println("im at Booking " + booking);
            //convert check-in/ check-out date to string
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            //retrieve checkinDate
            Date checkinDate = booking.getCheckInDateTime();
            Date checkOutDate = booking.getCheckOutDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkinDate);
            String currOutDate = dateFormat.format(checkOutDate);
            //convert int to string
            String stayDays = Integer.toString(booking.getNumOfDays());
            if (booking.getRoomNumber() == null) {
                booking.setRoomNumber("0");
            }
            //create a json result with require fields
            JsonArrayBuilder builder = Json.createArrayBuilder();
            booking.setSpecialRequests("NONE");
            builder.add(Json.createObjectBuilder()
                    .add("bookingId", booking.getBookingId().toString())
                    .add("checkInDateTime", currStringDate)
                    .add("checkOutDateTime", currOutDate)
                    .add("custIdentity", booking.getCustomer().getCustIdentity())
                    .add("firstName", booking.getCustomer().getFirstName())
                    .add("lastName", booking.getCustomer().getLastName())
                    .add("numOfDays", stayDays)
                    .add("roomNumber", booking.getRoomNumber())
                    .add("status", booking.getStatus().toString())
            // .add("totalAmount", booking.getTotalAmount().toString())
            );

            JsonArray array = builder.build();

            System.out.println(array);
            //bookingRsp book = wrapBooking(booking);
            System.out.println("***Retrieve Specific Booking at RoomBooking Resource Ended ***\n");
            return Response.status(Status.OK).entity(array).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Booking Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }

    } //end getBookingById  

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
        try {
            System.out.println("***Retrieve All Booking at RoomBooking Resource Started ***\n");
            List<RoomBookingEntity> results = roomBookingSessionLocal.retrieveAllBookingForStaff();
            //List<bookingRsp> bookings = wrapBookings(results);

            GenericEntity<List<RoomBookingEntity>> entity = new GenericEntity<List<RoomBookingEntity>>(results) {
            };
            System.out.println("***Retrieve All Booking at RoomBooking Resource Ended ***\n");
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Booking Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getAllBookings

    @GET
    @Path("/getAllRoom/{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoomNo(@PathParam("bookingId") Long bId) {
        try {
            System.out.println("***Retrieve All Room Number at RoomBooking Resource Started ***\n");
            RoomBookingEntity currBooking = roomBookingSessionLocal.retrieveBookingById(bId);
            String custRoomType = currBooking.getRoomTypeCode();
            if (custRoomType.equals("SUP")) {
                custRoomType = "SUR";
            }
            System.out.println(custRoomType);
            List<RoomEntity> getAvaRoom = roomBookingSessionLocal.retrieveRoomNumber(currBooking);
            for (int i = 0; i < getAvaRoom.size(); i++) {
                String checkRoomType = getAvaRoom.get(i).getRoomType().getRoomTypecode();
                //System.out.println(checkRoomType);
                //check whether room is clean
                String roomKeeper = getAvaRoom.get(i).getCleaningStatus();
                System.out.println("Room status (Housekeeping): " + roomKeeper);
                if (!custRoomType.equals(checkRoomType) || roomKeeper.equals("Dirty")) {
                    //roomTypeCode is wrongly place or room is still dirty, remove it
                    System.out.println("Room Type Code is wrong or room is still dirty. Removing in progress");
                    getAvaRoom.remove(i);
                }
                
                getAvaRoom.get(i).setRoomType(null);
            }
            GenericEntity<List<RoomEntity>> entity = new GenericEntity<List<RoomEntity>>(getAvaRoom) {
            };
            System.out.println("***Retrieve All Room Number at RoomBooking Resource Ended ***\n");
            return Response.status(Response.Status.OK).entity(entity).build();
        } catch (Exception e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Booking Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }//end getAllRoomNo
    }

    @GET
    @Path("/viewRoomStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRoomStatus() {
        System.out.println("***Retrieve All Rooms At RoomBookingResource Started ***\n");
        List<RoomEntity> rooms = roomSessionLocal.retrieveAllEntities();
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < rooms.size(); i++) {
            builder.add(Json.createObjectBuilder()
                    .add("roomId", rooms.get(i).getRoomId().toString())
                    .add("roomName", rooms.get(i).getRoomType().getName())
                    .add("roomTypeId", rooms.get(i).getRoomType().getRoomTypeId().toString())
                    .add("roomTypeCode", rooms.get(i).getRoomType().getRoomTypecode())
                    .add("roomNumber", rooms.get(i).getRoomUnitNumber().toString())
                    .add("roomStatus", rooms.get(i).getStatus().toString())
                    .add("roomKeeping", rooms.get(i).getCleaningStatus())
            );

        }
        JsonArray array = builder.build();
        System.out.println("***Retrieve All Rooms At RoomBookingResource Ended ***\n");
        return Response.status(200).entity(array).build();
    } //end retrieveAllRoomStatus

    @GET
    @Path("/viewCalendar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCalendar() {
        System.out.println("***Restful View in Calendar for RoomBooking Started***");

        //obtain list of today check-in via roomBookingSession.java
        List<RoomBookingEntity> viewCalendar = roomBookingSessionLocal.retrieveAllBookingForStaff();
        System.out.println("Number of bookings in database: " + viewCalendar.size());
        //DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z ", Locale.ENGLISH);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < viewCalendar.size(); i++) {
            //retrieve checkinDate
            Date checkinDate = viewCalendar.get(i).getCheckInDateTime();
            Date checkOutDate = viewCalendar.get(i).getCheckOutDateTime();
            //format to string
            String currInDate = dateFormat.format(checkinDate);
            String currOutDate = dateFormat.format(checkOutDate);

            /*split according to Year, month and date
             String inDate[] = currInDate.split("/");
             int intInDay = Integer.parseInt(inDate[2]);
             String inDay = String.valueOf(intInDay);
             int month = Integer.parseInt(inDate[1]);
             int newMonth = month - 1;
             String inMonth = String.valueOf(newMonth);
             //System.out.println("Day in String: " + inDay);
             //System.out.println("Month in integer: " + inMonth);
             String inYear = inDate[0];
             //String inMonth = inDate[1];
             //String inDay = inDate[2];

             String outDate[] = currOutDate.split("/");
             String outYear = outDate[0];
             int intOutDay = Integer.parseInt(outDate[2]);
             String outDay = String.valueOf(intOutDay);
             int varMonth = Integer.parseInt(outDate[1]);
             int newOutMonth = varMonth - 1;
             String outMonth = String.valueOf(newOutMonth);
             //String outMonth = outDate[1];
             //String outDay = outDate[2];
            
             */
            boolean allDay = true;
            //Title
            String title = viewCalendar.get(i).getCustomer().getFirstName() + " " + viewCalendar.get(i).getCustomer().getLastName();
            //HexColor
            String hexColor = "FF5722";
            builder.add(Json.createObjectBuilder()
                    //.add("roomBookingId", viewCalendar.get(i).getBookingId().toString())
                    .add("title", title)
                    .add("allDay", allDay)
                    .add("start", currInDate)
                    .add("end", currOutDate)
                    //.add("start", inYear + "," + inMonth + "," + inDay)
                    //.add("end", outYear + "," + outMonth + "," + outDay)
                    //.add("start", "new Date ( " + inYear + "," + inMonth + "," + inDay + " )")
                    //.add("end", "new Date ( " + outYear + "," + outMonth + "," + outDay + " )")
                    .add("roomNumber", viewCalendar.get(i).getRoomNumber())
                    .add("roomType", viewCalendar.get(i).getRoomTypeCode())
                    .add("roomStatus", viewCalendar.get(i).getStatus().toString())
                    .add("hexColor", hexColor)
            //.add("checkInYear", inYear)
            //.add("checkInMonth", inMonth)
            //.add("checkInDay", inDay)
            //.add("checkOutYear", outYear)
            //.add("checkOutMonth", outMonth)
            //.add("checkOutDay", outDay)

            );

        }

        JsonArray array = builder.build();

        System.out.println("***Restful View in Calendar for RoomBooking Ended***");
        return Response.status(Response.Status.OK).entity(array).build();

    } //end viewCalendar

}
