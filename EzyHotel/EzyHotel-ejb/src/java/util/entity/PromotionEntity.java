/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class PromotionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;

    private String type;
    @Temporal(TemporalType.DATE)     
    private Date startDate;
    private int duration;
    private String pRateCategory;
    private double price;
    
    @ManyToOne
    private FacilityEntity facility;

    @ManyToOne
    private RoomTypeEntity roomType;
    
    public PromotionEntity(String type, Date startDate, int duration, String pRateCategory,
            double price, FacilityEntity facility) {
        this.type = type;
        this.startDate = startDate;
        this.duration = duration;
        this.pRateCategory = pRateCategory;
        this.price = price;
        this.facility = facility;
    }

    public PromotionEntity() {
    }

    public Long getPromotionId() {
        return promotionId;
    }
    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getpRateCategory() {
        return pRateCategory;
    }

    public void setpRateCategory(String pRateCategory) {
        this.pRateCategory = pRateCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.promotionId);
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
        final PromotionEntity other = (PromotionEntity) obj;
        if (!Objects.equals(this.promotionId, other.promotionId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PromotionEntity{" + "promotionId=" + promotionId + '}';
    }
}
