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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author berni
 */
@Entity
public class InventoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    
    private int thresholdQuantity;
    private int quantityInStock;
    
    private String name;
    private String description;
    private String category;
    

    @OneToMany(mappedBy = "inventory")
    private List<HousekeepingOrderItemEntity> housekeepingOrderItems;
    

    public InventoryEntity(int thresholdQuantity, int quantityInStock, List<HousekeepingOrderItemEntity> housekeepingOrderItems) {
        this.thresholdQuantity = thresholdQuantity;
        this.quantityInStock = quantityInStock;
        this.housekeepingOrderItems = housekeepingOrderItems;
    }
    
    public InventoryEntity(int thresholdQuantity, int quantityInStock, String name, String description, String category) {
        this.thresholdQuantity = thresholdQuantity;
        this.quantityInStock = quantityInStock;
        this.name = name;
        this.description = description;
        this.category = category;
    }
    

    public InventoryEntity(){
        housekeepingOrderItems = new ArrayList<>();
    }
    
    public Long getInventoryId() {
        return inventoryId;
    }

    public int getThresholdQuantity() {
        return thresholdQuantity;
    }

    public void setThresholdQuantity(int thresholdQuantity) {
        this.thresholdQuantity = thresholdQuantity;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public List<HousekeepingOrderItemEntity> getHousekeepingOrderItems() {
        return housekeepingOrderItems;
    }

    public void setHousekeepingOrderItems(List<HousekeepingOrderItemEntity> housekeepingOrderItems) {
        this.housekeepingOrderItems = housekeepingOrderItems;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    
    
    
}
