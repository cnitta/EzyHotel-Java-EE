/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.MerchandisePurchaseOrderEntity;
import util.exception.MerchandisePurchaseOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MerchandisePurchaseOrderSessionLocal {
    public MerchandisePurchaseOrderEntity createMerchandisePurchaseOrder(MerchandisePurchaseOrderEntity merchandisePurchaseOrder);
    
    public MerchandisePurchaseOrderEntity retrieveMerchandisePurchaseOrderById(Long id) throws MerchandisePurchaseOrderNotFoundException;
    
    public List<MerchandisePurchaseOrderEntity> retrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes (MerchandisePurchaseOrderEntity merchandisePurchaseOrder);    
    
    public List<MerchandisePurchaseOrderEntity> retrieveAllMerchandisePurchaseOrders();

    public void updateMerchandisePurchaseOrder(MerchandisePurchaseOrderEntity merchandisePurchaseOrder);
    
    public void deleteMerchandisePurchaseOrder(Long id) throws MerchandisePurchaseOrderNotFoundException;     
}
