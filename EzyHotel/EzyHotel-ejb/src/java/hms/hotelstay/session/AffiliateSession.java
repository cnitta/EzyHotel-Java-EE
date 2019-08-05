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
import util.entity.AffiliateEntity;
import util.exception.AffiliateNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class AffiliateSession implements AffiliateSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public AffiliateEntity createAffiliate(AffiliateEntity affiliate) {
        em.persist(affiliate);
        em.flush();
        em.refresh(affiliate);
        return affiliate;
    }

    @Override
    public AffiliateEntity retrieveAffiliateById(Long id) throws AffiliateNotFoundException{
        AffiliateEntity affiliate = em.find(AffiliateEntity.class, id);
        if (affiliate != null) {
            return affiliate;
        } else {
            throw new AffiliateNotFoundException("Affiliate ID " + id + " does not exist!");
        }
    }
    @Override
    public List<AffiliateEntity> retrieveAffiliateByAffiliateAttributes(AffiliateEntity affiliate){
     
        Query query = null;
        List<AffiliateEntity> affiliates;
        
        if(affiliate.getAffiliateName() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.affiliateName LIKE :inName");
            query.setParameter("inName", "%" + affiliate.getAffiliateName() + "%");
        }
        else if(affiliate.getOrganizationEntityNumber() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.organizationEntityNumber LIKE :inOrgEntityNum");  
            query.setParameter("inOrgEntityNum", "%" + affiliate.getOrganizationEntityNumber() + "%");
        }
        else if(affiliate.getBusinessAddress() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.businessAddress LIKE :inBusinessAddress");
            query.setParameter("inBusinessAddress", "%" + affiliate.getBusinessAddress() + "%");
        }
        else if(affiliate.getRepresentativeName() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.representativeName LIKE :inRepresentativeName"); 
            query.setParameter("inRepresentativeName", "%" + affiliate.getRepresentativeName() + "%");
        }   
        else if(affiliate.getEmail() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.email LIKE :inEmail");
            query.setParameter("inEmail", "%" + affiliate.getEmail() + "%");
        }
        else if(affiliate.getContactNumber() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.contactNumber LIKE :inContactNumber");
            query.setParameter("inContactNumber", "%" + affiliate.getContactNumber() + "%");
        }
        else if(affiliate.getAffiliateType() != null)
        {                 
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.affiliateType = :inAffiliateType");    
            query.setParameter("inAffiliateType", affiliate.getAffiliateType());
        } 
//        else if(affiliate.getIsPremium()!= null)
//        {            
//            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.isPremium = :inIsPremium");    
//            query.setParameter("inIsPremium", affiliate.getIsPremium());
//        }  
        else if(affiliate.getAffiliateStatus() != null)
        {            
            query = em.createQuery("SELECT a FROM AffiliateEntity a WHERE a.affiliateStatus = :inAffiliateStatus");    
            query.setParameter("inAffiliateStatus", affiliate.getAffiliateStatus());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        affiliates = query.getResultList();
        
        if(affiliates.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return affiliates;
    }

    @Override
    public List<AffiliateEntity> retrieveAllAffiliates() {
        System.out.println("***AffiliateSession retrieveAllAffiliates Started***");
        Query query = em.createQuery("SELECT a FROM AffiliateEntity a");
        
        List<AffiliateEntity> affiliates = query.getResultList();
        System.out.println("***AffiliateSession affiliates.size() " + affiliates.size() + "***");
        
        if(affiliates.size() == 0)
        {
            return new ArrayList<AffiliateEntity>();
        }
        
        return query.getResultList();
    }

    @Override
    public void updateAffiliate(AffiliateEntity affiliate) {
        em.merge(affiliate);
    }
    
    @Override
    public void deleteAffiliate(Long id) throws AffiliateNotFoundException{
        AffiliateEntity affiliate = retrieveAffiliateById(id);
        em.remove(affiliate);
    }
}
