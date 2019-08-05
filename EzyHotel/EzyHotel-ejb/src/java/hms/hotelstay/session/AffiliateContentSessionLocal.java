/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AffiliateContentEntity;
import util.exception.AffiliateContentNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface AffiliateContentSessionLocal {
    public AffiliateContentEntity createAffiliateContent(AffiliateContentEntity affiliateContent);
    
    public AffiliateContentEntity retrieveAffiliateContentById(Long id) throws AffiliateContentNotFoundException;
    
    public List<AffiliateContentEntity> retrieveAffiliateContentByAffiliateContentAttributes (AffiliateContentEntity affiliateContent);    
    
    public List<AffiliateContentEntity> retrieveAllAffiliateContents();

    public void updateAffiliateContent(AffiliateContentEntity affiliateContent);
    
    public void deleteAffiliateContent(Long id) throws AffiliateContentNotFoundException;     
}
