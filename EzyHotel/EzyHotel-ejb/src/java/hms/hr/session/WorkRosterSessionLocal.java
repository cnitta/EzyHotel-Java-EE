/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.util.List;
import util.entity.StaffEntity;
import util.entity.WorkRosterEntity;
import util.exception.CustomNotFoundException;
import util.exception.WorkRosterException;


public interface WorkRosterSessionLocal {

    public WorkRosterEntity createEntity(WorkRosterEntity entity);

    public WorkRosterEntity retrieveEntityById(Long id)throws CustomNotFoundException;

    public List<WorkRosterEntity> retrieveAllEntities();

    public void deleteEntity(Long id)throws CustomNotFoundException, WorkRosterException ;

    public void updateEntity(WorkRosterEntity entity);

    public WorkRosterEntity retrieveEntity(String attribute, String value)throws CustomNotFoundException ;

    public List<WorkRosterEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB)throws CustomNotFoundException ;

    public void addStaffToRoster(Long wid, StaffEntity staff) throws CustomNotFoundException,WorkRosterException;

    public void removeStaffFromRoster(Long wid, StaffEntity staff) throws CustomNotFoundException, WorkRosterException;
    
    public WorkRosterEntity retrieveTodayMorningRosters();
    
    public WorkRosterEntity retrieveTodayEveningRosters();

    public List<StaffEntity> retrieveStaffFromRosterId(Long id)throws WorkRosterException;

    public List<WorkRosterEntity> retrieveWorkRosterBystaffId(Long sId) throws WorkRosterException;
    
}
