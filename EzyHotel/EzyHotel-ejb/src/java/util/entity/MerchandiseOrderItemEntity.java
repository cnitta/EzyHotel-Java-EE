/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class MerchandiseOrderItemEntity extends OrderItemEntity implements Serializable {
    @ManyToOne
    private MerchandiseEntity merchandise;

    public MerchandiseOrderItemEntity(String name, String description, double unitPrice, List<PictureEntity> picture) {
        super(name, description, unitPrice, picture);
    }

    public MerchandiseOrderItemEntity() {
    }
        
    
    public MerchandiseEntity getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(MerchandiseEntity merchandise) {
        this.merchandise = merchandise;
    }
    
    
}
