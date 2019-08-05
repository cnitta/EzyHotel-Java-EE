/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author vincentyeo
 */
@Entity
public class MerchandisePurchaseOrderEntity extends PurchaseOrderEntity implements Serializable {
    @OneToMany
    private List<MerchandiseOrderDetailEntity> merchandiseOrderDetails;

    public List<MerchandiseOrderDetailEntity> getMerchandiseOrderDetails() {
        return merchandiseOrderDetails;
    }

    public void setMerchandiseOrderDetails(List<MerchandiseOrderDetailEntity> merchandiseOrderDetails) {
        this.merchandiseOrderDetails = merchandiseOrderDetails;
    }
}
