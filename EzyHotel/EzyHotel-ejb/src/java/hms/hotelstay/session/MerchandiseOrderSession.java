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
import util.entity.MerchandiseOrderEntity;
import util.entity.RoomEntity;
import util.exception.MerchandiseOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MerchandiseOrderSession implements MerchandiseOrderSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public MerchandiseOrderEntity createMerchandiseOrder(MerchandiseOrderEntity merchandiseOrder) {
        em.persist(merchandiseOrder);
        em.flush();
        em.refresh(merchandiseOrder);
        return merchandiseOrder;        
    }

    @Override
    public MerchandiseOrderEntity retrieveMerchandiseOrderById(Long id) throws MerchandiseOrderNotFoundException {
        MerchandiseOrderEntity merchandiseOrder = em.find(MerchandiseOrderEntity.class, id);
        if (merchandiseOrder != null) {
            return merchandiseOrder;
        } else {
            throw new MerchandiseOrderNotFoundException("MerchandiseOrder ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<MerchandiseOrderEntity> retrieveMerchandiseOrderByMerchandiseOrderAttributes(MerchandiseOrderEntity merchandiseOrder) {
        
        Query query = null;
        List<MerchandiseOrderEntity> merchandiseOrders;
        
        if(merchandiseOrder.getStatus() != null)
        {
            query = em.createQuery("SELECT mo FROM MerchandiseOrderEntity mo WHERE mo.status = :inStatus");
            query.setParameter("inStatus", merchandiseOrder.getStatus());
        }
        else if(merchandiseOrder.getQuantityRedeemed() != null)
        {
            query = em.createQuery("SELECT mo FROM MerchandiseOrderEntity mo WHERE mo.quantityRedeemed = :inQuantity");
            query.setParameter("inQuantity", merchandiseOrder.getQuantityRedeemed());
        }        
        else
        {
            return new ArrayList<>();
        }
        
        merchandiseOrders = query.getResultList();
        
        if(merchandiseOrders.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return merchandiseOrders;        
    }
    
@Override
    public List<MerchandiseOrderEntity> retrieveMerchandiseOrderByRoomAttributes(RoomEntity room){
        
        Query query = em.createQuery("SELECT mo FROM MerchandiseOrderEntity mo WHERE mo.room.roomUnitNumber =:inRoomNumber OR mo.room.roomId =:inRoomId");
        query.setParameter("inRoomNumber", room.getRoomUnitNumber());
        query.setParameter("inRoomId", room.getRoomId());
        
        List<MerchandiseOrderEntity> merchandiseOrder = query.getResultList();
        
        if(merchandiseOrder.size() == 0)
        { 
            return new ArrayList<>();
        }
                
        return merchandiseOrder;
    }    

    @Override
    public List<MerchandiseOrderEntity> retrieveAllMerchandiseOrders() {
        System.out.println("***MerchandiseOrderSession retrieveAllMerchandiseOrder Started***");
        Query query = em.createQuery("SELECT mo FROM MerchandiseOrderEntity mo");
        
        List<MerchandiseOrderEntity> merchandiseOrders = query.getResultList();
        System.out.println("***MerchandiseOrderSession merchandiseOrders.size() " + merchandiseOrders.size() + "***");
        
        if(merchandiseOrders.size() == 0)
        {
            return new ArrayList<MerchandiseOrderEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateMerchandiseOrder(MerchandiseOrderEntity merchandiseOrder) {
        em.merge(merchandiseOrder);
    }

    @Override
    public void deleteMerchandiseOrder(Long id) throws MerchandiseOrderNotFoundException {
        MerchandiseOrderEntity merchandiseOrder = retrieveMerchandiseOrderById(id);
        em.remove(merchandiseOrder);
    }

    

}
