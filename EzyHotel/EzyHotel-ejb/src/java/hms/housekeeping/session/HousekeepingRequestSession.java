/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.HousekeepingRequestEntity;
import util.entity.RoomEntity;
import util.entity.StaffEntity;
import util.enumeration.DepartmentEnum;

/**
 *
 * @author bryantan
 */
@Stateless
public class HousekeepingRequestSession implements HousekeepingRequestSessionLocal {

    @PersistenceContext 
    private EntityManager em;
    
    @Override
    public HousekeepingRequestEntity createEntity(HousekeepingRequestEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }
    
    @Override
    public List<HousekeepingRequestEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM HousekeepingRequestEntity c");
        
        if(query.getResultList().isEmpty())
        {
            return new ArrayList<>();
        }
        
        return query.getResultList();
    }
    
    @Override
    public HousekeepingRequestEntity retrieveEntityById(Long id) {
        HousekeepingRequestEntity entity = em.find(HousekeepingRequestEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("HousekeepingRequestEntity ID " + id + " does not exist!");
        }
    }
    
    @Override
    public void deleteEntity(Long id) {
        HousekeepingRequestEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }
    
    @Override
    public void updateEntity(HousekeepingRequestEntity entity) {
        em.merge(entity);
    }

    @Override
    public List<HousekeepingRequestEntity> retrieveStaffOngoingRequests(Long sid) {
        StaffEntity staff = em.find(StaffEntity.class, sid);
        Query query = em.createQuery("SELECT c FROM HousekeepingRequestEntity c WHERE c.staff = :value1 AND c.status = :value2");
        query.setParameter("value1", staff);
        query.setParameter("value2", "In Progress");
        System.out.println("REQUESTTTTTT");
        System.out.println(query.getResultList().toString());
        return query.getResultList();
    }
    
    @Override
    public HousekeepingRequestEntity createRequest(HousekeepingRequestEntity entity, String roomNumber, String staffName, String requestType, String message) {
        Query query1 = em.createQuery("SELECT c FROM RoomEntity c WHERE c.roomUnitNumber = :value1");
        query1.setParameter("value1", Integer.parseInt(roomNumber));
        RoomEntity room = (RoomEntity) query1.getSingleResult();
        
        Query query2 = em.createQuery("SELECT s FROM StaffEntity s WHERE s.name = :value2");
        query2.setParameter("value2", staffName);
        StaffEntity staff = (StaffEntity) query2.getSingleResult();
        
        entity.setRoom(room);
        entity.setStaff(staff);
        entity.setRequestType(requestType);
        entity.setMessage(message);
        
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public HousekeepingRequestEntity createRequest(HousekeepingRequestEntity entity, String roomNumber, String requestType, String message) {
        Query query1 = em.createQuery("SELECT c FROM RoomEntity c WHERE c.roomUnitNumber = :value1");
        query1.setParameter("value1", Integer.parseInt(roomNumber));
        RoomEntity room = (RoomEntity) query1.getSingleResult();
        
        //check what time is it to assign the appropriate housekeeping staff (to do later)
//        LocalTime timeNow = LocalTime.now(ZoneId.systemDefault());
//        LocalTime start = LocalTime.parse( "20:11:13" );
//        LocalTime stop = LocalTime.parse( "14:49:00" ); 
//        if( start.isAfter( stop ) ) {
//    return ! isBetweenStartAndStopStrictlySpeaking ;
//} else {
//    return isBetweenStartAndStopStrictlySpeaking ;
//}     
        DepartmentEnum enum1 = DepartmentEnum.HOUSEKEEPING;
        
        Query query2 = em.createQuery("SELECT s FROM StaffEntity s WHERE s.department = :value2 AND s.name = :value3");
        query2.setParameter("value2", enum1);
        query2.setParameter("value3", "Clayton Hull");
        StaffEntity staff = (StaffEntity) query2.getSingleResult();
        
        entity.setStaff(staff);
        entity.setRoom(room);
        entity.setRequestType(requestType);
        entity.setMessage(message);
        
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }
    
    @Override
    public List<HousekeepingRequestEntity> retrieveAllEntitiesByRoomUnitNumber(String roomNumber)
    {
        Query query = em.createQuery("SELECT request FROM HousekeepingRequestEntity request WHERE request.room.roomUnitNumber = :value1");
        query.setParameter("value1", Integer.parseInt(roomNumber));
        
        List<HousekeepingRequestEntity> results = query.getResultList();
        
        if(results.isEmpty())
        {
            return new ArrayList<>();
        }
        
        return results;
    }
}
