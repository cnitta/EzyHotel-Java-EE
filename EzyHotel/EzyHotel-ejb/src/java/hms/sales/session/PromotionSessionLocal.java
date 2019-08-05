/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.PromotionEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface PromotionSessionLocal {

    public PromotionEntity createEntity(PromotionEntity entity);
    public PromotionEntity retrieveEntityById(Long id);
    public List<PromotionEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(PromotionEntity entity);
    public PromotionEntity retrieveEntity(String attribute, String value);
    public List<PromotionEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
