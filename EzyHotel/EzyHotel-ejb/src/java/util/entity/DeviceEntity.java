/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.DeviceCategoryEnum;
import util.enumeration.DeviceStateEnum;
import util.enumeration.DeviceStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class DeviceEntity implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    private String serialNumber; 
    @Enumerated(EnumType.STRING)
    private DeviceCategoryEnum deviceCategory;
    private String deviceModel;
    @Enumerated(EnumType.STRING)
    private DeviceStatusEnum deviceStatus;
    private String manufacturerName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMaintenanceDate;
    @Enumerated(EnumType.STRING)
    private DeviceStateEnum deviceState;
        
    @ManyToOne
    private RoomEntity Room;

    public DeviceEntity() {}

    public DeviceEntity(String serialNumber, DeviceCategoryEnum deviceCategory, String deviceModel, String manufacturerName) {
        this.serialNumber = serialNumber;
        this.deviceCategory = deviceCategory;
        this.deviceModel = deviceModel;
        this.deviceStatus = DeviceStatusEnum.WORKING;
        this.manufacturerName = manufacturerName;
        this.lastMaintenanceDate = new Date();
        this.deviceState = DeviceStateEnum.NOT_DEPLOYED;       
    }
    
    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DeviceCategoryEnum getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(DeviceCategoryEnum deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public DeviceStatusEnum getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatusEnum deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Date getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public DeviceStateEnum getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceStateEnum deviceState) {
        this.deviceState = deviceState;
    }

    public RoomEntity getRoom() {
        return Room;
    }

    public void setRoom(RoomEntity Room) {
        this.Room = Room;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.deviceId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceEntity other = (DeviceEntity) obj;
        if (!Objects.equals(this.deviceId, other.deviceId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeviceEntity{" + "deviceId=" + deviceId + '}';
    }
}

