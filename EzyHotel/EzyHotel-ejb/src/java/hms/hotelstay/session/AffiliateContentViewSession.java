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
import util.entity.AffiliateContentViewEntity;
import util.exception.AffiliateContentViewNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class AffiliateContentViewSession implements AffiliateContentViewSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public AffiliateContentViewEntity createAffiliateContentView(AffiliateContentViewEntity affiliateContentView) {
        em.persist(affiliateContentView);
        em.flush();
        em.refresh(affiliateContentView);
        return affiliateContentView;
    }

    @Override
    public AffiliateContentViewEntity retrieveAffiliateContentViewById(Long id) throws AffiliateContentViewNotFoundException {
        AffiliateContentViewEntity affiliateContentView = em.find(AffiliateContentViewEntity.class, id);
        if (affiliateContentView != null) {
            return affiliateContentView;
        } else {
            throw new AffiliateContentViewNotFoundException("AffiliateContentView ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<AffiliateContentViewEntity> retrieveAffiliateContentViewByAffiliateContentViewAttributes(AffiliateContentViewEntity affiliateContentView) {
     
        Query query = null;
        List<AffiliateContentViewEntity> affiliateContentViews;
        
        if(affiliateContentView.getStartDate() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentViewEntity a WHERE a.startDate = :inStartDate");
            query.setParameter("inStartDate", affiliateContentView.getStartDate());
        }  
        else if(affiliateContentView.getEndDate() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentViewEntity a WHERE a.endDate = :inEndDate");
            query.setParameter("inEndDate", affiliateContentView.getEndDate());
        } 
//        else if(affiliateContentView.getViewDuration() != null)
//        {
//            query = em.createQuery("SELECT a FROM AffiliateContentViewEntity a WHERE a.viewDuration = :inDuration");
//            query.setParameter("inDuration", affiliateContentView.getViewDuration());
//        }         
        else
        {
            return new ArrayList<>();
        }
        
        affiliateContentViews = query.getResultList();
        
        if(affiliateContentViews.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return affiliateContentViews;        
    }

    @Override
    public List<AffiliateContentViewEntity> retrieveAllAffiliateContentViews() {

        Query query = em.createQuery("SELECT a FROM AffiliateContentViewEntity a");
        
        List<AffiliateContentViewEntity> affiliateContentViews = query.getResultList();
        
        if(affiliateContentViews.isEmpty())
        {
            return new ArrayList<AffiliateContentViewEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateAffiliateContentView(AffiliateContentViewEntity affiliateContentView) {
        em.merge(affiliateContentView);        
    }

    @Override
    public void deleteAffiliateContentView(Long id) throws AffiliateContentViewNotFoundException {
        AffiliateContentViewEntity affiliateContentView = retrieveAffiliateContentViewById(id);
        em.remove(affiliateContentView);        
    }
}
