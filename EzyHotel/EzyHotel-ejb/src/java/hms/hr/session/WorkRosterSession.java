/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;
import util.enumeration.RosterTypeEnum;
import util.exception.CustomNotFoundException;
import util.exception.WorkRosterException;

/**
 *
 * @author USER
 */
@Stateless
@Local(WorkRosterSessionLocal.class)
public class WorkRosterSession implements WorkRosterSessionLocal{
    @EJB
    private StaffSessionLocal staffSessionLocal;
    
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
     private final Logger log = Logger
        .getLogger(WorkRosterSession.class.getName());
    
    
    @Override
    public WorkRosterEntity createEntity(WorkRosterEntity entity) {
        entity.setCreateDateTime(new Date());
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public WorkRosterEntity retrieveEntityById(Long id)throws CustomNotFoundException {
        WorkRosterEntity entity = em.find(WorkRosterEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new CustomNotFoundException("WorkRosterEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<WorkRosterEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM WorkRosterEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id)throws CustomNotFoundException,WorkRosterException {
        WorkRosterEntity roster = retrieveEntityById(id);
        List<StaffEntity> staffs = roster.getStaffs(); 
        for(int i = 0 ; i < staffs.size(); i++){
            removeStaffFromRoster(roster.getWorkRosterId(),staffs.get(i));
        }
        roster.setStaffs(null);
        em.remove(roster);
    }

    @Override
    public void updateEntity(WorkRosterEntity entity) {
        em.merge(entity);
    }

    @Override
    public WorkRosterEntity retrieveEntity(String attribute, String value)throws CustomNotFoundException {
        String queryInput = "SELECT c FROM WorkRosterEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (WorkRosterEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomNotFoundException("Entity not found!");
        }
    }

    @Override
    public List<WorkRosterEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB)throws CustomNotFoundException {
        String queryInput = "SELECT c FROM WorkRosterEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomNotFoundException("Entity not found!");
        }
    }
    @Override
    public void addStaffToRoster(Long wid, StaffEntity staff) throws CustomNotFoundException,WorkRosterException{
        WorkRosterEntity roster = retrieveEntityById(wid); 
       
           roster.getStaffs().add(staff); 
       em.merge(roster);
    }
    @Override
    public void removeStaffFromRoster(Long wid, StaffEntity staff) throws CustomNotFoundException,WorkRosterException{
        log.log(Level.INFO,"STAFFFFFFFFF: " + new Date().toString());
        WorkRosterEntity roster = retrieveEntityById(wid); 
       if(roster.getStaffs().contains(staff)){
           roster.getStaffs().remove(staff); 
       }else{
           throw new WorkRosterException("staff is not present in roster"); 
       }  
    }

    @Override
    public WorkRosterEntity retrieveTodayMorningRosters() {
        
        Date today = new Date();
        
        //get 10April Date!!
        Date date = new Date(2019, 4, 10);
        RosterTypeEnum morning = RosterTypeEnum.SHIFT1;
        
        //get todays morning roster
        //set as 10 April for demonstration
        Query query = em.createQuery("SELECT c FROM WorkRosterEntity c WHERE c.startDateTime =:inValue AND c.rosterStatus = :value");
        query.setParameter("inValue", java.sql.Date.valueOf( "2019-04-10" ));
        query.setParameter("value", morning);
        return (WorkRosterEntity) query.getSingleResult();
    }
    
    @Override
    public WorkRosterEntity retrieveTodayEveningRosters() {
        
        Date today = new Date();
        
        //get 10April Date!!
        Date date = new Date(2019, 4, 10);
        RosterTypeEnum evening = RosterTypeEnum.SHIFT2;
        
        //get todays morning roster
        //set as 10 April for demonstration
        Query query = em.createQuery("SELECT c FROM WorkRosterEntity c WHERE c.startDateTime =:inValue AND c.rosterStatus = :value");
        query.setParameter("inValue", java.sql.Date.valueOf( "2019-04-10" ));
        query.setParameter("value", evening);
        return (WorkRosterEntity) query.getSingleResult();
    }
    @Override
    public List<StaffEntity> retrieveStaffFromRosterId(Long rid)throws WorkRosterException{
       try{ 
        WorkRosterEntity roster = retrieveEntityById(rid);
        List<StaffEntity> staffs = roster.getStaffs(); 
        return staffs;
       }catch(CustomNotFoundException e){
           throw new WorkRosterException(e.getMessage()); 
       }
    }
    @Override
    public List<WorkRosterEntity>retrieveWorkRosterBystaffId(Long sId)throws WorkRosterException{
        try{
            StaffEntity staff = staffSessionLocal.retrieveEntityById(sId);
            Query q = em.createQuery("Select w from WorkRosterEntity w WHERE :staff MEMBER OF w.staffs"); 
            q.setParameter("staff", staff);
            return q.getResultList(); 
        }catch(CustomNotFoundException e){
            throw new WorkRosterException(e.getMessage());
        }
    }   
    
}
