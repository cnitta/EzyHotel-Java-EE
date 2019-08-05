/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomEntity;
import util.exception.StaffNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface FindAssociatedItemsSessionLocal {
    public List<RoomEntity> findHotelRoomsByStaffId(Long staffId) throws StaffNotFoundException;
}
