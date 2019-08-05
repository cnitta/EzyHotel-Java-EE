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
import util.entity.SupplierEntity;
import util.exception.SupplierNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class SupplierSession implements SupplierSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public SupplierEntity createSupplier(SupplierEntity supplier) {
        em.persist(supplier);
        em.flush();
        em.refresh(supplier);
        return supplier;
    }

    @Override
    public SupplierEntity retrieveSupplierById(Long id) throws SupplierNotFoundException {
        SupplierEntity supplier = em.find(SupplierEntity.class, id);
        if (supplier != null) {
            return supplier;
        } else {
            throw new SupplierNotFoundException("Supplier ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<SupplierEntity> retrieveSupplierBySupplierAttributes(SupplierEntity supplier) {
     
        Query query = null;
        List<SupplierEntity> suppliers;
        
        if(supplier.getName() != null)
        {
            query = em.createQuery("SELECT s FROM SupplierEntity s WHERE s.name LIKE :inName");
            query.setParameter("inName", "%" + supplier.getName() + "%");
        }
        else if(supplier.getAddress() != null)
        {
            query = em.createQuery("SELECT s FROM SupplierEntity s WHERE s.address LIKE :inAddress");  
            query.setParameter("inAddress", "%" + supplier.getAddress() + "%");
        }
        else if(supplier.getEmail()!= null)
        {
            query = em.createQuery("SELECT s FROM SupplierEntity s WHERE s.email LIKE :inEmail");  
            query.setParameter("inEmail", "%" + supplier.getEmail() + "%");
        } 
        else if(supplier.getPhoneNum() != null)
        {
            query = em.createQuery("SELECT s FROM SupplierEntity s WHERE s.phoneNum LIKE :inPhoneNum");  
            query.setParameter("inPhoneNum", "%" + supplier.getPhoneNum() + "%");
        }   
        else if(supplier.getCompany()!= null)
        {
            query = em.createQuery("SELECT s FROM SupplierEntity s WHERE s.company LIKE :inCompany");  
            query.setParameter("inCompany", "%" + supplier.getCompany() + "%");
        }             
        else
        {
            return new ArrayList<>();
        }
        
        suppliers = query.getResultList();
        
        if(suppliers.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return suppliers;        
    }

    @Override
    public List<SupplierEntity> retrieveAllSuppliers() {

        Query query = em.createQuery("SELECT a FROM SupplierEntity a");
        
        List<SupplierEntity> suppliers = query.getResultList();

        
        if(suppliers.size() == 0)
        {
            return new ArrayList<SupplierEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateSupplier(SupplierEntity supplier) {
        em.merge(supplier);        
    }

    @Override
    public void deleteSupplier(Long id) throws SupplierNotFoundException {
        SupplierEntity supplier = retrieveSupplierById(id);
        em.remove(supplier);        
    } 
}
