/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.entity.FacilityEntity;
import util.entity.HotelEntity;
import util.entity.PromotionEntity;
import util.entity.SalesPackageEntity;

/**
 *
 * @author berni
 */

//Don't really need an entity per se nor store into DB, but need a class to help structure the info
//To that end, will not be affected by any form of associations especially when calling RESTful methods

//Will be for hotel-specific, i.e. information of 1 hotel.
public class InformationRetrievalClass implements Serializable{
    
    private LocalDateTime dateTimeRetrieval; //The date and time when the info is first retrieved
    private HotelEntity hotel;
    private List<FacilityEntity> facilities;
    private List<PriceRateRoomTypeClass> priceRoomTypeInfos;
    private List<SalesPackageEntity> salesPackages;
    private List<PromotionEntity> promotions;

    public InformationRetrievalClass() {
        facilities = new ArrayList<>();
        priceRoomTypeInfos = new ArrayList<>();
        salesPackages = new ArrayList<>();
        promotions = new ArrayList<>();
    }

    public LocalDateTime getDateTimeRetrieval() {
        return dateTimeRetrieval;
    }

    public void setDateTimeRetrieval(LocalDateTime dateTimeRetrieval) {
        this.dateTimeRetrieval = dateTimeRetrieval;
    }

    public List<PriceRateRoomTypeClass> getPriceRoomTypeInfos() {
        return priceRoomTypeInfos;
    }

    public void setPriceRoomTypeInfos(List<PriceRateRoomTypeClass> priceRoomTypeInfos) {
        this.priceRoomTypeInfos = priceRoomTypeInfos;
    }

    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    public List<SalesPackageEntity> getSalesPackages() {
        return salesPackages;
    }

    public void setSalesPackages(List<SalesPackageEntity> salesPackages) {
        this.salesPackages = salesPackages;
    }

    public List<PromotionEntity> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionEntity> promotions) {
        this.promotions = promotions;
    }

    public List<FacilityEntity> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilityEntity> facilities) {
        this.facilities = facilities;
    }

    @Override
    public String toString() {
        return "InformationRetrievalClass{" + "dateTimeRetrieval=" + dateTimeRetrieval + ", hotel=" + hotel + ", facilities=" + facilities + ", priceRoomTypeInfos=" + priceRoomTypeInfos + ", salesPackages=" + salesPackages + ", promotions=" + promotions + '}';
    }


}

