/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nicholas
 */
@Entity
public class RoomOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomOrderId;
    @Temporal(TemporalType.DATE)
    private Date roomOrderDate;
    private String roomOrderName;
    private String roomOrderDesc;
    private String roomNumber;
    private String status;
    @Column(precision = 20, scale = 2)
    private BigDecimal totalAmount;

    public RoomOrderEntity(Date roomOrderDate, String roomOrderName, String roomOrderDesc, String roomNumber, String status, BigDecimal totalAmount) {
        this.roomOrderDate = roomOrderDate;
        this.roomOrderName = roomOrderName;
        this.roomOrderDesc = roomOrderDesc;
        this.roomNumber = roomNumber;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public RoomOrderEntity() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRoomOrderId() != null ? getRoomOrderId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomOrderEntity)) {
            return false;
        }
        RoomOrderEntity other = (RoomOrderEntity) object;
        if ((this.getRoomOrderId() == null && other.getRoomOrderId() != null) || (this.getRoomOrderId() != null && !this.roomOrderId.equals(other.roomOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.RoomOrder[ id=" + getRoomOrderId() + " ]";
    }

    /**
     * @return the roomOrderId
     */
    public Long getRoomOrderId() {
        return roomOrderId;
    }

    /**
     * @param roomOrderId the roomOrderId to set
     */
    public void setRoomOrderId(Long roomOrderId) {
        this.roomOrderId = roomOrderId;
    }

    /**
     * @return the roomOrderDate
     */
    public Date getRoomOrderDate() {
        return roomOrderDate;
    }

    /**
     * @param roomOrderDate the roomOrderDate to set
     */
    public void setRoomOrderDate(Date roomOrderDate) {
        this.roomOrderDate = roomOrderDate;
    }

    /**
     * @return the roomOrderName
     */
    public String getRoomOrderName() {
        return roomOrderName;
    }

    /**
     * @param roomOrderName the roomOrderName to set
     */
    public void setRoomOrderName(String roomOrderName) {
        this.roomOrderName = roomOrderName;
    }

    /**
     * @return the roomOrderDesc
     */
    public String getRoomOrderDesc() {
        return roomOrderDesc;
    }

    /**
     * @param roomOrderDesc the roomOrderDesc to set
     */
    public void setRoomOrderDesc(String roomOrderDesc) {
        this.roomOrderDesc = roomOrderDesc;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the roomNumber
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

}
