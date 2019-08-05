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
import util.entity.ProxyBidEntity;
import util.exception.ProxyBidNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface ProxyBidSessionLocal {
    public ProxyBidEntity createProxyBid(ProxyBidEntity proxyBid);
    
    public BidEntity retrieveProxyBidById(Long id) throws ProxyBidNotFoundException;
    
    public List<ProxyBidEntity> retrieveProxyBidByProxyBidAttributes (ProxyBidEntity proxyBid);  
    
    public List<ProxyBidEntity> retrieveProxyBidByAffiliateContentAttributes (AffiliateContentEntity affiliateContent); 
    
    public List<ProxyBidEntity> retrieveAllProxyBids();
  
    public void updateProxyBid(ProxyBidEntity proxyBid);
    
    public void deleteProxyBid(Long id) throws ProxyBidNotFoundException;      
}
