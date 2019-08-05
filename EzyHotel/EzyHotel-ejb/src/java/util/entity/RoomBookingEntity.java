/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.RoomBookingStatusEnum;

/**
 *
 * @author vincentyeo
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ROOMBOOKINGENTITY")
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "RoomBookingEntity")
public class RoomBookingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String reservationNumber;
    @Temporal(TemporalType.DATE)
    private Date checkInDateTime;
    @Temporal(TemporalType.DATE)
    private Date checkOutDateTime;
    private int numOfDays;
    @Enumerated(EnumType.STRING)
    private RoomBookingStatusEnum status;
    private String specialRequests;
    private String roomNumber;
    private String roomTypeCode;
    private int adultCount;
    private int childCount;

    @ManyToOne
    private RoomTypeEntity roomType;

    @ManyToOne
    private CustomerEntity customer;
    
    @OneToOne
    private RoomOrderEntity roomOrder;

    public RoomBookingEntity() {
    }

    public RoomBookingEntity(int adultCount, Date checkInDateTime, Date checkOutDateTime, int childCount, int numOfDays,
            String reservationNumber, String roomNumber, String roomTypeCode, String specialRequests,
            RoomBookingStatusEnum status, CustomerEntity customer, RoomTypeEntity roomType) {
        this.adultCount = adultCount;
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.childCount = childCount;
        this.numOfDays = numOfDays;
        this.reservationNumber = reservationNumber;
        this.roomNumber = roomNumber;
        this.roomTypeCode = roomTypeCode;
        this.specialRequests = specialRequests;
        this.status = status;
        this.customer = customer;
        this.roomType = roomType;
    }

    public RoomBookingEntity(Date checkInDateTime, Date checkOutDateTime, int numOfDays, int childCount,
            RoomTypeEntity roomType) {
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.numOfDays = numOfDays;
        this.childCount = childCount;
        this.roomType = roomType;
    }

    public RoomBookingEntity(Date checkInDateTime, Date checkOutDateTime, int numOfDays, String roomTypeCode,
            int childCount) {
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.numOfDays = numOfDays;
        this.roomTypeCode = roomTypeCode;
        this.childCount = childCount;
    }

    public RoomBookingEntity(Date checkInDateTime, Date checkOutDateTime, int numOfDays, String roomTypeCode,
            int adultCount, int childCount, RoomTypeEntity roomType) {
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.numOfDays = numOfDays;
        this.roomTypeCode = roomTypeCode;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.roomType = roomType;
    }

    public RoomBookingEntity(Date checkInDateTime, Date checkOutDateTime, int numOfDays, String specialRequests,
            String roomNumber, String roomTypeCode, int adultCount, int childCount, RoomTypeEntity roomType,
            CustomerEntity customer) {
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.numOfDays = numOfDays;
        this.specialRequests = specialRequests;
        this.roomNumber = roomNumber;
        this.roomTypeCode = roomTypeCode;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.roomType = roomType;
        this.customer = customer;
    }

    public Long getBookingId() {
        return bookingId;
    }

    /**
     * @param bookingId the bookingId to set
     */
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    /**
     * @return the checkInDateTime
     */
    public Date getCheckInDateTime() {
        return checkInDateTime;
    }

    /**
     * @param checkInDateTime the checkInDateTime to set
     */
    public void setCheckInDateTime(Date checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    /**
     * @return the checkOutDateTime
     */
    public Date getCheckOutDateTime() {
        return checkOutDateTime;
    }

    /**
     * @param checkOutDateTime the checkOutDateTime to set
     */
    public void setCheckOutDateTime(Date checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }

    /**
     * @return the status
     */
    public RoomBookingStatusEnum getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RoomBookingStatusEnum status) {
        this.status = status;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.bookingId);
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
        final RoomBookingEntity other = (RoomBookingEntity) obj;
        if (!Objects.equals(this.bookingId, other.bookingId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoomBookingEntity{" + "bookingId=" + bookingId + ", reservationNumber=" + reservationNumber
                + ", checkInDateTime=" + checkInDateTime + ", checkOutDateTime=" + checkOutDateTime + ", numOfDays="
                + numOfDays + ", status=" + status + ", specialRequests=" + specialRequests + ", roomNumber="
                + roomNumber + ", roomTypeCode=" + roomTypeCode + ", adultCount=" + adultCount + ", childCount="
                + childCount + ", roomType=" + roomType + ", customer=" + customer + '}';
    }

    /**
     * @return the adultCount
     */
    public int getAdultCount() {
        return adultCount;
    }

    /**
     * @param adultCount the adultCount to set
     */
    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    /**
     * @return the childCount
     */
    public int getChildCount() {
        return childCount;
    }

    /**
     * @param childCount the childCount to set
     */
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    /**
     * @return the roomOrder
     */
    public RoomOrderEntity getRoomOrder() {
        return roomOrder;
    }

    /**
     * @param roomOrder the roomOrder to set
     */
    public void setRoomOrder(RoomOrderEntity roomOrder) {
        this.roomOrder = roomOrder;
    }

}
