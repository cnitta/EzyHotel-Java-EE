/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class MaintenanceRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;
    private String status;
    private String description;
    @Temporal(TemporalType.DATE)     
    private Date expectedFixDate;
    
    @ManyToOne
    private RoomEntity room;
    
    @ManyToOne
    private HousekeepingStaffEntity housekeepingStaff;
    
    @OneToOne
    private HousekeepingMaintenanceOrderEntity housekeepingMaintenanceOrder;

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpectedFixDate() {
        return expectedFixDate;
    }

    public void setExpectedFixDate(Date expectedFixDate) {
        this.expectedFixDate = expectedFixDate;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public HousekeepingStaffEntity getHousekeepingStaff() {
        return housekeepingStaff;
    }

    public void setHousekeepingStaff(HousekeepingStaffEntity housekeepingStaff) {
        this.housekeepingStaff = housekeepingStaff;
    }

    public HousekeepingMaintenanceOrderEntity getHousekeepingMaintenanceOrder() {
        return housekeepingMaintenanceOrder;
    }

    public void setHousekeepingMaintenanceOrder(HousekeepingMaintenanceOrderEntity housekeepingMaintenanceOrder) {
        this.housekeepingMaintenanceOrder = housekeepingMaintenanceOrder;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maintenanceId != null ? maintenanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the maintenanceId fields are not set
        if (!(object instanceof MaintenanceRecordEntity)) {
            return false;
        }
        MaintenanceRecordEntity other = (MaintenanceRecordEntity) object;
        if ((this.maintenanceId == null && other.maintenanceId != null) || (this.maintenanceId != null && !this.maintenanceId.equals(other.maintenanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.MaintenanceRecordEntity[ id=" + maintenanceId + " ]";
    }
    
}
