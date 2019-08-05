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
import util.entity.AffiliateContentEntity;
import util.exception.AffiliateContentNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class AffiliateContentSession implements AffiliateContentSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public AffiliateContentEntity createAffiliateContent(AffiliateContentEntity affiliateContent) {
        em.persist(affiliateContent);
        em.flush();
        em.refresh(affiliateContent);
        return affiliateContent;
    }

    @Override
    public AffiliateContentEntity retrieveAffiliateContentById(Long id) throws AffiliateContentNotFoundException {
        AffiliateContentEntity affiliateContent = em.find(AffiliateContentEntity.class, id);
        if (affiliateContent != null) {
            return affiliateContent;
        } else {
            throw new AffiliateContentNotFoundException("AffiliateContent ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<AffiliateContentEntity> retrieveAffiliateContentByAffiliateContentAttributes(AffiliateContentEntity affiliateContent) {
     
        Query query = null;
        List<AffiliateContentEntity> affiliateContents;
        
        if(affiliateContent.getTitle()!= null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentEntity a WHERE a.title LIKE :inTitle");
            query.setParameter("inTitle", "%" + affiliateContent.getTitle() + "%");
        }
        else if(affiliateContent.getPromoCode()!= null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentEntity a WHERE a.promoCode LIKE :inPromoCode");
            query.setParameter("inPromoCode", "%" + affiliateContent.getPromoCode() + "%");
        }  
        else if(affiliateContent.getPromoDescription() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentEntity a WHERE a.promoDescription LIKE :inPromoDesription");
            query.setParameter("inPromoDesription", "%" + affiliateContent.getPromoDescription() + "%");
        } 
        else if(affiliateContent.getCategory() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentEntity a WHERE a.category = :inCategory");
            query.setParameter("inCategory", affiliateContent.getCategory());
        }         
        else if(affiliateContent.getPromotionStartDate() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentEntity a WHERE a.promotionStartDate = :inStartDate");
            query.setParameter("inStartDate", affiliateContent.getPromotionStartDate());
        } 
        else if(affiliateContent.getPromotionEndDate() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentEntity a WHERE a.promotionEndDate = :inEndDate");
            query.setParameter("inEndDate", affiliateContent.getPromotionEndDate());
        }         
        else
        {
            return new ArrayList<>();
        }
        
        affiliateContents = query.getResultList();
        
        if(affiliateContents.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return affiliateContents;        
    }

    @Override
    public List<AffiliateContentEntity> retrieveAllAffiliateContents() {

        Query query = em.createQuery("SELECT a FROM AffiliateContentEntity a");
        
        List<AffiliateContentEntity> affiliateContents = query.getResultList();
        
        if(affiliateContents.isEmpty())
        {
            return new ArrayList<AffiliateContentEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateAffiliateContent(AffiliateContentEntity affiliateContent) {
        em.merge(affiliateContent);        
    }

    @Override
    public void deleteAffiliateContent(Long id) throws AffiliateContentNotFoundException {
        AffiliateContentEntity affiliateContent = retrieveAffiliateContentById(id);
        em.remove(affiliateContent);        
    }
}
