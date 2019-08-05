/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomBookingEntity;
import util.entity.RoomOrderEntity;

/**
 *
 * @author Nicholas
 */
@Local
public interface RoomOrderSessionLocal {

    public RoomOrderEntity createEntity(RoomOrderEntity roomOrder);

    public RoomBookingEntity retrieveRoomNumberHK(String roomNumber);

    public RoomOrderEntity retrieveRoomOrderById(Long rId);

    public List<RoomOrderEntity> retrieveAllEntites();

    public void deleteEntity(Long rId);

    public void updateEntity(RoomOrderEntity entity);

    public List<RoomOrderEntity> retrieveUnpaidRoomOrders();

    public RoomOrderEntity makePaymentSucc(Long roomOrderId);
    
    public RoomOrderEntity getRoomOrderByRoomNumber(String roomNumber);
}
