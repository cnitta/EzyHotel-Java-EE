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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bryantan
 */
@Entity
public class InventoryUsageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.DATE)     
    private Date date = new Date();
    
    private int quantityUsed;
    
    
    @ManyToOne
    private InventoryEntity item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventoryUsageEntity)) {
            return false;
        }
        InventoryUsageEntity other = (InventoryUsageEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.InventoryUsageEntity[ id=" + id + " ]";
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the quantityUsed
     */
    public int getQuantityUsed() {
        return quantityUsed;
    }

    /**
     * @param quantityUsed the quantityUsed to set
     */
    public void setQuantityUsed(int quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    /**
     * @return the item
     */
    public InventoryEntity getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(InventoryEntity item) {
        this.item = item;
    }
    
}
