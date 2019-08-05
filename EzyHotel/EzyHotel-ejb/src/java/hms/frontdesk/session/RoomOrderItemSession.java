/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.RoomOrderEntity;
import util.entity.RoomOrderItemEntity;

/**
 *
 * @author Nicholas
 */
@Stateless
public class RoomOrderItemSession implements RoomOrderItemSessionLocal {
    @EJB
    private RoomOrderSessionLocal roomOrderSessionLocal;

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public RoomOrderItemEntity createEntity(RoomOrderItemEntity orderItem) {
        System.out.println("*** Create Room Order Item Started ***\n");

        em.persist(orderItem);
        em.flush();
        em.refresh(orderItem);
        //calculate amount for this item
        calculateTotalAmount(orderItem.getRoomOrder().getRoomOrderId());
        System.out.println("*** Create Room Order Item Ended ***\n");

        return orderItem;
    }//end createEntity

    @Override
    public RoomOrderItemEntity retrieveRoomOrderItemById(Long rId) {
        System.out.println("*** Retrieve Room Order Item by Id Started ***\n");

        RoomOrderItemEntity orderItem = em.find(RoomOrderItemEntity.class, rId);
        System.out.println("*** Retrieve Room Order Item by Id Ended ***\n");

        return orderItem;

    }//end retrieveRoomOrderItemById

    @Override
    public List<RoomOrderItemEntity> retrieveAllEntites() {
        System.out.println("*** Retrieve all Room Order Item Started ***\n");

        Query q = em.createQuery("SELECT o FROM RoomOrderItemEntity  o");

        System.out.println("*** Retrieve All Room Order Item Ended ***\n");
        return q.getResultList();
    }//end retrieveAllEntity

    @Override
    public void deleteEntity(Long rId) {
        System.out.println("*** Delete Room Order Item  Started ***\n");
        RoomOrderItemEntity dOrderItem = retrieveRoomOrderItemById(rId);
        em.remove(dOrderItem);
        System.out.println("*** Delete Room Order Item  Ended ***\n");
    }//end deleteEntity

    @Override
    public void updateEntity(RoomOrderItemEntity entity) {
        retrieveRoomOrderItemById(entity.getRoomOrderItemId());
        em.merge(entity);
    }//end updateEntity
    
     @Override
    public RoomOrderEntity retrieveById(Long rId) {
        System.out.println("*** Retrieve Room Order by Id Started ***\n");

        RoomOrderEntity roomOrder = em.find(RoomOrderEntity.class, rId);
        System.out.println("*** Retrieve Room Order by Id Ended ***\n");

        return roomOrder;

    }//end retrieveRoomOrderById


    @Override
    public void calculateTotalAmount(Long roomOrderId) {
        BigDecimal bd = new BigDecimal("0.00");
        // create MathContext object with 6 precision
        MathContext mc = new MathContext(6);
        //retrieve all order item in RoomOrderItem database
        List<RoomOrderItemEntity> retrieveAllItems = retrieveAllEntites();
        System.out.println("Size of retrieve order items: " + retrieveAllItems.size());
        //loop through the RoomOrderItem
        for (int i = 0; i < retrieveAllItems.size(); i++) {
            //compare if room order id is equal
            Long currOrderItem = retrieveAllItems.get(i).getRoomOrder().getRoomOrderId();
            System.out.println("Current Order Item id: " + currOrderItem.toString());
            if (roomOrderId.equals(currOrderItem)) {
                //if equal, means this item belong to the orderItem, add to subTotal
                bd = bd.add(retrieveAllItems.get(i).getSubAmount(), mc);
            }
        }
        //set totalAmount to RoomOrderEntity
        RoomOrderEntity order = retrieveById(roomOrderId);
        order.setTotalAmount(bd);
        roomOrderSessionLocal.updateEntity(order);    
    }//end calculateTotalAmount

}
