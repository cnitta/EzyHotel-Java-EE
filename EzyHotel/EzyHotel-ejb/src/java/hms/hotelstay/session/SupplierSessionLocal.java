/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.SupplierEntity;
import util.exception.SupplierNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface SupplierSessionLocal {
    public SupplierEntity createSupplier(SupplierEntity supplier);
    
    public SupplierEntity retrieveSupplierById(Long id) throws SupplierNotFoundException;
    
    public List<SupplierEntity> retrieveSupplierBySupplierAttributes (SupplierEntity supplier);    
    
    public List<SupplierEntity> retrieveAllSuppliers();

    public void updateSupplier(SupplierEntity supplier);
    
    public void deleteSupplier(Long id) throws SupplierNotFoundException;     
}
