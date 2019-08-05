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
import util.entity.ProxyBidEntity;
import util.exception.ProxyBidNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class ProxyBidSession implements ProxyBidSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;    
    
    @Override
    public ProxyBidEntity createProxyBid(ProxyBidEntity proxyBid) {
        em.persist(proxyBid);
        em.flush();
        em.refresh(proxyBid);
        return proxyBid;
    }

    @Override
    public ProxyBidEntity retrieveProxyBidById(Long id) throws ProxyBidNotFoundException {
        
        Query query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.bidId = :inId");
        query.setParameter("inId", id);               
        
        List<ProxyBidEntity> proxyBid = query.getResultList();        
        
//                em.find(ProxyBidEntity.class, id);
        if (!proxyBid.isEmpty()) {
            return (ProxyBidEntity) proxyBid.get(0);
        } else {
            throw new ProxyBidNotFoundException("ProxyBid ID " + id + " does not exist!");
        }          
    }

    @Override
    public List<ProxyBidEntity> retrieveProxyBidByProxyBidAttributes(ProxyBidEntity proxyBid) {
        Query query = null;
        List<ProxyBidEntity> proxyBids;
        
        if(proxyBid.getBidAmount() != 0.00)
        {
            query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.bidAmount = :inBidAmount");
            query.setParameter("inBidAmount", proxyBid.getBidAmount());
        }  
        else if(proxyBid.getBidDateTime() != null)
        {
            query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.bidDateTime = :inBidDateTime");
            query.setParameter("inBidDateTime", proxyBid.getBidDateTime());
        }   
        else if(proxyBid.getStartDateTime()!= null)
        {
            query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.startDateTime = :inBidStartDateTime");
            query.setParameter("inBidStartDateTime", proxyBid.getStartDateTime());
        }   
         else if(proxyBid.getEndDateTime()!= null)
        {
            query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.endDateTime = :inBidEndDateTime");
            query.setParameter("inBidEndDateTime", proxyBid.getEndDateTime());
        } 
        else if(proxyBid.getMaxBidAmount() != null)
        {
            query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.maxBidAmount = :inMaxBidAmount");
            query.setParameter("inMaxBidAmount", proxyBid.getMaxBidAmount());
        } 
        else if(proxyBid.getIncrement() != null)
        {
            query = em.createQuery("SELECT b FROM ProxyBidEntity b WHERE b.increment = :inIncrement");
            query.setParameter("inIncrement", proxyBid.getIncrement());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        proxyBids = query.getResultList();
        
        if(proxyBids.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return proxyBids;                
    }
    
    @Override
    public List<ProxyBidEntity> retrieveProxyBidByAffiliateContentAttributes(AffiliateContentEntity affiliateContent) {
        Query query = null;
        List<ProxyBidEntity> bids;
        
        if(affiliateContent.getTitle() != null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.affiliateContent.title LIKE :inTitle");
            query.setParameter("inTitle", affiliateContent.getTitle());
        }
        else if(affiliateContent.getCategory() != null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.affiliateContent.category = :inCategory");
            query.setParameter("inCategory", affiliateContent.getCategory());
        }
        else if(affiliateContent.getAffiliateContentState() != null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.affiliateContent.affiliateContentState = :inState");
            query.setParameter("inState", affiliateContent.getAffiliateContentState());
        }  
        else if(affiliateContent.getAffiliateContentStatus()!= null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.affiliateContent.affiliateContentStatus = :inStatus");
            query.setParameter("inStatus", affiliateContent.getAffiliateContentStatus());
        } 
        else if(affiliateContent.getPromotionStartDate() != null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.affiliateContent.promotionStartDate = :inStartDate");
            query.setParameter("inStartDate", affiliateContent.getPromotionStartDate());
        } 
        else if(affiliateContent.getPromotionEndDate() != null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.affiliateContent.promotionEndDate = :inEndDate");
            query.setParameter("inEndDate", affiliateContent.getPromotionEndDate());
        }         
        else
        {
            return new ArrayList<>();
        }
        
        bids = query.getResultList();
        
        if(bids.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return bids;                        
    }    

    @Override
    public List<ProxyBidEntity> retrieveAllProxyBids() {
        Query query = em.createQuery("SELECT p FROM ProxyBidEntity p");
        
        List<ProxyBidEntity> proxyBids = query.getResultList();
        System.out.println("***ProxyBidSession proxyBids.size() " + proxyBids.size() + "***");
        
        if(proxyBids.size() == 0)
        {
            return new ArrayList<ProxyBidEntity>();
        }
        
        return query.getResultList();          
    }

    @Override
    public void updateProxyBid(ProxyBidEntity proxyBid) {
        em.merge(proxyBid);
    }

    @Override
    public void deleteProxyBid(Long id) throws ProxyBidNotFoundException {
        ProxyBidEntity proxyBid = retrieveProxyBidById(id);
        em.remove(proxyBid);        
    }




}
