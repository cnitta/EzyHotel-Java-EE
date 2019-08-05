/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import hms.hpm.session.FacilitySessionLocal;
import hms.hpm.session.HotelSessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import hms.sales.session.PromotionSessionLocal;
import hms.sales.session.SalesPackageSessionLocal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.FacilityEntity;
import util.entity.HotelEntity;
import util.entity.PriceRateEntity;
import util.entity.PromotionEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomTypeEntity;
import util.entity.SalesPackageEntity;

/**
 *
 * @author berni
 */

@Stateless
public class InformationSession implements InformationSessionLocal {
    @EJB
    private OnlineRoomBookingSessionLocal onlineRoomBookingSessionLocal;
    
    @EJB
    private RoomSessionLocal roomSessionLocal;
    @EJB
    private RoomTypeSessionLocal roomTypeSessionLocal;
    @EJB
    private PromotionSessionLocal promotionSessionLocal;
    
    @EJB
    private SalesPackageSessionLocal salesPackageSessionLocal;
    
    @EJB
    private FacilitySessionLocal facilitySessionLocal;
    
    @EJB
    private HotelSessionLocal hotelSessionLocal;
    
    @EJB
    private PriceRateSessionLocal priceRateSessionLocal;   
    
    @PersistenceContext
    private EntityManager em;
    
    
    private List<RoomBookingEntity> getBookingsForRoomType(String roomType, String hotelName){
        String queryInput = "SELECT c FROM RoomBookingEntity c WHERE c.roomType.name =:inValueA AND c.roomType.hotel.name =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", roomType);
        query.setParameter("inValueB", hotelName);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }     
    }
    
    private List<LocalDate> getDateRangeForRoomBooking(RoomBookingEntity rb){
        System.out.println("RB Start Date: " + rb.getCheckInDateTime() + ", RB End Date: " + rb.getCheckOutDateTime());
        LocalDate start = rb.getCheckInDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = rb.getCheckOutDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        List<LocalDate> listOfDates = new ArrayList<>();
     
        while (!start.isAfter(end)) {
            
            listOfDates.add(start);
            start = start.plusDays(1);
        }
        
        return listOfDates;
    }
    
        
    private RoomTypeEntity getTotalRooms(String roomType, String hotelName){
        String queryInput = "SELECT c FROM RoomTypeEntity c WHERE c.name =:inValueA AND c.hotel.name =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", roomType);
        query.setParameter("inValueB", hotelName);
        try {
            return (RoomTypeEntity)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }     
    }
    
    private HashMap<LocalDate, Integer> getBookedRoomTypeForDay(Date startDate, Date endDate,String hotelName, String roomType) throws Exception{
        
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        List<LocalDate> custListOfDates = new ArrayList<>();
     
        while (!start.isAfter(end)) {
            
            custListOfDates.add(start);
            start = start.plusDays(1);
        }
        
        HashMap<LocalDate, Integer> bookingsPerDay = new HashMap<>();
        for(LocalDate d: custListOfDates){
            bookingsPerDay.put(d, 0);
        }
        System.out.println("Populated hashmap: " + bookingsPerDay.toString());
    
        //Get bookings from specific hotel and roomType
        List<RoomBookingEntity> getBookings = getBookingsForRoomType(roomType, hotelName);
        System.out.println("Number of booking for roomType: " + getBookings.size());
        
        //Find the bookings that intersect with the customer selected dates
        List<RoomBookingEntity> intersects = new ArrayList<>();
        for(RoomBookingEntity rb: getBookings){
            
            if(rb.getRoomType().getName().equals(roomType)){
                System.out.println("CURRENT BOOKING: " + rb.getBookingId());
                System.out.println("Start Date: " + rb.getCheckInDateTime() + ", End Date: " + rb.getCheckOutDateTime());
                if(rb.getCheckInDateTime().before(endDate)){

                    
                    if(rb.getCheckInDateTime().compareTo(startDate) >= 0 && rb.getCheckOutDateTime().compareTo(endDate) <= 0){
                        System.out.println("Equal start and end date");
                        intersects.add(rb);
                    }else if(rb.getCheckInDateTime().compareTo(startDate) > 0 && rb.getCheckOutDateTime().compareTo(endDate) < 0){
                        System.out.println("rb start later than cust, but end earlier than cust");
                        intersects.add(rb);
                    }else if(rb.getCheckInDateTime().compareTo(startDate) > 0 && rb.getCheckOutDateTime().compareTo(endDate) > 0){
                        System.out.println("rb start later than cust and end later than cust");
                        intersects.add(rb);
                    }
                             
                }
                
            }
        }
        
        System.out.println("Number of Intersects: " + intersects.size());
        //Then loop through this list of bookings
        System.out.println("Customer rb date range: " + custListOfDates.toString());
        for(RoomBookingEntity roomBooking : intersects){
            List<LocalDate> dateRangeForBooking = getDateRangeForRoomBooking(roomBooking);
            System.out.println("Existing rb date range: " + dateRangeForBooking.toString());
            for(LocalDate d : dateRangeForBooking){
                System.out.println("Current Date to Check: " + d);
                if(custListOfDates.contains(d)){
                    System.out.println("Before: " + bookingsPerDay.toString());
                    Integer roomCount = bookingsPerDay.get(d) + 1;
                    bookingsPerDay.replace(d, roomCount);
                    System.out.println("After: " + bookingsPerDay.toString());
                }
            }
        }
        return bookingsPerDay;
    }
    
    //Check if have intersects in the dates & if there is any rooms available

    @Override
    public boolean checkVacancyForDateRange(Date startDate, Date endDate, String roomType, String hotelName) throws Exception{
        HashMap<LocalDate, Integer> bookedRoomCountForRoomType = getBookedRoomTypeForDay(startDate, endDate, hotelName, roomType);
        System.out.println("Final List: " + bookedRoomCountForRoomType.toString());
        
        boolean availableForDateRange = true;
        try{
           RoomTypeEntity getRoomType = getTotalRooms(roomType, hotelName);
           
            for(LocalDate dateInRange : bookedRoomCountForRoomType.keySet()){
                System.out.println("Room Count: " + getRoomType.getTotalRooms());
                if(bookedRoomCountForRoomType.get(dateInRange) > getRoomType.getTotalRooms()){
                    availableForDateRange = false;
                    return availableForDateRange;
                }
            }     
        }catch(Exception e){
            availableForDateRange = false;
            return availableForDateRange;
        }
  
        return availableForDateRange;
    }

    
    

     
    @Override
    public InformationRetrievalClass getAllInformationForHotel(String hotelName) throws Exception{
        InformationRetrievalClass infoRetrieval = new InformationRetrievalClass();
        
        //Set hotel information
        HotelEntity hotel = getHotelByName(hotelName);
        infoRetrieval.setHotel(hotel);
        
        List<FacilityEntity> facilities = getFacilitiesById(hotel.getHotelId());
        
        //Applicable across all hotels
//        List<SalesPackageEntity> salePackages = salesPackageSessionLocal.retrieveAllEntities();
//        List<PromotionEntity> promotions = promotionSessionLocal.retrieveAllEntities();
        
        //Set price rate, room type details based on the specific hotel
        infoRetrieval.setPriceRoomTypeInfos(formatPriceRoomTypeInformationByHotelId(hotel.getHotelId()));
        infoRetrieval.setFacilities(facilities);
//        infoRetrieval.setSalesPackages(salePackages);
//        infoRetrieval.setPromotions(promotions);
        infoRetrieval.setDateTimeRetrieval(LocalDateTime.now());
        
        return infoRetrieval;
    }

    
    private List<RoomBookingEntity> getRoomBookingsByHotel(String hotelName) throws Exception{
        String queryInput = "SELECT c FROM RoomBookingEntity c WHERE c.roomType.hotel.name =:inValue";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", hotelName);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    private HotelEntity getHotelByName(String hotelName) throws Exception{
        try{
            HotelEntity getHotel = hotelSessionLocal.retrieveEntity("name", hotelName);
            return getHotel;
        }catch(Exception e){
            throw new Exception ("Hotel not found.");
        }
    }
    
    private List<FacilityEntity> getFacilitiesById(Long hotelId) throws Exception{
        try{
            List<FacilityEntity> facilities = retrieveFacilitiesByHotelId("hotel.hotelId", hotelId);  
            return facilities;
        }catch(Exception e){
            throw new Exception ("Facilities not found");
        }
    }
    
    
    //Retrieve price rate to be relevant to the hotel
    @Override
    public List<PriceRateRoomTypeClass> formatPriceRoomTypeInformationByHotelId(Long hotelId){
    
        try
        {
            List<PriceRateRoomTypeClass> priceRoomTypeInfos = new ArrayList<>();
        
            //Contains the  by hotelId and roomTypeId
            List<RoomTypeEntity> roomTypes = retrieveRoomTypeByHotel("hotel.hotelId", hotelId);  

            //Check today's date
            int day = LocalDate.now().getDayOfWeek().getValue();
            System.out.println("Today is: " + day);
            System.out.println(roomTypes);
         
            //Check if the room type exists in the hotel
            for(RoomTypeEntity roomType : roomTypes){
                
                //Retrieve the price rates related to the current room type
                List<PriceRateEntity> priceRates = retrievePriceRatesByRoomType("roomType.roomTypeId", roomType.getRoomTypeId());
                
                int indexToRemove = 0;
                for(PriceRateEntity priceRate : priceRates){
                    if(priceRate.getRateTitle().contains("Group")){
                        continue;
                    }
                    
                    if(priceRate.getRateTitle().contains("Standard") || priceRate.getRateTitle().contains("Premier")){
                        if(day >= 6){
                            indexToRemove = priceRates.indexOf(priceRate);
                            break;
                        }
                    }
                    
                    if(priceRate.getRateTitle().contains("Leisure")){
                        if(day < 6){
                             indexToRemove = priceRates.indexOf(priceRate);
                             break;
                        }
                    }
                }
                priceRates.remove(indexToRemove);
                

                //Final list of price rates to keep for current room type -> Convert to PriceRateRoomTypeClass
                priceRoomTypeInfos = formatPriceRoomType(priceRates, priceRoomTypeInfos);
            }

       
            if(priceRoomTypeInfos == null || priceRoomTypeInfos.isEmpty()){
                priceRoomTypeInfos = new ArrayList<>();
                
                System.out.println("PriceRoomTypeInfo is empty or null");
            }

            return priceRoomTypeInfos;
                
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return new ArrayList<>();
    }

    
    @Override
    public List<PriceRateEntity> getPriceRateByDay(Long roomTypeId){
    
        try
        {

            //Check today's date
            int day = LocalDate.now().getDayOfWeek().getValue();
            System.out.println("Today is: " + day);
         
                //Retrieve the price rates related to the current room type
                List<PriceRateEntity> priceRates = retrievePriceRatesByRoomType("roomType.roomTypeId", roomTypeId);
                
                int indexToRemove = 0;
                for(PriceRateEntity priceRate : priceRates){
                    if(priceRate.getRateTitle().contains("Group")){
                        continue;
                    }
                    
                    if(priceRate.getRateTitle().contains("Standard") || priceRate.getRateTitle().contains("Premier")){
                        if(day >= 6){
                            indexToRemove = priceRates.indexOf(priceRate);
                            break;
                        }
                    }
                    
                    if(priceRate.getRateTitle().contains("Leisure")){
                        if(day < 6){
                             indexToRemove = priceRates.indexOf(priceRate);
                             break;
                        }
                    }
                }
                priceRates.remove(indexToRemove);
                


            return priceRates;
                
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return new ArrayList<>();
    }    
    
    
    private List<PriceRateRoomTypeClass> formatPriceRoomType(List<PriceRateEntity> priceRates, List<PriceRateRoomTypeClass> currentList){
        for(PriceRateEntity priceRate : priceRates){
            PriceRateRoomTypeClass priceRoomTypeInfo = new PriceRateRoomTypeClass();
            
            priceRoomTypeInfo.setRoomType(priceRate.getRoomType().getName());
            priceRoomTypeInfo.setRoomTypeCode(priceRate.getRoomType().getRoomTypecode());
            priceRoomTypeInfo.setMaxGuestSize(priceRate.getRoomType().getNumMaxGuest());
            
            priceRoomTypeInfo.setPriceRateTitle(priceRate.getRateTitle());
            priceRoomTypeInfo.setFinalPrice(priceRate.getMarkupPrice());
            
            currentList.add(priceRoomTypeInfo);
        }
        return currentList;
    }
    
    
    private List<FacilityEntity> retrieveFacilitiesByHotelId(String attribute, Long hotelId){
        String queryInput = "SELECT c FROM FacilityEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", hotelId);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    
    @Override
    public List<PriceRateEntity> retrievePriceRatesByRoomType(String attribute, Long roomTypeId){
        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", roomTypeId);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    @Override
    public List<RoomTypeEntity> retrieveRoomTypeByHotel(String attribute, Long hotelId){
        String queryInput = "SELECT c FROM RoomTypeEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", hotelId);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    
    
    
    
    
    
    


    
}
