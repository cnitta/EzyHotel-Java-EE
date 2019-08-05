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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
public class ReserveFacilityEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveFacilityId;

    @Temporal(TemporalType.DATE)     
    private Date reservedDate;
    private String functionType;
    private String personContacted;
    private String phoneNum;
    @Temporal(TemporalType.DATE)     
    private Date dateBooked;
    private String personInitial;
    private String status;
    
    @ManyToOne
    private FacilityEntity facility;

    @OneToOne
    private ProgramEntryEntity programEntry;

    public ReserveFacilityEntity(Long reserveFacilityId, Date reservedDate, 
            String functionType, String personContacted, String phoneNum, 
            Date dateBooked, String personInitial, String status, ProgramEntryEntity programEntry) {
        this.reserveFacilityId = reserveFacilityId;
        this.reservedDate = reservedDate;
        this.functionType = functionType;
        this.personContacted = personContacted;
        this.phoneNum = phoneNum;
        this.dateBooked = dateBooked;
        this.personInitial = personInitial;
        this.status = status;
        this.programEntry = programEntry;
    }

    public ReserveFacilityEntity() {
    }

    public Long getReserveFacilityId() {
        return reserveFacilityId;
    }

    public void setReserveFacilityId(Long reserveFacilityId) {
        this.reserveFacilityId = reserveFacilityId;
    }
        
    public Date getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FacilityEntity getFacility() {
        return facility;
    }

    public void setFacility(FacilityEntity facility) {
        this.facility = facility;
    }

    public ProgramEntryEntity getProgramEntry() {
        return programEntry;
    }

    public void setProgramEntry(ProgramEntryEntity programEntry) {
        this.programEntry = programEntry;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.reserveFacilityId);
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
        final ReserveFacilityEntity other = (ReserveFacilityEntity) obj;
        if (!Objects.equals(this.reserveFacilityId, other.reserveFacilityId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReserveFacilityEntity{" + "reserveFacilityId=" + reserveFacilityId + '}';
    }

    
    
}

