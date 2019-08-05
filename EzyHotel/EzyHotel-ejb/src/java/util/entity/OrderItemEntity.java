/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 *
 * @author berni
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OrderItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private String name;
    private String description;
    private double unitPrice;
    private List<PictureEntity> picture;           
    @ManyToOne
    private SupplierEntity supplier;

    public OrderItemEntity(String name, String description, double unitPrice, List<PictureEntity> picture) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.picture = picture;
    }
    
    public OrderItemEntity(){}

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }        
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<PictureEntity> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureEntity> picture) {
        this.picture = picture;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.orderItemId);
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
        final OrderItemEntity other = (OrderItemEntity) obj;
        
        if (!Objects.equals(this.orderItemId, other.orderItemId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderItemEntity{" + "itemId=" + orderItemId + '}';
    }


    
}
