/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Nicholas
 */
@Entity
public class RoomOrderItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RoomOrderItemId;
    private String subItemName;
    private String subItemDesc;
    @Column(precision = 20, scale = 2)
    private BigDecimal subAmount;

    @ManyToOne
    private RoomOrderEntity roomOrder;

    public RoomOrderItemEntity(String subItemName, String subItemDesc, BigDecimal subAmount, RoomOrderEntity roomOrder) {
        this.subItemName = subItemName;
        this.subItemDesc = subItemDesc;
        this.subAmount = subAmount;
        this.roomOrder = roomOrder;
    }

    public RoomOrderItemEntity() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRoomOrderItemId() != null ? getRoomOrderItemId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomOrderItemEntity)) {
            return false;
        }
        RoomOrderItemEntity other = (RoomOrderItemEntity) object;
        if ((this.getRoomOrderItemId() == null && other.getRoomOrderItemId() != null) || (this.getRoomOrderItemId() != null && !this.RoomOrderItemId.equals(other.RoomOrderItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.RoomOrderItemEntity[ id=" + getRoomOrderItemId() + " ]";
    }

    /**
     * @return the RoomOrderItemId
     */
    public Long getRoomOrderItemId() {
        return RoomOrderItemId;
    }

    /**
     * @param RoomOrderItemId the RoomOrderItemId to set
     */
    public void setRoomOrderItemId(Long RoomOrderItemId) {
        this.RoomOrderItemId = RoomOrderItemId;
    }

    /**
     * @return the subItemName
     */
    public String getSubItemName() {
        return subItemName;
    }

    /**
     * @param subItemName the subItemName to set
     */
    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }

    /**
     * @return the subItemDesc
     */
    public String getSubItemDesc() {
        return subItemDesc;
    }

    /**
     * @param subItemDesc the subItemDesc to set
     */
    public void setSubItemDesc(String subItemDesc) {
        this.subItemDesc = subItemDesc;
    }

    /**
     * @return the subAmount
     */
    public BigDecimal getSubAmount() {
        return subAmount;
    }

    /**
     * @param subAmount the subAmount to set
     */
    public void setSubAmount(BigDecimal subAmount) {
        this.subAmount = subAmount;
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
