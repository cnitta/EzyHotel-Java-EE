/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import util.enumeration.GroupBookingEnum;

/**
 *
 * @author berni
 */
@Entity(name="GroupBookingEntity")
@Table(name="ROOMBOOKINGENTITY")
@DiscriminatorValue(value="GroupBookingEntity")

public class GroupBookingEntity extends RoomBookingEntity {

    @Enumerated(EnumType.STRING)
    private GroupBookingEnum groupBookingType;
    private String companyName;
    
    public GroupBookingEntity() {
    }
    
    //For corporate booking
    public GroupBookingEntity(GroupBookingEnum groupBookingType, String companyName, Date checkInDateTime, Date checkOutDateTime, int numOfDays, String roomTypeCode, int adultCount, int childCount, RoomTypeEntity roomType) {
        super(checkInDateTime, checkOutDateTime, numOfDays, roomTypeCode, adultCount, childCount, roomType);
        this.groupBookingType = groupBookingType;
        this.companyName = companyName;
        
    }

    public GroupBookingEnum getGroupBookingType() {
        return groupBookingType;
    }

    public void setGroupBookingType(GroupBookingEnum groupBookingType) {
        this.groupBookingType = groupBookingType;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "GroupBookingEntity{" + "groupBookingType=" + groupBookingType + ", companyName=" + companyName + '}';
    }

}