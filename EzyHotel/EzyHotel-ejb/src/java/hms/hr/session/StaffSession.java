/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import hms.common.EmailManager;
import hms.commoninfra.session.AccessCodeSessionLocal;
import hms.commoninfra.session.AccountSession;
import hms.commoninfra.session.AccountSessionLocal;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import util.exception.StaffException;
import util.entity.StaffEntity;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.GenericEntity;
import util.entity.AccessCodeEntity;
import util.entity.HotelEntity;
import util.entity.LeaveEntity;
import util.enumeration.LeaveCategoryEnum;
import util.enumeration.LeaveStatusEnum;

import util.enumeration.StaffStatusEnum;
import util.exception.CustomNotFoundException;
import util.exception.LeaveException;

/**
 *
 * @author berni
 */
@Stateless
@Local(StaffSessionLocal.class)
public class StaffSession implements StaffSessionLocal {

    @EJB(name = "AccessCodeSessionLocal")
    private AccessCodeSessionLocal accessCodeSessionLocal;
    @EJB
    private AccountSessionLocal accountSessionLocal;
    @EJB
    private LeaveSessionLocal LeaveSessionLocal;

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    private final EmailManager emailManager = new EmailManager();

    /**
     *
     * @param entity
     * @return
     * @throws util.exception.StaffException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    @Override
    public StaffEntity createEntity(StaffEntity entity) throws StaffException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("bbb");
        Query q = em.createQuery("select s from StaffEntity s where s.username =:username");
        q.setParameter("username", entity.getUsername());

        if (q.getResultList().isEmpty()) {
            //TODO (Chinsong): Note the changes, included the method to generate random password
            String pass = accountSessionLocal.staffOnboardPwdGen(entity);
            entity.setPassword(pass);
            em.persist(entity);
            em.flush();
            em.refresh(entity);

            //Sending of Staff Onboarding Email to new staff
            //TODO (Chinsong): Please create a test staff account with any email provider to test if works.
            CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("Sending Staff Onboarding email asynchronously");
                    accountSessionLocal.sendNotificationEmail(new GenericEntity(entity, StaffEntity.class), "ONBOARDING", null, null, "");
                } catch (Exception e) {
                    Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, null, e);
                }
            });

            return entity;
        } else {
            throw new StaffException("Username has been used");
        }
    }

    @Override
    public StaffEntity retrieveEntityById(Long id) throws CustomNotFoundException {
        StaffEntity entity = em.find(StaffEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new CustomNotFoundException("StaffEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<StaffEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM StaffEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) throws CustomNotFoundException, StaffException {
        StaffEntity entity = retrieveEntityById(id);
        List<LeaveEntity> leaves = entity.getLeaves();
        for (LeaveEntity leave : leaves) {
            LeaveSessionLocal.deleteEntity(leave.getLeaveId());
        }
        System.out.println("after remove leave");
        
         AccessCodeEntity code = null;
         try {
         code = accessCodeSessionLocal.retrieveAccessCodeByStaffId(id);
         } catch (NoResultException|javax.ws.rs.NotFoundException|javax.ejb.EJBTransactionRolledbackException|javax.ejb.TransactionRolledbackLocalException e) {
         System.out.println("No Access Code");
         }
         if (code != null) {
         code.setStaff(null);
         }
        entity.setLeaves(null);
        entity.setHotel(null);
        em.remove(entity);

    }

    @Override
    public void updateEntity(StaffEntity entity) {
        System.out.println("update");
        em.merge(entity);
    }

    @Override
    public StaffEntity retrieveEntity(String attribute, String value) throws CustomNotFoundException {
        String queryInput = "SELECT c FROM StaffEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (StaffEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomNotFoundException("Entity not found!");
        }
    }

    @Override
    public List<StaffEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) throws CustomNotFoundException {
        String queryInput = "SELECT c FROM StaffEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomNotFoundException("Entity not found!");
        }
    }

    @Override
    public void deactivateStaff(Long id) throws StaffException {
        try {
            StaffEntity entity = retrieveEntityById(id);
            if (entity.getStaffStatus().equals(StaffStatusEnum.ACTIVE)) {
                entity.setStaffStatus(StaffStatusEnum.INACTIVE);
            } else {
                throw new StaffException("Staff ID " + id + " has already been deactivated");
            }
        } catch (CustomNotFoundException e) {
            throw new StaffException(e.getMessage());
        }
    }

    @Override
    public void activateStaff(Long id) throws StaffException {
        try {
            StaffEntity entity = retrieveEntityById(id);
            if (entity.getStaffStatus().equals(StaffStatusEnum.INACTIVE)) {
                entity.setStaffStatus(StaffStatusEnum.ACTIVE);
            } else {
                throw new StaffException("Staff ID" + id + " has already been activated");
            }
        } catch (CustomNotFoundException e) {
            throw new StaffException(e.getMessage());
        }
    }

    @Override
    public void addHotel(Long sId, HotelEntity h) throws CustomNotFoundException, StaffException {
        StaffEntity entity = retrieveEntityById(sId);
        if (entity.getHotel() == null) {
            entity.setHotel(h);
        } else {
            throw new StaffException("Staff already belongs to a hotel");
        }
    }

    @Override
    public void removeHotel(Long sId) throws CustomNotFoundException, StaffException {
        StaffEntity entity = retrieveEntityById(sId);
        if (entity.getHotel() != null) {
            entity.setHotel(null);
        } else {
            throw new StaffException("Staff does not belong to any hotel");
        }
    }

    @Override
    public void editHotel(Long sId, HotelEntity h) throws CustomNotFoundException {
        StaffEntity entity = retrieveEntityById(sId);
        Query q = em.createQuery("Select h from HotelEntity h where h.hotelId =:id ");
        q.setParameter("id", h.getHotelId());
        if (q.getResultList().isEmpty()) {
            throw new CustomNotFoundException("Hotel Not Found");
        } else {
            entity.setHotel(h);
        }
    }

    @Override
    public List<StaffEntity> retrieveActiveStaff() {
        Query q = em.createQuery("Select s from StaffEntity s where s.staffStatus =:status");
        q.setParameter("status", StaffStatusEnum.ACTIVE);
        return q.getResultList();
    }
    /*
     public List<StaffEntity> retrieveEntitybyField(Long staffId, String ic, String name, String jobtitle, 
     String department, String jobType, String position, String staffStatus){
     Query q = em.createQuery("Select s from StaffEntity s where s.staffId =:id "
     + " AND s.nric =:ic"
     + " AND s.name =:name"
     + " AND s.jobTitle =:title "
     + " AND s.department =:dpt "
     + " AND s.jobType =:type "
     + " AND s.jobPosition =:pos "
     + " AND s.staffStatus =:status"); 
     q.setParameter("id", staffId);
     q.setParameter("ic", ic);
     q.setParameter("name", name);
     q.setParameter("title", jobtitle);
     q.setParameter("dpt", department);
     q.setParameter("type", jobType);
     q.setParameter("pos",  position); 
     q.setParameter("status", staffStatus); 
     return q.getResultList(); 
     }
     */

    @Override
    public void addLeave(Long sId, LeaveEntity l) throws CustomNotFoundException, LeaveException {
        //still need to check existing leave
        StaffEntity entity = retrieveEntityById(sId);
        LeaveEntity newLeave = LeaveSessionLocal.createEntity(l);
        entity.getLeaves().add(l);
    }

    @Override
    public void removeLeave(Long sId, LeaveEntity l) throws CustomNotFoundException {
        StaffEntity entity = retrieveEntityById(sId);
        if (entity.getLeaves().contains(l)) {
            entity.getLeaves().remove(l);
            LeaveSessionLocal.deleteEntity(l.getLeaveId());
            System.out.println("leave size" + entity.getLeaves().size());
        } else {
            throw new CustomNotFoundException("leave not found");
        }

    }
    //If he is available, return true, else return false 
    @Override
    public boolean checkAvailability(Long sId, Date date)throws CustomNotFoundException{
        StaffEntity entity = retrieveEntityById(sId);
        List<LeaveEntity> leaves = entity.getLeaves(); 
        for(LeaveEntity leave: leaves){
            if((leave.getLeaveStatus().equals(LeaveStatusEnum.APPROVED))){
                if(leave.getStartDateTime()!=null&&leave.getEndDateTime()!=null){
                    Date startDate = leave.getStartDateTime(); 
                    Date endDate = leave.getEndDateTime(); 
                    if(date.equals(startDate)||date.equals(endDate)||(leave.getStartDateTime().before(date)&&leave.getEndDateTime().after(date))){
                        return false; 
                    }
                }
            }
        }
        return true; 
    }

}
