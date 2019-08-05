/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AugmentedRealityContentEntity;
import util.exception.AugmentedRealityContentNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface AugmentedRealityContentSessionLocal {
    public AugmentedRealityContentEntity createAugmentedRealityContent(AugmentedRealityContentEntity augmentedRealityContent);
    
    public AugmentedRealityContentEntity retrieveAugmentedRealityContentById(Long id) throws AugmentedRealityContentNotFoundException;
    
    public List<AugmentedRealityContentEntity> retrieveAugmentedRealityContentByAugmentedRealityContentAttributes (AugmentedRealityContentEntity augmentedRealityContent);    
    
    public List<AugmentedRealityContentEntity> retrieveAllAugmentedRealityContents();

    public void updateAugmentedRealityContent(AugmentedRealityContentEntity augmentedRealityContent);
    
    public void deleteAugmentedRealityContent(Long id) throws AugmentedRealityContentNotFoundException;     
}
