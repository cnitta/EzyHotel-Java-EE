/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
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
public class ListMenuItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listMenuItemId;

    @OneToOne
    private MenuItemEntity menuItem;
    
    private Integer quantity;
    
    private Double subtotal;   

    public ListMenuItemEntity(MenuItemEntity menuItem, Double subtotal, Integer quantity) {
        this.menuItem = menuItem;
        this.subtotal = subtotal;
        this.quantity = quantity;
    }

    public ListMenuItemEntity() {}

    public Long getListMenuItemId() {
        return listMenuItemId;
    }

    public void setListMenuItemId(Long listMenuItemId) {
        this.listMenuItemId = listMenuItemId;
    }

    public MenuItemEntity getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemEntity menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.listMenuItemId);
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
        final ListMenuItemEntity other = (ListMenuItemEntity) obj;
        if (!Objects.equals(this.listMenuItemId, other.listMenuItemId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ListMenuItemEntity{" + "listMenuItem=" + listMenuItemId + '}';
    }
    
    
}
