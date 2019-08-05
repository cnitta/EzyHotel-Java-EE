/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author bryantan
 */
@Entity
public class HousekeepingForcastEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date forcastDate;
    private Integer dirtyRooms;
    private Time estimatedCleaningTime;
    private Integer estimatedHousekeepers;
    private Integer superior;
    private Integer deluxe;
    private Integer junior;
    private Integer executive;
    private Integer president;
    
    public HousekeepingForcastEntity() {
        
    }
    
    public HousekeepingForcastEntity(Date forcastDate, Integer dirtyRooms, Time estimatedCleaningTime, Integer estimatedHousekeepers, Integer superior, Integer deluxe, Integer junior, Integer executive, Integer president) {
        this.forcastDate = forcastDate;
        this.dirtyRooms = dirtyRooms;
        this.estimatedCleaningTime = estimatedCleaningTime;
        this.estimatedHousekeepers = estimatedHousekeepers;
        this.superior = superior;
        this.deluxe = deluxe;
        this.junior = junior;
        this.executive = executive;
        this.president = president;
    }
 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HousekeepingForcastEntity)) {
            return false;
        }
        HousekeepingForcastEntity other = (HousekeepingForcastEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.HousekeepingForcastEntity[ id=" + id + " ]";
    }

    /**
     * @return the forcastDate
     */
    public Date getForcastDate() {
        return forcastDate;
    }

    /**
     * @param forcastDate the forcastDate to set
     */
    public void setForcastDate(Date forcastDate) {
        this.forcastDate = forcastDate;
    }

    /**
     * @return the dirtyRooms
     */
    public Integer getDirtyRooms() {
        return dirtyRooms;
    }

    /**
     * @param dirtyRooms the dirtyRooms to set
     */
    public void setDirtyRooms(Integer dirtyRooms) {
        this.dirtyRooms = dirtyRooms;
    }

    /**
     * @return the estimatedCleaningTime
     */
    public Time getEstimatedCleaningTime() {
        return estimatedCleaningTime;
    }

    /**
     * @param estimatedCleaningTime the estimatedCleaningTime to set
     */
    public void setEstimatedCleaningTime(Time estimatedCleaningTime) {
        this.estimatedCleaningTime = estimatedCleaningTime;
    }

    /**
     * @return the estimatedHousekeepers
     */
    public Integer getEstimatedHousekeepers() {
        return estimatedHousekeepers;
    }

    /**
     * @param estimatedHousekeepers the estimatedHousekeepers to set
     */
    public void setEstimatedHousekeepers(Integer estimatedHousekeepers) {
        this.estimatedHousekeepers = estimatedHousekeepers;
    }

    /**
     * @return the superior
     */
    public Integer getSuperior() {
        return superior;
    }

    /**
     * @param superior the superior to set
     */
    public void setSuperior(Integer superior) {
        this.superior = superior;
    }

    /**
     * @return the deluxe
     */
    public Integer getDeluxe() {
        return deluxe;
    }

    /**
     * @param deluxe the deluxe to set
     */
    public void setDeluxe(Integer deluxe) {
        this.deluxe = deluxe;
    }

    /**
     * @return the junior
     */
    public Integer getJunior() {
        return junior;
    }

    /**
     * @param junior the junior to set
     */
    public void setJunior(Integer junior) {
        this.junior = junior;
    }

    /**
     * @return the executive
     */
    public Integer getExecutive() {
        return executive;
    }

    /**
     * @param executive the executive to set
     */
    public void setExecutive(Integer executive) {
        this.executive = executive;
    }

    /**
     * @return the president
     */
    public Integer getPresident() {
        return president;
    }

    /**
     * @param president the president to set
     */
    public void setPresident(Integer president) {
        this.president = president;
    }

   

}
