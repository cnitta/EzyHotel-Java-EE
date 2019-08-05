/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author vincentyeo
 */
@Entity
public class HousekeepingManagerEntity extends HousekeepingStaffEntity implements Serializable {
 
    @OneToMany(mappedBy = "housekeepingManager")
    private List<LaundryRecordEntity> laundryRecords;
    
    
    @OneToMany(mappedBy = "housekeepingManager")
    private List<HousekeepingPurchaseOrderEntity> housekeepingPurchaseOrders;

    public HousekeepingManagerEntity(List<LaundryRecordEntity> laundryRecords, List<HousekeepingPurchaseOrderEntity> housekeepingPurchaseOrders) {
        this.laundryRecords = laundryRecords;
        this.housekeepingPurchaseOrders = housekeepingPurchaseOrders;
    }

    public HousekeepingManagerEntity() {
        laundryRecords = new ArrayList<>();
        housekeepingPurchaseOrders = new ArrayList<>();
    }

    public List<LaundryRecordEntity> getLaundryRecords() {
        return laundryRecords;
    }

    public void setLaundryRecords(List<LaundryRecordEntity> laundryRecords) {
        this.laundryRecords = laundryRecords;
    }

    public List<HousekeepingPurchaseOrderEntity> getPurchaseOrders() {
        return housekeepingPurchaseOrders;
    }

    public void setPurchaseOrders(List<HousekeepingPurchaseOrderEntity> housekeepingPurchaseOrders) {
        this.housekeepingPurchaseOrders = housekeepingPurchaseOrders;
    }
    
    
    
    
}

