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
import util.entity.ExternalMaintenanceCompanyEntity;
import util.exception.ExternalMaintenanceCompanyNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class ExternalMaintenanceCompanySession implements ExternalMaintenanceCompanySessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public ExternalMaintenanceCompanyEntity createExternalMaintenanceCompany(ExternalMaintenanceCompanyEntity externalMaintenanceCompany) {
        em.persist(externalMaintenanceCompany);
        em.flush();
        em.refresh(externalMaintenanceCompany);
        return externalMaintenanceCompany;
    }

    @Override
    public ExternalMaintenanceCompanyEntity retrieveExternalMaintenanceCompanyById(Long id) throws ExternalMaintenanceCompanyNotFoundException {
        ExternalMaintenanceCompanyEntity externalMaintenanceCompany = em.find(ExternalMaintenanceCompanyEntity.class, id);
        if (externalMaintenanceCompany != null) {
            return externalMaintenanceCompany;
        } else {
            throw new ExternalMaintenanceCompanyNotFoundException("ExternalMaintenanceCompany ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<ExternalMaintenanceCompanyEntity> retrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes(ExternalMaintenanceCompanyEntity externalMaintenanceCompany) {
     
        Query query = null;
        List<ExternalMaintenanceCompanyEntity> externalMaintenanceCompanys;
        
        if(externalMaintenanceCompany.getCompanyName() != null)
        {
            query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e WHERE e.companyName LIKE :inName");
            query.setParameter("inName", "%" + externalMaintenanceCompany.getCompanyName() + "%");
        }
        else if(externalMaintenanceCompany.getBusinessEntityNumber() != null)
        {
            query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e WHERE e.businessEntityNumber LIKE :inBizNumber");  
            query.setParameter("inBizNumber", "%" + externalMaintenanceCompany.getBusinessEntityNumber() + "%");
        } 
        else if(externalMaintenanceCompany.getAddress() != null)
        {
            query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e WHERE e.address LIKE :inAddress");  
            query.setParameter("inAddress", "%" + externalMaintenanceCompany.getAddress() + "%");
        }
        else if(externalMaintenanceCompany.getContactNum() != null)
        {
            query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e WHERE e.contactNum LIKE :inContactNum");  
            query.setParameter("inContactNum", "%" + externalMaintenanceCompany.getContactNum() + "%");
        } 
        else if(externalMaintenanceCompany.getEmail() != null)
        {
            query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e WHERE e.email LIKE :inEmail");  
            query.setParameter("inEmail", "%" + externalMaintenanceCompany.getEmail() + "%");
        }  
        else if(externalMaintenanceCompany.getPocName() != null)
        {
            query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e WHERE e.pocName LIKE :inPocName");  
            query.setParameter("inPocName", "%" + externalMaintenanceCompany.getPocName() + "%");
        }
        else
        {
            return new ArrayList<>();
        }
        
        externalMaintenanceCompanys = query.getResultList();
        
        if(externalMaintenanceCompanys.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return externalMaintenanceCompanys;        
    }

    @Override
    public List<ExternalMaintenanceCompanyEntity> retrieveAllExternalMaintenanceCompanys() {

        Query query = em.createQuery("SELECT e FROM ExternalMaintenanceCompanyEntity e");
        
        List<ExternalMaintenanceCompanyEntity> externalMaintenanceCompanys = query.getResultList();

        
        if(externalMaintenanceCompanys.size() == 0)
        {
            return new ArrayList<ExternalMaintenanceCompanyEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateExternalMaintenanceCompany(ExternalMaintenanceCompanyEntity externalMaintenanceCompany) {
        em.merge(externalMaintenanceCompany);        
    }

    @Override
    public void deleteExternalMaintenanceCompany(Long id) throws ExternalMaintenanceCompanyNotFoundException {
        ExternalMaintenanceCompanyEntity externalMaintenanceCompany = retrieveExternalMaintenanceCompanyById(id);
        em.remove(externalMaintenanceCompany);        
    }
}
