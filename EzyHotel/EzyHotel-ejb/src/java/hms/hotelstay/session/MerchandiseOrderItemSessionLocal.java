/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.MerchandiseOrderItemEntity;
import util.exception.MerchandiseOrderItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MerchandiseOrderItemSessionLocal {
    public MerchandiseOrderItemEntity createMerchandiseOrderItem(MerchandiseOrderItemEntity merchandiseOrderItem);
    
    public MerchandiseOrderItemEntity retrieveMerchandiseOrderItemById(Long id) throws MerchandiseOrderItemNotFoundException;
    
    public List<MerchandiseOrderItemEntity> retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes (MerchandiseOrderItemEntity merchandiseOrderItem);    

//    public List<MerchandiseOrderItemEntity> retrieveMerchandiseOrderItemBySupplierAttributes (SupplierEntity suppplier);    
    
    public List<MerchandiseOrderItemEntity> retrieveAllMerchandiseOrderItems();

    public void updateMerchandiseOrderItem(MerchandiseOrderItemEntity merchandiseOrderItem);
    
    public void deleteMerchandiseOrderItem(Long id) throws MerchandiseOrderItemNotFoundException;     
}
