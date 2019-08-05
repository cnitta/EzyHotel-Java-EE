/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author berni
 */
@Entity
public class HousekeepingRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long housekeepingRecordId;
    
    @Temporal(TemporalType.DATE)     
    private Date recordDate = new Date();
    private Timestamp actualCleaningTime;
    private int inspectorRating;
    private String inspectorComments;
    private Timestamp inspectionTime;
    private String housekeepingStatus;
    private String inspectorName;
    private String shift;
    private List<String> recentActivity;
    
    @ManyToOne
    private StaffEntity housekeepingStaff;

    @OneToMany
    private List<RoomEntity> rooms;
    
    @OneToOne
    private FacilityEntity facility;
    
    public HousekeepingRecordEntity(StaffEntity housekeepingStaff, List<RoomEntity> rooms, String inspectorName, String shift) {
        this.housekeepingStaff = housekeepingStaff;
        this.rooms = rooms;
        this.inspectorName = inspectorName;
        this.shift = shift;
    }
    
    public HousekeepingRecordEntity(StaffEntity housekeepingStaff, FacilityEntity facility, String inspectorName, String shift) {
        this.housekeepingStaff = housekeepingStaff;
        this.facility = facility;
        this.inspectorName = inspectorName;
        this.shift = shift;
    }
    

    public HousekeepingRecordEntity() {
    }

    public Long getHousekeepingRecordId() {
        return housekeepingRecordId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Timestamp getActualCleaningTime() {
        return actualCleaningTime;
    }

    public void setActualCleaningTime(Timestamp actualCleaningTime) {
        this.actualCleaningTime = actualCleaningTime;
    }

    public int getInspectorRating() {
        return inspectorRating;
    }

    public void setInspectorRating(int inspectorRating) {
        this.inspectorRating = inspectorRating;
    }

    public String getInspectorComments() {
        return inspectorComments;
    }

    public void setInspectorComments(String inspectorComments) {
        this.inspectorComments = inspectorComments;
    }

    public Timestamp getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Timestamp inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getHousekeepingStatus() {
        return housekeepingStatus;
    }

    public void setHousekeepingStatus(String housekeepingStatus) {
        this.housekeepingStatus = housekeepingStatus;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public StaffEntity getHousekeepingStaff() {
        return housekeepingStaff;
    }

    public void setHousekeepingStaff(StaffEntity housekeepingStaff) {
        this.housekeepingStaff = housekeepingStaff;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.housekeepingRecordId);
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
        final HousekeepingRecordEntity other = (HousekeepingRecordEntity) obj;
        if (!Objects.equals(this.housekeepingRecordId, other.housekeepingRecordId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HousekeepingRecordEntity{" + "housekeepingRecordId=" + housekeepingRecordId + '}';
    }

    /**
     * @return the rooms
     */
    public List<RoomEntity> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return the facility
     */
    public FacilityEntity getFacility() {
        return facility;
    }

    /**
     * @param facility the facility to set
     */
    public void setFacility(FacilityEntity facility) {
        this.facility = facility;
    }

    /**
     * @return the shift
     */
    public String getShift() {
        return shift;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     * @return the recentActivity
     */
    public List<String> getRecentActivity() {
        return recentActivity;
    }

    /**
     * @param recentActivity the recentActivity to set
     */
    public void setRecentActivity(List<String> recentActivity) {
        this.recentActivity = recentActivity;
    }

    
    
}

