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

/**
 *
 * @author vincentyeo
 */
@Entity
public class ProxyBidEntity extends BidEntity implements Serializable {

   private Double maxBidAmount;
   private Double increment;

    public ProxyBidEntity() {
        super();
    }

    public ProxyBidEntity(Double maxBidAmount, Double increment, double bidAmount, Date bidDateTime, Date startDateTime, Date endDateTime, AffiliateContentEntity affiliateContent) {
        super(bidAmount, bidDateTime, startDateTime, endDateTime, affiliateContent);
        this.maxBidAmount = maxBidAmount;
        this.increment = increment;
    }

    public Double getMaxBidAmount() {
        return maxBidAmount;
    }

    public void setMaxBidAmount(Double maxBidAmount) {
        this.maxBidAmount = maxBidAmount;
    }

    public Double getIncrement() {
        return increment;
    }

    public void setIncrement(Double increment) {
        this.increment = increment;
    }
}
