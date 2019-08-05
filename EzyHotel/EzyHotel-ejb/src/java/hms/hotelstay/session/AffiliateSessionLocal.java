/*
 * Affiliateo change this license header, choose License Headers in Project Properties.
 * Affiliateo change this template file, choose Affiliateools | Affiliateemplates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AffiliateEntity;
import util.exception.AffiliateNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface AffiliateSessionLocal {
    public AffiliateEntity createAffiliate(AffiliateEntity affiliate);
    
    public AffiliateEntity retrieveAffiliateById(Long id) throws AffiliateNotFoundException;
    
    public List<AffiliateEntity> retrieveAffiliateByAffiliateAttributes (AffiliateEntity affiliate);    
    
    public List<AffiliateEntity> retrieveAllAffiliates();

    public void updateAffiliate(AffiliateEntity affiliate);
    
    public void deleteAffiliate(Long id) throws AffiliateNotFoundException;             
    
//    public AffiliateEntitylogin(AffiliateEntityaffiliate);
//    
//    public AffiliateEntitylogout(AffiliateEntityaffiliate);
}
