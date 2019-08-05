/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import util.enumeration.CourseCategoryEnum;
import util.enumeration.TrainingStatusEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class TrainingRosterEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;
    
    private String courseName;
    private String description;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDateTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDateTime;

    @Enumerated(EnumType.STRING)
    private TrainingStatusEnum trainingStatus;
    @Enumerated(EnumType.STRING)
    private CourseCategoryEnum courseCategory;
    
    @OneToMany
    private List<StaffEntity> staffs;

    public TrainingRosterEntity(Long trainingId, String courseName, String description, Date startDateTime, Date endDateTime, TrainingStatusEnum trainingStatus, CourseCategoryEnum courseCategory, List<StaffEntity> staffs) {
        this.trainingId = trainingId;
        this.courseName = courseName;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.trainingStatus = trainingStatus;
        this.courseCategory = courseCategory;
        this.staffs = staffs;
    }


    public TrainingRosterEntity(String courseName, String description, Date startDateTime, Date endDateTime, TrainingStatusEnum trainingStatus, CourseCategoryEnum courseCategory) {
        this.courseName = courseName;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.trainingStatus = trainingStatus;
        this.courseCategory = courseCategory;
    }

    public TrainingRosterEntity() {
        staffs = new ArrayList<>();
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TrainingStatusEnum getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(TrainingStatusEnum trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public CourseCategoryEnum getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(CourseCategoryEnum courseCategory) {
        this.courseCategory = courseCategory;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.trainingId);
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
        final TrainingRosterEntity other = (TrainingRosterEntity) obj;
        if (!Objects.equals(this.trainingId, other.trainingId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TrainingRosterEntity{" + "trainingId=" + trainingId + '}';
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
