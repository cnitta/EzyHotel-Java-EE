/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
public class LostFoundRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lostFoundId;
    private Long bookingId;
    private String description;
    private List<PictureEntity> photos;
    
    @ManyToOne
    private HousekeepingStaffEntity housekeepingStaff;


    public LostFoundRecordEntity(Long bookingId, String description, List<PictureEntity> photos, HousekeepingStaffEntity housekeepingStaff) {
        this.bookingId = bookingId;
        this.description = description;
        this.photos = photos;
        this.housekeepingStaff = housekeepingStaff;
    }

    public LostFoundRecordEntity() {
        photos = new ArrayList<>();
    }

    public Long getLostFoundId() {
        return lostFoundId;
    }

    public void setLostFoundId(Long lostFoundId) {
        this.lostFoundId = lostFoundId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PictureEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PictureEntity> photos) {
        this.photos = photos;
    }

    public HousekeepingStaffEntity getHousekeepingStaff() {
        return housekeepingStaff;
    }

    public void setHousekeepingStaff(HousekeepingStaffEntity housekeepingStaff) {
        this.housekeepingStaff = housekeepingStaff;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.lostFoundId);
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
        final LostFoundRecordEntity other = (LostFoundRecordEntity) obj;
        if (!Objects.equals(this.lostFoundId, other.lostFoundId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LostFoundRecordEntity{" + "lostFoundId=" + lostFoundId + '}';
    }
    
    
    
}
