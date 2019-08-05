/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author berni
 */
@Entity
public class LaundryItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laundryItemId;
    
    @ManyToMany(mappedBy = "laundryItems")
    private List<LaundryRecordEntity> laundryRecords;

    public LaundryItemEntity(List<LaundryRecordEntity> laundryRecords) {
        this.laundryRecords = laundryRecords;
    }

    public LaundryItemEntity() {
        laundryRecords = new ArrayList<>();
    }

    public Long getLaundryItemId() {
        return laundryItemId;
    }

    public List<LaundryRecordEntity> getLaundryRecords() {
        return laundryRecords;
    }

    public void setLaundryRecords(List<LaundryRecordEntity> laundryRecords) {
        this.laundryRecords = laundryRecords;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.laundryItemId);
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
        final LaundryItemEntity other = (LaundryItemEntity) obj;
        if (!Objects.equals(this.laundryItemId, other.laundryItemId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LaundryItemEntity{" + "laundryItemId=" + laundryItemId + '}';
    }

   
    
}
