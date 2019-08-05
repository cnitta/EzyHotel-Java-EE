/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import util.enumeration.MenuItemCategoryEnum;

/**
 *
 * @author berni
 */
@Entity
public class MenuItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;
    private String menuItemName;
    private String description;
    private Double unitPrice; 
    @Enumerated(EnumType.STRING)
    private MenuItemCategoryEnum category;
        
    @OneToOne
    private PictureEntity picture;

    public MenuItemEntity(String menuItemName, String description, Double unitPrice, MenuItemCategoryEnum category) {
        this.menuItemName = menuItemName;
        this.description = description;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public MenuItemEntity() {}

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public MenuItemCategoryEnum getCategory() {
        return category;
    }

    public void setCategory(MenuItemCategoryEnum category) {
        this.category = category;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.menuItemId);
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
        final MenuItemEntity other = (MenuItemEntity) obj;
        if (!Objects.equals(this.menuItemId, other.menuItemId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MenuItemEntity{" + "menuItemId=" + menuItemId + '}';
    }
      
}
