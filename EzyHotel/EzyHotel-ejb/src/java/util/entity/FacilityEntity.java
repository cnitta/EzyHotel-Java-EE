/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import util.enumeration.FacilityStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class FacilityEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    private String name;
    private String description;
    private String facilityType;
    private String capacity;
    private Double area;
    private String facFeature;
    @Enumerated(EnumType.STRING)
    private FacilityStatusEnum facStatus;   
    private String cleaningStatus = "Dirty";


    @ManyToOne
    private HotelEntity hotel;
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.getFacilityId());
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
        final FacilityEntity other = (FacilityEntity) obj;
        if (!Objects.equals(this.facilityId, other.facilityId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FacilityEntity{" + "facilityId=" + getFacilityId() + '}';
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

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getFacFeature() {
        return facFeature;
    }

    public void setFacFeature(String facFeature) {
        this.facFeature = facFeature;
    }


    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    

    /**
     * @return the facilityId
     */
    public Long getFacilityId() {
        return facilityId;
    }

    /**
     * @param facilityId the facilityId to set
     */
    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    /**
     * @return the facStatus
     */
    public FacilityStatusEnum getFacStatus() {
        return facStatus;
    }

    /**
     * @param facStatus the facStatus to set
     */
    public void setFacStatus(FacilityStatusEnum facStatus) {
        this.facStatus = facStatus;
    }

    /**
     * @return the cleaningStatus
     */
    public String getCleaningStatus() {
        return cleaningStatus;
    }

    /**
     * @param cleaningStatus the cleaningStatus to set
     */
    public void setCleaningStatus(String cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }
    
}
