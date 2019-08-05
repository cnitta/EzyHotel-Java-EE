/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.datamodel.ws;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import util.entity.RoomTypeEntity;

/**
 *
 * @author berni
 */
@XmlRootElement
@XmlType(name = "RetrieveAllRoomTypesRsp", propOrder = {
    "roomTypes"
})
public class RetrieveAllRoomTypesRsp {
    private static final long serialVersionUID = 1L;
    private List<RoomTypeEntity> roomTypes;

    public RetrieveAllRoomTypesRsp(List<RoomTypeEntity> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public RetrieveAllRoomTypesRsp() {
    }

    public List<RoomTypeEntity> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomTypeEntity> roomTypes) {
        this.roomTypes = roomTypes;
    }
}
