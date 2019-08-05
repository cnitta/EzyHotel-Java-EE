/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomAssetEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Local
public interface RoomAssetSessionLocal {
    public RoomAssetEntity createEntity(RoomAssetEntity entity);
    public RoomAssetEntity retrieveEntityById(Long id);
    public List<RoomAssetEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(RoomAssetEntity entity);
    public RoomAssetEntity retrieveEntity(String attribute, String value);
    public List<RoomAssetEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
