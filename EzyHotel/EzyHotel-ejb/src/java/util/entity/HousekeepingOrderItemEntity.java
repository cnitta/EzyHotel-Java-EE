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
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class HousekeepingOrderItemEntity extends OrderItemEntity implements Serializable {
    private String category;

    @ManyToOne
    private InventoryEntity inventory;
    
    @OneToOne(mappedBy = "housekeepingOrderItem")
    private HousekeepingOrderDetailEntity housekeepingOrderDetail;

    public HousekeepingOrderItemEntity(String category, InventoryEntity inventory, HousekeepingOrderDetailEntity housekeepingOrderDetail, String name, String description, double unitPrice, List<PictureEntity> picture) {
        super(name, description, unitPrice, picture);
        this.category = category;
        this.inventory = inventory;
        this.housekeepingOrderDetail = housekeepingOrderDetail;
    }

    public HousekeepingOrderItemEntity() {
    }

    public HousekeepingOrderItemEntity(String name, String description, double unitPrice, List<PictureEntity> picture) {
        super(name, description, unitPrice, picture);
    }
 

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public HousekeepingOrderDetailEntity getHousekeepingOrderDetail() {
        return housekeepingOrderDetail;
    }

    public void setHousekeepingOrderDetail(HousekeepingOrderDetailEntity housekeepingOrderDetail) {
        this.housekeepingOrderDetail = housekeepingOrderDetail;
    }
    
}
