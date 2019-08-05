/*
 * DeviceEntityo change this license header, choose License Headers in Project Properties.
 * DeviceEntityo change this template file, choose DeviceEntityools | DeviceEntityemplates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.DeviceEntity;
import util.entity.RoomEntity;
import util.exception.DeviceNotFoundException;
import util.exception.RoomNotFoundException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface DeviceSessionLocal {
    public DeviceEntity createDevice(DeviceEntity device);
    
    public DeviceEntity retrieveDeviceById(Long id) throws DeviceNotFoundException;
    
    public List<DeviceEntity> retrieveDeviceByDeviceAttributes (DeviceEntity device);
    
    public List<DeviceEntity> retrieveDeviceByRoomAttributes(RoomEntity room);
    
    public List<RoomEntity> retrieveRoomsWithDevice();
    
    public List<DeviceEntity> retrieveAllDevices();

    public void updateDevice(DeviceEntity device);
        
    public Boolean assignDeviceWithRoom(Long dId, Long rId) throws DeviceNotFoundException, RoomNotFoundException;
    
    public Boolean unassignDeviceWithRoom(Long dId) throws DeviceNotFoundException;
    
    public void deleteDevice(Long id) throws DeviceNotFoundException;  
    
    public void deleteDeviceBySerialNumber(String serialNumber) throws DeviceNotFoundException;
}
