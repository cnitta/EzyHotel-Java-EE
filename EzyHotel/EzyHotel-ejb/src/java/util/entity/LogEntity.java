/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.enumeration.LogStatusEnum;
import util.enumeration.SystemCategoryEnum;

/**
 *
 * @author vincentyeo
 */
@Entity
public class LogEntity implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logiId;
    
    private String message;
    private Timestamp dateTimestamp;
    
    @Enumerated(EnumType.STRING)
    private SystemCategoryEnum systemCategory;
    
    @Enumerated(EnumType.STRING)
    private LogStatusEnum logStatus;
    
    
    public LogEntity(String message, Timestamp dateTimestamp, SystemCategoryEnum systemCategory, LogStatusEnum logStatus) {
        this.message = message;
        this.dateTimestamp = dateTimestamp;
        this.systemCategory = systemCategory;
        this.logStatus = logStatus;
    }

    public LogEntity() {}

    public Long getLogiId() {
        return logiId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateTimestamp() {
        return dateTimestamp;
    }

    public void setDateTimestamp(Timestamp dateTimestamp) {
        this.dateTimestamp = dateTimestamp;
    }

    public SystemCategoryEnum getSystemCategory() {
        return systemCategory;
    }

    public void setSystemCategory(SystemCategoryEnum systemCategory) {
        this.systemCategory = systemCategory;
    }

    public LogStatusEnum getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(LogStatusEnum logStatus) {
        this.logStatus = logStatus;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.logiId);
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
        final LogEntity other = (LogEntity) obj;
        if (!Objects.equals(this.logiId, other.logiId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LogEntity{" + "logiId=" + logiId + '}';
    }

}
