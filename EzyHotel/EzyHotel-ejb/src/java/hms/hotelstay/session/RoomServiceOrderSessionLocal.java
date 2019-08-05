/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomEntity;
import util.entity.RoomServiceOrderEntity;
import util.exception.RoomServiceOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface RoomServiceOrderSessionLocal {
    public RoomServiceOrderEntity createRoomServiceOrder(RoomServiceOrderEntity roomServiceOrderEntity);
    
    public RoomServiceOrderEntity retrieveRoomServiceOrderById(Long id) throws RoomServiceOrderNotFoundException;
    
    public List<RoomServiceOrderEntity> retrieveRoomServiceOrderByRoomServiceOrderAttributes (RoomServiceOrderEntity roomServiceOrderEntity);
    
    public List<RoomServiceOrderEntity> retrieveRoomServiceOrderByRoomAttributes(RoomEntity room);
    
    public List<RoomServiceOrderEntity> retrieveAllRoomServiceOrders();

    public void updateRoomServiceOrder(RoomServiceOrderEntity roomServiceOrderEntity);
    
    public void deleteRoomServiceOrder(Long id) throws RoomServiceOrderNotFoundException;    
}
