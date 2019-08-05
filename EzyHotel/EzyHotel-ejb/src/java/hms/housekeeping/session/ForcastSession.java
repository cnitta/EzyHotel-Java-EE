/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.HousekeepingForcastEntity;
import util.entity.RoomBookingEntity;

/**
 *
 * @author bryantan
 */
@Stateless
public class ForcastSession implements ForcastSessionLocal {

    @PersistenceContext 
    private EntityManager em;

    @Override
    public RoomBookingEntity createTestRoomBooking(RoomBookingEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public HousekeepingForcastEntity createEntity(HousekeepingForcastEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public List<HousekeepingForcastEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM HousekeepingForcastEntity c");
        return query.getResultList();
    }
    
    @Override
    public HousekeepingForcastEntity retrieveEntityById(Long id) {
        HousekeepingForcastEntity entity = em.find(HousekeepingForcastEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("HousekeepingSOPEntity ID " + id + " does not exist!");
        }
    }
    
    
}
