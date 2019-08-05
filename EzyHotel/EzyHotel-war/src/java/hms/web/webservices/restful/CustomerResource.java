/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.EmailManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import hms.frontdesk.session.CustomerSessionLocal;
import hms.frontdesk.session.RoomBookingSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.PUT;
import util.entity.CustomerEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.RoomTypeEntity;
import util.enumeration.RoomBookingStatusEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.NoResultException;

/**
 * REST Web Service
 *
 * @author Nicholas Nah
 */
@Path("customer")

public class CustomerResource {

    @EJB
    RoomBookingSessionLocal roomBookingSessionLocal;

    @EJB
    private CustomerSessionLocal customerSessionLocal;

    @EJB
    private RoomSessionLocal roomSessionLocal;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CustomerEntity c) {
        System.out.println("***Create Customer At CustomerResource Started ***\n");

        CustomerEntity customer = customerSessionLocal.createEntity(c);
        System.out.println("***Create Customer At CustomerResource Ended ***\n");
        return Response.status(Status.OK).entity(customer).build();

    } //end createCustomer   

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAllCustomers() {
        System.out.println("***Retrieve All Customer At CustomerResource Started ***\n");
        // List<CustomerEntity> results = customerSessionLocal.retrieveAllEntites();
        List<RoomBookingEntity> bookingResults = roomBookingSessionLocal.retrieveAllBookingForStaff();
        List<RoomBookingEntity> listRoomBooking = new ArrayList<RoomBookingEntity>();
        RoomBookingEntity roomBooking = new RoomBookingEntity();
        roomBooking.setBookingId(Long.parseLong("1"));
        roomBooking.setCheckInDateTime(new Date("03/03/2019"));
        roomBooking.setCheckOutDateTime(new Date("08/03/2019"));
        roomBooking.setNumOfDays(6);
        roomBooking.setReservationNumber("ASDASDLKAHKSJDLASH");
        roomBooking.setRoomNumber("");
        roomBooking.setRoomTypeCode("SUP");
        roomBooking.setSpecialRequests(null);
        roomBooking.setStatus(RoomBookingStatusEnum.MANUAL);
        // roomBooking.setTotalAmount(BigDecimal.valueOf(253));
        listRoomBooking.add(roomBooking);

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < bookingResults.size(); i++) {
            bookingResults.get(i).setStatus(RoomBookingStatusEnum.MANUAL);
            bookingResults.get(i).setRoomNumber("0");
            builder.add(Json.createObjectBuilder()
                    .add("roomBookingId", bookingResults.get(i).getBookingId().toString())
                    .add("roomNumber", bookingResults.get(i).getRoomNumber())
                    .add("roomType", bookingResults.get(i).getRoomTypeCode())
                    .add("roomStatus", bookingResults.get(i).getStatus().toString())
                    .add("customerId", bookingResults.get(i).getCustomer().getCustomerId().toString())
                    .add("customerIdentity", bookingResults.get(i).getCustomer().getCustIdentity())
                    .add("firstName", bookingResults.get(i).getCustomer().getFirstName())
                    .add("lastName", bookingResults.get(i).getCustomer().getLastName())
                    .add("email", bookingResults.get(i).getCustomer().getEmail())
            );

        }

        JsonArray array = builder.build();

        System.out.println("***Retrieve All Customer At CustomerResource Ended ***\n");
        return Response.status(Status.OK).entity(array).build();
        /*
         System.out.println("***Restful searchAllCustomer Ended***");
         GenericEntity<List<CustomerEntity>> entity = new GenericEntity<List<CustomerEntity>>(results) {
         };
         return Response.status(Status.OK).entity(entity).build();
         */
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") Long cId) {
        try {
            System.out.println("***Delete Customer At CustomerResource Started ***\n");
            customerSessionLocal.deleteEntity(cId);
            System.out.println("***Delete Customer At CustomerResource Ended ***\n");
            return Response.status(Status.OK).build();
        } catch (NoResultException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "CustomerEntity is not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteCustomerEntity  

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerEntityById(@PathParam("id") Long cId) {
        try {
            System.out.println("***Retrieve Customer by Id at CustomerResource Started ***\n");
            //retrieve customer details by id
            CustomerEntity customer = customerSessionLocal.retrieveEntityById(cId);
            GenericEntity<CustomerEntity> entity = new GenericEntity<CustomerEntity>(customer) {
            };
            System.out.println("CustomerEntity c with id: " + cId + " and its identity " + customer.getCustIdentity());
            System.out.println("***Retrieve Customer by Id at CustomerResource Ended ***\n");
            return Response.status(Response.Status.OK).entity(entity).build();

        } catch (NoResultException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "CustomerEntity Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();

        } catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getCustomerEntity

    @GET
    @Path("/custId/{identity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerIdentity(@PathParam("identity") String cId) {
        try {
            System.out.println("***Restful Retrieve Customer Identity Started***");

            CustomerEntity customer = customerSessionLocal.retrieveCustId(cId);
            System.out.println("Retrieve customer entity successfully with identity: " + customer.getCustIdentity());

            //CustomerEntity customer = customerSessionLocal.retrieveEntityById(cId);
            GenericEntity<CustomerEntity> entity = new GenericEntity<CustomerEntity>(customer) {
            };
            System.out.println("CustomerEntity c with id: " + cId + " and its identity " + customer.getCustIdentity());
            System.out.println("***Restful Retrieve Customer Identity Ended***");
            return Response.status(Response.Status.OK).entity(entity).build();

        } catch (NoResultException e) {
            System.out.println("Error received " + e.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "CustomerEntity Not Found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();

        } catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getCustomerEntity

    @PUT
    @Path("/checkIn/{bookingId}/{RoomNo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CheckIn(@PathParam("bookingId") Long bId, @PathParam("RoomNo") String roomNo) throws NoResultException {
        System.out.println("***Restful Check-In Started***");
        //retrieve curr booking entity
        RoomBookingEntity booking = roomBookingSessionLocal.retrieveBookingById(bId);
        System.out.println("Retrieve booking id successful");
        //retrieve the curr room entity
        RoomEntity currRoom = customerSessionLocal.retrieveRoomId(roomNo);
        System.out.println("Retrieve current room in Room Enttiy successful");

        System.out.println("Current Room Number: " + booking.getRoomNumber());
        //Check whether if there is existing room number attach to booking
        if (booking.getRoomNumber().equals("0")) {
            //means its first time check in
            System.out.println("Enter 1st loop");
            booking.setRoomNumber(roomNo);
        } else {
            System.out.println("Enter else loop");
            String currRoomNumber = booking.getRoomNumber();
            RoomEntity prevRoom = customerSessionLocal.retrieveRoomId(currRoomNumber);
            System.out.println("retrieve previous room number if edit: " + prevRoom.getRoomUnitNumber());
            prevRoom.setStatus(RoomStatusEnum.UNOCCUPIED);
            roomSessionLocal.updateEntity(prevRoom);
            //after set prev room status to unoccupied, set current room number to this bookingF
            booking.setRoomNumber(roomNo);

        }
        //set association to RoomTypeEntity
        RoomTypeEntity getType = currRoom.getRoomType();
        booking.setRoomType(getType);
        //set room to be occupied and status to be check in
        booking.setStatus(RoomBookingStatusEnum.CHECKED_IN);
        //Set the status
        currRoom.setStatus(RoomStatusEnum.OCCUPIED);
        try {
            // update entity
            roomBookingSessionLocal.updateEntity(booking);
            roomSessionLocal.updateEntity(currRoom);
            System.out.println("Successfully Check in roomBooking with room number: " + roomNo);
            //send email for successful CHECK_IN
            customerSessionLocal.sendEmailNotificationRB(booking, "CHECKED_IN");
            System.out.println("***Restful Check-In Ended***");
            return Response.status(Status.OK).entity(booking).build();
        } catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }// end CheckIn

    @PUT
    @Path("/checkout/{bookingId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response CheckOut(@PathParam("bookingId") Long bId) throws NoResultException {
        System.out.println("***Restful Check-Out Started***");
        RoomBookingEntity booking = roomBookingSessionLocal.retrieveBookingById(bId);
        booking.setStatus(RoomBookingStatusEnum.CHECKED_OUT);
        String roomNumber = booking.getRoomNumber();
        //print out current room number assign to this booking
        System.out.println("Room Number for checkout: " + roomNumber);
        //Get Room number to set status
        RoomEntity checkOutRoom = customerSessionLocal.retrieveRoomId(roomNumber);
        checkOutRoom.setStatus(RoomStatusEnum.UNOCCUPIED);
        //Set cleaning status to be dirty
        checkOutRoom.setCleaningStatus("Dirty");
        try {
            // update RoomBookingEntity
            roomBookingSessionLocal.updateEntity(booking);
            //Update RoomEntity
            roomSessionLocal.updateEntity(checkOutRoom);
            System.out.println("Successfully Check Out by: " + booking.getCustomer().getCustIdentity());
            customerSessionLocal.sendEmailNotificationRB(booking, "CHECKED_OUT");
            System.out.println("***Restful Check-Out Ended***");
            return Response.status(Status.OK).entity(booking).build();
        } catch (Exception ex) {
            System.out.println("Error received " + ex.getMessage());
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }// end CheckOut

    @GET
    @Path("/todayCheckIn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCheckIn() {
        System.out.println("***Restful Retrieve all Check-In for Today Started***");

        //obtain list of today check-in via roomBookingSession.java
        List<RoomBookingEntity> CheckInList = roomBookingSessionLocal.retrieveTodayCheckIn();
        System.out.println("Number of Check-In for today: " + CheckInList.size());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < CheckInList.size(); i++) {
            CheckInList.get(i).setStatus(RoomBookingStatusEnum.MANUAL);
            CheckInList.get(i).setRoomNumber("0");
            //retrieve checkinDate
            Date checkinDate = CheckInList.get(i).getCheckInDateTime();
            Date checkOutDate = CheckInList.get(i).getCheckOutDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkinDate);
            String currOutDate = dateFormat.format(checkOutDate);
            builder.add(Json.createObjectBuilder()
                    .add("roomBookingId", CheckInList.get(i).getBookingId().toString())
                    .add("roomNumber", CheckInList.get(i).getRoomNumber())
                    .add("roomType", CheckInList.get(i).getRoomTypeCode())
                    .add("roomStatus", CheckInList.get(i).getStatus().toString())
                    .add("checkInDateTime", currStringDate)
                    .add("checkOutDateTime", currOutDate)
                    .add("customerId", CheckInList.get(i).getCustomer().getCustomerId().toString())
                    .add("customerIdentity", CheckInList.get(i).getCustomer().getCustIdentity())
                    .add("firstName", CheckInList.get(i).getCustomer().getFirstName())
                    .add("lastName", CheckInList.get(i).getCustomer().getLastName())
                    .add("email", CheckInList.get(i).getCustomer().getEmail())
            );

        }

        JsonArray array = builder.build();

        System.out.println("***Restful Retrieve all Check-In for Today Ended***");
        return Response.status(Response.Status.OK).entity(array).build();

    } //end retrieveCheckIn

    @GET
    @Path("/todayCheckOut")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCheckOut() {
        System.out.println("***Restful Retrieve all Check-Out for Today Started***");

        //obtain list of today check-in via roomBookingSession.java
        List<RoomBookingEntity> CheckOutList = roomBookingSessionLocal.retrieveTodayCheckOut();
        System.out.println("Number of Check-In for today: " + CheckOutList.size());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < CheckOutList.size(); i++) {
            //CheckOutList.get(i).setStatus(RoomBookingStatusEnum.MANUAL);
            //CheckOutList.get(i).setRoomNumber("0");
            //retrieve checkOutDate
            Date checkinDate = CheckOutList.get(i).getCheckInDateTime();
            Date checkOutDate = CheckOutList.get(i).getCheckOutDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkinDate);
            String currOutDate = dateFormat.format(checkOutDate);
            builder.add(Json.createObjectBuilder()
                    .add("roomBookingId", CheckOutList.get(i).getBookingId().toString())
                    .add("roomNumber", CheckOutList.get(i).getRoomNumber())
                    .add("roomType", CheckOutList.get(i).getRoomTypeCode())
                    .add("roomStatus", CheckOutList.get(i).getStatus().toString())
                    .add("checkInDateTime", currStringDate)
                    .add("checkOutDateTime", currOutDate)
                    .add("customerId", CheckOutList.get(i).getCustomer().getCustomerId().toString())
                    .add("customerIdentity", CheckOutList.get(i).getCustomer().getCustIdentity())
                    .add("firstName", CheckOutList.get(i).getCustomer().getFirstName())
                    .add("lastName", CheckOutList.get(i).getCustomer().getLastName())
                    .add("email", CheckOutList.get(i).getCustomer().getEmail())
            );

        }

        JsonArray array = builder.build();

        System.out.println("***Restful Retrieve all Check-In for Today Ended***");
        return Response.status(Response.Status.OK).entity(array).build();

    } //end retrieveCheckOut

    @GET
    @Path("/viewBookings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllBookings() {
        System.out.println("***Restful Retrieve bookings for Edit Room Allocation Started***");

        //obtain list of all bookings via roomBookingSession.java
        List<RoomBookingEntity> roomBookings = roomBookingSessionLocal.retrieveAllRoomBookingEdit();
        System.out.println("Number of RoomBookings made: " + roomBookings.size());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (int i = 0; i < roomBookings.size(); i++) {
            //retrieve checkOutDate
            Date checkinDate = roomBookings.get(i).getCheckInDateTime();
            Date checkOutDate = roomBookings.get(i).getCheckOutDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkinDate);
            String currOutDate = dateFormat.format(checkOutDate);

            builder.add(Json.createObjectBuilder()
                    .add("roomBookingId", roomBookings.get(i).getBookingId().toString())
                    .add("roomNumber", roomBookings.get(i).getRoomNumber())
                    .add("roomType", roomBookings.get(i).getRoomTypeCode())
                    .add("roomStatus", roomBookings.get(i).getStatus().toString())
                    .add("checkInDateTime", currStringDate)
                    .add("checkOutDateTime", currOutDate)
                    .add("customerId", roomBookings.get(i).getCustomer().getCustomerId().toString())
                    .add("customerIdentity", roomBookings.get(i).getCustomer().getCustIdentity())
                    .add("firstName", roomBookings.get(i).getCustomer().getFirstName())
                    .add("lastName", roomBookings.get(i).getCustomer().getLastName())
                    .add("email", roomBookings.get(i).getCustomer().getEmail())
            );

        }

        JsonArray array = builder.build();

        System.out.println("***Restful Retrieve bookings for Edit Room Allocation Ended***");
        return Response.status(Response.Status.OK).entity(array).build();

    } //end retrieveAllBookings

    private CustomerSessionLocal lookupDeviceSessionLocal() {
        try {
            Context c = new InitialContext();
            return (CustomerSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/CustomerSession!hms.frontdesk.session.CustomerSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomSessionLocal lookupRoomSessionLocal() {
        try {
            Context c = new InitialContext();
            return (RoomSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomSession!hms.hpm.session.RoomSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomBookingSessionLocal lookupRoomBookingSessionLocal() {
        try {
            Context c = new InitialContext();
            return (RoomBookingSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomBookingSession!hms.frontdesk.session.RoomBookingSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
