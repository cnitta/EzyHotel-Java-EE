/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Objects;
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
public class PublicRelationProgramEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicRelationProgramId;
    
    private String type;
    private String objective;
    private String strategy;
    private String targetMarket;
    private String expectedResult;
    private String attribute;
    private String timetable;
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public PublicRelationProgramEntity(String type, String objective, String strategy, String targetMarket, String expectedResult, String attribute, String timetable) {
        this.type = type;
        this.objective = objective;
        this.strategy = strategy;
        this.targetMarket = targetMarket;
        this.expectedResult = expectedResult;
        this.attribute = attribute;
        this.timetable = timetable;
    }

    public PublicRelationProgramEntity() {}

    public Long getPublicRelationProgramId() {
        return publicRelationProgramId;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getTargetMarket() {
        return targetMarket;
    }

    public void setTargetMarket(String targetMarket) {
        this.targetMarket = targetMarket;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.publicRelationProgramId);
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
        final PublicRelationProgramEntity other = (PublicRelationProgramEntity) obj;
        if (!Objects.equals(this.publicRelationProgramId, other.publicRelationProgramId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PublicRelationProgramEntity{" + "publicRelationProgramId=" + publicRelationProgramId + '}';
    }
    
    
}
