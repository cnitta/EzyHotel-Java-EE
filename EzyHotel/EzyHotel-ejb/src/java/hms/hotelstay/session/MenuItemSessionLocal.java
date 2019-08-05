/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.MenuItemEntity;
import util.exception.MenuItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MenuItemSessionLocal {
    public MenuItemEntity createMenuItem(MenuItemEntity menuItem);
    
    public MenuItemEntity retrieveMenuItemById(Long id) throws MenuItemNotFoundException;
    
    public List<MenuItemEntity> retrieveMenuItemByMenuItemAttributes (MenuItemEntity menuItem);    
    
    public List<MenuItemEntity> retrieveAllMenuItems();

    public void updateMenuItem(MenuItemEntity menuItem);
    
    public void deleteMenuItem(Long id) throws MenuItemNotFoundException;    
}
