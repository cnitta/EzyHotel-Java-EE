/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.MenuItemEntity;
import util.exception.MenuItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MenuItemSession implements MenuItemSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
    @Override
    public MenuItemEntity createMenuItem(MenuItemEntity menuItem) {
        em.persist(menuItem);
        em.flush();
        em.refresh(menuItem);
        return menuItem;
        
    }

    @Override
    public MenuItemEntity retrieveMenuItemById(Long id) throws MenuItemNotFoundException {
        MenuItemEntity menuItem = em.find(MenuItemEntity.class, id);
        if (menuItem != null) {
            return menuItem;
        } else {
            throw new MenuItemNotFoundException("MenuItem ID " + id + " does not exist!");
        }         
    }

    @Override
    public List<MenuItemEntity> retrieveMenuItemByMenuItemAttributes(MenuItemEntity menuItem) {
        Query query = null;
        List<MenuItemEntity> menuItems;
        
        if(menuItem.getMenuItemName()!= null)
        {
            query = em.createQuery("SELECT m FROM MenuItemEntity m WHERE m.menuItemName LIKE :inName");
            query.setParameter("inName", "%" + menuItem.getMenuItemName() + "%");
        }
        else if(menuItem.getDescription() != null)
        {
            query = em.createQuery("SELECT m FROM MenuItemEntity m WHERE m.description LIKE :inDescription");  
            query.setParameter("inDescription", "%" + menuItem.getDescription() + "%");
        }
        else if(menuItem.getUnitPrice()!= null)
        {
            query = em.createQuery("SELECT m FROM MenuItemEntity m WHERE m.unitPrice = :inUnitPrice");  
            query.setParameter("inUnitPrice", menuItem.getUnitPrice());
        } 
        else if(menuItem.getCategory() != null)
        {
            query = em.createQuery("SELECT m FROM MenuItemEntity m WHERE m.category = :inCategory");  
            query.setParameter("inCategory", menuItem.getCategory());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        menuItems = query.getResultList();
        
        if(menuItems.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return menuItems;          
    }

    @Override
    public List<MenuItemEntity> retrieveAllMenuItems() {
        Query query = em.createQuery("SELECT m FROM MenuItemEntity m");
        
        List<MenuItemEntity> menuItems = query.getResultList();
        
        if(menuItems.size() == 0)
        {
            return new ArrayList<MenuItemEntity>();
        }
        
        return query.getResultList();          
    }

    @Override
    public void updateMenuItem(MenuItemEntity menuItem) {
        em.merge(menuItem);           
    }

    @Override
    public void deleteMenuItem(Long id) throws MenuItemNotFoundException {
        MenuItemEntity menuItem = retrieveMenuItemById(id);
        em.remove(menuItem);            
    }
   
}
