/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.MerchandiseEntity;
import util.exception.MerchandiseNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface MerchandiseSessionLocal {

    public MerchandiseEntity createMerchandise(MerchandiseEntity merchandise);
    
    public MerchandiseEntity retrieveMerchandiseById(Long id) throws MerchandiseNotFoundException;
    
    public List<MerchandiseEntity> retrieveMerchandiseByMerchandiseAttributes (MerchandiseEntity merchandise);    
    
    public List<MerchandiseEntity> retrieveAllMerchandises();

    public void updateMerchandise(MerchandiseEntity merchandise);
    
    public void deleteMerchandise(Long id) throws MerchandiseNotFoundException;   
    
    //public void redeemMerchandise()
}
