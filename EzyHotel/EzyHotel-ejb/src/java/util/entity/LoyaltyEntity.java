/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class LoyaltyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loyaltyPointId;
    private int maxPoint;
    private int currentPoint;
    private String membershipType;
    @Temporal(TemporalType.DATE)     
    private Date startDate;
    
    @OneToOne
    private CustomerEntity customer;
    
    public LoyaltyEntity() {
        maxPoint = 0;
        currentPoint = 0;
        membershipType = "NORMAL";
    }
    
    public LoyaltyEntity(int maxPoint, int currentPoint, String membershipType, Date startDate, CustomerEntity customer) {
        this.maxPoint = maxPoint;
        this.currentPoint = currentPoint;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.customer = customer;
    }
  
    public Long getLoyaltyPointId() {
        return loyaltyPointId;
    }

    public void setLoyaltyPointId(Long loyaltyPointId) {
        this.loyaltyPointId = loyaltyPointId;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
        hash += (loyaltyPointId != null ? loyaltyPointId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the loyaltyPointId fields are not set
        if (!(object instanceof LoyaltyEntity)) {
            return false;
        }
        LoyaltyEntity other = (LoyaltyEntity) object;
        if ((this.loyaltyPointId == null && other.loyaltyPointId != null) || (this.loyaltyPointId != null && !this.loyaltyPointId.equals(other.loyaltyPointId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.LoyaltyEntity[ id=" + loyaltyPointId + " ]";
    }
    
}
