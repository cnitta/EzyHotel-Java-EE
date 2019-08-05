/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class MerchandiseOrderDetailEntity extends OrderDetailEntity implements Serializable {
 
    @OneToOne
    private MerchandiseOrderItemEntity merchandiseOrderItem;

    public MerchandiseOrderItemEntity getMerchandiseOrderItem() {
        return merchandiseOrderItem;
    }

    public void setMerchandiseOrderItem(MerchandiseOrderItemEntity merchandiseOrderItem) {
        this.merchandiseOrderItem = merchandiseOrderItem;
    }
}
