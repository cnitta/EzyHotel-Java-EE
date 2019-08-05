/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.util.Date;
import java.util.List;
import util.entity.LeaveEntity;
import util.entity.StaffEntity;
import util.exception.CustomNotFoundException;
import util.exception.LeaveException;



public interface LeaveSessionLocal {

    public LeaveEntity createEntity(LeaveEntity entity)throws LeaveException ;

    public LeaveEntity retrieveEntityById(Long id)throws CustomNotFoundException;

    public List<LeaveEntity> retrieveAllEntities();

    public void deleteEntity(Long id)throws CustomNotFoundException;

    public void updateEntity(LeaveEntity entity);

    public LeaveEntity retrieveEntity(String attribute, String value);

    public List<LeaveEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public List<LeaveEntity> viewUnapprovedLeave(Long staffId);

    public List<LeaveEntity> viewAllLeaveByStaffId(Long sId);

    public StaffEntity retrieveStaffEntityfromLeave(LeaveEntity leave)throws CustomNotFoundException;

    public boolean checkOverlappedLeaves(LeaveEntity entity, StaffEntity staff)throws CustomNotFoundException;

    public boolean validateDates(Date startDate, Date endDate)throws LeaveException;

    public boolean checkExceedAnnualLeave(LeaveEntity leave, StaffEntity staff);

    public StaffEntity minusLeaveQuota(LeaveEntity leave, StaffEntity staff);

    public StaffEntity addLeaveQuota(LeaveEntity leave, StaffEntity staff);

    
}
