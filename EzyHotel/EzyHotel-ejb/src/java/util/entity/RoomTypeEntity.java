/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author berni
 */
@Entity
public class RoomTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    private String roomTypecode;
    private String name;
    private String bedType;
    private String description;
    private Integer numMaxGuest;
    private Integer totalRooms;
    private Double roomSize;
    
    @OneToMany(mappedBy ="roomType")
    private List<RoomEntity> rooms;
    
    @OneToOne
    private RoomAmenityEntity amenity;

    @ManyToOne
    private HotelEntity hotel;
    
    @OneToOne
    private PictureEntity picture;    
   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getRoomTypeId());
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
        final RoomTypeEntity other = (RoomTypeEntity) obj;
        if (!Objects.equals(this.roomTypeId, other.roomTypeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoomTypeEntity{" + "roomTypeId=" + getRoomTypeId() + '}';
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypecode() {
        return roomTypecode;
    }

    public void setRoomTypecode(String roomTypecode) {
        this.roomTypecode = roomTypecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumMaxGuest() {
        return numMaxGuest;
    }

    public void setNumMaxGuest(Integer numMaxGuest) {
        this.numMaxGuest = numMaxGuest;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public double getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(double roomSize) {
        this.roomSize = roomSize;
    }

    /**
     * @return the rooms
     */
    public List<RoomEntity> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public HotelEntity getHotel() {
        return hotel;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    /**
     * @return the amenity
     */
    public RoomAmenityEntity getAmenity() {
        return amenity;
    }

    /**
     * @param amenity the amenity to set
     */
    public void setAmenity(RoomAmenityEntity amenity) {
        this.amenity = amenity;
    }

    public void setRoomSize(Double roomSize) {
        this.roomSize = roomSize;
    }

    
}

