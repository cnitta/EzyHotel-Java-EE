/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import util.enumeration.MaintenanceOrderStateEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class DeviceMaintenanceOrderEntity extends MaintenanceOrderEntity implements Serializable {
    
    @OneToMany
    private List<DeviceEntity> devices;

    public DeviceMaintenanceOrderEntity() {
    }

    public DeviceMaintenanceOrderEntity(List<DeviceEntity> devices, Date startDate, Date endDate, MaintenanceOrderStateEnum maintenanceOrderStateEnum, ExternalMaintenanceCompanyEntity externalMaintenanceCompany) {
        super(startDate, endDate, maintenanceOrderStateEnum, externalMaintenanceCompany);
        this.devices = devices;
    }
        
    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
    }

}
