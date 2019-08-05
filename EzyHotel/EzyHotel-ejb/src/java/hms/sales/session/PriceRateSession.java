/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.text.DecimalFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.PriceRateEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class PriceRateSession implements PriceRateSessionLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public PriceRateEntity createEntity(PriceRateEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public PriceRateEntity retrieveEntityById(Long id) {
        PriceRateEntity entity = em.find(PriceRateEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("PriceRateEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<PriceRateEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM PriceRateEntity c");
        return query.getResultList();
    }
    
    @Override
    public List<PriceRateEntity> retrieveAllEntitiesByRoomTypeId(Long roomTypeId) {
        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c.roomType.roomTypeId =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", roomTypeId);
        return query.getResultList();
    }
    
    @Override
    public List<PriceRateEntity> retrieveAllEntitiesByFacilityId(Long facilityId) {
        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c.facility.facilityId =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", facilityId);
        return query.getResultList();
    }
    
    @Override
    public List<PriceRateEntity> retrieveAllEntitiesByFacilityTypeId(Long facilityTypeId) {
        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c.facility.facilityId =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", facilityTypeId);
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        PriceRateEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(PriceRateEntity entity) {
        retrieveEntityById(entity.getPriceRateId());
        em.merge(entity);
    }

    @Override
    public PriceRateEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (PriceRateEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<PriceRateEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    @Override
    public double dynamicPrice(double normalPrice, double rackPrice, int roomOccupancy){
        DecimalFormat df = new DecimalFormat(".##");
        double projectedRoomOccupancy = roomOccupancy - 0.1;
        double dymanicPrice;
        if(roomOccupancy > 0){
            //round double to 2 decimal places
            dymanicPrice = Math.round(Math.pow((roomOccupancy/projectedRoomOccupancy), 2) * normalPrice * 100.0) / 100.0;
            if(dymanicPrice > rackPrice){
                return rackPrice;
            }
            else
                return dymanicPrice;
        }
        else
            return -1;
    }
}
