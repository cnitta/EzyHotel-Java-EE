/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.HousekeepingRequestEntity;
import util.entity.RoomServiceOrderEntity;

/**
 *
 * @author bryantan
 */
@Local
public interface HousekeepingRequestSessionLocal {
    
    public HousekeepingRequestEntity createEntity(HousekeepingRequestEntity entity);
    
    public List<HousekeepingRequestEntity> retrieveAllEntities();
    
    public List<HousekeepingRequestEntity> retrieveAllEntitiesByRoomUnitNumber(String roomUnitNumber);
    
    public HousekeepingRequestEntity retrieveEntityById(Long id);
    
    public void deleteEntity(Long id);
    
    public void updateEntity(HousekeepingRequestEntity entity);
    
    public List<HousekeepingRequestEntity> retrieveStaffOngoingRequests(Long sid);
    
    public HousekeepingRequestEntity createRequest(HousekeepingRequestEntity entity, String roomNumber, String staffName, String requestType, String message);
    
    public HousekeepingRequestEntity createRequest(HousekeepingRequestEntity entity, String roomNumber, String requestType, String message);
}
