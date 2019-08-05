/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import hms.common.CommonMethods;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import util.enumeration.AccessCodeTypeEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class AccessCodeEntity implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessCodeId;

    private String codeIdentifier;
    
    private Timestamp expiryDateTime;

    @Enumerated(EnumType.STRING)
    private AccessCodeTypeEnum type;
    
    @OneToOne
    private CustomerEntity customer;
    
    @OneToOne
    private StaffEntity staff;
        
    public AccessCodeEntity(Timestamp expiryDateTime, AccessCodeTypeEnum type) {
        CommonMethods commons = new CommonMethods();
        codeIdentifier = commons.generateRandomAlphanumeric(10);
        this.expiryDateTime = expiryDateTime;
        this.type = type;
    }
    
    public AccessCodeEntity() {
        CommonMethods commons = new CommonMethods();
        codeIdentifier = commons.generateRandomAlphanumeric(10);
    }
    public AccessCodeEntity(Long accessCodeId, String codeIdentifier, Timestamp expiryDateTime, AccessCodeTypeEnum type) {
        this.accessCodeId = accessCodeId;
        this.codeIdentifier = codeIdentifier;
        this.expiryDateTime = expiryDateTime;
        this.type = type;
    }

    public Long getAccessCodeId() {
        return accessCodeId;
    }

    public String getCodeIdentifier() {
        return codeIdentifier;
    }

    public void setCodeIdentifier(String codeIdentifier) {
        this.codeIdentifier = codeIdentifier;
    }

    public Timestamp getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(Timestamp expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public AccessCodeTypeEnum getType() {
        return type;
    }

    public void setType(AccessCodeTypeEnum type) {
        this.type = type;
    }

    
    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
    
    public StaffEntity getStaff() {
        return staff;
    }

    public void setStaff(StaffEntity staff) {
        this.staff = staff;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.accessCodeId);
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
        final AccessCodeEntity other = (AccessCodeEntity) obj;
        if (!Objects.equals(this.accessCodeId, other.accessCodeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccessCodeEntity{" + "accessCodeId=" + accessCodeId + '}';
    }


    
    
}

