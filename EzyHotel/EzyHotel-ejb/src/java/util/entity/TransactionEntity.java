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
public class TransactionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String custIdentity;
    private String dateTime;
    @Column(precision = 20, scale = 2)
    private BigDecimal totalAmount;
    private String otherDetails;
    
    @OneToOne
    private PaymentEntity payment;

    public TransactionEntity(String custIdentity, String dateTime, BigDecimal totalAmount, String otherDetails) {
        this.custIdentity = custIdentity;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
        this.otherDetails = otherDetails;
    }

    public TransactionEntity() {}

    public Long getTransactionId() {
        return transactionId;
    }

    public String getCustIdentity() {
        return custIdentity;
    }

    public void setCustIdentity(String custIdentity) {
        this.custIdentity = custIdentity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.transactionId);
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
        final TransactionEntity other = (TransactionEntity) obj;
        if (!Objects.equals(this.transactionId, other.transactionId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" + "transactionId=" + transactionId + ", custIdentity=" + custIdentity + ", dateTime=" + dateTime + ", totalAmount=" + totalAmount + ", otherDetails=" + otherDetails + ", payment=" + payment + '}';
    }

 

    
    
}