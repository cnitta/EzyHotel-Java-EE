/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.MerchandiseOrderDetailEntity;
import util.exception.MerchandiseOrderDetailNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MerchandiseOrderDetailSessionLocal {
    public MerchandiseOrderDetailEntity createMerchandiseOrderDetail(MerchandiseOrderDetailEntity merchandiseOrderDetail);
    
    public MerchandiseOrderDetailEntity retrieveMerchandiseOrderDetailById(Long id) throws MerchandiseOrderDetailNotFoundException;
    
    public List<MerchandiseOrderDetailEntity> retrieveMerchandiseOrderDetailByMerchandiseOrderDetailAttributes (MerchandiseOrderDetailEntity merchandiseOrderDetail);    
    
    public List<MerchandiseOrderDetailEntity> retrieveAllMerchandiseOrderDetails();

    public void updateMerchandiseOrderDetail(MerchandiseOrderDetailEntity merchandiseOrderDetail);
    
    public void deleteMerchandiseOrderDetail(Long id) throws MerchandiseOrderDetailNotFoundException;     
}
