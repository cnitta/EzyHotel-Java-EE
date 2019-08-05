/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
public class HousekeepingRequestEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;
    private String requestType;
    private String status = "In Progress";
    private String message;  
    private LocalDateTime dateCreated = LocalDateTime.now(ZoneId.systemDefault());
    private Long roomServiceDelivery;
    
    @ManyToOne
    private RoomEntity room;
    
    @ManyToOne
    private StaffEntity staff;
    
    public HousekeepingRequestEntity(String requestType, String message, RoomEntity room, StaffEntity staff) {
        this.requestType = requestType;
        this.message = message;
        this.room = room;
        this.staff = staff;
    }

    public HousekeepingRequestEntity() {
    }
    

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestId != null ? requestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the requestId fields are not set
        if (!(object instanceof HousekeepingRequestEntity)) {
            return false;
        }
        HousekeepingRequestEntity other = (HousekeepingRequestEntity) object;
        if ((this.requestId == null && other.requestId != null) || (this.requestId != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.HousekeepingRequestEntity[ id=" + requestId + " ]";
    }

    /**
     * @return the staff
     */
    public StaffEntity getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(StaffEntity staff) {
        this.staff = staff;
    }

    /**
     * @return the dateCreated
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the roomServiceDelivery
     */
    public Long getRoomServiceDelivery() {
        return roomServiceDelivery;
    }

    /**
     * @param roomServiceDelivery the roomServiceDelivery to set
     */
    public void setRoomServiceDelivery(Long roomServiceDelivery) {
        this.roomServiceDelivery = roomServiceDelivery;
    }

    
}
