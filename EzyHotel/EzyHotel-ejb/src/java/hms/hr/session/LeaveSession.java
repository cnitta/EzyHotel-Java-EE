/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.util.Date;
import util.entity.LeaveEntity;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.StaffEntity;
import util.enumeration.LeaveStatusEnum;
import util.exception.CustomNotFoundException;
import util.exception.LeaveException;

/**
 *
 * @author berni
 */
@Stateless
@Local(LeaveSessionLocal.class)
public class LeaveSession implements LeaveSessionLocal {

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public LeaveEntity createEntity(LeaveEntity entity) throws LeaveException {
        boolean checkDates = validateDates(entity.getStartDateTime(), entity.getEndDateTime());
        System.out.println("enter check date");
        if (checkDates == true) {
            throw new LeaveException("Invalid date input");
        } else {
            em.persist(entity);
            em.flush();
            em.refresh(entity);
            return entity;
        }
    }

    @Override
    public LeaveEntity retrieveEntityById(Long id) throws CustomNotFoundException {
        LeaveEntity entity = em.find(LeaveEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new CustomNotFoundException("LeaveEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<LeaveEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM LeaveEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) throws CustomNotFoundException {
        LeaveEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(LeaveEntity entity) {
        em.merge(entity);
    }

    @Override
    public LeaveEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM LeaveEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (LeaveEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<LeaveEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM LeaveEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
    public List<LeaveEntity> viewUnapprovedLeave(Long staffId) {
        Query q; 
        if(staffId==null){
            q = em.createQuery("select l from LeaveEntity l where l.leaveStatus =:status");
        }else{
            q = em.createQuery("select l from LeaveEntity l, StaffEntity s where s.staffId<>:id AND l MEMBER OF s.leaves AND l.leaveStatus =:status"); 
            q.setParameter("id", staffId); 
        }
        q.setParameter("status", LeaveStatusEnum.PENDING);
        return q.getResultList();
    }

    @Override
    public List<LeaveEntity> viewAllLeaveByStaffId(Long sId) {
        Query q = em.createQuery("select l from LeaveEntity l, StaffEntity s where s.staffId =:id AND l MEMBER OF s.leaves");
        q.setParameter("id", sId);
        return q.getResultList();
    }

    @Override
    public StaffEntity retrieveStaffEntityfromLeave(LeaveEntity leave) throws CustomNotFoundException {
        Query q = em.createQuery("select s from StaffEntity s where :leave MEMBER OF s.leaves");
        q.setParameter("leave", leave);
        List<StaffEntity> list = q.getResultList();
        if (list.isEmpty()) {
            System.out.println("Empty list" + list.isEmpty());
            return null;
            //throw new CustomNotFoundException("No staff found");
        } else {
            return list.get(0);
        }
    }

    @Override
    public boolean checkOverlappedLeaves(LeaveEntity leave, StaffEntity staff) throws CustomNotFoundException {
        if (leave == null) {
            throw new CustomNotFoundException("Leave does not exist");
        } else {
            if (staff.getLeaves() == null) {
                throw new CustomNotFoundException("Error in staff");
            } else if (staff.getLeaves().isEmpty()) {
                return false;
            } else {
                List<LeaveEntity> leaves = staff.getLeaves();
                for (LeaveEntity l : leaves) {

                    //leave.start == staff.start && leave.end == staff.end
                    boolean checkEqual = leave.getStartDateTime().equals(l.getStartDateTime()) && leave.getEndDateTime().equals(l.getEndDateTime());

                    //leave.start == staff.end||leave.end == staff.start 
                    boolean checkStartEnd = leave.getStartDateTime().equals(l.getEndDateTime()) || leave.getEndDateTime().equals(l.getStartDateTime());

                    //leave.start ==staff.start && (leave.end!=staff.end)
                    boolean checkIntercept1 = leave.getStartDateTime().equals(l.getStartDateTime()) && (!leave.getEndDateTime().equals(l.getEndDateTime()));

                    //leave.end == staff.end && (leave.start != staff.start)
                    boolean checkIntercept2 = leave.getEndDateTime().equals(l.getEndDateTime()) && (!leave.getStartDateTime().equals(l.getStartDateTime()));

                    //leave.start<staff.start&&leave.end<staff.end
                    boolean checkOverlap1 = leave.getStartDateTime().before(l.getStartDateTime()) && (leave.getEndDateTime().after(l.getEndDateTime()));

//leave.start>staff.start&&leave.end>staff.end
                    boolean checkOverlap2 = leave.getStartDateTime().after(l.getStartDateTime()) && (leave.getEndDateTime().before(l.getEndDateTime()));

                    //get true value 
                    boolean check = checkEqual || checkStartEnd || checkIntercept1 || checkIntercept2 || checkOverlap1 || checkOverlap2;

                    if (check) {
                        return check;
                    }
                }
                return false;
            }
        }
    }

    @Override
    public boolean validateDates(Date startDate, Date endDate) throws LeaveException {
        Boolean check = false;
        Date curr = new Date();
        if (startDate == null || endDate == null) {
            throw new LeaveException("No missing dates");
        } else {
            if (startDate.before(curr) && endDate.before(curr)&&endDate.before(startDate)) {
                check = true;
            }
        }
        return check;
    }

    @Override
    public boolean checkExceedAnnualLeave(LeaveEntity leave, StaffEntity staff) {
        int leaveQuota = staff.getLeaveQuota();
        Long diff = leave.getEndDateTime().getTime() - leave.getStartDateTime().getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        if (leaveQuota < days) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public StaffEntity minusLeaveQuota(LeaveEntity leave, StaffEntity staff) {
        int leaveQuota = staff.getLeaveQuota();
        Long diff = leave.getEndDateTime().getTime() - leave.getStartDateTime().getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        int newQuotaValue = leaveQuota-days; 
        /*if(leave.getEndDateTime().equals(leave.getStartDateTime())){
           newQuotaValue -=1; 
       }*/
        staff.setLeaveQuota(newQuotaValue);
        return staff; 
    }
    @Override
    public StaffEntity addLeaveQuota(LeaveEntity leave, StaffEntity staff) {
        int leaveQuota = staff.getLeaveQuota();
        Long diff = leave.getEndDateTime().getTime() - leave.getStartDateTime().getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        int newQuotaValue = leaveQuota+days; 
       /* if(leave.getEndDateTime().equals(leave.getStartDateTime())){
           newQuotaValue +=1; 
       }*/
        staff.setLeaveQuota(newQuotaValue);
        return staff; 
    }
}
