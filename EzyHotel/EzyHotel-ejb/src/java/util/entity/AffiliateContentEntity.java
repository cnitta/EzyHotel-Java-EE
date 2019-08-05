/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.AffiliateContentCategoryEnum;
import util.enumeration.AffiliateContentStateEnum;
import util.enumeration.AffiliateContentStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class AffiliateContentEntity implements Serializable {
private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long affiliateContentId;
    
    private String title;
    @Enumerated(EnumType.STRING)
    private AffiliateContentCategoryEnum category;
    private String promoDescription;
//    private Integer currentRanking;

    private String promoCode;
    @Temporal(TemporalType.TIMESTAMP)     
    private Date promotionStartDate;
    @Temporal(TemporalType.TIMESTAMP)     
    private Date promotionEndDate;
    
//    private Hashtable <Date,Integer> rankingHistory;

    @Enumerated(EnumType.STRING)
    private AffiliateContentStatusEnum affiliateContentStatus;
    @Enumerated(EnumType.STRING)
    private AffiliateContentStateEnum affiliateContentState;
    
    @OneToOne
    private PictureEntity picture;
    
    @ManyToOne
    private AffiliateEntity affiliate;
    
    public AffiliateContentEntity() {
    }
    
    public AffiliateContentEntity(String title, AffiliateContentCategoryEnum category, String promoDescription, String promoCode, Date promotionStartDate, Date promotionEndDate, AffiliateContentStatusEnum affiliateContentStatus, AffiliateContentStateEnum affiliateContentState, PictureEntity picture) {
        this.title = title;
        this.category = category;
        this.promoDescription = promoDescription;
    
        this.promoCode = promoCode;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
        this.affiliateContentStatus = affiliateContentStatus;
        this.affiliateContentState = affiliateContentState;
        this.picture = picture;
    }

//    public AffiliateContentEntity(String title, AffiliateContentCategoryEnum category, String promoDescription, Integer currentRanking, String promoCode, Date promotionStartDate, Date promotionEndDate, AffiliateContentStatusEnum affiliateContentStatus, AffiliateContentStateEnum affiliateContentState, PictureEntity picture) {
//        this.title = title;
//        this.category = category;
//        this.promoDescription = promoDescription;
//        this.currentRanking = currentRanking;
//        this.promoCode = promoCode;
//        this.promotionStartDate = promotionStartDate;
//        this.promotionEndDate = promotionEndDate;
//        this.affiliateContentStatus = affiliateContentStatus;
//        this.affiliateContentState = affiliateContentState;
//        this.picture = picture;
//        this.rankingHistory = new Hashtable<Date,Integer>();
//    }

//    public AffiliateContentEntity(String title, AffiliateContentCategoryEnum category, String promoDescription, Integer currentRanking, String promoCode, Date promotionStartDate, Date promotionEndDate, Hashtable<Date, Integer> rankingHistory, AffiliateContentStatusEnum affiliateContentStatus, AffiliateContentStateEnum affiliateContentState, PictureEntity picture, AffiliateEntity affiliate) {
//        this.title = title;
//        this.category = category;
//        this.promoDescription = promoDescription;
//        this.currentRanking = currentRanking;
//        this.promoCode = promoCode;
//        this.promotionStartDate = promotionStartDate;
//        this.promotionEndDate = promotionEndDate;
//        this.rankingHistory = rankingHistory;
//        this.affiliateContentStatus = affiliateContentStatus;
//        this.affiliateContentState = affiliateContentState;
//        this.picture = picture;
//        this.affiliate = affiliate;
//    }
       

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AffiliateContentCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(AffiliateContentCategoryEnum category) {
        this.category = category;
    }

    public String getPromoDescription() {
        return promoDescription;
    }

    public void setPromoDescription(String promoDescription) {
        this.promoDescription = promoDescription;
    }

//    public Integer getCurrentRanking() {
//        return currentRanking;
//    }
//
//    public void setCurrentRanking(Integer currentRanking) {
//
//        this.currentRanking = currentRanking;
//    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Date getPromotionStartDate() {
        return promotionStartDate;
    }

    public void setPromotionStartDate(Date promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public Date getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(Date promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

//    public Hashtable <Date,Integer> getRankingHistory() {
//        return rankingHistory;
//    }
//
//    public void setRankingHistory(Hashtable <Date,Integer> rankingHistory) {
//        this.rankingHistory = rankingHistory;
//    }

    public AffiliateContentStatusEnum getAffiliateContentStatus() {
        return affiliateContentStatus;
    }

    public void setAffiliateContentStatus(AffiliateContentStatusEnum affiliateContentStatus) {
        this.affiliateContentStatus = affiliateContentStatus;
    }

    public AffiliateContentStateEnum getAffiliateContentState() {
        return affiliateContentState;
    }

    public void setAffiliateContentState(AffiliateContentStateEnum affiliateContentState) {
        this.affiliateContentState = affiliateContentState;
    }


    public Long getAffiliateContentId() {
        return affiliateContentId;
    }

    public void setAffiliateContentId(Long affiliateContentId) {
        this.affiliateContentId = affiliateContentId;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        Integer hash = 7;
        hash = 31 * hash + Objects.hashCode(this.affiliateContentId);
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
        final AffiliateContentEntity other = (AffiliateContentEntity) obj;
        if (!Objects.equals(this.affiliateContentId, other.affiliateContentId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AffiliateContentEntity{" + "affiliateContentId=" + affiliateContentId + '}';
    }

    public AffiliateEntity getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(AffiliateEntity affiliate) {
        this.affiliate = affiliate;
    }
    
}
