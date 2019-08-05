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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nicholas
 */
@Entity
public class InvoiceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private String invoiceNo;
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;
    private String itemName;
    private String itemDesc;
    private String status;
    @Column(precision = 20, scale = 2)
    private BigDecimal totalAmount;

    public InvoiceEntity(String invoiceNo, Date invoiceDate, String itemName, String itemDesc, String status, BigDecimal totalAmount) {
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public InvoiceEntity() {
    }

    /**
     * @return the invoiceId
     */
    public Long getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the invoiceDate
     */
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemDesc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * @param itemDesc the itemDesc to set
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getInvoiceId() != null ? getInvoiceId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceEntity)) {
            return false;
        }
        InvoiceEntity other = (InvoiceEntity) object;
        if ((this.getInvoiceId() == null && other.getInvoiceId() != null) || (this.getInvoiceId() != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.InvoiceEntity[ id=" + getInvoiceId() + " ]";
    }
}
