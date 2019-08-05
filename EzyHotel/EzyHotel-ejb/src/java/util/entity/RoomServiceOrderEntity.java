/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.RoomServiceOrderStatusEnum;

/**
 *
 * @author berni
 */
@Entity
public class RoomServiceOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomServiceOrderId;
            
    private Double totalPrice;
    
    @Enumerated(EnumType.STRING)
    private RoomServiceOrderStatusEnum orderStatus;
    
    private String comments;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDatePlaced;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDateCompleteOrCancelled;    
    
    @ManyToOne
    private RoomEntity RoomEntity;
    
    @OneToMany
    private List<ListMenuItemEntity> listMenuItems;

    public RoomServiceOrderEntity() {
    }

    public RoomServiceOrderEntity(Double totalPrice, RoomServiceOrderStatusEnum orderStatus, String comments, RoomEntity RoomEntity, List<ListMenuItemEntity> listMenuItems) {
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.comments = comments;
        this.RoomEntity = RoomEntity;
        this.listMenuItems = listMenuItems;
    }

    public Long getRoomServiceOrderId() {
        return roomServiceOrderId;
    }

    public void setRoomServiceOrderId(Long roomServiceOrderId) {
        this.roomServiceOrderId = roomServiceOrderId;
    }
    

    public List<ListMenuItemEntity> getListMenuItems() {
        return listMenuItems;
    }

    public void setListMenuItems(List<ListMenuItemEntity> listMenuItems) {
        this.listMenuItems = listMenuItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RoomServiceOrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(RoomServiceOrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getOrderDatePlaced() {
        return orderDatePlaced;
    }

    public void setOrderDatePlaced(Date orderDatePlaced) {
        this.orderDatePlaced = orderDatePlaced;
    }

    public Date getOrderDateCompleteOrCancelled() {
        return orderDateCompleteOrCancelled;
    }

    public void setOrderDateCompleteOrCancelled(Date orderDateCompleteOrCancelled) {
        this.orderDateCompleteOrCancelled = orderDateCompleteOrCancelled;
    }

    public RoomEntity getRoomEntity() {
        return RoomEntity;
    }

    public void setRoomEntity(RoomEntity RoomEntity) {
        this.RoomEntity = RoomEntity;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.roomServiceOrderId);
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
        final RoomServiceOrderEntity other = (RoomServiceOrderEntity) obj;
        if (!Objects.equals(this.roomServiceOrderId, other.roomServiceOrderId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoomServiceOrderEntity{" + "roomServiceOrderId=" + roomServiceOrderId + '}';
    }

 
}
