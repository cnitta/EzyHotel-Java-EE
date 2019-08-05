/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomAmenityEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Local
public interface RoomAmenitySessionLocal {
    public RoomAmenityEntity createEntity(RoomAmenityEntity entity);
    public RoomAmenityEntity retrieveEntityById(Long id);
    public List<RoomAmenityEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(RoomAmenityEntity entity);
    public RoomAmenityEntity retrieveEntity(String attribute, String value);
    public List<RoomAmenityEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB); 
}
