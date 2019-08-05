/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.ARGameStampBookStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class ARGameStampBookEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arGameStampBookId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private Integer rewardPoints;
    @Enumerated(EnumType.STRING)
    private ARGameStampBookStatusEnum aRGameStampBookStatus;
    
    private Hashtable<Long, Boolean> aRGameStampRecords; //id, true or false    
    
    @ManyToOne
    private CustomerEntity customer;
    
    public ARGameStampBookEntity() {
    }

    public ARGameStampBookEntity(Date startDate, Date endDate, Integer rewardPoints, ARGameStampBookStatusEnum aRGameStampBookStatus) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.rewardPoints = rewardPoints;
        this.aRGameStampBookStatus = aRGameStampBookStatus;
    }
        

    public ARGameStampBookEntity(Date startDate, Date endDate, Integer rewardPoints, ARGameStampBookStatusEnum aRGameStampBookStatus, Hashtable<Long, Boolean> aRGameStampRecords) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.rewardPoints = rewardPoints;
        this.aRGameStampBookStatus = aRGameStampBookStatus;
        this.aRGameStampRecords = aRGameStampRecords;
    }
        

    public Long getArGameStampBookId() {
        return arGameStampBookId;
    }

    public void setArGameStampBookId(Long arGameStampBookId) {
        this.arGameStampBookId = arGameStampBookId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public ARGameStampBookStatusEnum getaRGameStampBookStatus() {
        return aRGameStampBookStatus;
    }

    public void setaRGameStampBookStatus(ARGameStampBookStatusEnum aRGameStampBookStatus) {
        this.aRGameStampBookStatus = aRGameStampBookStatus;
    }

    public Hashtable<Long, Boolean> getaRGameStampRecords() {
        return aRGameStampRecords;
    }

    public void setaRGameStampRecords(Hashtable<Long, Boolean> aRGameStampRecords) {
        this.aRGameStampRecords = aRGameStampRecords;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (arGameStampBookId != null ? arGameStampBookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the arGameStampBookId fields are not set
        if (!(object instanceof ARGameStampBookEntity)) {
            return false;
        }
        ARGameStampBookEntity other = (ARGameStampBookEntity) object;
        if ((this.arGameStampBookId == null && other.arGameStampBookId != null) || (this.arGameStampBookId != null && !this.arGameStampBookId.equals(other.arGameStampBookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.ARGameStampBook[ id=" + arGameStampBookId + " ]";
    }
    
}
