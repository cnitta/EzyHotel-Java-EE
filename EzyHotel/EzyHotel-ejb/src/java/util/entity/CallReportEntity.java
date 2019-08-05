/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class CallReportEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long callReportId;
    
    private String callFrom;
    private String city;
    @Temporal(TemporalType.DATE)
    private Date callDate;
    private String telephoneNum;
    private String regarding;
    private String personContacted;
    private String title;
    private String remarks;
    private String followup;
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public CallReportEntity(String callFrom, String city, Date callDate, String telephoneNum, String regarding, String personContacted, String title, String remarks, String followup) {
        this.callFrom = callFrom;
        this.city = city;
        this.callDate = callDate;
        this.telephoneNum = telephoneNum;
        this.regarding = regarding;
        this.personContacted = personContacted;
        this.title = title;
        this.remarks = remarks;
        this.followup = followup;
    }

    public CallReportEntity() {}
    
    public Long getCallReportId() {
        return callReportId;
    }
    
    public void setCallReportId(Long callReportId) {
        this.callReportId = callReportId;
    }

    public String getFrom() {
        return callFrom;
    }

    public void setFrom(String callFrom) {
        this.callFrom = callFrom;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getTelephoneNum() {
        return telephoneNum;
    }

    public void setTelephoneNum(String telephoneNum) {
        this.telephoneNum = telephoneNum;
    }

    public String getRegarding() {
        return regarding;
    }

    public void setRegarding(String regarding) {
        this.regarding = regarding;
    }

    public String getPersonContacted() {
        return personContacted;
    }

    public void setPersonContacted(String personContacted) {
        this.personContacted = personContacted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFollowup() {
        return followup;
    }

    public void setFollowup(String followup) {
        this.followup = followup;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.callReportId);
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
        final CallReportEntity other = (CallReportEntity) obj;
        if (!Objects.equals(this.callReportId, other.callReportId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CallReportEntity{" + "callReportId=" + callReportId + '}';
    }
    
}
