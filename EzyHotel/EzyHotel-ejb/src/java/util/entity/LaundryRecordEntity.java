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
import javax.persistence.ManyToOne;

/**
 *
 * @author berni
 */
@Entity
public class LaundryRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laundryId;
    
    private String name;
    private String comments;
    private String RFIDTag; //Check if correct data-type
    
    @ManyToOne
    private HousekeepingManagerEntity housekeepingManager;
    
    @ManyToMany
    private List<LaundryItemEntity> laundryItems;

    public LaundryRecordEntity(String name, String comments, String RFIDTag, HousekeepingManagerEntity housekeepingManager, List<LaundryItemEntity> laundryItems) {
        this.name = name;
        this.comments = comments;
        this.RFIDTag = RFIDTag;
        this.housekeepingManager = housekeepingManager;
        this.laundryItems = laundryItems;
    }

    public LaundryRecordEntity() {
        laundryItems = new ArrayList<>();
    }

    public Long getLaundryId() {
        return laundryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRFIDTag() {
        return RFIDTag;
    }

    public void setRFIDTag(String RFIDTag) {
        this.RFIDTag = RFIDTag;
    }

    public HousekeepingManagerEntity getHousekeepingManager() {
        return housekeepingManager;
    }

    public void setHousekeepingManager(HousekeepingManagerEntity housekeepingManager) {
        this.housekeepingManager = housekeepingManager;
    }

    public List<LaundryItemEntity> getLaundryItems() {
        return laundryItems;
    }

    public void setLaundryItems(List<LaundryItemEntity> laundryItems) {
        this.laundryItems = laundryItems;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.laundryId);
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
        final LaundryRecordEntity other = (LaundryRecordEntity) obj;
        if (!Objects.equals(this.laundryId, other.laundryId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LaundryRecordEntity{" + "laundryId=" + laundryId + '}';
    }

    
}
