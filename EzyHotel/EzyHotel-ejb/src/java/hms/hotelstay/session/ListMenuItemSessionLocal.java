/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ListMenuItemEntity;
import util.exception.ListMenuItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface ListMenuItemSessionLocal {
    public ListMenuItemEntity createListMenuItem(ListMenuItemEntity listListMenuItemEntity);
    
    public ListMenuItemEntity retrieveListMenuItemById(Long id) throws ListMenuItemNotFoundException;
    
    public List<ListMenuItemEntity> retrieveListMenuItemByListMenuItemAttributes (ListMenuItemEntity listListMenuItemEntity);    
    
    public List<ListMenuItemEntity> retrieveAllListMenuItems();

    public void updateListMenuItem(ListMenuItemEntity listListMenuItemEntity);
    
    public void deleteListMenuItem(Long id) throws ListMenuItemNotFoundException;     
}
