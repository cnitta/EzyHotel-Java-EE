/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.io.Serializable;
import util.entity.PictureEntity;

/**
 *
 * @author berni
 */
public class PriceRateRoomTypeClass implements Serializable{
    
    private String roomType; //Based on the name from RoomTypeEntity
    private String roomTypeCode; //The code used from RoomTypeEntity
    private String priceRateTitle; // leisure, standard and group from PriceRateEntity
    private double finalPrice; //I.e. when checking to use markup price or base price from PriceRateEntity
    private Integer maxGuestSize; //No. of guest in the rooms
    private PictureEntity picture; //KIV: To be included later
 
    public PriceRateRoomTypeClass(String roomType, String roomTypeCode, String priceRateTitle, double finalPrice, Integer maxGuestSize, PictureEntity picture) {
        this.roomType = roomType;
        this.roomTypeCode = roomTypeCode;
        this.priceRateTitle = priceRateTitle;
        this.finalPrice = finalPrice;
        this.maxGuestSize = maxGuestSize;
        this.picture = picture;
    }

    public PriceRateRoomTypeClass() {
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public String getPriceRateTitle() {
        return priceRateTitle;
    }

    public void setPriceRateTitle(String priceRateTitle) {
        this.priceRateTitle = priceRateTitle;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }


    public Integer getMaxGuestSize() {
        return maxGuestSize;
    }

    public void setMaxGuestSize(Integer maxGuestSize) {
        this.maxGuestSize = maxGuestSize;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }
    
    
}