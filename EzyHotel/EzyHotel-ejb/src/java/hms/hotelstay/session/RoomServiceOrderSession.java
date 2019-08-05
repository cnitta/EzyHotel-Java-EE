/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.RoomEntity;
import util.entity.RoomServiceOrderEntity;
import util.exception.RoomServiceOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class RoomServiceOrderSession implements RoomServiceOrderSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
    @Override
    public RoomServiceOrderEntity createRoomServiceOrder(RoomServiceOrderEntity roomServiceOrder) {
        em.persist(roomServiceOrder);
        em.flush();
        em.refresh(roomServiceOrder);
        return roomServiceOrder;
        
    }

    @Override
    public RoomServiceOrderEntity retrieveRoomServiceOrderById(Long id) throws RoomServiceOrderNotFoundException {
        RoomServiceOrderEntity roomServiceOrder = em.find(RoomServiceOrderEntity.class, id);
        if (roomServiceOrder != null) {
            return roomServiceOrder;
        } else {
            throw new RoomServiceOrderNotFoundException("RoomServiceOrder ID " + id + " does not exist!");
        }         
    }

    @Override
    public List<RoomServiceOrderEntity> retrieveRoomServiceOrderByRoomServiceOrderAttributes(RoomServiceOrderEntity roomServiceOrder) {
        Query query = null;
        List<RoomServiceOrderEntity> roomServiceOrders;
        
        if(roomServiceOrder.getTotalPrice() != null)
        {
            query = em.createQuery("SELECT r FROM RoomServiceOrderEntity r WHERE r.totalPrice = :inTotalPrice");  
            query.setParameter("inTotalPrice", roomServiceOrder.getTotalPrice());
        } 
        else if(roomServiceOrder.getOrderStatus() != null)
        {
            query = em.createQuery("SELECT r FROM RoomServiceOrderEntity r WHERE r.orderStatus = :inOrderStatus");  
            query.setParameter("inOrderStatus", roomServiceOrder.getOrderStatus());
        } 
        else if(roomServiceOrder.getComments() != null)
        {
            query = em.createQuery("SELECT r FROM RoomServiceOrderEntity r WHERE r.comments LIKE :inComment");  
            query.setParameter("inComment", roomServiceOrder.getComments());
        }         
        else
        {
            return new ArrayList<>();
        }
        
        roomServiceOrders = query.getResultList();
        
        if(roomServiceOrders.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return roomServiceOrders;          
    }

    @Override
    public List<RoomServiceOrderEntity> retrieveAllRoomServiceOrders() {
        Query query = em.createQuery("SELECT m FROM RoomServiceOrderEntity m");
        
        List<RoomServiceOrderEntity> roomServiceOrders = query.getResultList();
        
        if(roomServiceOrders.size() == 0)
        {
            return new ArrayList<RoomServiceOrderEntity>();
        }
        
        return query.getResultList();          
    }

    @Override
    public List<RoomServiceOrderEntity> retrieveRoomServiceOrderByRoomAttributes(RoomEntity room){       
        
        Query query = em.createQuery("SELECT r FROM RoomServiceOrderEntity r WHERE r.RoomEntity.roomUnitNumber = :inRoomNumber OR r.RoomEntity.roomId =:inRoomId");
        query.setParameter("inRoomNumber", room.getRoomUnitNumber());
        query.setParameter("inRoomId", room.getRoomId());
        
        List<RoomServiceOrderEntity> roomServiceOrders = query.getResultList();
        
        if(roomServiceOrders.size() == 0)
        { 
            return new ArrayList<>();
        }
                
        return roomServiceOrders;
    }    

    @Override
    public void updateRoomServiceOrder(RoomServiceOrderEntity roomServiceOrder) {
        em.merge(roomServiceOrder);           
    }

    @Override
    public void deleteRoomServiceOrder(Long id) throws RoomServiceOrderNotFoundException {
        RoomServiceOrderEntity roomServiceOrder = retrieveRoomServiceOrderById(id);
        em.remove(roomServiceOrder);            
    }
}
