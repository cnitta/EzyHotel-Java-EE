/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.enumeration.RoomStatusEnum;
import util.exception.NoResultException;

/**
 *
 * @author Nicholas Nah
 */
@Stateless
public class RoomBookingSession implements RoomBookingSessionLocal {

    @PersistenceContext
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public RoomBookingEntity createBooking(RoomBookingEntity booking) {
        System.out.println("*** Create Room Booking Started ***\n");
        em.persist(booking);
        em.flush();
        em.refresh(booking);
        System.out.println("*** Create Room Booking Ended ***\n");
        return booking;
    }//end createBooking

    @Override
    public RoomBookingEntity retrieveBookingById(Long bId) throws NoResultException {
        System.out.println("*** Retrieve Room Booking by Id Started ***\n");
        RoomBookingEntity booking = em.find(RoomBookingEntity.class, bId);
        System.out.println("Booking retrieved: " + booking.toString());
       
        
        if (booking != null) {
            System.out.println("*** Retrieve Room Booking by Id Ended ***\n");
            return booking;
        } else {
            throw new NoResultException("Booking with id: " + bId + " does not exist!");
        }
    }//end retrieveBookingById

    @Override
    public List<RoomBookingEntity> retrieveAllBookingForStaff() {
        System.out.println("*** Retrieve All Booking Started ***\n");
        Query q = em.createQuery("SELECT b FROM RoomBookingEntity b");
        System.out.println("*** Retrieve All Booking Ended ***\n");
        return q.getResultList();
    }//end retrieveAllBookingForStaff

    @Override
    public void updateEntity(RoomBookingEntity entity) throws NoResultException {
        retrieveBookingById(entity.getBookingId());
        em.merge(entity);
    }//end updateEntity

    @Override
    public List<RoomEntity> retrieveRoomNumber(RoomBookingEntity roomBooking) {
        System.out.println("*** Retrieve Room Number Started ***\n");
        //Ensure that roomStatus is unoccupied
        String roomStatus = "UNOCCUPIED";
        RoomStatusEnum rStatus = RoomStatusEnum.valueOf(roomStatus);
        //query all room entity which has room status unoccupied

        //retrieve all room number base on roomType
        String roomType = roomBooking.getRoomTypeCode();
        if (roomType.equals("SUP")) {
            roomType = "SUR";
        }
        //System.out.println("Room Type by Customer: " + roomType);
        Query q1 = em.createQuery("SELECT r  FROM RoomEntity r   "
                + "WHERE r.roomType.roomTypecode  = :inRoom AND r.status = :inStatus");
        // Query q1 = em.createQuery("SELECT r  FROM RoomEntity r JOIN RoomTypeEntity rt  "
        //         + "WHERE rt.roomTypecode  = :inRoom AND r.status = :inStatus");

        q1.setParameter("inRoom", roomType);
        q1.setParameter("inStatus", rStatus);
        List<RoomEntity> list = new ArrayList<RoomEntity>();

        if (!q1.getResultList().isEmpty()) {
            for (Object o : q1.getResultList()) {
                //if (!list.contains((RoomEntity) o)) 
                list.add((RoomEntity) o);

            }
        }
        System.out.println("*** Retrieve Room Number Ended ***\n");
        return list;
    }//end retrieveRoomNumber

    @Override
    public List<RoomBookingEntity> retrieveTodayCheckIn() {
        System.out.println("*** Retrieve Today Room Booking for Check-In Started ***\n");
        //Gets todays date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date todayDate = new Date();
        //format to string
        String date = dateFormat.format(todayDate);
        System.out.println("Today Date: " + date);

        //create Empty list
        List<RoomBookingEntity> CheckInList = new ArrayList<RoomBookingEntity>();
        //retrieve all booking in RoomBooking database
        List<RoomBookingEntity> retrieveAllBooking = retrieveAllBookingForStaff();
        //loop through the bookings
        for (int i = 0; i < retrieveAllBooking.size(); i++) {
            //retrieve checkinDate
            Date checkinDate = retrieveAllBooking.get(i).getCheckInDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkinDate);
            //System.out.println("RoomBooking Check-In Date: " + currStringDate);    
            //add all room booking where status = MANUAL
            String currentEnum = retrieveAllBooking.get(i).getStatus().toString();
            //System.out.println("Current Room Booking Status in RoomBookingSession " + currentEnum);
            //compare todays date and booking date and status = MANUAL 
            if (date.equals(currStringDate) && currentEnum.equals("MANUAL")) {
                //if equal, means customer are suppose to check-in today, add to list
                CheckInList.add(retrieveAllBooking.get(i));
            }
        }
        System.out.println("*** Retrieve Today Room Booking for Check-In Ended ***\n");
        return CheckInList;
    }//end retrieveTodayCheckIn

    @Override
    public List<RoomBookingEntity> retrieveTodayCheckOut() {
        //Gets todays date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date todayDate = new Date();
        //format to string
        String date = dateFormat.format(todayDate);

        //create Empty list
        List<RoomBookingEntity> CheckOutList = new ArrayList<RoomBookingEntity>();
        //retrieve all booking in RoomBooking database
        List<RoomBookingEntity> retrieveAllBooking = retrieveAllBookingForStaff();
        //loop through the bookings
        for (int i = 0; i < retrieveAllBooking.size(); i++) {
            //retrieve checkOutDate
            Date checkOutDate = retrieveAllBooking.get(i).getCheckOutDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkOutDate);
            //retrieve current booking status
            String currentEnum = retrieveAllBooking.get(i).getStatus().toString();
            //compare todays date and current booking status must be CHECK_IN
            if (date.equals(currStringDate) && currentEnum.equals("CHECKED_IN")) {
                //if equal, means customer are suppose to check-in today, add to list
                CheckOutList.add(retrieveAllBooking.get(i));
            }
        }
        return CheckOutList;
    }//end retrieveTodayCheckOut

    @Override
    public List<RoomBookingEntity> retrieveAllRoomBookingEdit() {
        System.out.println("*** Retrieve Room Booking for Edit at Session Started ***\n");
        //Format of date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //create Empty list
        List<RoomBookingEntity> roomBooking = new ArrayList<RoomBookingEntity>();
        //retrieve all booking in RoomBooking database
        List<RoomBookingEntity> retrieveAllBooking = retrieveAllBookingForStaff();
        //loop through the bookings
        for (int i = 0; i < retrieveAllBooking.size(); i++) {
            //retrieve checkOutDate
            Date checkOutDate = retrieveAllBooking.get(i).getCheckOutDateTime();
            //format to string
            String currStringDate = dateFormat.format(checkOutDate);
            //System.out.println("RoomBooking Check-Out Date: " + currStringDate);
            //retrieve status
            String currentEnum = retrieveAllBooking.get(i).getStatus().toString();
            System.out.println("Current Room Booking Status in retrieveAllRoomBookingEdit " + currentEnum);
            //Add into list only if its status is CHECK IN
            if (currentEnum.equals("CHECKED_IN")) {
                //if equal, means customer are suppose to check-in today, add to list
                roomBooking.add(retrieveAllBooking.get(i));
            }
        }
        System.out.println("*** Retrieve Room Booking for Edit at Session Ended ***\n");
        return roomBooking;
    }//end retrieveAllRoomBookingEdit

}
