/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class RoomAmenityEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomAmenityId;
    private Boolean nonSmoking;
    private Boolean freeWifi;
    private Boolean televisionNcableChn;
    private Boolean writingDeskNChair;
    private Boolean airConditioning;
    private Boolean bathroomAmenities;
    private Boolean bathTowels;
    private Boolean bdromSlippers;
    private Boolean hairDryer;
    private Boolean coffeeNTeaMaker;
    private Boolean nespressoCoffeeMachine;
    private Boolean ironNIroningBoard;
    private Boolean miniBar;
    private Boolean electricCooktop;
    private Boolean microwaveOven;
    private Boolean toaster;
    private Boolean culteryNUtensils;
    private Boolean mobilePhoneDeviceNCharger;
    private Boolean electronicSafe;
    private Boolean washerCumDryer;
    private Boolean diningArea;
    private Boolean kitchenette;
    private Boolean livingRoom;
    
    
    public Long getRoomAmenityId() {
        return roomAmenityId;
    }

    public void setRoomAmenityId(Long roomAmenityId) {
        this.roomAmenityId = roomAmenityId;
    }

    public Boolean getNonSmoking() {
        return nonSmoking;
    }

    public void setNonSmoking(Boolean nonSmoking) {
        this.nonSmoking = nonSmoking;
    }

    public Boolean getFreeWifi() {
        return freeWifi;
    }

    public void setFreeWifi(Boolean freeWifi) {
        this.freeWifi = freeWifi;
    }

    public Boolean getTelevisionNcableChn() {
        return televisionNcableChn;
    }

    public void setTelevisionNcableChn(Boolean televisionNcableChn) {
        this.televisionNcableChn = televisionNcableChn;
    }

    public Boolean getWritingDeskNChair() {
        return writingDeskNChair;
    }

    public void setWritingDeskNChair(Boolean writingDeskNChair) {
        this.writingDeskNChair = writingDeskNChair;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public Boolean getBathroomAmenities() {
        return bathroomAmenities;
    }

    public void setBathroomAmenities(Boolean bathroomAmenities) {
        this.bathroomAmenities = bathroomAmenities;
    }

    public Boolean getBathTowels() {
        return bathTowels;
    }

    public void setBathTowels(Boolean bathTowels) {
        this.bathTowels = bathTowels;
    }

    public Boolean getBdromSlippers() {
        return bdromSlippers;
    }

    public void setBdromSlippers(Boolean bdromSlippers) {
        this.bdromSlippers = bdromSlippers;
    }

    public Boolean getHairDryer() {
        return hairDryer;
    }

    public void setHairDryer(Boolean hairDryer) {
        this.hairDryer = hairDryer;
    }

    public Boolean getCoffeeNTeaMaker() {
        return coffeeNTeaMaker;
    }

    public void setCoffeeNTeaMaker(Boolean coffeeNTeaMaker) {
        this.coffeeNTeaMaker = coffeeNTeaMaker;
    }

    public Boolean getNespressoCoffeeMachine() {
        return nespressoCoffeeMachine;
    }

    public void setNespressoCoffeeMachine(Boolean nespressoCoffeeMachine) {
        this.nespressoCoffeeMachine = nespressoCoffeeMachine;
    }

    public Boolean getIronNIroningBoard() {
        return ironNIroningBoard;
    }

    public void setIronNIroningBoard(Boolean ironNIroningBoard) {
        this.ironNIroningBoard = ironNIroningBoard;
    }

    public Boolean getMiniBar() {
        return miniBar;
    }

    public void setMiniBar(Boolean miniBar) {
        this.miniBar = miniBar;
    }

    public Boolean getElectricCooktop() {
        return electricCooktop;
    }

    public void setElectricCooktop(Boolean electricCooktop) {
        this.electricCooktop = electricCooktop;
    }

    public Boolean getMicrowaveOven() {
        return microwaveOven;
    }

    public void setMicrowaveOven(Boolean microwaveOven) {
        this.microwaveOven = microwaveOven;
    }

    public Boolean getToaster() {
        return toaster;
    }

    public void setToaster(Boolean toaster) {
        this.toaster = toaster;
    }

    public Boolean getCulteryNUtensils() {
        return culteryNUtensils;
    }

    public void setCulteryNUtensils(Boolean culteryNUtensils) {
        this.culteryNUtensils = culteryNUtensils;
    }

    public Boolean getMobilePhoneDeviceNCharger() {
        return mobilePhoneDeviceNCharger;
    }

    public void setMobilePhoneDeviceNCharger(Boolean mobilePhoneDeviceNCharger) {
        this.mobilePhoneDeviceNCharger = mobilePhoneDeviceNCharger;
    }

    public Boolean getElectronicSafe() {
        return electronicSafe;
    }

    public void setElectronicSafe(Boolean electronicSafe) {
        this.electronicSafe = electronicSafe;
    }

    public Boolean getWasherCumDryer() {
        return washerCumDryer;
    }

    public void setWasherCumDryer(Boolean washerCumDryer) {
        this.washerCumDryer = washerCumDryer;
    }

    public Boolean getDiningArea() {
        return diningArea;
    }

    public void setDiningArea(Boolean diningArea) {
        this.diningArea = diningArea;
    }

    public Boolean getKitchenette() {
        return kitchenette;
    }

    public void setKitchenette(Boolean kitchenette) {
        this.kitchenette = kitchenette;
    }

    public Boolean getLivingRoom() {
        return livingRoom;
    }

    public void setLivingRoom(Boolean livingRoom) {
        this.livingRoom = livingRoom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomAmenityId != null ? roomAmenityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomAmenityId fields are not set
        if (!(object instanceof RoomAmenityEntity)) {
            return false;
        }
        RoomAmenityEntity other = (RoomAmenityEntity) object;
        if ((this.roomAmenityId == null && other.roomAmenityId != null) || (this.roomAmenityId != null && !this.roomAmenityId.equals(other.roomAmenityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.RoomAmenityEntity[ id=" + roomAmenityId + " ]";
    }

}
