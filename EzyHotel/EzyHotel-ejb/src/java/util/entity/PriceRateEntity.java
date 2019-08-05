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
import javax.persistence.ManyToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class PriceRateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceRateId;

    private String rateTitle;
    private double basePrice;
    private int percentageMarkup;
    private double markupPrice;
    private String remarks;
    
    @ManyToOne
    private FacilityEntity facility;
    
    @ManyToOne
    private RoomTypeEntity roomType;

    public PriceRateEntity(String rateTitle, double basePrice, int percentageMarkup, double markupPrice, String remarks, FacilityEntity facility, RoomTypeEntity roomType) {
        this.rateTitle = rateTitle;
        this.basePrice = basePrice;
        this.percentageMarkup = percentageMarkup;
        this.markupPrice = markupPrice;
        this.remarks = remarks;
        this.facility = facility;
        this.roomType = roomType;
    }

    public PriceRateEntity() {
    }

    public Long getPriceRateId() {
        return priceRateId;
    }

    public void setPriceRateId(Long priceRateId) {
        this.priceRateId = priceRateId;
    }

    public String getRateTitle() {
        return rateTitle;
    }

    public void setRateTitle(String rateTitle) {
        this.rateTitle = rateTitle;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getPercentageMarkup() {
        return percentageMarkup;
    }

    public void setPercentageMarkup(int percentageMarkup) {
        this.percentageMarkup = percentageMarkup;
    }

    public double getMarkupPrice() {
        return markupPrice;
    }

    public void setMarkupPrice(double markupPrice) {
        this.markupPrice = markupPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public FacilityEntity getFacility() {
        return facility;
    }

    public void setFacility(FacilityEntity facility) {
        this.facility = facility;
    }
    
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.priceRateId);
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
        final PriceRateEntity other = (PriceRateEntity) obj;
        if (!Objects.equals(this.priceRateId, other.priceRateId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PriceRateEntity{" + "priceRateId=" + priceRateId + '}';
    }
    
}