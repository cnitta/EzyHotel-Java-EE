/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
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
 * @author vincentyeo
 */
@Entity
public class PaymentMethodEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentMethodId;

    private int paymentCode;

    private String cardNo;
    private String cardName;
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @OneToOne
    private PaymentEntity payment;

    public PaymentMethodEntity(int paymentCode, String cardNo, String cardName, Date expiryDate,
            PaymentEntity payment) {
        this.paymentMethodId = paymentMethodId;
        this.paymentCode = paymentCode;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.payment = payment;
    }

    public PaymentMethodEntity() {
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public int getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(int paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.paymentMethodId);
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
        final PaymentMethodEntity other = (PaymentMethodEntity) obj;
        if (!Objects.equals(this.paymentMethodId, other.paymentMethodId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PaymentMethodEntity{" + "paymentMethodId=" + paymentMethodId + ", paymentCode=" + paymentCode + ", cardNo=" + cardNo + ", cardName=" + cardName + ", expiryDate=" + expiryDate + ", payment=" + payment + '}';
    }


}
