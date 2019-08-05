/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.SalesCallGuidelineEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface SalesCallGuidelineSessionLocal {
    
    public SalesCallGuidelineEntity createEntity(SalesCallGuidelineEntity entity);
    public SalesCallGuidelineEntity retrieveEntityById(Long id);
    public List<SalesCallGuidelineEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(SalesCallGuidelineEntity entity);
    public SalesCallGuidelineEntity retrieveEntity(String attribute, String value);
    public List<SalesCallGuidelineEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

}
