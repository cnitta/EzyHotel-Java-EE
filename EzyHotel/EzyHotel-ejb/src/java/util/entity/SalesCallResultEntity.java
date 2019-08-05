/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author berni
 */
@Entity
public class SalesCallResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesCallResultId;
    
    @Temporal(TemporalType.DATE)     
    private Date callDate;
    private String personContacted;
    private String phoneNum;
    private String details;
    private String outcome;
    private String remarks;
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public SalesCallResultEntity(Date callDate, String personContacted, String phoneNum, String details, String outcome, String remarks) {
        this.callDate = callDate;
        this.personContacted = personContacted;
        this.phoneNum = phoneNum;
        this.details = details;
        this.outcome = outcome;
        this.remarks = remarks;
    }

    public SalesCallResultEntity() {}
    
    public Long getSalesCallResultId() {
        return salesCallResultId;
    }
    
    public void setSalesCallResultId(Long salesCallResultId) {
        this.salesCallResultId = salesCallResultId;
        this.callDate = callDate;
    }    

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getPersonContacted() {
        return personContacted;
    }

    public void setPersonContacted(String personContacted) {
        this.personContacted = personContacted;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.salesCallResultId);
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
        final SalesCallResultEntity other = (SalesCallResultEntity) obj;
        if (!Objects.equals(this.salesCallResultId, other.salesCallResultId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SalesCallResultEntity{" + "salesCallResultId=" + salesCallResultId + '}';
    }
}

