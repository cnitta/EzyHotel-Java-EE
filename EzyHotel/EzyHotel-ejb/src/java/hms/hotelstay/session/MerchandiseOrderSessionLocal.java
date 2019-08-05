/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.MerchandiseOrderEntity;
import util.entity.RoomEntity;
import util.exception.MerchandiseOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MerchandiseOrderSessionLocal {

    public MerchandiseOrderEntity createMerchandiseOrder(MerchandiseOrderEntity merchandise);
    
    public MerchandiseOrderEntity retrieveMerchandiseOrderById(Long id) throws MerchandiseOrderNotFoundException;
    
    public List<MerchandiseOrderEntity> retrieveMerchandiseOrderByMerchandiseOrderAttributes (MerchandiseOrderEntity merchandise);    
    
    public List<MerchandiseOrderEntity> retrieveMerchandiseOrderByRoomAttributes(RoomEntity room);
    
    public List<MerchandiseOrderEntity> retrieveAllMerchandiseOrders();

    public void updateMerchandiseOrder(MerchandiseOrderEntity merchandise);
    
    public void deleteMerchandiseOrder(Long id) throws MerchandiseOrderNotFoundException;  
}
