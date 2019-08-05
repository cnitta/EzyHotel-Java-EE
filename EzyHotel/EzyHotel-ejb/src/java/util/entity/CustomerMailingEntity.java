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
import java.util.List;
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
public class CustomerMailingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerMailingId;
    @Temporal(TemporalType.DATE)    
    private Date mailingDate;
    private String title;
    private String message;
    private List<PictureEntity> advPhoto;
    @ManyToOne
    private SalesStaffEntity salesStaff;

    public CustomerMailingEntity(Date mailingDate, String title, String message, List<PictureEntity> advPhoto) {
        this.mailingDate = mailingDate;
        this.title = title;
        this.message = message;
        this.advPhoto = advPhoto;
    }

    public CustomerMailingEntity() {}

    public Long getCustomerMailingId() {
        return customerMailingId;
    }

    public void setCustomerMailingId(Long customerMailingId) {
        this.customerMailingId = customerMailingId;
    }

    public Date getDate() {
        return mailingDate;
    }

    public void setDate(Date mailingDate) {
        this.mailingDate = mailingDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PictureEntity> getAdvPhoto() {
        return advPhoto;
    }

    public void setAdvPhoto(List<PictureEntity> advPhoto) {
        this.advPhoto = advPhoto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.customerMailingId);
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
        final CustomerMailingEntity other = (CustomerMailingEntity) obj;
        if (!Objects.equals(this.customerMailingId, other.customerMailingId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomerMailingEntity{" + "customerMailingId=" + customerMailingId + '}';
    }
}
