/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.datamodel.ws;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import util.entity.RoomBookingEntity;

/**
 *
 * @author berni
 */
@XmlRootElement
@XmlType(name = "retrieveAllRoomBookingsRsp", propOrder = {
    "roomBookings"
})
public class RetrieveAllRoomBookingsRsp {
    private static final long serialVersionUID = 1L;
    private List<RoomBookingEntity> roomBookings;

    public RetrieveAllRoomBookingsRsp(List<RoomBookingEntity> roomBookings) {
        this.roomBookings = roomBookings;
    }

    public RetrieveAllRoomBookingsRsp() {
    }

    public List<RoomBookingEntity> getRoomBookings() {
        return roomBookings;
    }

    public void setRoomBookings(List<RoomBookingEntity> roomBookings) {
        this.roomBookings = roomBookings;
    }
    
}

