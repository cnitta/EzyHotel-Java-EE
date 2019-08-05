/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import util.enumeration.RosterTypeEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class WorkRosterEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workRosterId;
    private String workRosterName;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDateTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDateTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDateTime;
    @Enumerated(EnumType.STRING)
    private RosterTypeEnum rosterStatus;   
    
    @OneToMany
    private List<StaffEntity> staffs;

    public WorkRosterEntity(Long workRosterId, String workRosterName, Date startDateTime, Date endDateTime, Date createDateTime, RosterTypeEnum rosterStatus) {
        this.workRosterId = workRosterId;
        this.workRosterName = workRosterName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDateTime = createDateTime;
        this.rosterStatus = rosterStatus;
        this.staffs = new ArrayList<>();
    }

    public WorkRosterEntity(String workRosterName, Date startDateTime, Date endDateTime, Date createDateTime, 
             RosterTypeEnum rosterStatus,  List<StaffEntity> staffEntities) {
        this.workRosterName = workRosterName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDateTime = createDateTime;
        this.rosterStatus = rosterStatus;
        this.staffs = staffEntities;
    }

    public WorkRosterEntity() {
        staffs = new ArrayList<>();
    }

    public Long getWorkRosterId() {
        return workRosterId;
    }

    public String getWorkRosterName() {
        return workRosterName;
    }

    public void setWorkRosterName(String workRosterName) {
        this.workRosterName = workRosterName;
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

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public RosterTypeEnum getRosterStatus() {
        return rosterStatus;
    }

    public void setRosterStatus(RosterTypeEnum rosterStatus) {
        this.rosterStatus = rosterStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.workRosterId);
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
        final WorkRosterEntity other = (WorkRosterEntity) obj;
        if (!Objects.equals(this.workRosterId, other.workRosterId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WorkRosterEntity{" + "workRosterId=" + workRosterId + '}';
    }

    /**
     * @param workRosterId the workRosterId to set
     */
    public void setWorkRosterId(Long workRosterId) {
        this.workRosterId = workRosterId;
    }

    /**
     * @return the staffs
     */
    public List<StaffEntity> getStaffs() {
        return staffs;
    }

    /**
     * @param staffs the staffs to set
     */
    public void setStaffs(List<StaffEntity> staffs) {
        this.staffs = staffs;
    }
   
}
