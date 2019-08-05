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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BidEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;
    private double bidAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bidDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;
    
    @OneToOne
    private AffiliateContentEntity affiliateContent;
    
    @ManyToOne
    private AffiliateEntity affiliate;

    public BidEntity() {
    }

    public BidEntity(double bidAmount, Date bidDateTime, Date startDateTime, Date endDateTime, AffiliateContentEntity affiliateContent) {
        this.bidAmount = bidAmount;
        this.bidDateTime = bidDateTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.affiliateContent = affiliateContent;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }    

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getBidDateTime() {
        return bidDateTime;
    }

    public void setBidDateTime(Date bidDateTime) {
        this.bidDateTime = bidDateTime;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public AffiliateContentEntity getAffiliateContent() {
        return affiliateContent;
    }

    public void setAffiliateContent(AffiliateContentEntity affiliateContent) {
        this.affiliateContent = affiliateContent;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.bidId);
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
        final BidEntity other = (BidEntity) obj;
        if (!Objects.equals(this.bidId, other.bidId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BidEntity{" + "bidId=" + bidId + '}';
    }
    
    public AffiliateEntity getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateEntity affiliate) {
        this.affiliate = affiliate;
    }    

  
}
