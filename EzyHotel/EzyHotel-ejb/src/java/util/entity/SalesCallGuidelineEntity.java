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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author berni
 */
@Entity
public class SalesCallGuidelineEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesCallGuidelineId;
    @Temporal(TemporalType.DATE)     
    private Date createDate;
    private String title;
    
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public SalesCallGuidelineEntity(Long salesCallGuidelineId, Date createDate, String title) {
        this.salesCallGuidelineId = salesCallGuidelineId;
        this.createDate = createDate;
        this.title = title;
    }

    public SalesCallGuidelineEntity() {}

    public Long getSalesCallGuidelineId() {
        return salesCallGuidelineId;
    }

    public void setSalesCallGuidelineId(Long salesCallGuidelineId) {
        this.salesCallGuidelineId = salesCallGuidelineId;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.salesCallGuidelineId);
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
        final SalesCallGuidelineEntity other = (SalesCallGuidelineEntity) obj;
        if (!Objects.equals(this.salesCallGuidelineId, other.salesCallGuidelineId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SalesCallGuidelineEntity{" + "salesCallGuidelineId=" + salesCallGuidelineId + '}';
    }
}
