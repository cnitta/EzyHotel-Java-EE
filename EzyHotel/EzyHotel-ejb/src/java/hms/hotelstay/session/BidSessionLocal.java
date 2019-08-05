/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AffiliateContentEntity;
import util.entity.BidEntity;
import util.exception.BidNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface BidSessionLocal {
    public BidEntity createBid(BidEntity bid);
    
    public BidEntity retrieveBidById(Long id) throws BidNotFoundException;
    
    public List<BidEntity> retrieveBidByBidAttributes (BidEntity bid); 

    public List<BidEntity> retrieveBidByAffiliateContentAttributes (AffiliateContentEntity affiliateContent);     
    
    public List<BidEntity> retrieveAllBids();

    public void updateBid(BidEntity bid);
    
    public void deleteBid(Long id) throws BidNotFoundException;      
    
}
