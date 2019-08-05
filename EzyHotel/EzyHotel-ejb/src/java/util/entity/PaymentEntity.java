/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
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
public class PaymentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    
    private int paymentCode;
    
    @Column(precision = 20, scale = 2)
    private BigDecimal totalAmount;
    
    @OneToOne
    private RoomBookingEntity roomBooking;
    

    public PaymentEntity(int paymentCode, BigDecimal totalAmount, RoomBookingEntity roomBooking) {
        this.paymentCode = paymentCode;
        this.totalAmount = totalAmount;
        this.roomBooking = roomBooking;
    }

    public PaymentEntity() {}

    public Long getPaymentId() {
        return paymentId;
    }

    public int getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(int paymentCode) {
        this.paymentCode = paymentCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    
    public RoomBookingEntity getRoomBooking() {
        return roomBooking;
    }

    public void setRoomBooking(RoomBookingEntity roomBooking) {
        this.roomBooking = roomBooking;
    }
    

 
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.paymentId);
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
        final PaymentEntity other = (PaymentEntity) obj;
        if (!Objects.equals(this.paymentId, other.paymentId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PaymentEntity{" + "paymentId=" + paymentId + ", paymentCode=" + paymentCode + ", totalAmount=" + totalAmount + ", roomBooking=" + roomBooking + '}';
    }


}

