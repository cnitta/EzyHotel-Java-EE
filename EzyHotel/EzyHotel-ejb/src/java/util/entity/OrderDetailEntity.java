/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author berni
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class OrderDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    
    private Integer quantity;   

    public OrderDetailEntity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderDetailEntity() { }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }        
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.orderDetailId);
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
        final OrderDetailEntity other = (OrderDetailEntity) obj;
        if (!Objects.equals(this.orderDetailId, other.orderDetailId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderDetailEntity{" + "orderDetailId=" + orderDetailId + '}';
    }
    
    
    
}
