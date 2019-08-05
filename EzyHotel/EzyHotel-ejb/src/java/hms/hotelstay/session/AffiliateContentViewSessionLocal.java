/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AffiliateContentViewEntity;
import util.exception.AffiliateContentViewNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface AffiliateContentViewSessionLocal {
    public AffiliateContentViewEntity createAffiliateContentView(AffiliateContentViewEntity affiliateContentView);
    
    public AffiliateContentViewEntity retrieveAffiliateContentViewById(Long id) throws AffiliateContentViewNotFoundException;
    
    public List<AffiliateContentViewEntity> retrieveAffiliateContentViewByAffiliateContentViewAttributes (AffiliateContentViewEntity affiliateContentView);    
    
    public List<AffiliateContentViewEntity> retrieveAllAffiliateContentViews();

    public void updateAffiliateContentView(AffiliateContentViewEntity affiliateContentView);
    
    public void deleteAffiliateContentView(Long id) throws AffiliateContentViewNotFoundException;     
}
