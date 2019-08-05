/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author vincentyeo
 */
@Entity
public class ExternalMaintenanceCompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long externalMaintenanceCompanyId;
    private String companyName;
    private String address;
    private String pocName;
    private String contactNum;
    private String email;
    private String businessEntityNumber;
    

    public Long getExternalMaintenanceCompanyId() {
        return externalMaintenanceCompanyId;
    }

    public void setExternalMaintenanceCompanyId(Long externalMaintenanceCompanyId) {
        this.externalMaintenanceCompanyId = externalMaintenanceCompanyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPocName() {
        return pocName;
    }

    public void setPocName(String pocName) {
        this.pocName = pocName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessEntityNumber() {
        return businessEntityNumber;
    }

    public void setBusinessEntityNumber(String businessEntityNumber) {
        this.businessEntityNumber = businessEntityNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (externalMaintenanceCompanyId != null ? externalMaintenanceCompanyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the externalMaintenanceCompanyId fields are not set
        if (!(object instanceof ExternalMaintenanceCompanyEntity)) {
            return false;
        }
        ExternalMaintenanceCompanyEntity other = (ExternalMaintenanceCompanyEntity) object;
        if ((this.externalMaintenanceCompanyId == null && other.externalMaintenanceCompanyId != null) || (this.externalMaintenanceCompanyId != null && !this.externalMaintenanceCompanyId.equals(other.externalMaintenanceCompanyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.ExternalMaintenanceCompanyEntity[ id=" + externalMaintenanceCompanyId + " ]";
    }
    
}
