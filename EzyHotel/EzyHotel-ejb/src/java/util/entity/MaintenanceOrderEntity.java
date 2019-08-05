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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.MaintenanceOrderStateEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MaintenanceOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceOrderId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)   
    private MaintenanceOrderStateEnum maintenanceOrderStateEnum;    
    
    @ManyToOne
    private ExternalMaintenanceCompanyEntity externalMaintenanceCompany;
    
    public MaintenanceOrderEntity(Date startDate, Date endDate, MaintenanceOrderStateEnum maintenanceOrderStateEnum, ExternalMaintenanceCompanyEntity externalMaintenanceCompany) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.maintenanceOrderStateEnum = maintenanceOrderStateEnum;
        this.externalMaintenanceCompany = externalMaintenanceCompany;
    }

    public MaintenanceOrderEntity() {
    }
           

    public Long getMaintenanceOrderId() {
        return maintenanceOrderId;
    }

    public void setMaintenanceOrderId(Long maintenanceOrderId) {
        this.maintenanceOrderId = maintenanceOrderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public MaintenanceOrderStateEnum getMaintenanceOrderStateEnum() {
        return maintenanceOrderStateEnum;
    }

    public void setMaintenanceOrderStateEnum(MaintenanceOrderStateEnum maintenanceOrderStateEnum) {
        this.maintenanceOrderStateEnum = maintenanceOrderStateEnum;
    }

    public ExternalMaintenanceCompanyEntity getExternalMaintenanceCompany() {
        return externalMaintenanceCompany;
    }

    public void setExternalMaintenanceCompany(ExternalMaintenanceCompanyEntity externalMaintenanceCompany) {
        this.externalMaintenanceCompany = externalMaintenanceCompany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maintenanceOrderId != null ? maintenanceOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the maintenanceOrderId fields are not set
        if (!(object instanceof MaintenanceOrderEntity)) {
            return false;
        }
        MaintenanceOrderEntity other = (MaintenanceOrderEntity) object;
        if ((this.maintenanceOrderId == null && other.maintenanceOrderId != null) || (this.maintenanceOrderId != null && !this.maintenanceOrderId.equals(other.maintenanceOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.MaintenanceOrderEntity[ id=" + maintenanceOrderId + " ]";
    }
    
}
