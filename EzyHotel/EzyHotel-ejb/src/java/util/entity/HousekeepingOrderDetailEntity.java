/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class HousekeepingOrderDetailEntity extends OrderDetailEntity implements Serializable {
    
    @ManyToOne
    private HousekeepingPurchaseOrderEntity housekeepingPurchaseOrder;
    
    @OneToOne
    private HousekeepingOrderItemEntity housekeepingOrderItem;

    public HousekeepingPurchaseOrderEntity getHousekeepingPurchaseOrder() {
        return housekeepingPurchaseOrder;
    }

    public void setHousekeepingPurchaseOrder(HousekeepingPurchaseOrderEntity housekeepingPurchaseOrder) {
        this.housekeepingPurchaseOrder = housekeepingPurchaseOrder;
    }

    public HousekeepingOrderItemEntity getHousekeepingOrderItem() {
        return housekeepingOrderItem;
    }

    public void setHousekeepingOrderItem(HousekeepingOrderItemEntity housekeepingOrderItem) {
        this.housekeepingOrderItem = housekeepingOrderItem;
    }
}
