/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author vincentyeo
 */
@Entity
public class HousekeepingPurchaseOrderEntity extends PurchaseOrderEntity implements Serializable {

    private String status;

    @OneToMany(mappedBy = "housekeepingPurchaseOrder")
    private List<HousekeepingOrderDetailEntity> housekeepingOrderDetails;
    
    @ManyToOne
    private HousekeepingManagerEntity housekeepingManager;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HousekeepingOrderDetailEntity> getHousekeepingOrderDetails() {
        return housekeepingOrderDetails;
    }

    public void setHousekeepingOrderDetails(List<HousekeepingOrderDetailEntity> housekeepingOrderDetails) {
        this.housekeepingOrderDetails = housekeepingOrderDetails;
    }

    public HousekeepingManagerEntity getHousekeepingManager() {
        return housekeepingManager;
    }

    public void setHousekeepingManager(HousekeepingManagerEntity housekeepingManager) {
        this.housekeepingManager = housekeepingManager;
    }

}
