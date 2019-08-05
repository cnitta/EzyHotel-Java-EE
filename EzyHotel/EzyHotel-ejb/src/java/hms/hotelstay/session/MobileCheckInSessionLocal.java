/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import javax.ejb.Local;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MobileCheckInSessionLocal {
        
    public RoomBookingEntity mobileCheckIn(RoomBookingEntity roomBooking) throws Exception;
    
    public RoomEntity retrieveRoomByRoomUnitNumber(int roomUnitNumber);
}
