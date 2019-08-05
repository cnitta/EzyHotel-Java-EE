/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomTypeEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Local
public interface RoomTypeSessionLocal {
    
    public RoomTypeEntity createEntity(RoomTypeEntity entity);
    public RoomTypeEntity retrieveEntityById(Long id);
    public List<RoomTypeEntity> retrieveAllEntities();
    public List<RoomTypeEntity> retrieveAllRoomTypesByHotelId(Long hId);
    public void deleteEntity(Long id);
    public void updateEntity(RoomTypeEntity entity);
    public RoomTypeEntity retrieveEntity(String attribute, String value);
    public List<RoomTypeEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
