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
import util.entity.BidEntity;
import util.exception.BidNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class BidSession implements BidSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public BidEntity createBid(BidEntity bid) {
        em.persist(bid);
        em.flush();
        em.refresh(bid);
        return bid;
    }

    @Override
    public BidEntity retrieveBidById(Long id) throws BidNotFoundException {
        BidEntity bid = em.find(BidEntity.class, id);
        if (bid != null) {
            return bid;
        } else {
            throw new BidNotFoundException("Bid ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<BidEntity> retrieveBidByBidAttributes(BidEntity bid) {
        Query query = null;
        List<BidEntity> bids;
        
        if(bid.getBidAmount() != 0.00)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.bidAmount = :inBidAmount");
            query.setParameter("inBidAmount", bid.getBidAmount());
        }  
        else if(bid.getBidDateTime() != null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.bidDateTime = :inBidDateTime");
            query.setParameter("inBidDateTime", bid.getBidDateTime());
        }   
        else if(bid.getStartDateTime()!= null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.startDateTime = :inBidStartDateTime");
            query.setParameter("inBidStartDateTime", bid.getStartDateTime());
        }   
         else if(bid.getEndDateTime()!= null)
        {
            query = em.createQuery("SELECT b FROM BidEntity b WHERE b.endDateTime = :inBidEndDateTime");
            query.setParameter("inBidEndDateTime", bid.getEndDateTime());
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
    public List<BidEntity> retrieveBidByAffiliateContentAttributes(AffiliateContentEntity affiliateContent) {
        Query query = null;
        List<BidEntity> bids;
        
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
    public List<BidEntity> retrieveAllBids() {
        Query query = em.createQuery("SELECT a FROM BidEntity a");
        
        List<BidEntity> bids = query.getResultList();
        System.out.println("***AffiliateSession bids.size() " + bids.size() + "***");
        
        if(bids.size() == 0)
        {
            return new ArrayList<BidEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateBid(BidEntity bid) {
        em.merge(bid);
    }

    @Override
    public void deleteBid(Long id) throws BidNotFoundException {
        BidEntity bid = retrieveBidById(id);
        em.remove(bid);
    }


}
