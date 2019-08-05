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
import util.entity.ListMenuItemEntity;
import util.exception.ListMenuItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class ListMenuItemSession implements ListMenuItemSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
    @Override
    public ListMenuItemEntity createListMenuItem(ListMenuItemEntity listMenuItem) {
        em.persist(listMenuItem);
        em.flush();
        em.refresh(listMenuItem);
        return listMenuItem;
        
    }

    @Override
    public ListMenuItemEntity retrieveListMenuItemById(Long id) throws ListMenuItemNotFoundException {
        ListMenuItemEntity listMenuItem = em.find(ListMenuItemEntity.class, id);
        if (listMenuItem != null) {
            return listMenuItem;
        } else {
            throw new ListMenuItemNotFoundException("ListMenuItem ID " + id + " does not exist!");
        }         
    }

    @Override
    public List<ListMenuItemEntity> retrieveListMenuItemByListMenuItemAttributes(ListMenuItemEntity listMenuItem) {
        Query query = null;
        List<ListMenuItemEntity> listMenuItems;
        
        if(listMenuItem.getMenuItem() != null)
        {
            query = em.createQuery("SELECT m FROM ListMenuItemEntity m WHERE m.menuItem = :inMenuItem");
            query.setParameter("inMenuItem", listMenuItem.getMenuItem());
        }
        else if(listMenuItem.getQuantity() != null)
        {
            query = em.createQuery("SELECT m FROM ListMenuItemEntity m WHERE m.quantity = :inQuantity");  
            query.setParameter("inQuantity", listMenuItem.getQuantity());
        }   
        else if(listMenuItem.getSubtotal()!= null)
        {
            query = em.createQuery("SELECT m FROM ListMenuItemEntity m WHERE m.subtotal = :inSubtotal");  
            query.setParameter("inSubtotal", listMenuItem.getSubtotal());
        }         
        else
        {
            return new ArrayList<>();
        }
        
        listMenuItems = query.getResultList();
        
        if(listMenuItems.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return listMenuItems;          
    }

    @Override
    public List<ListMenuItemEntity> retrieveAllListMenuItems() {
        Query query = em.createQuery("SELECT m FROM ListMenuItemEntity m");
        
        List<ListMenuItemEntity> listMenuItems = query.getResultList();
        
        if(listMenuItems.size() == 0)
        {
            return new ArrayList<ListMenuItemEntity>();
        }
        
        return query.getResultList();          
    }

    @Override
    public void updateListMenuItem(ListMenuItemEntity listMenuItem) {
        em.merge(listMenuItem);           
    }

    @Override
    public void deleteListMenuItem(Long id) throws ListMenuItemNotFoundException {
        ListMenuItemEntity listMenuItem = retrieveListMenuItemById(id);
        em.remove(listMenuItem);            
    }

}
