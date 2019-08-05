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
import util.entity.MerchandiseEntity;
import util.exception.MerchandiseNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MerchandiseSession implements MerchandiseSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public MerchandiseEntity createMerchandise(MerchandiseEntity merchandise) {
        em.persist(merchandise);
        em.flush();
        em.refresh(merchandise);
        return merchandise;
    }

    @Override
    public MerchandiseEntity retrieveMerchandiseById(Long id) throws MerchandiseNotFoundException {
        MerchandiseEntity merchandise = em.find(MerchandiseEntity.class, id);
        if (merchandise != null) {
            return merchandise;
        } else {
            throw new MerchandiseNotFoundException("Merchandise ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<MerchandiseEntity> retrieveMerchandiseByMerchandiseAttributes(MerchandiseEntity merchandise) {
     
        Query query = null;
        List<MerchandiseEntity> merchandises;
        
        if(merchandise.getName() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.name LIKE :inName");
            query.setParameter("inName", "%" + merchandise.getName() + "%");
        }
        else if(merchandise.getDescription() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.description LIKE :inDescription");  
            query.setParameter("inDescription", "%" + merchandise.getDescription() + "%");
        }  
        else if(merchandise.getCostPoints()!= null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.costPoints = :inPoints");  
            query.setParameter("inPoints", merchandise.getCostPoints());
        }  
        else if(merchandise.getMaxCostPriceLimit()!= null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.maxCostPriceLimit = :inPoints");  
            query.setParameter("inPoints", merchandise.getMaxCostPriceLimit());
        } 
        else if(merchandise.getQuantityOnHand() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.quantityOnHand = :inQty");  
            query.setParameter("inQty", merchandise.getQuantityOnHand());
        }  
        else if(merchandise.getMerchandiseStatus() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.merchandiseStatus = :inStatus");  
            query.setParameter("inStatus", merchandise.getMerchandiseStatus());
        }    
        else if(merchandise.getPoTriggerLevel() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.poTriggerLevel = :inPOTrigger");  
            query.setParameter("inPOTrigger", merchandise.getPoTriggerLevel());
        }    
        else if(merchandise.getIsTriggerOn() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseEntity m WHERE m.isTriggerOn = :inIsTriggerOn");  
            query.setParameter("inIsTriggerOn", merchandise.getIsTriggerOn());
        }         
        else
        {
            return new ArrayList<>();
        }
        
        merchandises = query.getResultList();
        
        if(merchandises.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return merchandises;        
    }

    @Override
    public List<MerchandiseEntity> retrieveAllMerchandises() {

        Query query = em.createQuery("SELECT a FROM MerchandiseEntity a");
        
        List<MerchandiseEntity> merchandises = query.getResultList();

        
        if(merchandises.size() == 0)
        {
            return new ArrayList<MerchandiseEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateMerchandise(MerchandiseEntity merchandise) {
        em.merge(merchandise);        
    }

    @Override
    public void deleteMerchandise(Long id) throws MerchandiseNotFoundException {
        MerchandiseEntity merchandise = retrieveMerchandiseById(id);
        em.remove(merchandise);        
    }



}
