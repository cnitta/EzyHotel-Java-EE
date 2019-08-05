/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common.singleton;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import hms.frontdesk.session.CustomerSessionLocal;
import hms.frontdesk.session.InvoiceSessionLocal;
import hms.frontdesk.session.RoomOrderItemSessionLocal;
import hms.frontdesk.session.RoomOrderSessionLocal;
import hms.housekeeping.session.ForcastSessionLocal;
import hms.housekeeping.session.HousekeepingRequestSessionLocal;
import hms.housekeeping.session.HousekeepingSOPSessionLocal;
import hms.housekeeping.session.InventorySessionLocal;
import hms.hpm.session.FacilitySessionLocal;
import hms.hpm.session.HotelSessionLocal;
import hms.hpm.session.RoomAmenitySessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import hms.hr.session.StaffSessionLocal;
import hms.hr.session.WorkRosterSessionLocal;
import hms.prepostarrival.session.OnlineRoomBookingSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.entity.CustomerEntity;
import util.entity.FacilityEntity;
import util.entity.HotelEntity;
import util.entity.HousekeepingRequestEntity;
import util.entity.HousekeepingSOPEntity;
import util.entity.InventoryEntity;
import util.entity.PriceRateEntity;
import util.entity.InvoiceEntity;
import util.entity.RoomAmenityEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.RoomOrderEntity;
import util.entity.RoomOrderItemEntity;
import util.entity.RoomTypeEntity;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;
import util.enumeration.CustomerMembershipEnum;
import util.enumeration.DepartmentEnum;
import util.enumeration.FacilityStatusEnum;
import util.enumeration.GenderEnum;
import util.enumeration.JobPositionEnum;
import util.enumeration.RoomBookingStatusEnum;
import util.enumeration.RoomStatusEnum;
import util.enumeration.RosterTypeEnum;

/**
 *
 * @author Nicholas Nah
 */
@Singleton
@LocalBean
@Startup

public class DataInitializationSession {

    @EJB
    private RoomOrderItemSessionLocal roomOrderItemSessionLocal;

    @EJB
    private RoomOrderSessionLocal roomOrderSessionLocal;

    @EJB
    private PriceRateSessionLocal priceRateSessionLocal;

    @EJB
    private OnlineRoomBookingSessionLocal onlineRoomBookingSessionLocal;

    @EJB
    private HousekeepingSOPSessionLocal housekeepingSOPSessionLocal;

    @EJB
    private RoomTypeSessionLocal roomTypeForDataSessionLocal;

    @EJB
    private CustomerSessionLocal customerSessionLocal;

    @EJB
    private HotelSessionLocal hotelSessonLocal;

    @EJB
    private RoomSessionLocal roomSessionLocal;

    @EJB
    private FacilitySessionLocal facilitySessionLocal;

    @EJB
    private RoomAmenitySessionLocal roomAmenitySessionLocal;

    @EJB
    private ForcastSessionLocal forcastSessionLocal;

    @EJB
    private StaffSessionLocal staffSessionLocal;

    @EJB
    private HousekeepingRequestSessionLocal housekeepingRequestSessionLocal;

    @EJB
    private WorkRosterSessionLocal workRosterSessionLocal;
        
    @EJB
    private InventorySessionLocal inventorySessionLocal;

    @EJB
    private InvoiceSessionLocal invoiceSessionLocal;

    public DataInitializationSession() {

    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("*** DataInitializationSession Bean ***\n");

        try {
            customerSessionLocal.retrieveEntityById(Long.valueOf(1));
            roomTypeForDataSessionLocal.retrieveEntity("name", "Superior");
            workRosterSessionLocal.retrieveEntityById(Long.valueOf(1));
            invoiceSessionLocal.retrieveInvoiceById(Long.valueOf(1));
            roomOrderSessionLocal.retrieveRoomOrderById(Long.valueOf(1));
        } catch (Exception ex) {
            initializeData_customer();
            initializeData_roomType();
            initializeData_invoice();
            initializeData_RoomOrders();
        }
    }

    private void initializeData_roomType() {

        // Start - Hotel Records
        HotelEntity hotel1 = new HotelEntity();
        hotel1.setName("Kent Ridge Hotel");
        hotel1.setAddress("1 Lower Kent Ridge Road Singapore. 119072");
        hotel1.setEmail("kentridge@krhg.com.sg");
        hotel1.setCountry("Singapore");
        hotel1.setTelephoneNumber("66778899");
        hotel1.setDescription(
                "Kent Ridge Hotel is managed under Kent Ridge Hotel Group that aimed to provide exceptional services to guests for their fulfilling stay at all of their hotels.");
        hotel1.setHotelURL("kentridgehotel.com.sg");
        hotel1.setPolicies(
                "Cancellation Policy: All confirmed reservations and payment made not cancelled at least two days prior to check-in date (2.00pm SGT two days prior to check-in date) will not be refunded. ");
        hotel1.setServices(
                "Fax & Photocopying: Our hotel offers photocopying and fax services. Please approach our reception");
        hotelSessonLocal.createEntity(hotel1);

        HotelEntity hotel2 = new HotelEntity();
        hotel2.setName("Blissful Hotel");
        hotel2.setAddress("Blissful Road Singapore 111111");
        hotel2.setEmail("blissful@krhg.com.sg");
        hotel2.setCountry("Singapore");
        hotel2.setTelephoneNumber("66771122");
        hotel2.setDescription(
                "Blissful Hotel is managed under Kent Ridge Hotel Group that aimed to provide exceptional services to guests for their fulfilling stay at all of their hotels.");
        hotel2.setHotelURL("blissfulhotel.com.sg");
        hotel2.setPolicies(
                "Cancellation Policy: All confirmed reservations and payment made not cancelled at least two days prior to check-in date (2.00pm SGT two days prior to check-in date) will not be refunded. ");
        hotel2.setServices(
                "Fax & Photocopying: Our hotel offers photocopying and fax services. Please approach our reception");
        hotelSessonLocal.createEntity(hotel2);

        HotelEntity hotel3 = new HotelEntity();
        hotel3.setName("Changi Biz Hotel");
        hotel3.setAddress("Changi Road Singapore, 222222");
        hotel3.setEmail("changibiz@krhg.com.sg");
        hotel3.setCountry("Singapore");
        hotel3.setTelephoneNumber("66771133");
        hotel3.setDescription(
                "Changi Biz Hotel is managed under Kent Ridge Hotel Group that aimed to provide exceptional services to guests for their fulfilling stay at all of their hotels.");
        hotel3.setHotelURL("changibizhotel.com.sg");
        hotel3.setPolicies(
                "Cancellation Policy: All confirmed reservations and payment made not cancelled at least two days prior to check-in date (2.00pm SGT two days prior to check-in date) will not be refunded. ");
        hotel3.setServices(
                "Fax & Photocopying: Our hotel offers photocopying and fax services. Please approach our reception");
        hotelSessonLocal.createEntity(hotel3);

        HotelEntity hotel4 = new HotelEntity();
        hotel4.setName("Horizon Hotel");
        hotel4.setAddress("Horizon Road, Singapore. 123456");
        hotel4.setEmail("horizon@krhg.com.sg");
        hotel4.setCountry("Singapore");
        hotel4.setTelephoneNumber("66774455");
        hotel4.setDescription(
                "Horizon Hotel is managed under Kent Ridge Hotel Group that aimed to provide exceptional services to guests for their fulfilling stay at all of their hotels.");
        hotel4.setHotelURL("horizonhotel.com.sg");
        hotel4.setPolicies(
                "Cancellation Policy: All confirmed reservations and payment made not cancelled at least two days prior to check-in date (2.00pm SGT two days prior to check-in date) will not be refunded. ");
        hotel4.setServices(
                "Fax & Photocopying: Our hotel offers photocopying and fax services. Please approach our reception");
        hotelSessonLocal.createEntity(hotel4);

        HotelEntity hotel5 = new HotelEntity();
        hotel5.setName("Joyful Hotel ");
        hotel5.setAddress("Jurong Road, Singapore. 122333");
        hotel5.setEmail("joyful@krhg.com.sg");
        hotel5.setCountry("Singapore");
        hotel5.setTelephoneNumber("66774466");
        hotel5.setDescription(
                "Joyful Hotel is managed under Kent Ridge Hotel Group that aimed to provide exceptional services to guests for their fulfilling stay at all of their hotels.");
        hotel5.setHotelURL("joyfulhotel.com.sg");
        hotel5.setPolicies(
                "Cancellation Policy: All confirmed reservations and payment made not cancelled at least two days prior to check-in date (2.00pm SGT two days prior to check-in date) will not be refunded. ");
        hotel5.setServices(
                "Fax & Photocopying: Our hotel offers photocopying and fax services. Please approach our reception");
        hotelSessonLocal.createEntity(hotel5);
                // End - Hotel Records

                // Start - Room Amenities
        // For Superior
        RoomAmenityEntity ra1 = new RoomAmenityEntity();
        ra1.setAirConditioning(true);
        ra1.setBathTowels(true);
        ra1.setBathroomAmenities(true);
        ra1.setBdromSlippers(true);
        ra1.setCoffeeNTeaMaker(true);
        ra1.setCulteryNUtensils(false);
        ra1.setDiningArea(false);
        ra1.setElectricCooktop(false);
        ra1.setElectronicSafe(true);
        ra1.setFreeWifi(true);
        ra1.setHairDryer(true);
        ra1.setIronNIroningBoard(false);
        ra1.setKitchenette(false);
        ra1.setLivingRoom(false);
        ra1.setMicrowaveOven(false);
        ra1.setMiniBar(true);
        ra1.setMobilePhoneDeviceNCharger(true);
        ra1.setNespressoCoffeeMachine(false);
        ra1.setNonSmoking(true);
        ra1.setTelevisionNcableChn(true);
        ra1.setToaster(false);
        ra1.setWasherCumDryer(false);
        ra1.setWritingDeskNChair(false);
        roomAmenitySessionLocal.createEntity(ra1);

        // For Deluxe
        RoomAmenityEntity ra2 = new RoomAmenityEntity();
        ra2.setAirConditioning(true);
        ra2.setBathTowels(true);
        ra2.setBathroomAmenities(true);
        ra2.setBdromSlippers(true);
        ra2.setCoffeeNTeaMaker(true);
        ra2.setCulteryNUtensils(false);
        ra2.setDiningArea(false);
        ra2.setElectricCooktop(false);
        ra2.setElectronicSafe(true);
        ra2.setFreeWifi(true);
        ra2.setHairDryer(true);
        ra2.setIronNIroningBoard(false);
        ra2.setKitchenette(false);
        ra2.setLivingRoom(false);
        ra2.setMicrowaveOven(false);
        ra2.setMiniBar(true);
        ra2.setMobilePhoneDeviceNCharger(true);
        ra2.setNespressoCoffeeMachine(false);
        ra2.setNonSmoking(true);
        ra2.setTelevisionNcableChn(true);
        ra2.setToaster(false);
        ra2.setWasherCumDryer(false);
        ra2.setWritingDeskNChair(true);
        roomAmenitySessionLocal.createEntity(ra2);

        // For Junior Suite
        RoomAmenityEntity ra3 = new RoomAmenityEntity();
        ra3.setAirConditioning(true);
        ra3.setBathTowels(true);
        ra3.setBathroomAmenities(true);
        ra3.setBdromSlippers(true);
        ra3.setCoffeeNTeaMaker(true);
        ra3.setCulteryNUtensils(false);
        ra3.setDiningArea(false);
        ra3.setElectricCooktop(false);
        ra3.setElectronicSafe(true);
        ra3.setFreeWifi(true);
        ra3.setHairDryer(true);
        ra3.setIronNIroningBoard(true);
        ra3.setKitchenette(false);
        ra3.setLivingRoom(true);
        ra3.setMicrowaveOven(false);
        ra3.setMiniBar(true);
        ra3.setMobilePhoneDeviceNCharger(true);
        ra3.setNespressoCoffeeMachine(false);
        ra3.setNonSmoking(true);
        ra3.setTelevisionNcableChn(true);
        ra3.setToaster(false);
        ra3.setWasherCumDryer(false);
        ra3.setWritingDeskNChair(true);
        roomAmenitySessionLocal.createEntity(ra3);

        // For Executive Suite
        RoomAmenityEntity ra4 = new RoomAmenityEntity();
        ra4.setAirConditioning(true);
        ra4.setBathTowels(true);
        ra4.setBathroomAmenities(true);
        ra4.setBdromSlippers(true);
        ra4.setCoffeeNTeaMaker(true);
        ra4.setCulteryNUtensils(true);
        ra4.setDiningArea(true);
        ra4.setElectricCooktop(false);
        ra4.setElectronicSafe(true);
        ra4.setFreeWifi(true);
        ra4.setHairDryer(true);
        ra4.setIronNIroningBoard(true);
        ra4.setKitchenette(false);
        ra4.setLivingRoom(true);
        ra4.setMicrowaveOven(false);
        ra4.setMiniBar(true);
        ra4.setMobilePhoneDeviceNCharger(true);
        ra4.setNespressoCoffeeMachine(false);
        ra4.setNonSmoking(true);
        ra4.setTelevisionNcableChn(true);
        ra4.setToaster(false);
        ra4.setWasherCumDryer(false);
        ra4.setWritingDeskNChair(true);
        roomAmenitySessionLocal.createEntity(ra4);

        // For President Suite
        RoomAmenityEntity ra5 = new RoomAmenityEntity();
        ra5.setAirConditioning(true);
        ra5.setBathTowels(true);
        ra5.setBathroomAmenities(true);
        ra5.setBdromSlippers(true);
        ra5.setCoffeeNTeaMaker(true);
        ra5.setCulteryNUtensils(true);
        ra5.setDiningArea(true);
        ra5.setElectricCooktop(true);
        ra5.setElectronicSafe(true);
        ra5.setFreeWifi(true);
        ra5.setHairDryer(true);
        ra5.setIronNIroningBoard(true);
        ra5.setKitchenette(true);
        ra5.setLivingRoom(true);
        ra5.setMicrowaveOven(true);
        ra5.setMiniBar(true);
        ra5.setMobilePhoneDeviceNCharger(true);
        ra5.setNespressoCoffeeMachine(true);
        ra5.setNonSmoking(true);
        ra5.setTelevisionNcableChn(true);
        ra5.setToaster(true);
        ra5.setWasherCumDryer(true);
        ra5.setWritingDeskNChair(true);
        roomAmenitySessionLocal.createEntity(ra5);

        // This ra6 is for Blissful Superior Room Type (Hotel 2)
        RoomAmenityEntity ra6 = new RoomAmenityEntity();
        ra6.setAirConditioning(true);
        ra6.setBathTowels(true);
        ra6.setBathroomAmenities(true);
        ra6.setBdromSlippers(true);
        ra6.setCoffeeNTeaMaker(true);
        ra6.setCulteryNUtensils(false);
        ra6.setDiningArea(false);
        ra6.setElectricCooktop(false);
        ra6.setElectronicSafe(true);
        ra6.setFreeWifi(true);
        ra6.setHairDryer(true);
        ra6.setIronNIroningBoard(false);
        ra6.setKitchenette(false);
        ra6.setLivingRoom(false);
        ra6.setMicrowaveOven(false);
        ra6.setMiniBar(true);
        ra6.setMobilePhoneDeviceNCharger(true);
        ra6.setNespressoCoffeeMachine(false);
        ra6.setNonSmoking(true);
        ra6.setTelevisionNcableChn(true);
        ra6.setToaster(false);
        ra6.setWasherCumDryer(false);
        ra6.setWritingDeskNChair(false);
        roomAmenitySessionLocal.createEntity(ra6);
                // End - Room Amenities

        // Start - Room Type Records
        RoomTypeEntity room1 = new RoomTypeEntity();
        room1.setRoomTypecode("SUR");
        room1.setName("Superior");
        room1.setBedType("Twin Beds or Queen Size Bed");
        room1.setDescription(
                "Our Superior Room offers a cozy and stylish furnishing and basic amenities for two people.");
        room1.setNumMaxGuest(2);
        room1.setTotalRooms(35);
        room1.setRoomSize(20.00);
        room1.setHotel(hotel1);
        room1.setAmenity(ra1); // ra1 is Room Amenity for SUR
        roomTypeForDataSessionLocal.createEntity(room1);

        RoomTypeEntity room2 = new RoomTypeEntity();
        room2.setRoomTypecode("DEX");
        room2.setName("Deluxe");
        room2.setBedType("Queen Size Bed");
        room2.setDescription(
                "Our Deluxe Rooms are larger than our Superior Rooms with a beautiful view of the swimming pool.");
        room2.setNumMaxGuest(2);
        room2.setTotalRooms(25);
        room2.setRoomSize(25.00);
        room2.setHotel(hotel1);
        room2.setAmenity(ra2); // ra2 is Room Amenity for DEX
        roomTypeForDataSessionLocal.createEntity(room2);

        RoomTypeEntity room3 = new RoomTypeEntity();
        room3.setRoomTypecode("JUN");
        room3.setName("Junior Suite");
        room3.setBedType("Twin Beds or King Size Bed");
        room3.setDescription(
                "Our Junior Suite offers a separate bedroom and a living room that allows you and your family to have more room during your stay. Featuring a bedroom and a separate living room with a large sofa bed and a writing desk.");
        room3.setNumMaxGuest(2);
        room3.setTotalRooms(15);
        room3.setRoomSize(35.00);
        room3.setHotel(hotel1);
        room3.setAmenity(ra3); // ra3 is Room Amenity for JUN
        roomTypeForDataSessionLocal.createEntity(room3);

        RoomTypeEntity room4 = new RoomTypeEntity();
        room4.setBedType("King Size Bed");
        room4.setName("Executive Suite");
        room4.setRoomTypecode("EXE");
        room4.setDescription(
                "Our Executive Suite gives you everything that you need for a fulfilling stay. These suites consist of a spacious bedroom, living room, fully-equipped kitchenette and kitchen appliances, electric cooktop, bathroom amenities.");
        room4.setNumMaxGuest(3);
        room4.setTotalRooms(10);
        room4.setRoomSize(45.00);
        room4.setHotel(hotel1);
        room4.setAmenity(ra4); // ra4 is Room Amenity for EXE
        roomTypeForDataSessionLocal.createEntity(room4);

        RoomTypeEntity room5 = new RoomTypeEntity();
        room5.setBedType("Twin Beds or King Size Bed");
        room5.setName("President Suite");
        room5.setRoomTypecode("PRE");
        room5.setDescription(
                "Our President Suite is located at the uppermost levels of our luxury serviced apartment with a stunning view of Singapore’s skyline.");
        room5.setNumMaxGuest(6);
        room5.setTotalRooms(3);
        room5.setRoomSize(60.00);
        room5.setHotel(hotel1);
        room5.setAmenity(ra5); // ra5 is Room Amenity for PRE
        roomTypeForDataSessionLocal.createEntity(room5);

        // for hotel 2 and room90
        RoomTypeEntity room6 = new RoomTypeEntity();
        room6.setRoomTypecode("SUR");
        room6.setName("Blissful Superior");
        room6.setBedType("Twin Beds or Queen Size Bed");
        room6.setDescription(
                "Our Superior Room offers a cozy and stylish furnishing and basic amenities for two people.");
        room6.setNumMaxGuest(2);
        room6.setTotalRooms(35);
        room6.setRoomSize(20.00);
        room6.setHotel(hotel2);
        room6.setAmenity(ra6); // ra6 is Room Amenity for Superior
        roomTypeForDataSessionLocal.createEntity(room6);
                // End - Room Type Records

        // Start - HouseKeeping SOP Records
        HousekeepingSOPEntity sop1 = new HousekeepingSOPEntity(new Time(00, 30, 00), null, room1);
        HousekeepingSOPEntity sop2 = new HousekeepingSOPEntity(new Time(00, 35, 00), null, room2);
        HousekeepingSOPEntity sop3 = new HousekeepingSOPEntity(new Time(00, 40, 00), null, room3);
        HousekeepingSOPEntity sop4 = new HousekeepingSOPEntity(new Time(01, 00, 00), null, room4);
        HousekeepingSOPEntity sop5 = new HousekeepingSOPEntity(new Time(01, 30, 00), null, room5);

        housekeepingSOPSessionLocal.createEntity(sop1);
        housekeepingSOPSessionLocal.createEntity(sop2);
        housekeepingSOPSessionLocal.createEntity(sop3);
        housekeepingSOPSessionLocal.createEntity(sop4);
        housekeepingSOPSessionLocal.createEntity(sop5);

        // room1.setHousekeepingSOP(sop1);
        // room2.setHousekeepingSOP(sop2);
        // room3.setHousekeepingSOP(sop3);
        // room4.setHousekeepingSOP(sop4);
        // room5.setHousekeepingSOP(sop5);
        // Delete one room
        // Should delete sop5 also
        // roomTypeForDataSessionLocal.deleteEntity(5L);
        // End - HouseKeeping SOP Records
        // Start - Room Records
        RoomEntity rm1 = new RoomEntity();
        rm1.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm1.setRoomUnitNumber(101);
        rm1.setRoomType(room1);
        rm1.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm1);

        RoomEntity rm2 = new RoomEntity();
        rm2.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm2.setRoomUnitNumber(102);
        rm2.setRoomType(room1);
        roomSessionLocal.createEntity(rm2);

        RoomEntity rm3 = new RoomEntity();
        rm3.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm3.setRoomUnitNumber(103);
        rm3.setRoomType(room1);
        roomSessionLocal.createEntity(rm3);

        RoomEntity rm4 = new RoomEntity();
        rm4.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm4.setRoomUnitNumber(104);
        rm4.setRoomType(room1);
        roomSessionLocal.createEntity(rm4);

        RoomEntity rm5 = new RoomEntity();
        rm5.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm5.setRoomUnitNumber(105);
        rm5.setRoomType(room1);
        roomSessionLocal.createEntity(rm5);

        RoomEntity rm6 = new RoomEntity();
        rm6.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm6.setRoomUnitNumber(106);
        rm6.setRoomType(room1);
        roomSessionLocal.createEntity(rm6);

        RoomEntity rm7 = new RoomEntity();
        rm7.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm7.setRoomUnitNumber(107);
        rm7.setRoomType(room1);
        rm7.setCleaningStatus("Dirty");
        rm7.setIsDND(Boolean.TRUE);
        roomSessionLocal.createEntity(rm7);

        RoomEntity rm8 = new RoomEntity();
        rm8.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm8.setRoomUnitNumber(108);
        rm8.setRoomType(room1);
        roomSessionLocal.createEntity(rm8);

        RoomEntity rm9 = new RoomEntity();
        rm9.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm9.setRoomUnitNumber(109);
        rm9.setRoomType(room2);
        rm9.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm9);

        RoomEntity rm10 = new RoomEntity();
        rm10.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm10.setRoomUnitNumber(110);
        rm10.setRoomType(room1);
        rm10.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm10);

        RoomEntity rm11 = new RoomEntity();
        rm11.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm11.setRoomUnitNumber(111);
        rm11.setRoomType(room1);
        rm11.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm11);

        RoomEntity rm12 = new RoomEntity();
        rm12.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm12.setRoomUnitNumber(112);
        rm12.setRoomType(room1);
        rm12.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm12);

        RoomEntity rm13 = new RoomEntity();
        rm13.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm13.setRoomUnitNumber(113);
        rm13.setRoomType(room1);
        rm13.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm13);

        RoomEntity rm14 = new RoomEntity();
        rm14.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm14.setRoomUnitNumber(114);
        rm14.setRoomType(room1);
        rm14.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm14);

        RoomEntity rm15 = new RoomEntity();
        rm15.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm15.setRoomUnitNumber(115);
        rm15.setRoomType(room1);
        roomSessionLocal.createEntity(rm15);

        RoomEntity rm16 = new RoomEntity();
        rm16.setStatus(RoomStatusEnum.OCCUPIED);
        rm16.setRoomUnitNumber(116);
        rm16.setRoomType(room1);
        rm16.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm16);

        RoomEntity rm17 = new RoomEntity();
        rm17.setStatus(RoomStatusEnum.OCCUPIED);
        rm17.setRoomUnitNumber(117);
        rm17.setRoomType(room1);
        rm17.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm17);

        RoomEntity rm18 = new RoomEntity();
        rm18.setStatus(RoomStatusEnum.OCCUPIED);
        rm18.setRoomUnitNumber(118);
        rm18.setRoomType(room1);
        rm18.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm18);

        RoomEntity rm19 = new RoomEntity();
        rm19.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm19.setRoomUnitNumber(119);
        rm19.setRoomType(room1);
        roomSessionLocal.createEntity(rm19);

        RoomEntity rm20 = new RoomEntity();
        rm20.setStatus(RoomStatusEnum.OCCUPIED);
        rm20.setRoomUnitNumber(120);
        rm20.setRoomType(room1);
        rm20.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm20);

        RoomEntity rm21 = new RoomEntity();
        rm21.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm21.setRoomUnitNumber(121);
        rm21.setRoomType(room1);
        roomSessionLocal.createEntity(rm21);

        RoomEntity rm22 = new RoomEntity();
        rm22.setStatus(RoomStatusEnum.OCCUPIED);
        rm22.setRoomUnitNumber(122);
        rm22.setRoomType(room1);
        rm22.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm22);

        RoomEntity rm23 = new RoomEntity();
        rm23.setStatus(RoomStatusEnum.OCCUPIED);
        rm23.setRoomUnitNumber(123);
        rm23.setRoomType(room1);
        rm23.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm23);

        RoomEntity rm24 = new RoomEntity();
        rm24.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm24.setRoomUnitNumber(124);
        rm24.setRoomType(room1);
        roomSessionLocal.createEntity(rm24);

        RoomEntity rm25 = new RoomEntity();
        rm25.setStatus(RoomStatusEnum.OCCUPIED);
        rm25.setRoomUnitNumber(125);
        rm25.setRoomType(room1);
        rm25.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm25);

        RoomEntity rm26 = new RoomEntity();
        rm26.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm26.setRoomUnitNumber(126);
        rm26.setRoomType(room1);
        roomSessionLocal.createEntity(rm26);

        RoomEntity rm27 = new RoomEntity();
        rm27.setStatus(RoomStatusEnum.OCCUPIED);
        rm27.setRoomUnitNumber(127);
        rm27.setRoomType(room1);
        rm27.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm27);

        RoomEntity rm28 = new RoomEntity();
        rm28.setStatus(RoomStatusEnum.OCCUPIED);
        rm28.setRoomUnitNumber(128);
        rm28.setRoomType(room1);
        rm28.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm28);

        RoomEntity rm29 = new RoomEntity();
        rm29.setStatus(RoomStatusEnum.OCCUPIED);
        rm29.setRoomUnitNumber(129);
        rm29.setRoomType(room1);
        rm29.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm29);

        RoomEntity rm30 = new RoomEntity();
        rm30.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm30.setRoomUnitNumber(130);
        rm30.setRoomType(room1);
        roomSessionLocal.createEntity(rm30);

        RoomEntity rm31 = new RoomEntity();
        rm31.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm31.setRoomUnitNumber(131);
        rm31.setRoomType(room1);
        rm31.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm31);

        RoomEntity rm32 = new RoomEntity();
        rm32.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm32.setRoomUnitNumber(132);
        rm32.setRoomType(room1);
        roomSessionLocal.createEntity(rm32);

        RoomEntity rm33 = new RoomEntity();
        rm33.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm33.setRoomUnitNumber(133);
        rm33.setRoomType(room1);
        roomSessionLocal.createEntity(rm33);

        RoomEntity rm34 = new RoomEntity();
        rm34.setStatus(RoomStatusEnum.OCCUPIED);
        rm34.setRoomUnitNumber(134);
        rm34.setRoomType(room1);
        rm34.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm34);

        RoomEntity rm35 = new RoomEntity();
        rm35.setStatus(RoomStatusEnum.OCCUPIED);
        rm35.setRoomUnitNumber(135);
        rm35.setRoomType(room1);
        rm35.setCleaningStatus("Dirty");
        rm7.setIsDND(Boolean.TRUE);
        roomSessionLocal.createEntity(rm35);

        // Deluxe Rooms
        RoomEntity rm36 = new RoomEntity();
        rm36.setStatus(RoomStatusEnum.OCCUPIED);
        rm36.setRoomUnitNumber(201);
        rm36.setRoomType(room2);
        rm36.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm36);

        RoomEntity rm37 = new RoomEntity();
        rm37.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm37.setRoomUnitNumber(202);
        rm37.setRoomType(room2);
        roomSessionLocal.createEntity(rm37);

        RoomEntity rm38 = new RoomEntity();
        rm38.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm38.setRoomUnitNumber(203);
        rm38.setRoomType(room2);
        roomSessionLocal.createEntity(rm38);

        RoomEntity rm39 = new RoomEntity();
        rm39.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm39.setRoomUnitNumber(204);
        rm39.setRoomType(room2);
        roomSessionLocal.createEntity(rm39);

        RoomEntity rm40 = new RoomEntity();
        rm40.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm40.setRoomUnitNumber(205);
        rm40.setRoomType(room2);
        roomSessionLocal.createEntity(rm40);

        RoomEntity rm41 = new RoomEntity();
        rm41.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm41.setRoomUnitNumber(206);
        rm41.setRoomType(room2);
        rm41.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm41);

        RoomEntity rm42 = new RoomEntity();
        rm42.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm42.setRoomUnitNumber(207);
        rm42.setRoomType(room2);
        rm42.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm42);

        RoomEntity rm43 = new RoomEntity();
        rm43.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm43.setRoomUnitNumber(208);
        rm43.setRoomType(room2);
        rm43.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm43);

        RoomEntity rm44 = new RoomEntity();
        rm44.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm44.setRoomUnitNumber(209);
        rm44.setRoomType(room2);
        rm44.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm44);

        RoomEntity rm45 = new RoomEntity();
        rm45.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm45.setRoomUnitNumber(210);
        rm45.setRoomType(room2);
        rm45.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm45);

        RoomEntity rm46 = new RoomEntity();
        rm46.setStatus(RoomStatusEnum.OCCUPIED);
        rm46.setRoomUnitNumber(211);
        rm46.setRoomType(room2);
        rm46.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm46);

        RoomEntity rm47 = new RoomEntity();
        rm47.setStatus(RoomStatusEnum.OCCUPIED);
        rm47.setRoomUnitNumber(212);
        rm47.setRoomType(room2);
        rm47.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm47);

        RoomEntity rm48 = new RoomEntity();
        rm48.setStatus(RoomStatusEnum.OCCUPIED);
        rm48.setRoomUnitNumber(213);
        rm48.setRoomType(room2);
        rm48.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm48);

        RoomEntity rm49 = new RoomEntity();
        rm49.setStatus(RoomStatusEnum.OCCUPIED);
        rm49.setRoomUnitNumber(214);
        rm49.setRoomType(room2);
        rm49.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm49);

        RoomEntity rm50 = new RoomEntity();
        rm50.setStatus(RoomStatusEnum.OCCUPIED);
        rm50.setRoomUnitNumber(215);
        rm50.setRoomType(room2);
        rm50.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm50);

        RoomEntity rm51 = new RoomEntity();
        rm51.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm51.setRoomUnitNumber(216);
        rm51.setRoomType(room2);
        roomSessionLocal.createEntity(rm51);

        RoomEntity rm52 = new RoomEntity();
        rm52.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm52.setRoomUnitNumber(217);
        rm52.setRoomType(room2);
        roomSessionLocal.createEntity(rm52);

        RoomEntity rm53 = new RoomEntity();
        rm53.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm53.setRoomUnitNumber(218);
        rm53.setRoomType(room2);
        roomSessionLocal.createEntity(rm53);

        RoomEntity rm54 = new RoomEntity();
        rm54.setStatus(RoomStatusEnum.OCCUPIED);
        rm54.setRoomUnitNumber(219);
        rm54.setRoomType(room2);
        rm54.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm54);

        RoomEntity rm55 = new RoomEntity();
        rm55.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm55.setRoomUnitNumber(220);
        rm55.setRoomType(room2);
        roomSessionLocal.createEntity(rm55);

        RoomEntity rm56 = new RoomEntity();
        rm56.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm56.setRoomUnitNumber(221);
        rm56.setRoomType(room2);
        rm56.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm56);

        RoomEntity rm57 = new RoomEntity();
        rm57.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm57.setRoomUnitNumber(222);
        rm57.setRoomType(room2);
        rm57.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm57);

        RoomEntity rm58 = new RoomEntity();
        rm58.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm58.setRoomUnitNumber(223);
        rm58.setRoomType(room2);
        rm58.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm58);

        RoomEntity rm59 = new RoomEntity();
        rm59.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm59.setRoomUnitNumber(224);
        rm59.setRoomType(room2);
        rm59.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm59);

        RoomEntity rm60 = new RoomEntity();
        rm60.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm60.setRoomUnitNumber(225);
        rm60.setRoomType(room2);
        rm60.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm60);

        // Junior Suite Room
        RoomEntity rm61 = new RoomEntity();
        rm61.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm61.setRoomUnitNumber(301);
        rm61.setRoomType(room3);
        roomSessionLocal.createEntity(rm61);

        RoomEntity rm62 = new RoomEntity();
        rm62.setStatus(RoomStatusEnum.OCCUPIED);
        rm62.setRoomUnitNumber(302);
        rm62.setRoomType(room3);
        rm62.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm62);

        RoomEntity rm63 = new RoomEntity();
        rm63.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm63.setRoomUnitNumber(303);
        rm63.setRoomType(room3);
        roomSessionLocal.createEntity(rm63);

        RoomEntity rm64 = new RoomEntity();
        rm64.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm64.setRoomUnitNumber(304);
        rm64.setRoomType(room3);
        roomSessionLocal.createEntity(rm64);

        RoomEntity rm65 = new RoomEntity();
        rm65.setStatus(RoomStatusEnum.OCCUPIED);
        rm65.setRoomUnitNumber(305);
        rm65.setRoomType(room3);
        rm65.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm65);

        RoomEntity rm66 = new RoomEntity();
        rm66.setStatus(RoomStatusEnum.OCCUPIED);
        rm66.setRoomUnitNumber(306);
        rm66.setRoomType(room3);
        rm66.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm66);

        RoomEntity rm67 = new RoomEntity();
        rm67.setStatus(RoomStatusEnum.OCCUPIED);
        rm67.setRoomUnitNumber(307);
        rm67.setRoomType(room3);
        rm67.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm67);

        RoomEntity rm68 = new RoomEntity();
        rm68.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm68.setRoomUnitNumber(308);
        rm68.setRoomType(room3);
        roomSessionLocal.createEntity(rm68);

        RoomEntity rm69 = new RoomEntity();
        rm69.setStatus(RoomStatusEnum.OCCUPIED);
        rm69.setRoomUnitNumber(309);
        rm69.setRoomType(room3);
        rm69.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm69);

        RoomEntity rm70 = new RoomEntity();
        rm70.setStatus(RoomStatusEnum.OCCUPIED);
        rm70.setRoomUnitNumber(310);
        rm70.setRoomType(room3);
        rm70.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm70);

        RoomEntity rm71 = new RoomEntity();
        rm71.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm71.setRoomUnitNumber(311);
        rm71.setRoomType(room3);
        roomSessionLocal.createEntity(rm71);

        RoomEntity rm72 = new RoomEntity();
        rm72.setStatus(RoomStatusEnum.OCCUPIED);
        rm72.setRoomUnitNumber(312);
        rm72.setRoomType(room3);
        rm72.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm72);

        RoomEntity rm73 = new RoomEntity();
        rm73.setStatus(RoomStatusEnum.OCCUPIED);
        rm73.setRoomUnitNumber(313);
        rm73.setRoomType(room3);
        rm73.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm73);

        RoomEntity rm74 = new RoomEntity();
        rm74.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm74.setRoomUnitNumber(314);
        rm74.setRoomType(room3);
        roomSessionLocal.createEntity(rm74);

        RoomEntity rm75 = new RoomEntity();
        rm75.setStatus(RoomStatusEnum.OCCUPIED);
        rm75.setRoomUnitNumber(315);
        rm75.setRoomType(room3);
        rm75.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm75);

        // Executive Suite Rooms
        RoomEntity rm76 = new RoomEntity();
        rm76.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm76.setRoomUnitNumber(401);
        rm76.setRoomType(room4);
        roomSessionLocal.createEntity(rm76);

        RoomEntity rm77 = new RoomEntity();
        rm77.setStatus(RoomStatusEnum.OCCUPIED);
        rm77.setRoomUnitNumber(402);
        rm77.setRoomType(room4);
        rm77.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm77);

        RoomEntity rm78 = new RoomEntity();
        rm78.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm78.setRoomUnitNumber(403);
        rm78.setRoomType(room4);
        roomSessionLocal.createEntity(rm78);

        RoomEntity rm79 = new RoomEntity();
        rm79.setStatus(RoomStatusEnum.OCCUPIED);
        rm79.setRoomUnitNumber(404);
        rm79.setRoomType(room4);
        rm79.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm79);

        RoomEntity rm80 = new RoomEntity();
        rm80.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm80.setRoomUnitNumber(405);
        rm80.setRoomType(room4);
        roomSessionLocal.createEntity(rm80);

        RoomEntity rm81 = new RoomEntity();
        rm81.setStatus(RoomStatusEnum.OCCUPIED);
        rm81.setRoomUnitNumber(406);
        rm81.setRoomType(room4);
        rm81.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm81);

        RoomEntity rm82 = new RoomEntity();
        rm82.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm82.setRoomUnitNumber(407);
        rm82.setRoomType(room4);
        roomSessionLocal.createEntity(rm82);

        RoomEntity rm83 = new RoomEntity();
        rm83.setStatus(RoomStatusEnum.OCCUPIED);
        rm83.setRoomUnitNumber(408);
        rm83.setRoomType(room4);
        rm83.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm83);

        RoomEntity rm84 = new RoomEntity();
        rm84.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm84.setRoomUnitNumber(409);
        rm84.setRoomType(room4);
        roomSessionLocal.createEntity(rm84);

        RoomEntity rm85 = new RoomEntity();
        rm85.setStatus(RoomStatusEnum.OCCUPIED);
        rm85.setRoomUnitNumber(410);
        rm85.setRoomType(room4);
        rm85.setCleaningStatus("Dirty");
        roomSessionLocal.createEntity(rm85);

        // President Suite Rooms
        RoomEntity rm86 = new RoomEntity();
        rm86.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm86.setRoomUnitNumber(501);
        rm86.setRoomType(room5);
        roomSessionLocal.createEntity(rm86);

        RoomEntity rm87 = new RoomEntity();
        rm87.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm87.setRoomUnitNumber(502);
        rm87.setRoomType(room5);
        roomSessionLocal.createEntity(rm87);

        RoomEntity rm88 = new RoomEntity();
        rm88.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm88.setRoomUnitNumber(503);
        rm88.setRoomType(room5);
        roomSessionLocal.createEntity(rm88);

        RoomEntity rm89 = new RoomEntity();
        rm89.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm89.setRoomUnitNumber(504);
        rm89.setRoomType(room5); // President room type
        roomSessionLocal.createEntity(rm89);

        // for hotel 2 and roomtype is superior
        RoomEntity rm90 = new RoomEntity();
        rm90.setStatus(RoomStatusEnum.UNOCCUPIED);
        rm90.setRoomUnitNumber(101);
        rm90.setRoomType(room6); // Superior room type for Blissful Hotel
        roomSessionLocal.createEntity(rm90);


                // int j = 1;
        // for(int i = 86; i < 89; i++){
        // int k = 500 + j;
        // System.out.println("RoomEntity rm" + i + " = new RoomEntity();");
        // System.out.println("rm" + i + ".setStatus(RoomStatusEnum.UNOCCUPIED);");
        // System.out.println("rm" + i + ".setRoomUnitNumber(" + k + ");");
        // System.out.println("rm" + i + ".setRoomType(room5);");
        // System.out.println("roomSessionLocal.createEntity(rm" + i + ");");
        // j++;
        // }
        // End - Room Records
        // Start - Facilities Records
        FacilityEntity fac1 = new FacilityEntity();
        fac1.setArea(0.0);
        fac1.setCapacity("-");
        fac1.setDescription("Spa services at Kent Ridge Hotel are one of the best in Singapore.");
        fac1.setFacFeature("Jacuzzis, Sauna");
        fac1.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac1.setFacilityType("Spa");
        fac1.setName("Oriental Relaxing Spa");
        fac1.setHotel(hotel1);
        facilitySessionLocal.createEntity(fac1);

        FacilityEntity fac2 = new FacilityEntity();
        fac2.setArea(0.0);
        fac2.setCapacity("-");
        fac2.setDescription("Gym room at Kent Ridge Hotel is one of the best in Singapore.");
        fac2.setFacFeature("Towels will be provided for our hotel guests.");
        fac2.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac2.setFacilityType("Gym");
        fac2.setName("Golds Gym");
        fac2.setHotel(hotel1);
        facilitySessionLocal.createEntity(fac2);

        FacilityEntity fac3 = new FacilityEntity();
        fac3.setArea(110.0);
        fac3.setCapacity("Up to 120 Guests | 30 - 110 sqm");
        fac3.setDescription("Meeting room at Kent Ridge Hotel cater to businees meeting.");
        fac3.setFacFeature("AV equipment, Film projector, LCD Panel, LCD projector, Microphone, Smart TV");
        fac3.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac3.setFacilityType("Meeting Room");
        fac3.setName("Oceania Meeting Room");
        fac3.setHotel(hotel1);
        facilitySessionLocal.createEntity(fac3);

        FacilityEntity fac4 = new FacilityEntity();
        fac4.setArea(450.0);
        fac4.setCapacity("Up to 300 Guests | 150 - 450 sqm");
        fac4.setDescription("Conference room at Kent Ridge Hotel cater to both big or small events");
        fac4.setFacFeature("AV equipment, Film projector, LCD Panel, LCD projector, Microphone, Smart TV");
        fac4.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac4.setFacilityType("Conference Room");
        fac4.setName("Sunshine Conference Room");
        fac4.setHotel(hotel1);
        facilitySessionLocal.createEntity(fac4);

        FacilityEntity fac5 = new FacilityEntity();
        fac5.setArea(0.0);
        fac5.setCapacity("-");
        fac5.setDescription("Kent Ridge Hotel has two swimming pools that cater for small kids and adults.");
        fac5.setFacFeature("Towels will be provided for our hotel guests.");
        fac5.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac5.setFacilityType("Swimming Pool");
        fac5.setName("Oceania Swimming Pool");
        fac5.setHotel(hotel1);
        facilitySessionLocal.createEntity(fac5);

        FacilityEntity fac6 = new FacilityEntity();
        fac6.setArea(0.0);
        fac6.setCapacity("-");
        fac6.setDescription(
                "KRestaurant offers one of the best internation buffet in Singapore. Opening Hours: 10am - 10pm");
        fac6.setFacFeature("-");
        fac6.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac6.setFacilityType("Restaurant");
        fac6.setName("The KR Restaurant");
        fac6.setHotel(hotel1);
        facilitySessionLocal.createEntity(fac6);

        FacilityEntity fac7 = new FacilityEntity();
        fac7.setArea(0.0);
        fac7.setCapacity("-");
        fac7.setDescription(
                "Bliss Restaurant offers one of the best internation buffet in Singapore. Opening Hours: 10am - 10pm");
        fac7.setFacFeature("-");
        fac7.setFacStatus(FacilityStatusEnum.FUNCTIONAL);
        fac7.setFacilityType("Restaurant");
        fac7.setName("The Bliss Restaurant");
        fac7.setHotel(hotel2);
        facilitySessionLocal.createEntity(fac7);
                // End - Facilities Records

                // }

        // Test Data for check in and check out
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-20"),
                java.sql.Date.valueOf("2019-04-23"), 2, 4, "KRHG-1000000000", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(1L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(4, java.sql.Date.valueOf("2019-04-03"),
                java.sql.Date.valueOf("2019-04-05"), 2, 3, "KRHG-1000000001", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(2L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(3, java.sql.Date.valueOf("2019-04-02"),
                java.sql.Date.valueOf("2019-04-06"), 2, 5, "KRHG-1000000002", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(3L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(3, java.sql.Date.valueOf("2019-04-20"),
                java.sql.Date.valueOf("2019-04-22"), 2, 3, "KRHG-1000000003", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(4L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(4, java.sql.Date.valueOf("2019-04-17"),
                java.sql.Date.valueOf("2019-04-20"), 2, 4, "KRHG-1000000004", "201", "DEX", "NULL",
                RoomBookingStatusEnum.CHECKED_IN, customerSessionLocal.retrieveForHouseKeeping(5L),
                room2));

        // Test Data for Room Order (HouseKeeping)
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(3, java.sql.Date.valueOf("2019-04-18"),
                java.sql.Date.valueOf("2019-04-21"), 2, 4, "KRHG-1000000005", "211", "DEX", "NULL",
                RoomBookingStatusEnum.CHECKED_IN, customerSessionLocal.retrieveForHouseKeeping(41L),
                room2));
        
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-18"),
                java.sql.Date.valueOf("2019-04-22"), 3, 5, "KRHG-1000000006", "212", "DEX", "NULL",
                RoomBookingStatusEnum.CHECKED_IN, customerSessionLocal.retrieveForHouseKeeping(42L),
                room2));

        // private void initializeData_roomBooking(){
        System.out.println("Start of RoomBooking");
        Date cid = new Date("12/04/2019");
        Date cod = new Date("15/04/2019");
        // 35 Superior room bookings
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-14"),
                java.sql.Date.valueOf("2019-04-21"), 5, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-16"),
                java.sql.Date.valueOf("2019-04-22"), 3, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-15"),
                java.sql.Date.valueOf("2019-04-25"), 3, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-14"),
                java.sql.Date.valueOf("2019-04-23"), 2, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-14"),
                java.sql.Date.valueOf("2019-04-24"), 6, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-20"),
                java.sql.Date.valueOf("2019-04-22"), 3, 2, "KRHG-1000000012", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-18"),
                java.sql.Date.valueOf("2019-04-22"), 5, 2, "KRHG-1000000013", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-19"),
                java.sql.Date.valueOf("2019-04-24"), 5, 2, "KRHG-1000000014", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-17"),
                java.sql.Date.valueOf("2019-04-24"), 9, 2, "KRHG-1000000015", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-17"),
                java.sql.Date.valueOf("2019-04-21"), 6, 2, "KRHG-1000000016", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-29"),
                java.sql.Date.valueOf("2019-05-05"), 8, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-26"),
                java.sql.Date.valueOf("2019-04-30"), 8, 2, "KRHG-1000000018", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));

        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-25"), 5, 2, "KRHG-1000000019", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-28"), 3, 2, "KRHG-1000000020", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-19"),
                java.sql.Date.valueOf("2019-04-24"), 3, 2, "KRHG-1000000021", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-25"),
                java.sql.Date.valueOf("2019-04-28"), 2, 2, "KRHG-1000000022", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-22"),
                java.sql.Date.valueOf("2019-04-29"), 6, 2, "KRHG-1000000023", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-26"),
                java.sql.Date.valueOf("2019-04-28"), 3, 2, "KRHG-1000000024", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-28"),
                java.sql.Date.valueOf("2019-04-28"), 3, 2, "KRHG-1000000025", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-30"),
                java.sql.Date.valueOf("2019-04-31"), 1, 2, "KRHG-1000000026", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-04"),
                java.sql.Date.valueOf("2019-05-03"), 1, 2, "KRHG-1000000027", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-03"),
                java.sql.Date.valueOf("2019-04-09"), 5, 2, "KRHG-1000000028", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-10"),
                java.sql.Date.valueOf("2019-04-20"), 10, 2, "KRHG-1000000029", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-07"),
                java.sql.Date.valueOf("2019-04-11"), 4, 2, "KRHG-1000000030", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));

        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-12"),
                java.sql.Date.valueOf("2019-04-20"), 8, 2, "KRHG-1000000031", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-15"),
                java.sql.Date.valueOf("2019-04-20"), 5, 2, "KRHG-1000000032", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-17"),
                java.sql.Date.valueOf("2019-04-24"), 3, 2, "KRHG-1000000033", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-22"),
                java.sql.Date.valueOf("2019-04-24"), 11, 2, "KRHG-1000000034", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-20"),
                java.sql.Date.valueOf("2019-04-24"), 6, 2, "KRHG-1000000035", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-15"),
                java.sql.Date.valueOf("2019-04-19"), 3, 2, "KRHG-1000000036", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-14"),
                java.sql.Date.valueOf("2019-04-18"), 5, 2, "KRHG-1000000037", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-15"),
                java.sql.Date.valueOf("2019-05-19"), 4, 2, "KRHG-1000000038", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-18"), 4, 2, "KRHG-1000000039", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-16"), 3, 2, "KRHG-1000000040", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room1));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-17"), 4, 2, "KRHG-1000000041", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room1));

        // 25 Deluxe Room bookings
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-20"), 5, 2, "KRHG-1000000042", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-16"),
                java.sql.Date.valueOf("2019-05-18"), 3, 2, "KRHG-1000000043", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-15"),
                java.sql.Date.valueOf("2019-05-17"), 3, 2, "KRHG-1000000044", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-14"),
                java.sql.Date.valueOf("2019-04-15"), 2, 2, "KRHG-1000000045", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-03-14"),
                java.sql.Date.valueOf("2019-03-19"), 6, 2, "KRHG-1000000046", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-28"), 3, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-23"),
                java.sql.Date.valueOf("2019-04-29"), 1, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-29"), 6, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-15"),
                java.sql.Date.valueOf("2019-04-30"), 5, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-30"), 4, 2, "AAA", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-29"),
                java.sql.Date.valueOf("2019-05-05"), 3, 2, "KRHG-1000000052", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-26"),
                java.sql.Date.valueOf("2019-04-30"), 2, 2, "KRHG-1000000053", "0", "SUR", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room2));

        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-25"), 5, 2, "KRHG-1000000054", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-24"),
                java.sql.Date.valueOf("2019-04-28"), 6, 2, "KRHG-1000000055", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-19"),
                java.sql.Date.valueOf("2019-04-24"), 7, 2, "KRHG-1000000056", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-25"),
                java.sql.Date.valueOf("2019-04-29"), 8, 2, "KRHG-1000000057", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-22"),
                java.sql.Date.valueOf("2019-04-29"), 4, 2, "KRHG-1000000058", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-26"),
                java.sql.Date.valueOf("2019-04-27"), 3, 2, "KRHG-1000000059", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-28"),
                java.sql.Date.valueOf("2019-04-29"), 2, 2, "KRHG-1000000060", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-30"),
                java.sql.Date.valueOf("2019-05-01"), 1, 2, "KRHG-1000000061", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-04"),
                java.sql.Date.valueOf("2019-05-03"), 1, 2, "KRHG-1000000062", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-03"),
                java.sql.Date.valueOf("2019-05-09"), 5, 2, "KRHG-1000000063", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-10"),
                java.sql.Date.valueOf("2019-05-20"), 10, 2, "KRHG-1000000064", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-07"),
                java.sql.Date.valueOf("2019-05-11"), 4, 2, "KRHG-1000000065", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room2));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-30"),
                java.sql.Date.valueOf("2019-05-26"), 4, 2, "KRHG-1000000066", "0", "DEX", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room2));

        // 15 Junior Suite Room bookings
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-30"),
                java.sql.Date.valueOf("2019-05-04"), 6, 2, "KRHG-1000000067", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-30"),
                java.sql.Date.valueOf("2019-05-05"), 6, 2, "KRHG-1000000068", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-28"),
                java.sql.Date.valueOf("2019-05-02"), 7, 2, "KRHG-1000000069", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-01"),
                java.sql.Date.valueOf("2019-05-02"), 8, 2, "KRHG-1000000070", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-04"),
                java.sql.Date.valueOf("2019-05-08"), 4, 2, "KRHG-1000000071", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-04"),
                java.sql.Date.valueOf("2019-05-08"), 3, 2, "KRHG-1000000072", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-17"), 2, 2, "KRHG-1000000073", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-18"),
                java.sql.Date.valueOf("2019-05-22"), 1, 2, "KRHG-1000000074", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-20"),
                java.sql.Date.valueOf("2019-05-24"), 1, 2, "KRHG-1000000075", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-21"),
                java.sql.Date.valueOf("2019-04-23"), 5, 2, "KRHG-1000000076", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-22"),
                java.sql.Date.valueOf("2019-04-28"), 10, 2, "KRHG-1000000077", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-26"),
                java.sql.Date.valueOf("2019-04-29"), 4, 2, "KRHG-1000000078", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-04-30"),
                java.sql.Date.valueOf("2019-05-04"), 4, 2, "KRHG-1000000079", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-03"),
                java.sql.Date.valueOf("2019-05-07"), 4, 2, "KRHG-1000000080", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room3));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-10"),
                java.sql.Date.valueOf("2019-05-15"), 4, 2, "KRHG-1000000081", "0", "JUN", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room3));

        // 10 Executive Suite Room Bookings
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-10"),
                java.sql.Date.valueOf("2019-05-15"), 6, 2, "KRHG-1000000082", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-20"), 7, 2, "KRHG-1000000083", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-09"),
                java.sql.Date.valueOf("2019-05-12"), 5, 2, "KRHG-1000000084", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-12"),
                java.sql.Date.valueOf("2019-05-15"), 4, 2, "KRHG-1000000085", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-21"), 5, 2, "KRHG-1000000086", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-16"),
                java.sql.Date.valueOf("2019-05-21"), 6, 2, "KRHG-1000000087", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-18"),
                java.sql.Date.valueOf("2019-05-22"), 4, 2, "KRHG-1000000088", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(7L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-02"),
                java.sql.Date.valueOf("2019-05-07"), 6, 2, "KRHG-1000000089", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(8L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-03"),
                java.sql.Date.valueOf("2019-05-08"), 6, 2, "KRHG-1000000090", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(9L), room4));
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-05-14"),
                java.sql.Date.valueOf("2019-05-21"), 7, 2, "KRHG-1000000091", "0", "EXE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(10L),
                room4));

        // 1 president suite room booking
        forcastSessionLocal.createTestRoomBooking(new RoomBookingEntity(2, java.sql.Date.valueOf("2019-03-14"),
                java.sql.Date.valueOf("2019-03-14"), 28, 2, "KRHG-1000000092", "0", "PRE", "NULL",
                RoomBookingStatusEnum.MANUAL, customerSessionLocal.retrieveForHouseKeeping(6L), room5));

        try {

            JobPositionEnum enum1 = JobPositionEnum.MANAGER;
            DepartmentEnum enum2 = DepartmentEnum.HOUSEKEEPING;

            StaffEntity manager1 = new StaffEntity("Ahman Khan", "Manager", enum1, enum2, "hkmanager1",
                    "371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            staffSessionLocal.createEntity(manager1);
            manager1.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            StaffEntity manager2 = new StaffEntity("Sheena Lister", "Manager", enum1, enum2);
            staffSessionLocal.createEntity(manager2);
            StaffEntity manager3 = new StaffEntity("Raiden Randall", "Manager", enum1, enum2);
            staffSessionLocal.createEntity(manager3);

            StaffEntity staff1 = new StaffEntity("Clayton Hull", "Staff", enum1, enum2, "hkstaff1",
                    "371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            staffSessionLocal.createEntity(staff1);
            staff1.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            StaffEntity staff2 = new StaffEntity("Patrick Gould", "Staff", enum1, enum2, "hkstaff2",
                    "371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            staffSessionLocal.createEntity(staff2);
            staff2.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            StaffEntity staff3 = new StaffEntity("Bailey Mair", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff3);
            StaffEntity staff4 = new StaffEntity("Kayley Benitez", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff4);
            StaffEntity staff5 = new StaffEntity("Miya Stafford", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff5);
            StaffEntity staff6 = new StaffEntity("Jaylan Blackwell", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff6);
            StaffEntity staff7 = new StaffEntity("Jennifer Ayala", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff7);
            StaffEntity staff8 = new StaffEntity("Mariya Bright", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff8);
            StaffEntity staff9 = new StaffEntity("Lauryn Velazquez", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff9);
            StaffEntity staff10 = new StaffEntity("Jolene Hendricks", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff10);
            StaffEntity staff11 = new StaffEntity("Jade Sykes", "Staff", enum1, enum2, "hkstaff11",
                    "371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            staffSessionLocal.createEntity(staff11);
            staff11.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
            StaffEntity staff12 = new StaffEntity("Kaci Iles", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff12);
            StaffEntity staff13 = new StaffEntity("Kaitlyn Cervantes", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff13);
            StaffEntity staff14 = new StaffEntity("Aiesha Landry", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff14);
            StaffEntity staff15 = new StaffEntity("Kareem Michael", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff15);
            StaffEntity staff16 = new StaffEntity("Luna Cherry", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff16);
            StaffEntity staff17 = new StaffEntity("Arianna Armstrong", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff17);

            StaffEntity staff18 = new StaffEntity("Olivia Randolph", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff18);
            StaffEntity staff19 = new StaffEntity("Penelope Salazar", "Staff", enum1, enum2);
            staffSessionLocal.createEntity(staff19);

            List<StaffEntity> shift1 = new ArrayList<>();
            shift1.add(manager1);
            shift1.add(staff1);
            shift1.add(staff2);
            shift1.add(staff3);
            shift1.add(staff4);
            shift1.add(staff5);
            shift1.add(staff6);
            shift1.add(staff7);
            shift1.add(staff8);
            shift1.add(staff9);
            shift1.add(staff10);

            List<StaffEntity> shift2 = new ArrayList<>();

            shift2.add(manager2);
            shift2.add(staff11);
            shift2.add(staff12);
            shift2.add(staff13);
            shift2.add(staff14);
            shift2.add(staff15);

            List<StaffEntity> shift3 = new ArrayList<>();
            shift3.add(manager3);
            shift3.add(staff16);
            shift3.add(staff17);

            RosterTypeEnum morning = RosterTypeEnum.SHIFT1;
            RosterTypeEnum afternoon = RosterTypeEnum.SHIFT2;
            RosterTypeEnum night = RosterTypeEnum.SHIFT3;

            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-10"), java.sql.Date.valueOf("2019-04-10"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-10"), java.sql.Date.valueOf("2019-04-10"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-10"), java.sql.Date.valueOf("2019-04-10"),
                    new java.util.Date(), night, shift3));

            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-18"), java.sql.Date.valueOf("2019-04-18"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-18"), java.sql.Date.valueOf("2019-04-18"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-18"), java.sql.Date.valueOf("2019-04-18"),
                    new java.util.Date(), night, shift3));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-19"), java.sql.Date.valueOf("2019-04-19"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-19"), java.sql.Date.valueOf("2019-04-19"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-19"), java.sql.Date.valueOf("2019-04-19"),
                    new java.util.Date(), night, shift3));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-20"), java.sql.Date.valueOf("2019-04-20"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-20"), java.sql.Date.valueOf("2019-04-20"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-20"), java.sql.Date.valueOf("2019-04-20"),
                    new java.util.Date(), night, shift3));

            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-22"), java.sql.Date.valueOf("2019-04-22"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-22"), java.sql.Date.valueOf("2019-04-22"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-22"), java.sql.Date.valueOf("2019-04-22"),
                    new java.util.Date(), night, shift3));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-23"), java.sql.Date.valueOf("2019-04-23"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-23"), java.sql.Date.valueOf("2019-04-23"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-23"), java.sql.Date.valueOf("2019-04-23"),
                    new java.util.Date(), night, shift3));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-24"), java.sql.Date.valueOf("2019-04-24"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-24"), java.sql.Date.valueOf("2019-04-24"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-24"), java.sql.Date.valueOf("2019-04-24"),
                    new java.util.Date(), night, shift3));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-29"), java.sql.Date.valueOf("2019-04-29"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-29"), java.sql.Date.valueOf("2019-04-29"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-29"), java.sql.Date.valueOf("2019-04-29"),
                    new java.util.Date(), night, shift3));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-30"), java.sql.Date.valueOf("2019-04-30"),
                    new java.util.Date(), morning, shift1));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-30"), java.sql.Date.valueOf("2019-04-30"),
                    new java.util.Date(), afternoon, shift2));
            workRosterSessionLocal.createEntity(new WorkRosterEntity("Housekeeping",
                    java.sql.Date.valueOf("2019-04-30"), java.sql.Date.valueOf("2019-04-30"),
                    new java.util.Date(), night, shift3));

            housekeepingRequestSessionLocal.createEntity(new HousekeepingRequestEntity("Delivery",
                    "Please deliver 2 more towels", rm85, staff1));
            housekeepingRequestSessionLocal.createEntity(new HousekeepingRequestEntity("Clean up",
                    "Please clean up the room, someone vomited on the floor", rm67, staff1));

        } catch (Exception e) {

        }
    }

    private void initializeData_customer() {
        // ....implement creation objects here
        Date adminDob = new Date("23/09/1992");
        byte adminGender = 1;
        CustomerEntity[] customers = new CustomerEntity[10];
        customers[0] = new CustomerEntity("Alice", "Tan", GenderEnum.FEMALE, "67891234", "S9002315A",
                "alice@gmail.com", new Date("20/10/2018"), new Date("21/04/2017"));
        customers[0].setCountry("Singapore");
        customers[1] = new CustomerEntity("Bob", "Lim", GenderEnum.MALE, "67891223", "S9103224F",
                "bob@gmail.com", new Date("02/05/2016"), new Date("27/08/2015"));
        customers[1].setCountry("Singapore");
        customers[2] = new CustomerEntity("Peter", "Wong", GenderEnum.MALE, "62920192", "S9604567J",
                "peter@gmail.com", new Date("10/11/2017"), new Date("22/03/2016"));
        customers[2].setCountry("Singapore");
        customers[3] = new CustomerEntity("Fatin", "Min", GenderEnum.MALE, "68943423", "S8923457H",
                "fatin@gmail.com", new Date("14/06/2017"), new Date("14/06/2016"));
        customers[3].setCountry("Singapore");
        customers[4] = new CustomerEntity("Vincent", "Tan", GenderEnum.MALE, "60984567", "S8872345T",
                "vincent@gmail.com", new Date("19/03/2016"), new Date("19/03/2015"));
        customers[4].setCountry("Singapore");
        // Customers to be used for test data
        customers[5] = new CustomerEntity("Patricia", "Koh", GenderEnum.FEMALE, "61222202", "S8624567P",
                "patricia@gmail.com", new Date("19/10/2016"), new Date("19/10/2015"));
        customers[5].setCountry("Singapore");
        customers[6] = new CustomerEntity("Wendy", "Lee", GenderEnum.FEMALE, "68273222", "S9023456B",
                "wendy@gmail.com", new Date("20/02/2016"), new Date("20/02/2015"));
        customers[6].setCountry("Singapore");
        customers[7] = new CustomerEntity("Jennifer", "Goh", GenderEnum.FEMALE, "67456789", "S5465768H",
                "jen@gmail.com", new Date("16/06/2015"), new Date("16/06/2016"));
        customers[7].setCountry("Singapore");
        customers[8] = new CustomerEntity("Tim", "Randy", GenderEnum.MALE, "65453546", "S9187634R",
                "tim@gmail.com", new Date("03/08/2012"), new Date("03/08/2013"));
        customers[8].setCountry("Singapore");
        customers[9] = new CustomerEntity("Richard", "Poh", GenderEnum.MALE, "65497054", "S8934568F",
                "richard@gmail.com", new Date("22/04/2016"), new Date("22/04/2015"));
        customers[9].setCountry("Singapore");

        for (int i = 0; i < customers.length; i++) {
            customers[i] = customerSessionLocal.createEntity(customers[i]);
        }

                // For Loyalty
        // Normal Tier 1-15
        CustomerEntity custMem1 = new CustomerEntity("Bernice", "Choy", GenderEnum.FEMALE, "96953579",
                "S9610911C", "Singapore", "bernicecpz@gmail.com", new Date("29/03/1996"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem1.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem1.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem1);

        CustomerEntity custMem2 = new CustomerEntity("Dylan", "Akhtar", GenderEnum.MALE, "96953100",
                "S9610100C", "Singapore", "dakhtar@gmail.com", new Date("29/03/1992"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem2.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem2.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem2);

        CustomerEntity custMem3 = new CustomerEntity("Jett", "Colley", GenderEnum.FEMALE, "96955101",
                "S9610101C", "Singapore", "jcolley@gmail.com", new Date("29/04/1992"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem3.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem3.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem3);

        CustomerEntity custMem4 = new CustomerEntity("Ashley", "Goodwin", GenderEnum.FEMALE, "96955102",
                "S9610102C", "Singapore", "agoodwin@gmail.com", new Date("19/01/1992"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem4.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem4.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem4);

        CustomerEntity custMem5 = new CustomerEntity("Rees", "Chan", GenderEnum.FEMALE, "96955103", "S9610103C",
                "Singapore", "rchan@gmail.com", new Date("12/01/1990"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem5.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem5.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem5);

        CustomerEntity custMem6 = new CustomerEntity("Chanel", "Tate", GenderEnum.FEMALE, "96955104",
                "S9610104F", "Singapore", "ctate@gmail.com", new Date("10/11/1999"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem6.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem6.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem6);

        CustomerEntity custMem7 = new CustomerEntity("Niko", "Mccray", GenderEnum.FEMALE, "96955105",
                "S9610105G", "Singapore", "nmccray@gmail.com", new Date("10/11/1998"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem7.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem7.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem7);

        CustomerEntity custMem8 = new CustomerEntity("Fallon", "Anthony", GenderEnum.FEMALE, "96955106",
                "S9610106G", "Singapore", "fanthony@gmail.com", new Date("20/11/1989"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem8.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem8.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem8);

        CustomerEntity custMem9 = new CustomerEntity("Beth", "Villa", GenderEnum.FEMALE, "96955107",
                "S9610107G", "Singapore", "bvilla@gmail.com", new Date("20/02/1999"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem9.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem9.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem9);

        CustomerEntity custMem10 = new CustomerEntity("Jessie", "Kay", GenderEnum.FEMALE, "96955108",
                "S9610108G", "Singapore", "jkay@gmail.com", new Date("25/12/1994"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem10.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem10.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem10);

        CustomerEntity custMem11 = new CustomerEntity("Troy", "North", GenderEnum.MALE, "96955109", "S9610109J",
                "Singapore", "tnorth@gmail.com", new Date("15/09/1992"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem11.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem11.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem11);

        CustomerEntity custMem12 = new CustomerEntity("Kacper", "Rodgers", GenderEnum.MALE, "96955110",
                "S9610110J", "Singapore", "krodgers@gmail.com", new Date("12/02/1993"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem12.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem12.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem12);

        CustomerEntity custMem13 = new CustomerEntity("Zayyan", "Jordan", GenderEnum.MALE, "96955111",
                "S9610111J", "Singapore", "zjordan@gmail.com", new Date("09/08/1990"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem13.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem13.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        custMem13 = customerSessionLocal.createEntity(custMem13);

        CustomerEntity custMem14 = new CustomerEntity("Jose", "Pickett", GenderEnum.MALE, "96955112",
                "S9610112J", "Singapore", "jpickett@gmail.com", new Date("09/08/1990"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem14.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem14.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem14);

        CustomerEntity custMem15 = new CustomerEntity("Zachery", "Barrett", GenderEnum.MALE, "96955113",
                "S9610113J", "Singapore", "zbarrett@gmail.com", new Date("19/09/1990"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem15.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem15.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem15);

        // Silver Tier 16-25
        CustomerEntity custMem16 = new CustomerEntity("Moses", "Russo", GenderEnum.MALE, "96955114",
                "S9610114J", "Singapore", "mrusso@gmail.com", new Date("01/04/1990"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem16.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem16.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem16);

        CustomerEntity custMem17 = new CustomerEntity("Sophia", "Tan", GenderEnum.MALE, "96955115", "S9610115F",
                "Singapore", "stan@gmail.com", new Date("21/03/1991"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem17.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem17.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem17);

        CustomerEntity custMem18 = new CustomerEntity("Fallon", "Decker", GenderEnum.MALE, "96955116",
                "S9610116J", "Singapore", "fdecker@gmail.com", new Date("11/03/1990"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem18.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem18.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem18);

        CustomerEntity custMem19 = new CustomerEntity("Norman", "Mohammed", GenderEnum.MALE, "96955117",
                "S9610117J", "Singapore", "nmohammed@gmail.com", new Date("21/07/1994"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem19.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem19.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem19);

        CustomerEntity custMem20 = new CustomerEntity("Johnathon", "Roach", GenderEnum.MALE, "96955118",
                "S9610118J", "Singapore", "jroach@gmail.com", new Date("21/09/1994"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem20.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem20.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem20);

        CustomerEntity custMem21 = new CustomerEntity("Andrew", "Spears", GenderEnum.MALE, "96955119",
                "S9610119J", "Singapore", "aspears@gmail.com", new Date("12/10/1995"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem21.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem21.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem21);

        CustomerEntity custMem22 = new CustomerEntity("Raisa", "Leonard", GenderEnum.MALE, "96955120",
                "S9610120J", "Singapore", "rleonard@gmail.com", new Date("15/06/1996"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem22.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem22.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem22);

        CustomerEntity custMem23 = new CustomerEntity("Amiyah", "Lee", GenderEnum.FEMALE, "96955121",
                "S9610121J", "Singapore", "alee@gmail.com", new Date("17/07/1996"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem23.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem23.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem23);

        CustomerEntity custMem24 = new CustomerEntity("Donna", "Tan", GenderEnum.FEMALE, "96955122",
                "S9610122J", "Singapore", "dtan@gmail.com", new Date("07/08/1996"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem24.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem24.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem24);

        CustomerEntity custMem25 = new CustomerEntity("Alicia", "Toh", GenderEnum.FEMALE, "96955123",
                "S9610123J", "Singapore", "atoh@gmail.com", new Date("23/01/1993"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem25.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem25.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem25);

        // Gold - Tier 25-30
        CustomerEntity custMem26 = new CustomerEntity("Charlotte", "Yee", GenderEnum.FEMALE, "96955124",
                "S9610124J", "Singapore", "cyee@gmail.com", new Date("22/02/1997"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem26.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem26.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem26);

        CustomerEntity custMem27 = new CustomerEntity("Nichole", "Soh", GenderEnum.FEMALE, "96955125",
                "S9610125J", "Singapore", "nsoh@gmail.com", new Date("19/08/1991"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem27.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem27.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem27);

        CustomerEntity custMem28 = new CustomerEntity("Leia", "Koh", GenderEnum.FEMALE, "92235126", "S9610126J",
                "Singapore", "lkoh@gmail.com", new Date("11/10/1997"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem28.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem28.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem28);

        CustomerEntity custMem29 = new CustomerEntity("Selina", "Loh", GenderEnum.FEMALE, "94321127",
                "S9610127J", "Singapore", "sloh@gmail.com", new Date("15/02/1998"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem29.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem29.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem29);

        CustomerEntity custMem30 = new CustomerEntity("Tiya", "Goh", GenderEnum.FEMALE, "91234128", "S9610128J",
                "Singapore", "tgoh@gmail.com", new Date("15/02/1998"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem30.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem30.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem30);

        // Customers used for Room Order
        CustomerEntity custMem31 = new CustomerEntity("Vivian", "Koh", GenderEnum.FEMALE, "62547621",
                "S9678245G", "Singapore", "scivincent@gmail.com", new Date("22/03/1996"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem31.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        custMem31.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem31);

        CustomerEntity custMem32 = new CustomerEntity("Katrine", "Rah", GenderEnum.FEMALE, "67876542",
                "S9756534R", "Singapore", "katrine@gmail.com", new Date("24/07/1997"),
                java.sql.Date.valueOf(LocalDate.now()));
        custMem32.setMembershipStatus(CustomerMembershipEnum.NON_MEMBER);
        custMem32.setPassword("371F8BCAA3489177E0496061D1216E0F29EA2448BEA55258B");
        customerSessionLocal.createEntity(custMem32);

        System.out.println("***Created Customer at DataInitisationBean***\n");

    }

    private void initializeData_invoice() {
        invoiceSessionLocal.createEntity(new InvoiceEntity("90009923", java.sql.Date.valueOf("2019-04-19"),
                "KRHG - Wedding Package 1",
                "This payment is made for " + " Kent Ridge Hotel Wedding Package 1", "Unpaid",
                new BigDecimal("1000.00")));
        invoiceSessionLocal.createEntity(new InvoiceEntity("87334454", java.sql.Date.valueOf("2019-04-29"),
                "KRHG - Wedding Package 2",
                "This payment is made for Kent Ridge Hotel Wedding Package 2", "Unpaid",
                new BigDecimal("1090.50")));
        invoiceSessionLocal.createEntity(new InvoiceEntity("67882822", java.sql.Date.valueOf("2019-06-04"),
                "Function Event for Big E Organisation", "Function room is booked", "Unpaid",
                new BigDecimal("890.00")));
        invoiceSessionLocal.createEntity(new InvoiceEntity("78889923", java.sql.Date.valueOf("2019-07-17"),
                "KRHG - Wedding Package 3",
                "This payment is made for" + " Kent Ridge Hotel Wedding Package 3", "Unpaid",
                new BigDecimal("2550.20")));
        invoiceSessionLocal.createEntity(new InvoiceEntity("89221234", java.sql.Date.valueOf("2019-08-15"),
                "Room 210 Service Bill",
                "The following has been requested by Room 210: 1. 3x Seafood Fried Rice 2. 3x Coke",
                "Paid", new BigDecimal("1970.90")));
        invoiceSessionLocal.createEntity(new InvoiceEntity("99999988", java.sql.Date.valueOf("2019-04-21"),
                "Conference Room",
                "This payment is for facility booking.",
                "Unpaid", new BigDecimal("1032.00")));
    }

    private void initializeData_RoomOrders() {
        roomOrderSessionLocal.createEntity(new RoomOrderEntity(java.sql.Date.valueOf("2019-04-19"),
                "KRHG - Room Service Bill",
                "Room Payments",
                "211", "Unpaid", new BigDecimal("00.00")));
        roomOrderSessionLocal.createEntity(new RoomOrderEntity(java.sql.Date.valueOf("2019-04-21"),
                "KRHG - Room Service Bill",
                "Room Payments",
                "212", "Unpaid", new BigDecimal("00.00")));
        roomOrderItemSessionLocal.createEntity(new RoomOrderItemEntity("Coke", "Ordered 1x coke",
                new BigDecimal("2.50"), roomOrderSessionLocal.retrieveRoomOrderById(1L)));
        roomOrderItemSessionLocal.createEntity(new RoomOrderItemEntity("Fried Kway Teow",
                "Ordered 5x Fried Kway Teow", new BigDecimal("45.60"),
                roomOrderSessionLocal.retrieveRoomOrderById(1L)));
        roomOrderItemSessionLocal.createEntity(new RoomOrderItemEntity("Sprite", "Ordered 2x Sprite",
                new BigDecimal("5.60"), roomOrderSessionLocal.retrieveRoomOrderById(2L)));
        roomOrderItemSessionLocal.createEntity(new RoomOrderItemEntity("Hokkein Mee", "Ordered 3x Hokkein Mee",
                new BigDecimal("35.80"), roomOrderSessionLocal.retrieveRoomOrderById(2L)));
    }
}
