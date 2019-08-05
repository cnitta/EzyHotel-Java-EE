
package util.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */

@Entity
public class HousekeepingSOPEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long housekeepingSOPId;

    private Time estimatedCleaningTime;
    private String instructions;


    @OneToOne
    private FacilityEntity facility;
    
    @OneToOne
    private RoomTypeEntity roomType;
     
    
    public HousekeepingSOPEntity(Time estimatedCleaningTime, String instructions, RoomTypeEntity roomType) {
        this.estimatedCleaningTime = estimatedCleaningTime;
        this.instructions = instructions;
        this.roomType = roomType;
    }

    public HousekeepingSOPEntity() {
        
    }

    public Time getEstimatedCleaningTime() {
        return estimatedCleaningTime;
    }

    public void setEstimatedCleaningTime(Time estimatedCleaningTime) {

        this.estimatedCleaningTime = estimatedCleaningTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public FacilityEntity getFacility() {
        return facility;
    }

    public void setFacility(FacilityEntity facility) {
        this.facility = facility;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.getHousekeepingSopId());
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
        final HousekeepingSOPEntity other = (HousekeepingSOPEntity) obj;
        if (!Objects.equals(this.housekeepingSOPId, other.housekeepingSOPId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HousekeepingSOPEntity{" + "housekeepingSOPId=" + getHousekeepingSopId() + '}';
    }

    /**
     * @return the roomType
     */
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }

    /**
     * @return the housekeepingSopId
     */
    public Long getHousekeepingSopId() {
        return housekeepingSOPId;
    }

    /**
     * @param housekeepingSopId the housekeepingSopId to set
     */
    public void setHousekeepingSopId(Long housekeepingSopId) {
        this.housekeepingSOPId = housekeepingSopId;
    }

}
