/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import hms.frontdesk.session.CustomerSessionLocal;
import hms.frontdesk.session.RoomBookingSessionLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.enumeration.RoomBookingStatusEnum;
import util.enumeration.RoomStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MobileCheckInSession implements MobileCheckInSessionLocal {
    
    @EJB(name = "CustomerSessionLocal")
    private CustomerSessionLocal customerSessionLocal;
    
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
    @EJB(name = "RoomBookingSessionLocal")
    private RoomBookingSessionLocal roomBookingSessionLocal;
    
        
    @Override
    public RoomBookingEntity mobileCheckIn(RoomBookingEntity roomBooking) throws Exception{
        
        Boolean isDebugging = true;
        
        //retrieve all existing rooms that is without occupants
        List<RoomEntity> avaliableRooms = roomBookingSessionLocal.retrieveRoomNumber(roomBooking);
        
        int numRooms = avaliableRooms.size();
        
        int randRoomIndex = (int)(Math.random() * numRooms);               
        
        String allocatedRoomUnitNumber = avaliableRooms.get(randRoomIndex).getRoomUnitNumber().toString();       
        
        //call checkIn                   
        roomBooking.setRoomNumber(allocatedRoomUnitNumber);
        roomBooking.setStatus(RoomBookingStatusEnum.CHECKED_IN);
        
        //retrieve the Room to set the status
        try{
            RoomEntity chosenRoom = retrieveRoomByRoomUnitNumber(avaliableRooms.get(randRoomIndex).getRoomUnitNumber());
            chosenRoom.setStatus(RoomStatusEnum.OCCUPIED);

            roomBookingSessionLocal.updateEntity(roomBooking);  

            //TODO IMPORTANT**: Remember to diassociate the relationship between the RoomType to remove cyclic redundancy  
            //RoomTypeEntity typeRoom = typeRoom.getRooms()

            System.out.println("*** Customer Check-In Ended ***\n");
            return roomBooking;
        } 
        catch (Exception ex) 
        {
            throw new Exception(ex.getMessage());
        }        
              
    }
    
    @Override
    public RoomEntity retrieveRoomByRoomUnitNumber(int roomUnitNumber)
    {
        Query q = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomUnitNumber = :inRoomUnitNumber");

        q.setParameter("inRoomUnitNumber", roomUnitNumber);

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve Room ID By number Ended ***\n");
            return (RoomEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve Room ID by number Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }
    }
    
   



}
