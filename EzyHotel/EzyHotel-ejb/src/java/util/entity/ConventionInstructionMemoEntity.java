/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
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
public class ConventionInstructionMemoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conventionInstructionMemoId;

    private String groupTitle;
    private String vipNames;
    private String billingInstructions;
    private String houseCommitments;
    private String roomPickup;
    private String conventionProgram;
    private String instructions;
    private String otherDiscussion;
    private String arrivalOfTour;
    private String departureOfTour;
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public ConventionInstructionMemoEntity(String groupTitle, String vipNames, String billingInstructions, String houseCommitments, String roomPickup, String conventionProgram, String instructions, String otherDiscussion, String arrivalOfTour, String departureOfTour) {
        this.groupTitle = groupTitle;
        this.vipNames = vipNames;
        this.billingInstructions = billingInstructions;
        this.houseCommitments = houseCommitments;
        this.roomPickup = roomPickup;
        this.conventionProgram = conventionProgram;
        this.instructions = instructions;
        this.otherDiscussion = otherDiscussion;
        this.arrivalOfTour = arrivalOfTour;
        this.departureOfTour = departureOfTour;
    }

    public ConventionInstructionMemoEntity() {}

    public Long getConventionInstructionMemoId() {
        return conventionInstructionMemoId;
    }

    public void setConventionInstructionMemoId(Long conventionInstructionMemoId) {
        this.conventionInstructionMemoId = conventionInstructionMemoId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getVipNames() {
        return vipNames;
    }

    public void setVipNames(String vipNames) {
        this.vipNames = vipNames;
    }

    public String getBillingInstructions() {
        return billingInstructions;
    }

    public void setBillingInstructions(String billingInstructions) {
        this.billingInstructions = billingInstructions;
    }

    public String getHouseCommitments() {
        return houseCommitments;
    }

    public void setHouseCommitments(String houseCommitments) {
        this.houseCommitments = houseCommitments;
    }

    public String getRoomPickup() {
        return roomPickup;
    }

    public void setRoomPickup(String roomPickup) {
        this.roomPickup = roomPickup;
    }

    public String getConventionProgram() {
        return conventionProgram;
    }

    public void setConventionProgram(String conventionProgram) {
        this.conventionProgram = conventionProgram;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getOtherDiscussion() {
        return otherDiscussion;
    }

    public void setOtherDiscussion(String otherDiscussion) {
        this.otherDiscussion = otherDiscussion;
    }

    public String getArrivalOfTour() {
        return arrivalOfTour;
    }

    public void setArrivalOfTour(String arrivalOfTour) {
        this.arrivalOfTour = arrivalOfTour;
    }

    public String getDepartureOfTour() {
        return departureOfTour;
    }

    public void setDepartureOfTour(String departureOfTour) {
        this.departureOfTour = departureOfTour;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.conventionInstructionMemoId);
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
        final ConventionInstructionMemoEntity other = (ConventionInstructionMemoEntity) obj;
        if (!Objects.equals(this.conventionInstructionMemoId, other.conventionInstructionMemoId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConventionInstructionMemoEntity{" + "conventionInstructionMemoId=" + conventionInstructionMemoId + '}';
    }
}
