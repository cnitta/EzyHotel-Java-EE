/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author berni
 */
@Entity
public class FeedbackEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    private String email;
    private List<Integer> ratings;
    
    @OneToOne
    private RoomBookingEntity roomBooking;
    
    private boolean isCompleted;
    

    public FeedbackEntity(String email, List<Integer> ratings, boolean isCompleted) {
        this.email = email;
        this.ratings = ratings;
        this.isCompleted = isCompleted;
    }

    public FeedbackEntity() {}
    
    
    
    public Long getFeedbackId() {
        return feedbackId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }
    
    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public RoomBookingEntity getRoomBooking() {
        return roomBooking;
    }

    public void setRoomBookingEntity(RoomBookingEntity roomBooking) {
        this.roomBooking = roomBooking;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.feedbackId);
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
        final FeedbackEntity other = (FeedbackEntity) obj;
        if (!Objects.equals(this.feedbackId, other.feedbackId)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "CustomerEntity{" + "feedbackId=" + feedbackId + '}';
    }   


}
