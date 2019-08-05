/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class AffiliateContentTagEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    private String tagKeyword;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @ManyToOne
    private AffiliateContentEntity affiliateContentEntity;

    public AffiliateContentTagEntity() {
    }

    public AffiliateContentTagEntity(String tagKeyword, Date dateCreated, AffiliateContentEntity affiliateContentEntity) {
        this.tagKeyword = tagKeyword;
        this.dateCreated = dateCreated;
        this.affiliateContentEntity = affiliateContentEntity;
    }
        
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagKeyword() {
        return tagKeyword;
    }

    public void setTagKeyword(String tagKeyword) {
        this.tagKeyword = tagKeyword;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AffiliateContentEntity getAffiliateContentEntity() {
        return affiliateContentEntity;
    }

    public void setAffiliateContentEntity(AffiliateContentEntity affiliateContentEntity) {
        this.affiliateContentEntity = affiliateContentEntity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tagId != null ? tagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the tagId fields are not set
        if (!(object instanceof AffiliateContentTagEntity)) {
            return false;
        }
        AffiliateContentTagEntity other = (AffiliateContentTagEntity) object;
        if ((this.tagId == null && other.tagId != null) || (this.tagId != null && !this.tagId.equals(other.tagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.AffiliateContentTag[ id=" + tagId + " ]";
    }
    
}
