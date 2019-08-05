/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.InventoryEntity;

/**
 *
 * @author bryantan
 */
@Local
public interface InventorySessionLocal {
    
    public InventoryEntity createEntity(InventoryEntity entity);
    
    public void updateEntity(InventoryEntity entity);
    
    public List<InventoryEntity> retrieveAllEntities();
    
    public void deleteEntity(Long id);
    
    public InventoryEntity retrieveEntityById(Long id);
}
