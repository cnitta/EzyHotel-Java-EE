/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.SalesCallGuidelineWysiwygEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface SalesCallGuidelineWysiwygSessionLocal {
    public SalesCallGuidelineWysiwygEntity createEntity(SalesCallGuidelineWysiwygEntity entity);
    public SalesCallGuidelineWysiwygEntity retrieveEntityById(Long id);
    public List<SalesCallGuidelineWysiwygEntity> retrieveAllEntityBySalesCallGuidelineId(Long salesCallGuidelineId);
    public List<SalesCallGuidelineWysiwygEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(SalesCallGuidelineWysiwygEntity entity);
    public SalesCallGuidelineWysiwygEntity retrieveEntity(String attribute, String value);
    public List<SalesCallGuidelineWysiwygEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
 
}
