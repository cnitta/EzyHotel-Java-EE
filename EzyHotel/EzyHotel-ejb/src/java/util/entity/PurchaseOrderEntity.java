/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author berni
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PurchaseOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    public PurchaseOrderEntity(Timestamp orderDate) {
        this.orderDate = orderDate;     
    }

    public PurchaseOrderEntity() { }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
        
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.purchaseOrderId);
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
        final PurchaseOrderEntity other = (PurchaseOrderEntity) obj;
        if (!Objects.equals(this.purchaseOrderId, other.purchaseOrderId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PurchaseOrderEntity{" + "purchaseOrderId=" + purchaseOrderId + '}';
    }

}
