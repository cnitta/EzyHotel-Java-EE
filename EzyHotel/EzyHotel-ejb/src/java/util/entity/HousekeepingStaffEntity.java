/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import util.enumeration.DepartmentEnum;
import util.enumeration.JobPositionEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
@DiscriminatorValue("HousekeepingStaffEntity")
public class HousekeepingStaffEntity extends StaffEntity implements Serializable {
   
    private double rating;
    private int requestAccepted;
    
    
    @OneToMany(mappedBy = "housekeepingStaff")
    private List<LostFoundRecordEntity> lostFoundRecords;
    
    @OneToMany(mappedBy = "housekeepingStaff")
    private List<MaintenanceRecordEntity> maintenanceRecords;  
    



    public HousekeepingStaffEntity(double rating, int requestAccepted, List<LostFoundRecordEntity> lostFoundRecords) {
        this.rating = rating;
        this.requestAccepted = requestAccepted;
        this.lostFoundRecords = lostFoundRecords;
    }
    
    public HousekeepingStaffEntity() {}


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(int requestAccepted) {
        this.requestAccepted = requestAccepted;
    }


    public List<LostFoundRecordEntity> getLostFoundRecords() {
        return lostFoundRecords;
    }

    public void setLostFoundRecords(List<LostFoundRecordEntity> lostFoundRecords) {
        this.lostFoundRecords = lostFoundRecords;
    }
    
    public List<MaintenanceRecordEntity> getMaintenanceRecords() {
        return maintenanceRecords;
    }

    public void setMaintenanceRecords(List<MaintenanceRecordEntity> maintenanceRecords) {
        this.maintenanceRecords = maintenanceRecords;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.rating) ^ (Double.doubleToLongBits(this.rating) >>> 32));
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
        final HousekeepingStaffEntity other = (HousekeepingStaffEntity) obj;
        if (Double.doubleToLongBits(this.rating) != Double.doubleToLongBits(other.rating)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HousekeepingStaffEntity{" + "rating=" + rating + '}';
    }

    
    
}
