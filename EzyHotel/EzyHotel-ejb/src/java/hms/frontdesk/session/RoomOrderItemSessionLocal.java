/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomOrderEntity;
import util.entity.RoomOrderItemEntity;

/**
 *
 * @author Nicholas
 */
@Local
public interface RoomOrderItemSessionLocal {

    public RoomOrderItemEntity createEntity(RoomOrderItemEntity orderItem);

    public RoomOrderItemEntity retrieveRoomOrderItemById(Long rId);

    public List<RoomOrderItemEntity> retrieveAllEntites();

    public void deleteEntity(Long rId);

    public void updateEntity(RoomOrderItemEntity entity);

    public RoomOrderEntity retrieveById(Long rId);

    public void calculateTotalAmount(Long orderItemId);
}
