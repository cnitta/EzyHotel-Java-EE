/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.HousekeepingForcastEntity;
import util.entity.RoomBookingEntity;

/**
 *
 * @author bryantan
 */
@Stateless
@LocalBean
public class ForcastTimer {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ForcastSessionLocal forcastSessionLocal;

    //test timed logger
    private final Logger log = Logger
     .getLogger(ForcastTimer.class.getName());
     
    //every hour reconstruct forcast table
    @Schedule(minute = "*/1", hour = "*")
    public void forcastScript() {
        
     Query q1 = em.createQuery("SELECT f FROM HousekeepingForcastEntity f");
     if (!q1.getResultList().isEmpty()) {
     //purge database if database not empty
     Query q2 = em.createQuery("DELETE FROM HousekeepingForcastEntity");
     q2.executeUpdate();
     }
     log.log(Level.INFO,
     "UPDATE1: " + new Date().toString());
         
        
     //curent date
     Date currentDate = new Date();
        
     //current date + 30 days
     Calendar c = Calendar.getInstance(); 
     c.setTime(currentDate); 
     c.add(Calendar.DATE, 30);
     Date laterDate = c.getTime();
        
     Query q3 = em.createQuery("SELECT r FROM RoomBookingEntity r");
        
     List<RoomBookingEntity> newList = new ArrayList<>();
     List<RoomBookingEntity> oldList = q3.getResultList();
        
        
        
        
     for (RoomBookingEntity booking : oldList) {
     Date checkIn = booking.getCheckInDateTime();
     Date checkOut = booking.getCheckOutDateTime();
            
            
     //if date ranges overlap, add to new list
     if (currentDate.before(checkOut) || currentDate.equals(checkOut) && laterDate.after(checkIn) || laterDate.equals(checkIn)) {
     newList.add(booking);
     }
     }
        
     Calendar start = Calendar.getInstance();
     Calendar end = Calendar.getInstance();
     start.setTime(currentDate);
     end.setTime(laterDate);
        
        
        
        
        
     //superior, deluxe, junior, executive, president, total EstimatedCleaning Time, etimated housekeepers
     Map<Date, List<Integer>> map1 = new HashMap();
     Double time = 60.0;
        
     //iterate through 30days
     for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
     int superior = 0;
     int deluxe = 0;
     int junior = 0;
     int executive = 0;
     int president = 0;
     int estimatedCleaningTime = 0;
     List<Integer> bookings = new ArrayList<>();
     for (RoomBookingEntity b : newList) {
                 
     Date checkIn = b.getCheckInDateTime();
     Date checkOut = b.getCheckOutDateTime();
                 
     //if date is within checkin->checkout
     if (!date.before(checkIn) && !date.after(checkOut)) {
     String roomType = b.getRoomType().getName();
     switch(roomType) {
     case "Superior":
     superior++;
     estimatedCleaningTime += 30;
     break;
     case "Deluxe":
     deluxe++;
     estimatedCleaningTime += 35;
     break;
     case "Junior Suite":
     junior++;
     estimatedCleaningTime += 40;
     break;
     case "Executive Suite":
     executive++;
     estimatedCleaningTime += 60;
     break;
     case "President Suite":
     president++;
     estimatedCleaningTime += 90;
     break;
     default:
     System.out.println("something wrong with switch");    
     }
     }
     }
     bookings.add(superior);
     bookings.add(deluxe);
     bookings.add(junior);
     bookings.add(executive);
     bookings.add(president);
     bookings.add(estimatedCleaningTime);
            
           
            
     //calculate estimated housekeepers based on cleaning time
     int housekeepers = (int) Math.ceil(estimatedCleaningTime/time);
            
     bookings.add(housekeepers);
     map1.put(date, bookings);
     }
     log.log(Level.INFO,
     "UPDATE2: forcastTimer running");
        
     //Insert data into database
     map1.forEach((k, v) -> {
     Time time1= new Time(0,v.get(5),0);
     java.sql.Date sd = new java.sql.Date(k.getTime());
     int numberDirtyRooms = v.get(0) + v.get(1) + v.get(2) + v.get(3) + v.get(4);
     forcastSessionLocal.createEntity(new HousekeepingForcastEntity(sd, numberDirtyRooms, time1, v.get(6), v.get(0),v.get(1), v.get(2), v.get(3), v.get(4)));
     });
     }
     
}
