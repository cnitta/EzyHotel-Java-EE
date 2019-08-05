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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import util.enumeration.MerchandiseOrderStatusEnum;

/**
 *
 * @author berni
 */
@Entity
public class MerchandiseOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchandiseOrderEntityId;
    
    @OneToMany
    private List<MerchandiseEntity> merchandises;
    
    private Integer quantityRedeemed;
    @Enumerated(EnumType.STRING)
    private MerchandiseOrderStatusEnum status;
    
    @ManyToOne
    private RoomEntity room;

    public MerchandiseOrderEntity(List<MerchandiseEntity> merchandises, Integer quantityRedeemed, MerchandiseOrderStatusEnum status, RoomEntity room) {
        this.merchandises = merchandises;
        this.quantityRedeemed = quantityRedeemed;
        this.status = status;
        this.room = room;
    }

    public MerchandiseOrderEntity() {}
    
    public Long getMerchandiseOrderEntityId() {
        return merchandiseOrderEntityId;
    }

    public void setMerchandiseOrderEntityId(Long merchandiseOrderEntityId) {
        this.merchandiseOrderEntityId = merchandiseOrderEntityId;
    }

    public List<MerchandiseEntity> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(List<MerchandiseEntity> merchandises) {
        this.merchandises = merchandises;
    }

    public Integer getQuantityRedeemed() {
        return quantityRedeemed;
    }

    public void setQuantityRedeemed(Integer quantityRedeemed) {
        this.quantityRedeemed = quantityRedeemed;
    }

    public MerchandiseOrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MerchandiseOrderStatusEnum status) {
        this.status = status;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.merchandiseOrderEntityId);
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
        final MerchandiseOrderEntity other = (MerchandiseOrderEntity) obj;
        if (!Objects.equals(this.merchandiseOrderEntityId, other.merchandiseOrderEntityId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MerchandiseOrderEntity{" + "merchandiseOrderEntityId=" + merchandiseOrderEntityId + '}';
    }
    
}
