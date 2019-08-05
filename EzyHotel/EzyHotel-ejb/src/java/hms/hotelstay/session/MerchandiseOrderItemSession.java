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
import util.entity.MerchandiseOrderItemEntity;
import util.exception.MerchandiseOrderItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MerchandiseOrderItemSession implements MerchandiseOrderItemSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public MerchandiseOrderItemEntity createMerchandiseOrderItem(MerchandiseOrderItemEntity merchandiseOrderItem) {
        em.persist(merchandiseOrderItem);
        em.flush();
        em.refresh(merchandiseOrderItem);
        return merchandiseOrderItem;
    }

    @Override
    public MerchandiseOrderItemEntity retrieveMerchandiseOrderItemById(Long id) throws MerchandiseOrderItemNotFoundException {
        MerchandiseOrderItemEntity merchandiseOrderItem = em.find(MerchandiseOrderItemEntity.class, id);
        if (merchandiseOrderItem != null) {
            return merchandiseOrderItem;
        } else {
            throw new MerchandiseOrderItemNotFoundException("MerchandiseOrderItem ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<MerchandiseOrderItemEntity> retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes(MerchandiseOrderItemEntity merchandiseOrderItem) {
     
        Query query = null;
        List<MerchandiseOrderItemEntity> merchandiseOrderItems;
        
        System.out.println("Name" + merchandiseOrderItem.getName());
        System.out.println("Description" + merchandiseOrderItem.getDescription());

        
        if(merchandiseOrderItem.getName() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseOrderItemEntity m WHERE m.name LIKE :inName");
            query.setParameter("inName", "%" + merchandiseOrderItem.getName() + "%");
        }
        else if(merchandiseOrderItem.getDescription() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseOrderItemEntity m WHERE m.description LIKE :inDescription");
            query.setParameter("inDescription", "%" + merchandiseOrderItem.getDescription() + "%");
        }         
        else if(merchandiseOrderItem.getUnitPrice() != 0.00)
        {
            query = em.createQuery("SELECT m FROM MerchandiseOrderItemEntity m WHERE m.unitPrice = :inUnitPrice");
            query.setParameter("inUnitPrice", merchandiseOrderItem.getUnitPrice());
        }    
        else
        {
            return new ArrayList<>();
        }
        
        merchandiseOrderItems = query.getResultList();
        
        if(merchandiseOrderItems.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return merchandiseOrderItems;        
    }

//    @Override
//    public List<MerchandiseOrderItemEntity> retrieveMerchandiseOrderItemBySupplierAttributes(SupplierEntity supplier) {
//        Query query = null;
//        List<MerchandiseOrderItemEntity> merchandiseOrderItems;
//        
//        if(supplier.getSupplierId() != null)
//        {
//            query = em.createQuery("SELECT m FROM MerchandiseOrderItemEntity m WHERE m.supplier = :inSupplierId");
//            query.setParameter("inSupplierId", supplier.getSupplierId());
//        }
//        else if(supplier.getCompany() != null)
//        {
//            query = em.createQuery("SELECT m FROM MerchandiseOrderItemEntity m WHERE m.supplier.company = :inCompany");
//            query.setParameter("inCompany", supplier.getCompany());
//        }
//        else
//        {
//            return new ArrayList<>();
//        }
//        
//        merchandiseOrderItems = query.getResultList();
//        
//        if(merchandiseOrderItems.isEmpty())
//        { 
//            return new ArrayList<>();
//        }
//                
//        return merchandiseOrderItems;          
//    }
    
    @Override
    public List<MerchandiseOrderItemEntity> retrieveAllMerchandiseOrderItems() {

        Query query = em.createQuery("SELECT m FROM MerchandiseOrderItemEntity m");
        
        List<MerchandiseOrderItemEntity> merchandiseOrderItems = query.getResultList();
        
        if(merchandiseOrderItems.size() == 0)
        {
            return new ArrayList<>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateMerchandiseOrderItem(MerchandiseOrderItemEntity merchandiseOrderItem) {
        em.merge(merchandiseOrderItem);        
    }

    @Override
    public void deleteMerchandiseOrderItem(Long id) throws MerchandiseOrderItemNotFoundException {
        MerchandiseOrderItemEntity merchandiseOrderItem = retrieveMerchandiseOrderItemById(id);
        em.remove(merchandiseOrderItem);        
    }

}
