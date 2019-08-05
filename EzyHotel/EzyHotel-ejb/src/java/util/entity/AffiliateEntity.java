/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.enumeration.AffiliateStatusEnum;
import util.enumeration.AffiliateTypeEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class AffiliateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long affiliateId;
    private String affiliateName;
    private String organizationEntityNumber;
    private String businessAddress;
    private String representativeName;
    private String email;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private AffiliateTypeEnum affiliateType;
//    private Boolean isPremium;
    @Enumerated(EnumType.STRING)
    private AffiliateStatusEnum affiliateStatus;          

    public AffiliateEntity() {
        
    }

    public AffiliateEntity(String affiliateName, String organizationEntityNumber, String businessAddress, String representativeName, String email, String contactNumber, AffiliateTypeEnum affiliateType) {
        this.affiliateName = affiliateName;
        this.organizationEntityNumber = organizationEntityNumber;
        this.businessAddress = businessAddress;
        this.representativeName = representativeName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.affiliateType = affiliateType;
        this.affiliateStatus = AffiliateStatusEnum.PENDING;
    }
    
//    public AffiliateEntity(String affiliateName, String organizationEntityNumber, String businessAddress, String representativeName, String email, String contactNumber, Boolean isPremium, AffiliateTypeEnum affiliateType) {
//        this.affiliateName = affiliateName;
//        this.organizationEntityNumber = organizationEntityNumber;
//        this.businessAddress = businessAddress;
//        this.representativeName = representativeName;
//        this.email = email;
//        this.contactNumber = contactNumber;
//        this.isPremium = isPremium;
//        this.affiliateType = affiliateType;
//        this.affiliateStatus = AffiliateStatusEnum.PENDING;
//    }
    
    public Long getAffiliateId() {
        return affiliateId;
    }
    
    public void setAffiliateId(Long affiliateId) {
        this.affiliateId = affiliateId;
    }    

    public String getAffiliateName() {
        return affiliateName;
    }

    public void setAffiliateName(String affiliateName) {
        this.affiliateName = affiliateName;
    }

    public String getOrganizationEntityNumber() {
        return organizationEntityNumber;
    }

    public void setOrganizationEntityNumber(String organizationEntityNumber) {
        this.organizationEntityNumber = organizationEntityNumber;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public AffiliateTypeEnum getAffiliateType() {
        return affiliateType;
    }

    public void setAffiliateType(AffiliateTypeEnum affiliateType) {
        this.affiliateType = affiliateType;
    }

//    public Boolean getIsPremium() {
//        return isPremium;
//    }
//
//    public void setIsPremium(Boolean isPremium) {
//        this.isPremium = isPremium;
//    }

    public AffiliateStatusEnum getAffiliateStatus() {
        return affiliateStatus;
    }

    public void setAffiliateStatus(AffiliateStatusEnum affiliateStatus) {
        this.affiliateStatus = affiliateStatus;
    }
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (affiliateId != null ? affiliateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AffiliateEntity)) {
            return false;
        }
        AffiliateEntity other = (AffiliateEntity) object;
        if ((this.affiliateId == null && other.affiliateId != null) || (this.affiliateId != null && !this.affiliateId.equals(other.affiliateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hms.hotelstay.afadvmgt.entity.AffiliateEntity[ affiliateId=" + affiliateId + " ]";
    }


}
