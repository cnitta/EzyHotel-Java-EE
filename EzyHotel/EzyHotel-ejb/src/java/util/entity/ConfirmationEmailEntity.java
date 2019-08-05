/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author berni
 */
@Entity
public class ConfirmationEmailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long confirmationEmailId;
    
    private String associateExecutiveEmailAddress;
    private String dateInQuestion;
    @Temporal(TemporalType.DATE)     
    private Date alternativeDate;
    private String daysOfTheWeek;
    private int roomsReserved;
    private int suitesReserved;
    private int mRoomsReserved;
    private String arrPatterns;
    private String reviewProgram;
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public ConfirmationEmailEntity(String associateExecutiveEmailAddress, String dateInQuestion, Date alternativeDate, String daysOfTheWeek, int roomsReserved, int suitesReserved, int mRoomsReserved, String arrPatterns, String reviewProgram) {
        this.associateExecutiveEmailAddress = associateExecutiveEmailAddress;
        this.dateInQuestion = dateInQuestion;
        this.alternativeDate = alternativeDate;
        this.daysOfTheWeek = daysOfTheWeek;
        this.roomsReserved = roomsReserved;
        this.suitesReserved = suitesReserved;
        this.mRoomsReserved = mRoomsReserved;
        this.arrPatterns = arrPatterns;
        this.reviewProgram = reviewProgram;
    }

    public ConfirmationEmailEntity() {}

    public Long getConfirmationEmailId() {
        return confirmationEmailId;
    }
    
    public void setConfirmationEmailId(Long confirmationEmailId) {
        this.confirmationEmailId = confirmationEmailId;
    }

    public String getAssociateExecutiveEmailAddress() {
        return associateExecutiveEmailAddress;
    }

    public void setAssociateEecutiveEmailAddress(String associateExecutiveEmailAddress) {
        this.associateExecutiveEmailAddress = associateExecutiveEmailAddress;
    }

    public String getDateInQuestion() {
        return dateInQuestion;
    }

    public void setDateInQuestion(String dateInQuestion) {
        this.dateInQuestion = dateInQuestion;
    }

    public Date getAlternativeDate() {
        return alternativeDate;
    }

    public void setAlternativeDate(Date alternativeDate) {
        this.alternativeDate = alternativeDate;
    }

    public String getDaysOfTheWeek() {
        return daysOfTheWeek;
    }

    public void setDaysOfTheWeek(String daysOfTheWeek) {
        this.daysOfTheWeek = daysOfTheWeek;
    }

    public int getRoomsReserved() {
        return roomsReserved;
    }

    public void setRoomsReserved(int roomsReserved) {
        this.roomsReserved = roomsReserved;
    }

    public int getSuitesReserved() {
        return suitesReserved;
    }

    public void setSuitesReserved(int suitesReserved) {
        this.suitesReserved = suitesReserved;
    }

    public int getmRoomsReserved() {
        return mRoomsReserved;
    }

    public void setmRoomsReserved(int mRoomsReserved) {
        this.mRoomsReserved = mRoomsReserved;
    }

    public String getArrPatterns() {
        return arrPatterns;
    }

    public void setArrPatterns(String arrPatterns) {
        this.arrPatterns = arrPatterns;
    }

    public String getReviewProgram() {
        return reviewProgram;
    }

    public void setReviewProgram(String reviewProgram) {
        this.reviewProgram = reviewProgram;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.confirmationEmailId);
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
        final ConfirmationEmailEntity other = (ConfirmationEmailEntity) obj;
        if (!Objects.equals(this.confirmationEmailId, other.confirmationEmailId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConfirmationEmailEntity{" + "confirmationEmailId=" + confirmationEmailId + '}';
    }

    
}
