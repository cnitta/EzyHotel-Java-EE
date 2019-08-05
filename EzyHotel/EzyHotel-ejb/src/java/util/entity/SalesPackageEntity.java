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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class SalesPackageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesPackageId;

    private String title;
    @Temporal(TemporalType.DATE)     
    private Date startDate;
    private int duration;
    private String facilityType;
    private String detail;
    private double price;
    
    @ManyToOne
    private FacilityEntity facility;
    
    @OneToOne
    private PictureEntity picture;
    
    @ManyToOne
    private RoomTypeEntity roomType;

    public SalesPackageEntity(String title, Date startDate, int duration, 
            String facilityType, String detail, double price, FacilityEntity facility) {
        this.title = title;
        this.startDate = startDate;
        this.duration = duration;
        this.facilityType = facilityType;
        this.detail = detail;
        this.price = price;
        this.facility = facility;
    }

    public SalesPackageEntity() {
    }
    
    
    public Long getSalesPackageId() {
        return salesPackageId;
    }  
    public void setSalesPackageId(Long salesPackageId) {
        this.salesPackageId = salesPackageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public FacilityEntity getFacility() {
        return facility;
    }

    public void setFacility(FacilityEntity facility) {
        this.facility = facility;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.salesPackageId);
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
        final SalesPackageEntity other = (SalesPackageEntity) obj;
        if (!Objects.equals(this.salesPackageId, other.salesPackageId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SalesPackageEntity{" + "salesPackageId=" + salesPackageId + '}';
    }

    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    /**
     * @return the picture
     */
    public PictureEntity getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }
    
}
