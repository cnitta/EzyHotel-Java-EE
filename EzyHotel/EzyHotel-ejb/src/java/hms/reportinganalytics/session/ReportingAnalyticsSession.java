/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.reportinganalytics.session;

import hms.common.CommonMethods;
import hms.common.CommonMethods.Triple;
import hms.frontdesk.session.PaymentSessionLocal;
import hms.frontdesk.session.RoomBookingSessionLocal;
import hms.hpm.session.RoomTypeSessionLocal;
import hms.prepostarrival.session.OnlineCustomerSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import util.entity.PaymentEntity;

/**
 *
 * @author berni
 */
@Stateless
public class ReportingAnalyticsSession implements ReportingAnalyticsSessionLocal {
    @EJB
    private OnlineCustomerSessionLocal onlineCustomerSession;

    @EJB
    private RoomTypeSessionLocal roomTypeSession;
    
    @EJB
    private RoomBookingSessionLocal roomBookingSession;
    
    @EJB
    private PaymentSessionLocal paymentSessionLocal;
    
    @EJB
    private PriceRateSessionLocal priceRateSessionLocal;
    
    private int currentMonth = LocalDate.now().getMonthValue()-1; //Start from 1
    
    private CommonMethods commons = new CommonMethods();
    private List<PaymentEntity> allPayments = new ArrayList<>();
    
    //valueFirst: Integer Month (Start from 0), valueSecond: Integer Count of Rooms, valueThird: Total Room Revenue for Month
    private HashMap<Integer, Pair<Integer, BigDecimal>> monthlyAdr = new HashMap<>();
    
    // private List<BigDecimal> monthlyRevPar = new ArrayList<>(Collections.nCopies(12, BigDecimal.ZERO));
    private void initializeContainer(){
        for(Integer month = 0; month < 11; month++){
            monthlyAdr.put(month, new Pair(0, BigDecimal.ZERO));
        }
    
    }
    
    //Hotel-Specific
    
    @Override
    public void averageDailyRate(Long hotelId){
       //Formula: Room Revenue/ Paid Occupied Rooms
       initializeContainer();
       allPayments = paymentSessionLocal.getPaymentByHotel(hotelId);
       Calendar calendar = Calendar.getInstance();

       if(allPayments.size() > 0){
           System.out.println("# of payments: " + allPayments.size());
            for(PaymentEntity payment: allPayments){
              calendar.setTime(payment.getRoomBooking().getCheckInDateTime());                      
              int bookingDateMonth = calendar.get(Calendar.MONTH);
              System.out.println("Booking id: " + payment.getRoomBooking().getBookingId() + ",BookingDateMonth: " + bookingDateMonth + ",Amount: " + payment.getTotalAmount());
              
              System.out.println("Month Adr Before Update: " + monthlyAdr.get(bookingDateMonth));
              Integer roomCount = monthlyAdr.get(bookingDateMonth).getKey();
              BigDecimal roomRevenue = monthlyAdr.get(bookingDateMonth).getValue();
              System.out.println("BEFORE= Room Count: " + roomCount + ", roomRevenue: " + roomRevenue);
              roomCount = roomCount + 1;
              roomRevenue = roomRevenue.add(payment.getTotalAmount());
              System.out.println("AFTER= Room Count: " + roomCount + ", roomRevenue: " + roomRevenue);
              boolean updatePairStatus =  monthlyAdr.replace(bookingDateMonth,monthlyAdr.get(bookingDateMonth),new Pair(roomCount, roomRevenue));
              System.out.println("Update Pair Status: " + updatePairStatus);
              System.out.println("Monthly Adr After Udpate: " + monthlyAdr.get(bookingDateMonth));
     
            }
            
            System.out.println("Monthly ADR Computation");
            System.out.println(monthlyAdr.toString());
        }
       
       
    }
    
    
    
    public void revenuePerAvailableRoom(){
        //Formula: Total Room Revenue / Total Rooms Available
        
    }
    
    
    
}
