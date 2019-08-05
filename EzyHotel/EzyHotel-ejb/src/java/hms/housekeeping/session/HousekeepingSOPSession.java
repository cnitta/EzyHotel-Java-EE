/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.HousekeepingSOPEntity;
import util.entity.RoomTypeEntity;

/**
 *
 * @author bryantan
 */
@Stateless
public class HousekeepingSOPSession implements HousekeepingSOPSessionLocal {

    @PersistenceContext 
    private EntityManager em;

    @Override
    public HousekeepingSOPEntity createEntity(HousekeepingSOPEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public HousekeepingSOPEntity createEntityRT(HousekeepingSOPEntity entity, String roomTypeName) {
        
        String queryInput = "SELECT c FROM RoomTypeEntity c WHERE c.name =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", roomTypeName);
        
        //must make sure that no association is with this roomType alrdy
        try {
            RoomTypeEntity roomType = (RoomTypeEntity) query.getSingleResult();
            entity.setRoomType(roomType);
            em.persist(entity);
            em.flush();
            em.refresh(entity);
            return entity;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
        
        
    }

    @Override
    public HousekeepingSOPEntity retrieveEntityById(Long id) {
        HousekeepingSOPEntity entity = em.find(HousekeepingSOPEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("HousekeepingSOPEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<HousekeepingSOPEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM HousekeepingSOPEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        HousekeepingSOPEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(HousekeepingSOPEntity entity) {
        em.merge(entity);
    }

    @Override
    public HousekeepingSOPEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM HousekeepingSOPEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (HousekeepingSOPEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<HousekeepingSOPEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM HousekeepingSOPEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
}
