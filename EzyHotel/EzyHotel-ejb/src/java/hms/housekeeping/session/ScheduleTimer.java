/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import hms.frontdesk.session.RoomBookingSessionLocal;
import hms.hpm.session.FacilitySessionLocal;
import hms.hpm.session.RoomSessionLocal;
import hms.hr.session.WorkRosterSessionLocal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.FacilityEntity;
import util.entity.HousekeepingRecordEntity;
import util.entity.HousekeepingSOPEntity;
import util.entity.HousekeepingStaffEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;

/**
 *
 * @author bryantan
 */
@Stateless
@LocalBean
public class ScheduleTimer {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private WorkRosterSessionLocal workRosterSessionLocal;

    @EJB
    private RoomSessionLocal roomSessionLocal;

    @EJB
    private HousekeepingRecordSessionLocal housekeepingRecordSessionLocal;

    @EJB
    private RoomBookingSessionLocal roomBookingSessionLocal;

    @EJB
    private FacilitySessionLocal facilitySessionLocal;

    //test timed logger
    private final Logger log = Logger
     .getLogger(ForcastTimer.class.getName());
     
    //manually set
    @Schedule(minute = "*/1", hour = "*")
     public void generateSchedule() {
     log.log(Level.INFO,
     "Update 3: " + new Date().toString() + "ScheduleTimer is running");
        
     //for demonstration purge database
     Query q1 = em.createQuery("SELECT f FROM HousekeepingRecordEntity f");
     if (!q1.getResultList().isEmpty()) {
     //purge database if database not empty
     Query q2 = em.createQuery("DELETE FROM HousekeepingRecordEntity");
     q2.executeUpdate();
     }
        
        
     //handling evening facility cleaning
     WorkRosterEntity todayEveningRoster = workRosterSessionLocal.retrieveTodayEveningRosters();
     StaffEntity eveningManager = todayEveningRoster.getStaffs().get(0);
     String eveningManagerName = eveningManager.getName();
     //assign facilities to evening staff
     List<FacilityEntity> facilities = facilitySessionLocal.retrieveAllEntities();
     int count = 0;
     for (StaffEntity housekeeperEvening: todayEveningRoster.getStaffs()) {
     if (housekeeperEvening == todayEveningRoster.getStaffs().get(0)) continue;
     FacilityEntity facility = facilities.get(count);
     housekeepingRecordSessionLocal.createEntity(new HousekeepingRecordEntity(housekeeperEvening, facility, eveningManagerName, "Evening"));
     count++;
     }
        
        
     //cant do this for now as roombooking not assgined any room entity
        
     //        //get todays evening workroster
     //        //Is 10 April for demonstration purposes

     //        List<RoomBookingEntity> checkoutRoomBookings = roomBookingSessionLocal.retrieveTodayCheckOut();
     //        List<RoomEntity> checkoutRooms = new ArrayList<>();
     //        for (RoomBookingEntity roomBooking: checkoutRoomBookings) {
     //            String roomId = roomBooking.getRoomNumber();
     //            RoomEntity room = roomSessionLocal.retrieveEntityById(Long.valueOf(roomId));
     //            checkoutRooms.add(room);
     //        }
     //        
     //        //handle scheduling for evening shift
     //        int staffTimeEvening = 0;
     //        List<RoomEntity> staffRoomsEvening = new ArrayList<>();
     //        for (StaffEntity housekeeperEvening: todayEveningRoster.getStaffs()) {
     //            if (housekeeperEvening == todayEveningRoster.getStaffs().get(0)) continue;
     //            staffTimeEvening = 0;
     //            staffRoomsEvening.clear();
     //            for (RoomEntity checkoutRoom: checkoutRooms) {
     //                switch(checkoutRoom.getRoomType().getName()) {
     //                    case "Superior":
     //                        staffTimeEvening += 30;
     //                        break;
     //                    case "Deluxe":
     //                        staffTimeEvening += 35;
     //                        break;
     //                    case "Junior Suite":
     //                        staffTimeEvening += 45;
     //                        break;
     //                    case "Executive Suite":
     //                        staffTimeEvening += 60;
     //                        break;
     //                    case "President Suite":
     //                        break;
     //                    default:
     //                }
     //                //4hrs - 15mins leeway
     //                if (staffTimeEvening < 225) {
     //                    staffRoomsEvening.add(checkoutRoom);
     //                } else {
     //                    break;
     //                }
     //            }
     //            checkoutRooms.removeAll(staffRoomsEvening);
     //            housekeepingRecordSessionLocal.createEntity(new HousekeepingRecordEntity(housekeeperEvening, staffRoomsEvening, eveningManagerName ));
     //        }
        
     //get todays morning workroster
     //Is 10 April for demonstration purposes
     WorkRosterEntity todayWorkRoster = workRosterSessionLocal.retrieveTodayMorningRosters();
     StaffEntity manager = todayWorkRoster.getStaffs().get(0);
     String managerName = manager.getName();
        
     List<RoomEntity> occupiedRooms = roomSessionLocal.occupiedRoomList();
        
     //remove the checkout rooms from occupiedRooms
     //        occupiedRooms.removeAll(checkoutRooms);
        
     int staffTime = 0;
     List<RoomEntity> staffRooms = new ArrayList<>();
     for (StaffEntity housekeeper: todayWorkRoster.getStaffs()) {
     if (housekeeper == todayWorkRoster.getStaffs().get(0)) continue;
     staffTime = 0;
     staffRooms.clear();
     for (RoomEntity occupiedRoom: occupiedRooms) {
     switch(occupiedRoom.getRoomType().getName()) {
     case "Superior":
     staffTime += 30;
     break;
     case "Deluxe":
     staffTime += 35;
     break;
     case "Junior Suite":
     staffTime += 45;
     break;
     case "Executive Suite":
     staffTime += 60;
     break;
     case "President Suite":
     break;
     default:
     }
     //6hrs - 15mins leeway
     if (staffTime < 345) {
     staffRooms.add(occupiedRoom);
     } else {
     break;
     }
     }
     occupiedRooms.removeAll(staffRooms);
     housekeepingRecordSessionLocal.createEntity(new HousekeepingRecordEntity(housekeeper, staffRooms, managerName, "Morning" ));
     }
        
        
     }
     
}
