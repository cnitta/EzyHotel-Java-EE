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
import util.entity.HousekeepingRecordEntity;
import util.entity.RoomEntity;
import util.entity.StaffEntity;


/**
 *
 * @author bryantan
 */
@Stateless
public class HousekeepingRecordSession implements HousekeepingRecordSessionLocal {

    @PersistenceContext 
    private EntityManager em;
    
    @Override
    public HousekeepingRecordEntity createEntity(HousekeepingRecordEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }
    
    @Override
    public List<HousekeepingRecordEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM HousekeepingRecordEntity c");
        return query.getResultList();
    }
    
    @Override
    public HousekeepingRecordEntity retrieveEntityById(Long id) {
        HousekeepingRecordEntity entity = em.find(HousekeepingRecordEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("HousekeepingRecordEntity ID " + id + " does not exist!");
        }
    }
    
    @Override
    public void deleteEntity(Long id) {
        HousekeepingRecordEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(HousekeepingRecordEntity entity) {
        em.merge(entity);
    }
    
    @Override
    public HousekeepingRecordEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM HousekeepingRecordEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (HousekeepingRecordEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<HousekeepingRecordEntity> retrieveStaffRecord(Long sid) {
        StaffEntity entity = em.find(StaffEntity.class, sid);
        Query query = em.createQuery("SELECT c FROM HousekeepingRecordEntity c WHERE c.housekeepingStaff.staffId = :inValue");
        query.setParameter("inValue", sid);
        
        return query.getResultList();
    }

    @Override
    public List<HousekeepingRecordEntity> retrieveMorningRecords() {
        Query query = em.createQuery("SELECT c FROM HousekeepingRecordEntity c WHERE c.shift = :inValue");
        query.setParameter("inValue", "Morning");
        return query.getResultList();
    }

    @Override
    public List<HousekeepingRecordEntity> retrieveEveningRecords() {
        Query query = em.createQuery("SELECT c FROM HousekeepingRecordEntity c WHERE c.shift = :inValue");
        query.setParameter("inValue", "Evening");
        return query.getResultList();
    }
    
    @Override
    public Integer calculateCompletion(HousekeepingRecordEntity entity) {
        double totalCount = 0;
        double roomCount = 0;
        double facilityCount = 0;
        if (entity.getRooms() != null) {
            for (RoomEntity room : entity.getRooms()) {
                totalCount++;
                if (room.getCleaningStatus() == "Clean") {
                    roomCount++;
                }
            }
        }
        
        if (entity.getFacility() != null) {
            totalCount++;
            if (entity.getFacility().getCleaningStatus() == "Clean") {
                facilityCount++;
            }
        }
        
        double division = (roomCount + facilityCount) / totalCount;
        double completionPercentage = division*100;
        return (int) Math.round(completionPercentage);
    }
    
    @Override
    public List<String> getRecentActivity(HousekeepingRecordEntity entity) {
        return entity.getRecentActivity();
    }
    
    @Override
    public HousekeepingRecordEntity findRecordWithRoomNumber(Integer roomNumber) {
        Query q1 = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomUnitNumber = :value");
        q1.setParameter("value", roomNumber);
        RoomEntity room = (RoomEntity) q1.getSingleResult();
                
        Query query = em.createQuery("SELECT h FROM HousekeepingRecordEntity h WHERE :room MEMBER OF h.rooms");
        query.setParameter("room", room);
        return (HousekeepingRecordEntity) query.getSingleResult();
    }

}
