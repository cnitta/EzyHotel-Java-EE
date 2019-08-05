/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.HousekeepingSOPEntity;

/**
 *
 * @author bryantan
 */
@Local
public interface HousekeepingSOPSessionLocal {
    
    public HousekeepingSOPEntity createEntity(HousekeepingSOPEntity entity);
    
    public HousekeepingSOPEntity createEntityRT(HousekeepingSOPEntity entity, String roomTypeName);
    
    public HousekeepingSOPEntity retrieveEntityById(Long id);
    
    public List<HousekeepingSOPEntity> retrieveAllEntities();
    
    public void deleteEntity(Long id);
    
    public void updateEntity(HousekeepingSOPEntity entity);
    
    public HousekeepingSOPEntity retrieveEntity(String attribute, String value);
    
    public List<HousekeepingSOPEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
