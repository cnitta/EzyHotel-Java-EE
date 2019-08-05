/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author berni
 */

@Entity(name = "BookOnBehalfEntity")
@Table(name = "ROOMBOOKINGENTITY")
@DiscriminatorValue(value = "BookOnBehalfEntity")
public class BookOnBehalfEntity extends RoomBookingEntity {
    private String guestFirstName;
    private String guestLastName;
    private String guestEmail;

    public BookOnBehalfEntity() {
    }

    public BookOnBehalfEntity(String guestFirstName, String guestLastName, String guestEmail, Date checkInDateTime,
            Date checkOutDateTime, int numOfDays, String roomTypeCode, int adultCount, int childCount,
            RoomTypeEntity roomType) {
        super(checkInDateTime, checkOutDateTime, numOfDays, roomTypeCode, adultCount, childCount, roomType);
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.guestEmail = guestEmail;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }

    public String getGuestLastName() {
        return guestLastName;
    }

    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    @Override
    public String toString() {
        return "BookOnBehalfEntity{" + "guestFirstName=" + guestFirstName + ", guestLastName=" + guestLastName
                + ", guestEmail=" + guestEmail + '}';
    }

}
