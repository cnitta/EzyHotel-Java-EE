/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.RoomEntity;
import util.entity.StaffEntity;
import util.exception.StaffNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class FindAssociatedItemsSession implements FindAssociatedItemsSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
    
    @Override
    public List<RoomEntity> findHotelRoomsByStaffId(Long staffId) throws StaffNotFoundException {
        //find staff 
        StaffEntity staff = em.find(StaffEntity.class, staffId);
        
        if(staff == null)
        {
            throw new StaffNotFoundException("staff not found");
        }        
        
        //find assoicated hotels
        Query query = null;
        List<RoomEntity> rooms;
                
        query = em.createQuery("SELECT r FROM RoomTypeEntity rt, RoomEntity r WHERE rt.hotel.hotelId = :inHotelId AND r MEMBER OF rt.rooms");
        query.setParameter("inHotelId", staff.getHotel().getHotelId());
        
        rooms = query.getResultList();
        
        if(rooms.isEmpty())
        {
            return new ArrayList<RoomEntity>();
        }
        
        for (RoomEntity room : rooms) {
                System.out.println("room " + room.getRoomId());
        }
                
        return rooms;
        
        //find rooms of room type assoicated to the hotels
        
    }



}
