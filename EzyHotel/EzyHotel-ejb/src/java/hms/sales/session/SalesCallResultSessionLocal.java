/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.SalesCallResultEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface SalesCallResultSessionLocal {
    
    public SalesCallResultEntity createEntity(SalesCallResultEntity entity);
    public SalesCallResultEntity retrieveEntityById(Long id);
    public List<SalesCallResultEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(SalesCallResultEntity entity);
    public SalesCallResultEntity retrieveEntity(String attribute, String value);
    public List<SalesCallResultEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

}
