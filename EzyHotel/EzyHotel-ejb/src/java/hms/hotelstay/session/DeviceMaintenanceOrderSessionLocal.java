/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.DeviceMaintenanceOrderEntity;
import util.exception.DeviceMaintenanceOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface DeviceMaintenanceOrderSessionLocal {
    public DeviceMaintenanceOrderEntity createDeviceMaintenanceOrder(DeviceMaintenanceOrderEntity deviceMaintenanceOrder);
    
    public DeviceMaintenanceOrderEntity retrieveDeviceMaintenanceOrderById(Long id) throws DeviceMaintenanceOrderNotFoundException;
    
    public List<DeviceMaintenanceOrderEntity> retrieveDeviceMaintenanceOrderByDeviceMaintenanceOrderAttributes (DeviceMaintenanceOrderEntity deviceMaintenanceOrder);    
    
    public List<DeviceMaintenanceOrderEntity> retrieveAllDeviceMaintenanceOrders();

    public void updateDeviceMaintenanceOrder(DeviceMaintenanceOrderEntity deviceMaintenanceOrder);
    
    public void deleteDeviceMaintenanceOrder(Long id) throws DeviceMaintenanceOrderNotFoundException;     
}
