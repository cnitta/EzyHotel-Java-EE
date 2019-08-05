/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.CommonMethods;
import hms.commoninfra.session.AccountSession;
import hms.commoninfra.session.AccountSessionLocal;
import hms.hpm.session.HotelSessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import hms.prepostarrival.session.LoyaltySessionLocal;
import hms.prepostarrival.session.LoyaltyTransactionSessionLocal;
import hms.prepostarrival.session.OnlineCustomerSessionLocal;
import hms.prepostarrival.session.OnlineRoomBookingSessionLocal;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.entity.BookOnBehalfEntity;
import static util.entity.BookmarkEntity_.customer;
import util.entity.CustomerEntity;
import util.entity.GroupBookingEntity;
import util.entity.HotelEntity;
import util.entity.LoyaltyTransactionEntity;
import static util.entity.LoyaltyTransactionEntity_.loyalty;
import static util.entity.LoyaltyTransactionEntity_.roomBooking;
import util.entity.PaymentEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomTypeEntity;
import util.enumeration.CustomerMembershipEnum;
import util.enumeration.GenderEnum;
import util.enumeration.GroupBookingEnum;
import util.exception.LoyaltyException;
import util.exception.RoomBookingException;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("onlineRoomBookings")
public class OnlineRoomBookingsResource {

    LoyaltyTransactionSessionLocal loyaltyTransactionSessionLocal = lookupLoyaltyTransactionSessionLocal();
    LoyaltySessionLocal loyaltySessionLocal = lookupLoyaltySessionLocal();
    HotelSessionLocal hotelSessionLocal = lookupHotelSessionLocal();
    AccountSessionLocal accountSessionLocal = lookupAccountSessionLocal();
    RoomTypeSessionLocal roomTypeSessionLocal = lookupRoomTypeSessionLocal();
    OnlineRoomBookingSessionLocal onlineRoomBookingSessionLocal = lookupOnlineRoomBookingSessionLocal();
    OnlineCustomerSessionLocal onlineCustomerSessionLocal = lookupOnlineCustomerSessionLocal();
    private CommonMethods commons = new CommonMethods();
    @Context
    private UriInfo context;

    public OnlineRoomBookingsResource() {
    }

    @GET
    @Path("/bookings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoomBookingsForAllTypes() {
        List<RoomBookingEntity> roomBookings = onlineRoomBookingSessionLocal.retrieveAllEntities();
        JsonArrayBuilder rbArrBuild = Json.createArrayBuilder();
        for (RoomBookingEntity rb : roomBookings) {

            //This is common for all room booking types
            if (rb.getClass().equals(GroupBookingEntity.class)) {

                GroupBookingEntity grb = (GroupBookingEntity) rb;
                if (grb.getGroupBookingType().equals(GroupBookingEnum.CORPORATE)) {
                    rbArrBuild.add(Json.createObjectBuilder()
                            .add("bookingId", rb.getBookingId())
                            .add("reservationNumber", rb.getReservationNumber())
                            .add("checkInDateTime", commons.dateFormat(rb.getCheckInDateTime()))
                            .add("checkOutDateTime", commons.dateFormat(rb.getCheckOutDateTime()))
                            .add("numOfDays", rb.getNumOfDays())
                            .add("status", rb.getStatus().toString())
                            .add("adultCount", rb.getAdultCount())
                            .add("childCount", rb.getChildCount())
                            .add("roomNumber", rb.getRoomNumber().toString())
                            .add("roomTypeId", rb.getRoomType().getRoomTypeId())
                            .add("roomTypeCode", rb.getRoomTypeCode())
                            .add("specialRequest", rb.getSpecialRequests())
                            .add("guestFirstName", "NOT APPLICABLE")
                            .add("guestLastName", "NOT APPLICABLE")
                            .add("guestEmail", "NOT APPLICABLE")
                            .add("groupBookingType", grb.getGroupBookingType().toString())
                            .add("companyName", grb.getCompanyName())
                            .add("firstName", rb.getCustomer().getFirstName())
                            .add("lastName", rb.getCustomer().getLastName())
                            .add("country", rb.getCustomer().getCountry())
                            .add("custIdentity", rb.getCustomer().getCustIdentity())
                            .add("email", rb.getCustomer().getEmail())
                            .add("phoneNum", rb.getCustomer().getPhoneNum())
                    );
                } else {
                    rbArrBuild.add(Json.createObjectBuilder()
                            .add("bookingId", rb.getBookingId())
                            .add("reservationNumber", rb.getReservationNumber())
                            .add("checkInDateTime", commons.dateFormat(rb.getCheckInDateTime()))
                            .add("checkOutDateTime", commons.dateFormat(rb.getCheckOutDateTime()))
                            .add("numOfDays", rb.getNumOfDays())
                            .add("status", rb.getStatus().toString())
                            .add("adultCount", rb.getAdultCount())
                            .add("childCount", rb.getChildCount())
                            .add("roomNumber", rb.getRoomNumber().toString())
                            .add("roomTypeId", rb.getRoomType().getRoomTypeId())
                            .add("roomTypeCode", rb.getRoomTypeCode())
                            .add("specialRequest", rb.getSpecialRequests())
                            .add("guestFirstName", "NOT APPLICABLE")
                            .add("guestLastName", "NOT APPLICABLE")
                            .add("guestEmail", "NOT APPLICABLE")
                            .add("groupBookingType", grb.getGroupBookingType().toString())
                            .add("companyName", "NOT APPLICABLE")
                            .add("firstName", rb.getCustomer().getFirstName())
                            .add("lastName", rb.getCustomer().getLastName())
                            .add("country", rb.getCustomer().getCountry())
                            .add("custIdentity", rb.getCustomer().getCustIdentity())
                            .add("email", rb.getCustomer().getEmail())
                            .add("phoneNum", rb.getCustomer().getPhoneNum())
                    );
                }

            } else if (rb.getClass().equals(BookOnBehalfEntity.class)) {
                rbArrBuild.add(Json.createObjectBuilder()
                        .add("bookingId", rb.getBookingId())
                        .add("reservationNumber", rb.getReservationNumber())
                        .add("checkInDateTime", commons.dateFormat(rb.getCheckInDateTime()))
                        .add("checkOutDateTime", commons.dateFormat(rb.getCheckOutDateTime()))
                        .add("numOfDays", rb.getNumOfDays())
                        .add("status", rb.getStatus().toString())
                        .add("adultCount", rb.getAdultCount())
                        .add("childCount", rb.getChildCount())
                        .add("roomNumber", rb.getRoomNumber().toString())
                        .add("roomTypeId", rb.getRoomType().getRoomTypeId())
                        .add("roomTypeCode", rb.getRoomTypeCode())
                        .add("specialRequest", rb.getSpecialRequests())
                        .add("guestFirstName", ((BookOnBehalfEntity) rb).getGuestFirstName())
                        .add("guestLastName", ((BookOnBehalfEntity) rb).getGuestLastName())
                        .add("guestEmail", ((BookOnBehalfEntity) rb).getGuestEmail())
                        .add("groupBookingType", "NOT APPLICABLE")
                        .add("companyName", "NOT APPLICABLE")
                        .add("firstName", rb.getCustomer().getFirstName())
                        .add("lastName", rb.getCustomer().getLastName())
                        .add("country", rb.getCustomer().getCountry())
                        .add("custIdentity", rb.getCustomer().getCustIdentity())
                        .add("email", rb.getCustomer().getEmail())
                        .add("phoneNum", rb.getCustomer().getPhoneNum())
                );
            } else {
                rbArrBuild.add(Json.createObjectBuilder()
                        .add("bookingId", rb.getBookingId())
                        .add("reservationNumber", rb.getReservationNumber())
                        .add("checkInDateTime", commons.dateFormat(rb.getCheckInDateTime()))
                        .add("checkOutDateTime", commons.dateFormat(rb.getCheckOutDateTime()))
                        .add("numOfDays", rb.getNumOfDays())
                        .add("status", rb.getStatus().toString())
                        .add("adultCount", rb.getAdultCount())
                        .add("childCount", rb.getChildCount())
                        .add("roomNumber", rb.getRoomNumber().toString())
                        .add("roomTypeId", rb.getRoomType().getRoomTypeId())
                        .add("roomTypeCode", rb.getRoomTypeCode())
                        .add("specialRequest", rb.getSpecialRequests())
                        .add("guestFirstName", "NOT APPLICABLE")
                        .add("guestLastName", "NOT APPLICABLE")
                        .add("guestEmail", "NOT APPLICABLE")
                        .add("groupBookingType", "NOT APPLICABLE")
                        .add("companyName", "NOT APPLICABLE")
                        .add("firstName", rb.getCustomer().getFirstName())
                        .add("lastName", rb.getCustomer().getLastName())
                        .add("country", rb.getCustomer().getCountry())
                        .add("custIdentity", rb.getCustomer().getCustIdentity())
                        .add("email", rb.getCustomer().getEmail())
                        .add("phoneNum", rb.getCustomer().getPhoneNum())
                );
            }
        }

        JsonArray jsonArr = rbArrBuild.build();
        return Response.status(Response.Status.OK).entity(jsonArr).type(MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Path("getRoomBooking")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomBookingByReservationNumber(JsonObject reservationDetails) {
        String reservationNumber = reservationDetails.getString("reservationNumber");
        String email = reservationDetails.getString("email");
        System.out.println("Reservation Number: " + reservationNumber + ", Email: " + email);

        try {
            //Find the Reservation Number from the Online Room Booking DB
            List<RoomBookingEntity> retrieveRoomBooking = onlineRoomBookingSessionLocal.retrieveEntityBy2Filters("reservationNumber", reservationNumber, "customer.email", email);
            if (!retrieveRoomBooking.isEmpty()) { //Should only have 1
                RoomBookingEntity customerRoomBooking = retrieveRoomBooking.get(0);
                return Response.status(Response.Status.CREATED).entity(customerRoomBooking).build();
            } else {

                throw new RoomBookingException("We are unable to retrieve your room booking. Please contact our customer support team for assistance. Thank you.");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /*Will only call this if payment is verified*/
    @POST
    @Path("createOnlineBooking")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoomBooking(JsonObject bookingDetails) throws ParseException, Exception, LoyaltyException {

        boolean isLogin = Boolean.valueOf(bookingDetails.getString("isLogin"));
        String bookingType = bookingDetails.getString("bookingType");
        System.out.println("START: BOOKING TYPE - " + bookingType);

        //The Customer is the single representative for the booking regardless of its type -> Common across the bookings
        //Customer Details
        CustomerEntity getCustomer = new CustomerEntity();
        boolean getCustomerProfile = false;

        //If non-member, the info will be included in the JsonObject, else, only the customerId will be included.
        if (isLogin) {
            System.out.println("Customer login as a member.");
            Long customerId = (long) bookingDetails.getInt("cId");
            getCustomer = onlineCustomerSessionLocal.retrieveEntityById(customerId);
            System.out.println("Customer Info when first retrieved: " + getCustomer.toString());
            getCustomerProfile = true;

        } else {
            System.out.println("Customer is a non-member.");
            String firstName = bookingDetails.getString("firstName");
            String lastName = bookingDetails.getString("lastName");
            GenderEnum gender = getGender(bookingDetails.getString("gender"));
            String country = bookingDetails.getString("country");
            String custIdentity = bookingDetails.getString("custIdentity");
            String email = bookingDetails.getString("email");
            String phoneNum = bookingDetails.getString("phoneNum");
            CustomerEntity customer = new CustomerEntity(firstName, lastName, gender, phoneNum, custIdentity, country, email);
            customer.setMembershipStatus(CustomerMembershipEnum.NON_MEMBER);
            getCustomerProfile = onlineCustomerSessionLocal.saveCustomerProfile(customer);
            getCustomer = onlineCustomerSessionLocal.retrieveEntity("email", customer.getEmail());
        }

        if (getCustomerProfile) {
            System.out.println("Able to save customer profile into DB");

            List<RoomBookingEntity> roomBookings = createBookingsByType(bookingDetails, bookingType, getCustomer);

            //Params required for PaymentEntity
            String cardHolderName = bookingDetails.getString("custCardHolderName");
            String hCreditCard = bookingDetails.getString("hCustCreditCard");
            String creditCardExpiryDate = bookingDetails.getString("creditCardExpiryDate");

            List<PaymentEntity> payments = new ArrayList<>();

            try {
                //Then create the respective payment
                if (bookingType.equals("INDIVIDUAL_BOOKING") || bookingType.equals("BOOK_ON_BEHALF_BOOKING")) {
                    //Create the room booking in the list.
                    System.out.println("IN THE CREATE BOOKING AND PAYMENT - SINGLE");
                    String priceRateTitle = bookingDetails.getString("priceRateTitle");
                    String finalPrice = bookingDetails.getString("finalPrice");

                    RoomBookingEntity bookingCreated = onlineRoomBookingSessionLocal.createSingleRoomBooking(roomBookings.get(0), getCustomer, priceRateTitle, finalPrice, cardHolderName, hCreditCard, creditCardExpiryDate);
                    PaymentEntity payment = onlineRoomBookingSessionLocal.createPaymentTransactionForSingleBooking(finalPrice, cardHolderName, hCreditCard, creditCardExpiryDate, bookingCreated, getCustomer);
                    payments.add(payment);

                } else {

                    System.out.println("IN THE CREATE BOOKING AND PAYMENT - GROUP");
                    List<RoomBookingEntity> bookingsCreated = onlineRoomBookingSessionLocal.createGoupRoomBooking(roomBookings, getCustomer, cardHolderName, hCreditCard, creditCardExpiryDate, bookingType);
                    payments = onlineRoomBookingSessionLocal.createPaymentTransactionForGroupBooking(roomBookings, getCustomer, cardHolderName, hCreditCard, creditCardExpiryDate);

                }

                String responseMsg = "Booking has been made successfully.";
                System.out.println("Membership Status: " + getCustomer.getMembershipStatus());

                if (isLogin && getCustomer.getMembershipStatus().equals(CustomerMembershipEnum.MEMBER)) {
                    //Create the loyaltyTransaction -> Link to Loyalty Entity, Payment-> RoomBooking
                    System.out.println("LOGIN & MEMBER BEFORE LOYALTY PROGRAM");
                    loyaltyProgramProcess(payments, getCustomer);
                    responseMsg = "Booking has been made successfully. Points has also been awarded, please check them in your customer profile.";
                }

                sendBookingNotification(getCustomer, roomBookings, payments, bookingType);

                return Response.status(Response.Status.CREATED).entity(responseMsg).build();

            } catch (Exception e) {
                //throw new Exception (e.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        } else {
            System.out.println("Data-related Failure: Please ensure that Price Rates and Room Type has been inserted into the DB.");
            return Response.status(Response.Status.BAD_REQUEST).entity("Room booking cannot be created.").build();

        }

    }

    @PUT
    @Path("updateBooking")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(JsonObject changesMade) {
        try {
            //Get the reservation number to retrieve from DB
            String reservationNumber = changesMade.getString("reservationNumber");
            String specialRequest = changesMade.getString("specialRequest");

            //Find the RoomBooking with the following details
            List<RoomBookingEntity> getRoomBookings = onlineRoomBookingSessionLocal.retrieveBookingsByReservationNumber(reservationNumber);
            System.out.println("Room bookings: " + getRoomBookings.toString());
            for (RoomBookingEntity rb : getRoomBookings) {
                rb.setSpecialRequests(specialRequest);
                onlineRoomBookingSessionLocal.updateRoomBooking(rb);
            }

            return Response.status(Response.Status.OK).entity("The special request has been updated.").build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("The special request is not udpated successfully.").build();
        }
    }

    /*
     @DELETE
     @Path("/cancellation/{reservationNumber}")
     @Produces(MediaType.APPLICATION_JSON)
     public Response cancelBooking(String reservationNumber){
     try{
     RoomBookingEntity getBooking = onlineRoomBookingSessionLocal.retrieveEntity("reservationNumber", reservationNumber);
     onlineRoomBookingSessionLocal.deleteEntity(getBooking.getBookingId());
     return Response.status(Response.Status.OK).entity("Room booking with reservation number " + reservationNumber + " has been cancelled successfully.").build(); 
     }catch(Exception e){
     return Response.status(Response.Status.BAD_REQUEST).entity("Room booking with reservation number " + reservationNumber + " cannot be found.").build(); 
     }
     }*/
    private void loyaltyProgramProcess(List<PaymentEntity> payments, CustomerEntity customer) throws LoyaltyException {
        try {
            List<LoyaltyTransactionEntity> loyaltyTransactions = loyaltyTransactionSessionLocal.createLoyaltyTransactions(payments, customer);
            loyaltySessionLocal.loyaltyPointsAwarded(loyaltyTransactions, customer);

        } catch (LoyaltyException e) {
            throw new LoyaltyException("We are unable to award you loyalty points. Please approach the Guest Service at the reception for assistance.");
        }
    }

    private void sendBookingNotification(CustomerEntity customer, List<RoomBookingEntity> newRoomBookings, List<PaymentEntity> payments, String bookingType) {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("In asynchronous CompletableFuture - Room Booking Notification");
                accountSessionLocal.sendNotificationEmail(new GenericEntity(customer, CustomerEntity.class), "BOOKING", newRoomBookings, payments, bookingType);
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private List<RoomBookingEntity> createBookingsByType(JsonObject bookingDetails, String bookingType, CustomerEntity getCustomer) {
        System.out.println("In create booking based on booking type: " + bookingType);

        HotelEntity hotel = hotelSessionLocal.retrieveEntity("name", bookingDetails.getString("hotelName"));

        //Common Booking Details across booking type
        String checkInDateString = bookingDetails.getString("arrivalDate");
        String checkOutDateString = bookingDetails.getString("departureDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date checkInDate = Date.valueOf(LocalDate.parse(checkInDateString, formatter));
        Date checkOutDate = Date.valueOf(LocalDate.parse(checkOutDateString, formatter));

        //Get RoomTypeEntity before creating the RoomBookingEntity
        long numOfDaysBtwn = ChronoUnit.DAYS.between(LocalDate.parse(checkInDate.toString()), LocalDate.parse(checkOutDate.toString()));
        int numOfDays = Math.toIntExact(numOfDaysBtwn);

        String specialRequest = bookingDetails.getString("specialRequest");
        if (specialRequest.isEmpty()) {
            specialRequest = "No special request stated by guest.";
        }
        RoomTypeEntity roomType = new RoomTypeEntity();
        String roomTypeCode = "";

        int adultCount = bookingDetails.getInt("adultCount");
        int childCount = bookingDetails.getInt("childCount");
        if ((bookingType.equals("INDIVIDUAL_BOOKING") || bookingType.equals("BOOK_ON_BEHALF_BOOKING"))) {
            roomTypeCode = bookingDetails.getString("roomTypeCode");
            List<RoomTypeEntity> roomTypeList = roomTypeSessionLocal.retrieveEntityBy2Filters("roomTypecode", roomTypeCode, "hotel.name", hotel.getName());
            if (!roomTypeList.isEmpty()) {
                roomType = roomTypeList.get(0);

            }
        }

        String roomQuantityJson = "";
        HashMap<String, Integer> roomList = new HashMap<>();
        if (bookingType.equals("GROUP_BOOKING") || bookingType.equals("CORP_BOOKING")) {
            roomQuantityJson = bookingDetails.getString("roomQuantityJson");
            roomList = convertStringToHashMap(roomQuantityJson);
        }

        List<RoomBookingEntity> roomBookings = new ArrayList<>();

        switch (bookingType) {
            case "CORP_BOOKING":
                String companyName = bookingDetails.getString("organization");
                System.out.println("In CORP BOOKING - createBookingsByType");
                System.out.println("HashMap: " + roomList);
                for (String key : roomList.keySet()) {
                    System.out.println("Key value: " + key);
                    System.out.println("Value for count: " + roomList.get(key));
                    for (int index = 0; index < roomList.get(key); index++) {
                        List<RoomTypeEntity> findRoomType = roomTypeSessionLocal.retrieveEntityBy2Filters("roomTypecode", key, "hotel.name", hotel.getName());
                        RoomBookingEntity corpRoomBooking = new GroupBookingEntity(GroupBookingEnum.CORPORATE, companyName, checkInDate, checkOutDate, numOfDays, findRoomType.get(0).getRoomTypecode(), adultCount, childCount, findRoomType.get(0));
                        corpRoomBooking.setRoomNumber("0");
                        corpRoomBooking.setSpecialRequests(specialRequest);
                        corpRoomBooking.setCustomer(getCustomer);

                        System.out.println("Corporate Booking Attributes: " + corpRoomBooking.toString());
                        System.out.println("Room Booking Attributes in Corporate: " + ((RoomBookingEntity) corpRoomBooking).toString());
                        roomBookings.add(corpRoomBooking);
                    }
                }

                break;

            case "GROUP_BOOKING":
                System.out.println("In GROUP BOOKING - createBookingsByType");
                System.out.println("HashMap: " + roomList);
                for (String key : roomList.keySet()) {
                    System.out.println("Key value: " + key);
                    System.out.println("Value for count: " + roomList.get(key));
                    for (int index = 0; index < roomList.get(key); index++) {
                        List<RoomTypeEntity> findRoomType = roomTypeSessionLocal.retrieveEntityBy2Filters("roomTypecode", key, "hotel.name", hotel.getName());
                        System.out.println("Find Room Type in GROUP BOOKING: " + findRoomType.toString());
                        RoomBookingEntity groupRoomBooking = new GroupBookingEntity(GroupBookingEnum.SOCIAL, "NOT APPLICABLE", checkInDate, checkOutDate, numOfDays, findRoomType.get(0).getRoomTypecode(), adultCount, childCount, findRoomType.get(0));
                        groupRoomBooking.setRoomNumber("0");
                        groupRoomBooking.setSpecialRequests(specialRequest);
                        groupRoomBooking.setCustomer(getCustomer);

                        System.out.println("Group Booking: " + groupRoomBooking.toString());
                        System.out.println("Room Booking Attributes in Group: " + ((RoomBookingEntity) groupRoomBooking).toString());
                        roomBookings.add(groupRoomBooking);
                    }
                }

                break;

            case "BOOK_ON_BEHALF_BOOKING":
                String guestFirstName = bookingDetails.getString("guestFirstName");
                String guestLastName = bookingDetails.getString("guestLastName");
                String guestEmail = bookingDetails.getString("guestEmail");
                System.out.println("Room Type in Book on Behalf: " + roomTypeCode);
                RoomBookingEntity bookOnBehalfBooking = new BookOnBehalfEntity(guestFirstName, guestLastName, guestEmail, checkInDate, checkOutDate, numOfDays, roomTypeCode, adultCount, childCount, roomType);
                bookOnBehalfBooking.setRoomNumber("0");
                bookOnBehalfBooking.setSpecialRequests(specialRequest);
                bookOnBehalfBooking.setCustomer(getCustomer);
                roomBookings.add(bookOnBehalfBooking);
                System.out.println("Book on Behalf Booking: " + bookOnBehalfBooking.toString());

                break;
            default:
                //INDIVIDUAL_BOOKING
                System.out.println("Creating Individual Booking");
                System.out.println("Room Type in Individual Booking: " + roomTypeCode);
                System.out.println("Room Type Entity: " + roomType.toString());
                RoomBookingEntity roomBooking = new RoomBookingEntity(checkInDate, checkOutDate, numOfDays, specialRequest, "0", roomTypeCode, adultCount, childCount, roomType, getCustomer);
                roomBookings.add(roomBooking);
                System.out.println("Individual Room Booking: " + roomBooking.toString());

                break;
        }
        return roomBookings;
    }

    private HashMap<String, Integer> convertStringToHashMap(String roomTypeQuantityJson) {
        HashMap<String, Integer> roomTypeQuantity = new HashMap<>();
        System.out.println("Inside of convert String to HashMap");
        try (
                JsonReader jsonReader = Json.createReader(new StringReader(roomTypeQuantityJson))) {
            System.out.println("jsonReader: " + jsonReader.toString());
            JsonObject obj = jsonReader.readObject();
            System.out.println("obj: " + obj.toString());

            // View all the keys and values
            for (String key : obj.keySet()) {
                roomTypeQuantity.put(key, obj.getInt(key));
                System.out.println("Key: " + key + ", value: " + obj.get(key));
            }

            return roomTypeQuantity;
        } catch (Exception e) {
            System.out.println("Error in converting: " + e.getMessage());
        }

        return roomTypeQuantity; // Return empty hashmap
    }

    private GenderEnum getGender(String genderString) {
        GenderEnum gender;
        switch (genderString) {
            case "FEMALE":
                gender = GenderEnum.FEMALE;
                break;
            default:
                gender = GenderEnum.MALE;
                break;
        }
        return gender;
    }

    private OnlineCustomerSessionLocal lookupOnlineCustomerSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OnlineCustomerSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/OnlineCustomerSession!hms.prepostarrival.session.OnlineCustomerSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OnlineRoomBookingSessionLocal lookupOnlineRoomBookingSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OnlineRoomBookingSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/OnlineRoomBookingSession!hms.prepostarrival.session.OnlineRoomBookingSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomTypeSessionLocal lookupRoomTypeSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RoomTypeSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/RoomTypeSession!hms.hpm.session.RoomTypeSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AccountSessionLocal lookupAccountSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AccountSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/AccountSession!hms.commoninfra.session.AccountSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private HotelSessionLocal lookupHotelSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (HotelSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/HotelSession!hms.hpm.session.HotelSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private LoyaltySessionLocal lookupLoyaltySessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LoyaltySessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LoyaltySession!hms.prepostarrival.session.LoyaltySessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private LoyaltyTransactionSessionLocal lookupLoyaltyTransactionSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LoyaltyTransactionSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LoyaltyTransactionSession!hms.prepostarrival.session.LoyaltyTransactionSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
