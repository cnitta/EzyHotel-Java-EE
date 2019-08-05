/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.HousekeepingForcastEntity;
import util.entity.RoomBookingEntity;

/**
 *
 * @author bryantan
 */
@Local
public interface ForcastSessionLocal {
    
    public RoomBookingEntity createTestRoomBooking(RoomBookingEntity entity);
    
    public HousekeepingForcastEntity createEntity(HousekeepingForcastEntity entity);
    
    public List<HousekeepingForcastEntity> retrieveAllEntities();
    
    public HousekeepingForcastEntity retrieveEntityById(Long id);
    
}
