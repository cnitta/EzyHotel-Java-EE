/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import util.enumeration.MerchandiseStatusEnum;

/**
 *
 * @author berni
 */
@Entity
public class MerchandiseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchandiseId;

    private String name;
    private String description;
    private Integer costPoints;
    private Integer maxCostPriceLimit;
    private Integer quantityOnHand;
    @Enumerated(EnumType.STRING)
    private MerchandiseStatusEnum merchandiseStatus;
    private Integer poTriggerLevel;
    private Boolean isTriggerOn;
    
    @OneToOne
    private PictureEntity picture;

    public MerchandiseEntity(String name, String description, Integer costPoints, Integer maxCostPriceLimit, Integer quantityOnHand, Integer poTriggerLevel, Boolean isTriggerOn) {
        this.name = name;
        this.description = description;
        this.costPoints = costPoints;
        this.maxCostPriceLimit = maxCostPriceLimit;
        this.quantityOnHand = quantityOnHand;
        this.poTriggerLevel = poTriggerLevel;
        this.isTriggerOn = isTriggerOn;
        this.merchandiseStatus = MerchandiseStatusEnum.NOT_ON_SALE;
    }

    public MerchandiseEntity() {
    }

    public Long getMerchandiseId() {
        return merchandiseId;
    }
    
    public void setMerchandiseId(Long merchandiseId) {
       this.merchandiseId = merchandiseId;
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

    public Integer getCostPoints() {
        return costPoints;
    }

    public void setCostPoints(Integer costPoints) {
        this.costPoints = costPoints;
    }

    public Integer getMaxCostPriceLimit() {
        return maxCostPriceLimit;
    }

    public void setMaxCostPriceLimit(Integer maxCostPriceLimit) {
        this.maxCostPriceLimit = maxCostPriceLimit;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public MerchandiseStatusEnum getMerchandiseStatus() {
        return merchandiseStatus;
    }

    public void setMerchandiseStatus(MerchandiseStatusEnum merchandiseStatus) {
        this.merchandiseStatus = merchandiseStatus;
    }

    public Integer getPoTriggerLevel() {
        return poTriggerLevel;
    }

    public void setPoTriggerLevel(Integer poTriggerLevel) {
        this.poTriggerLevel = poTriggerLevel;
    }

    public Boolean getIsTriggerOn() {
        return isTriggerOn;
    }

    public void setIsTriggerOn(Boolean isTriggerOn) {
        this.isTriggerOn = isTriggerOn;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.merchandiseId);
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
        final MerchandiseEntity other = (MerchandiseEntity) obj;
        if (!Objects.equals(this.merchandiseId, other.merchandiseId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MerchandiseEntiy{" + "merchandiseId=" + merchandiseId + '}';
    }

    
    
}
