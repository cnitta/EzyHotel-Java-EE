/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import util.enumeration.RoomStatusEnum;


/**
 *
 * @author vincentyeo
 */
@Entity
public class RoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private Integer roomUnitNumber;
    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;
    private Boolean isDND= false;
    private String cleaningStatus = "Clean";
    
    @ManyToOne
    private RoomTypeEntity roomType;

    public RoomEntity() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomUnitNumber() {
        return roomUnitNumber;
    }

    public void setRoomUnitNumber(Integer roomUnitNumber) {
        this.roomUnitNumber = roomUnitNumber;
    }

    /**
     * @return the status
     */
    public RoomStatusEnum getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(RoomStatusEnum status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomId fields are not set
        if (!(object instanceof RoomEntity)) {
            return false;
        }
        RoomEntity other = (RoomEntity) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.RoomEntity[ id=" + roomId + " ]";
    }

    /**
     * @return the roomType
     */
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
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

    /**
     * @return the isDND
     */
    public Boolean getIsDND() {
        return isDND;
    }

    /**
     * @param isDND the isDND to set
     */
    public void setIsDND(Boolean isDND) {
        this.isDND = isDND;
    }

}

