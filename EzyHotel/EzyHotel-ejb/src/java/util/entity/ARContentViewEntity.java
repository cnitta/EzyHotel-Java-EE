/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class ARContentViewEntity extends ViewEntity implements Serializable {

    @ManyToOne
    private AugmentedRealityContentEntity augmentedRealityContent;

    public ARContentViewEntity(AugmentedRealityContentEntity augmentedRealityContent, Date startDate, Date endDate, CustomerEntity customer) {
        super(startDate, endDate, customer);
        this.augmentedRealityContent = augmentedRealityContent;
    }

    public ARContentViewEntity() {
    }
    public AugmentedRealityContentEntity getAugmentedRealityContent() {
        return augmentedRealityContent;
    }

    public void setAugmentedRealityContent(AugmentedRealityContentEntity augmentedRealityContent) {
        this.augmentedRealityContent = augmentedRealityContent;
    }
}
