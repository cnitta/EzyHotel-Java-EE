/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.HousekeepingRecordEntity;

/**
 *
 * @author bryantan
 */
@Local
public interface HousekeepingRecordSessionLocal {
    
    public HousekeepingRecordEntity createEntity(HousekeepingRecordEntity entity);
    
    public List<HousekeepingRecordEntity> retrieveAllEntities();
    
    public HousekeepingRecordEntity retrieveEntityById(Long id);
    
    public void deleteEntity(Long id);
    
    public void updateEntity(HousekeepingRecordEntity entity);
    
    public HousekeepingRecordEntity retrieveEntity(String attribute, String value);
    
    public List<HousekeepingRecordEntity> retrieveStaffRecord(Long sid);
    
    public List<HousekeepingRecordEntity> retrieveMorningRecords();
    
    public List<HousekeepingRecordEntity> retrieveEveningRecords();
    
    public Integer calculateCompletion(HousekeepingRecordEntity entity);
    
    public List<String> getRecentActivity(HousekeepingRecordEntity entity);
    
    public HousekeepingRecordEntity findRecordWithRoomNumber(Integer roomNumber);
    
}
