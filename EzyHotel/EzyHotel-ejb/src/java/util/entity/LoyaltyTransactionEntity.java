/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class LoyaltyTransactionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyTransnId;
    private String transactionPoint;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    
    private boolean isFullyRedeemed;
    private int remainingPointForTransaction;

    @ManyToOne
    private LoyaltyEntity loyalty;

    @OneToOne
    private RoomBookingEntity roomBooking;

    @OneToOne
    private PaymentEntity payment;

    public LoyaltyTransactionEntity() {
    }

    public LoyaltyTransactionEntity(String transactionPoint, String description, Date dateCreated, LoyaltyEntity loyalty, RoomBookingEntity roomBooking, PaymentEntity payment) {
        this.transactionPoint = transactionPoint;
        this.description = description;
        this.dateCreated = dateCreated;
        this.loyalty = loyalty;
        this.roomBooking = roomBooking;
        this.payment = payment;
        isFullyRedeemed = false;
        remainingPointForTransaction = Integer.parseInt(transactionPoint);
    }

    public Long getLoyaltyTransnId() {
        return loyaltyTransnId;
    }

    public void setLoyaltyTransnId(Long loyaltyTransnId) {
        this.loyaltyTransnId = loyaltyTransnId;
    }

    public String getTransactionPoint() {
        return transactionPoint;
    }

    public void setTransactionPoint(String transactionPoint) {
        this.transactionPoint = transactionPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LoyaltyEntity getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(LoyaltyEntity loyalty) {
        this.loyalty = loyalty;
    }

    public RoomBookingEntity getRoomBooking() {
        return roomBooking;
    }

    public void setRoomBooking(RoomBookingEntity roomBooking) {
        this.roomBooking = roomBooking;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public boolean isIsFullyRedeemed() {
        return isFullyRedeemed;
    }

    public void setIsFullyRedeemed(boolean isFullyRedeemed) {
        this.isFullyRedeemed = isFullyRedeemed;
    }

    public int getRemainingPointForTransaction() {
        return remainingPointForTransaction;
    }

    public void setRemainingPointForTransaction(int remainingPointForTransaction) {
        this.remainingPointForTransaction = remainingPointForTransaction;    
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loyaltyTransnId != null ? loyaltyTransnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the loyaltyTransnId fields
        // are not set
        if (!(object instanceof LoyaltyTransactionEntity)) {
            return false;
        }
        LoyaltyTransactionEntity other = (LoyaltyTransactionEntity) object;
        if ((this.loyaltyTransnId == null && other.loyaltyTransnId != null)
                || (this.loyaltyTransnId != null && !this.loyaltyTransnId.equals(other.loyaltyTransnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.LoyaltyTransaction[ id=" + loyaltyTransnId + " ]";
    }

    

}
