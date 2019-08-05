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
import util.entity.MerchandisePurchaseOrderEntity;
import util.exception.MerchandisePurchaseOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MerchandisePurchaseOrderSession implements MerchandisePurchaseOrderSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public MerchandisePurchaseOrderEntity createMerchandisePurchaseOrder(MerchandisePurchaseOrderEntity merchandisePurchaseOrder) {
        em.persist(merchandisePurchaseOrder);
        em.flush();
        em.refresh(merchandisePurchaseOrder);
        return merchandisePurchaseOrder;
    }

    @Override
    public MerchandisePurchaseOrderEntity retrieveMerchandisePurchaseOrderById(Long id) throws MerchandisePurchaseOrderNotFoundException {
        MerchandisePurchaseOrderEntity merchandisePurchaseOrder = em.find(MerchandisePurchaseOrderEntity.class, id);
        if (merchandisePurchaseOrder != null) {
            return merchandisePurchaseOrder;
        } else {
            throw new MerchandisePurchaseOrderNotFoundException("MerchandisePurchaseOrder ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<MerchandisePurchaseOrderEntity> retrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes(MerchandisePurchaseOrderEntity merchandisePurchaseOrder) {
     
        System.out.println("retrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes");
        
        Query query = null;
        List<MerchandisePurchaseOrderEntity> merchandisePurchaseOrders;
        
        if(merchandisePurchaseOrder.getOrderDate() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandisePurchaseOrderEntity m WHERE m.orderDate = :inOrderDate");
            query.setParameter("inOrderDate", merchandisePurchaseOrder.getOrderDate());
        }       
        else
        {
            System.out.println("new AL");
            return new ArrayList<>();
        }
        
        merchandisePurchaseOrders = query.getResultList();
        
        System.out.println("size of array " + merchandisePurchaseOrders.size());
        
        if(merchandisePurchaseOrders.size() == 0)
        { 
            return new ArrayList<>();
        }
                
        return merchandisePurchaseOrders;        
    }

    @Override
    public List<MerchandisePurchaseOrderEntity> retrieveAllMerchandisePurchaseOrders() {

        Query query = em.createQuery("SELECT a FROM MerchandisePurchaseOrderEntity a");
        
        List<MerchandisePurchaseOrderEntity> merchandisePurchaseOrders = query.getResultList();

        
        if(merchandisePurchaseOrders.size() == 0)
        {
            return new ArrayList<MerchandisePurchaseOrderEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateMerchandisePurchaseOrder(MerchandisePurchaseOrderEntity merchandisePurchaseOrder) {
        em.merge(merchandisePurchaseOrder);        
    }

    @Override
    public void deleteMerchandisePurchaseOrder(Long id) throws MerchandisePurchaseOrderNotFoundException {
        MerchandisePurchaseOrderEntity merchandisePurchaseOrder = retrieveMerchandisePurchaseOrderById(id);
        em.remove(merchandisePurchaseOrder);        
    }

}
