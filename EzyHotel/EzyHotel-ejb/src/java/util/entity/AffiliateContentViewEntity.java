/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class AffiliateContentViewEntity extends ViewEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private AffiliateContentEntity affiliateContent;


    public AffiliateContentViewEntity(Date startDate, Date endDate, CustomerEntity customer) {
        super(startDate, endDate, customer);
    }

    public AffiliateContentViewEntity() {
    }
          
    public AffiliateContentEntity getAffiliateContent() {
        return affiliateContent;
    }

    public void setAffiliateContent(AffiliateContentEntity affiliateContent) {
        this.affiliateContent = affiliateContent;
    }
    
    
}
