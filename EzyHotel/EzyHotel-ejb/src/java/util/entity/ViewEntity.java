/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vincentyeo
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ViewEntity implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private long viewDuration;
    
    @ManyToOne
    private CustomerEntity customer;

    public ViewEntity(Date startDate, Date endDate, CustomerEntity customer) {
        this.startDate = startDate;
        this.endDate = endDate;
        setViewDuration(startDate,endDate);
        this.customer = customer;
    }
   
    public ViewEntity() {
    }

    public Long getViewId() {
        return viewId;
    }

    public void setViewId(Long viewId) {
        this.viewId = viewId;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        setViewDuration(startDate, this.endDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        setViewDuration(this.startDate, endDate);
    }

    public long getViewDuration() {
        return viewDuration;
    }

    public void setViewDuration(Date startDate, Date endDate) {
         this.viewDuration = endDate.getTime() - startDate.getTime();
    }
    
    public String printViewDuration()
    {
         long diffSeconds = this.viewDuration / 1000 % 60;
         long diffMinutes = this.viewDuration / (60 * 1000) % 60;
         long diffHours = this.viewDuration / (60 * 60 * 1000) % 24;
         long diffDays = this.viewDuration / (24 * 60 * 60 * 1000);
         return diffDays + " Days " + diffHours + " Hours " + diffMinutes + " Minutes " + diffSeconds + " Seconds";
    }

    public CustomerEntity getCustoemr() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
 
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.viewId);
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
        final ViewEntity other = (ViewEntity) obj;
        if (!Objects.equals(this.viewId, other.viewId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViewEntity{" + "viewId=" + viewId + '}';
    }
  
}

