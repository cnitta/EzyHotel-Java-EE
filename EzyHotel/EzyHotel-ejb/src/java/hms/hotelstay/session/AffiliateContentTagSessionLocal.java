/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AffiliateContentTagEntity;
import util.exception.AffiliateContentTagNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface AffiliateContentTagSessionLocal {
    public AffiliateContentTagEntity createAffiliateContentTag(AffiliateContentTagEntity affiliateContentTag);
    
    public AffiliateContentTagEntity retrieveAffiliateContentTagById(Long id) throws AffiliateContentTagNotFoundException;
    
    public List<AffiliateContentTagEntity> retrieveAffiliateContentTagByAffiliateContentTagAttributes (AffiliateContentTagEntity affiliateContentTag);    
    
    public List<AffiliateContentTagEntity> retrieveAllAffiliateContentTags();

    public void updateAffiliateContentTag(AffiliateContentTagEntity affiliateContentTag);
    
    public void deleteAffiliateContentTag(Long id) throws AffiliateContentTagNotFoundException;     
}
