/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class ProgramEntryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programEntryId;
    @Temporal(TemporalType.DATE)     
    private Date programDate;
    private String functionType;
    private Time startTime;
    private Time endTime;
    private int estNumPerson;
    private String groupName;
    private String contctName;
    private String phoneNum;
    @Temporal(TemporalType.DATE)     
    private Date dateBooked;
    private String personInitial;
    private String remarks;
    private String status;
    
    
    @OneToOne(mappedBy = "programEntry")
    private ReserveFacilityEntity reserveFacility;

    public ProgramEntryEntity(Date programDate, String functionType, Time startTime, Time endTime, int estNumPerson, String groupName, String contctName, String phoneNum, Date dateBooked, String personInitial, String remarks, String status) {
        this.programDate = programDate;
        this.functionType = functionType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.estNumPerson = estNumPerson;
        this.groupName = groupName;
        this.contctName = contctName;
        this.phoneNum = phoneNum;
        this.dateBooked = dateBooked;
        this.personInitial = personInitial;
        this.remarks = remarks;
        this.status = status;
    }

    public ProgramEntryEntity() {}

    public Long getProgramEntryId() {
        return programEntryId;
    }

    public void setProgramEntryId(Long programEntryId) {
        this.programEntryId = programEntryId;
    }
    public Date getProgramDate() {
        return programDate;
    }

    public void setProgramDate(Date programDate) {
        this.programDate = programDate;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getEstNumPerson() {
        return estNumPerson;
    }

    public void setEstNumPerson(int estNumPerson) {
        this.estNumPerson = estNumPerson;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContctName() {
        return contctName;
    }

    public void setContctName(String contctName) {
        this.contctName = contctName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(Date dateBooked) {
        this.dateBooked = dateBooked;
    }

    public String getPersonInitial() {
        return personInitial;
    }

    public void setPersonInitial(String personInitial) {
        this.personInitial = personInitial;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.programEntryId);
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
        final ProgramEntryEntity other = (ProgramEntryEntity) obj;
        if (!Objects.equals(this.programEntryId, other.programEntryId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProgramEntryEntity{" + "programEntryId=" + programEntryId + '}';
    }
}
