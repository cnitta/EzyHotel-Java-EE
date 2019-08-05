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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import util.enumeration.LeaveCategoryEnum;
import util.enumeration.LeaveStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class LeaveEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;
    @Enumerated(EnumType.STRING)
    private LeaveCategoryEnum leaveCategory;
    private String description;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDateTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDateTime;

    @Enumerated(EnumType.STRING)
    private LeaveStatusEnum leaveStatus;

    public LeaveEntity(LeaveCategoryEnum leaveCategory, Date startDateTime, 
            Date endDateTime, String description, LeaveStatusEnum leaveStatus) {
        this.leaveCategory = leaveCategory;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.leaveStatus = leaveStatus;
    }

    public LeaveEntity() {}

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public LeaveCategoryEnum getLeaveCategory() {
        return leaveCategory;
    }

    public void setLeaveCategory(LeaveCategoryEnum leaveCategory) {
        this.leaveCategory = leaveCategory;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LeaveStatusEnum getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatusEnum leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.getLeaveId());
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
        final LeaveEntity other = (LeaveEntity) obj;
        if (!Objects.equals(this.leaveId, other.leaveId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LeaveEntity{" + "leaveId=" + getLeaveId() + '}';
    }
       
}
