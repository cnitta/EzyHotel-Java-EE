/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.ARTypeEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class AugmentedRealityContentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long augmentedRealityContentId;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ARTypeEnum aRTypeEnum;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public AugmentedRealityContentEntity() {
    }

    public AugmentedRealityContentEntity(String name, String description, ARTypeEnum aRTypeEnum, Date dateCreated) {
        this.name = name;
        this.description = description;
        this.aRTypeEnum = aRTypeEnum;
        this.dateCreated = dateCreated;
    }
        
    public Long getAugmentedRealityContentId() {
        return augmentedRealityContentId;
    }

    public void setAugmentedRealityContentId(Long augmentedRealityContentId) {
        this.augmentedRealityContentId = augmentedRealityContentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ARTypeEnum getaRTypeEnum() {
        return aRTypeEnum;
    }

    public void setaRTypeEnum(ARTypeEnum aRTypeEnum) {
        this.aRTypeEnum = aRTypeEnum;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (augmentedRealityContentId != null ? augmentedRealityContentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the augmentedRealityContentId fields are not set
        if (!(object instanceof AugmentedRealityContentEntity)) {
            return false;
        }
        AugmentedRealityContentEntity other = (AugmentedRealityContentEntity) object;
        if ((this.augmentedRealityContentId == null && other.augmentedRealityContentId != null) || (this.augmentedRealityContentId != null && !this.augmentedRealityContentId.equals(other.augmentedRealityContentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.AugmentedRealityContentEntity[ id=" + augmentedRealityContentId + " ]";
    }
    
}
