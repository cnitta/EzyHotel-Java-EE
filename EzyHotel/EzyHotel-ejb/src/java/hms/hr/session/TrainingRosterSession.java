/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.StaffEntity;
import util.entity.TrainingRosterEntity;
import util.exception.CustomNotFoundException;
import util.exception.TrainingRosterException;

/**
 *
 * @author USER
 */
@Stateless
public class TrainingRosterSession implements TrainingRosterSessionLocal{
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public TrainingRosterEntity createEntity(TrainingRosterEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public TrainingRosterEntity retrieveEntityById(Long id) {
        TrainingRosterEntity entity = em.find(TrainingRosterEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("TrainingRosterEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<TrainingRosterEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM TrainingRosterEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        TrainingRosterEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(TrainingRosterEntity entity) {
        em.merge(entity);
    }

    @Override
    public TrainingRosterEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM TrainingRosterEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (TrainingRosterEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<TrainingRosterEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM TrainingRosterEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    @Override
    public void addStaffToRoster(Long tid, StaffEntity staff) throws CustomNotFoundException,TrainingRosterException{
        TrainingRosterEntity roster = retrieveEntityById(tid); 
       if(!roster.getStaffs().contains(staff)){
           roster.getStaffs().add(staff); 
       }else{
           throw new TrainingRosterException("staff has already been added into roster"); 
       }  
    }
    @Override
    public void removeStaffFromRoster(Long tid, StaffEntity staff) throws CustomNotFoundException,TrainingRosterException{
        TrainingRosterEntity roster = retrieveEntityById(tid);  
       if(roster.getStaffs().contains(staff)){
           roster.getStaffs().remove(staff); 
       }else{
           throw new TrainingRosterException("staff is not present in roster"); 
       }  
    }
}
