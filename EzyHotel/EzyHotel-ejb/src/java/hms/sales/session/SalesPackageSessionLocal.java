/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.SalesPackageEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface SalesPackageSessionLocal {

    public SalesPackageEntity createEntity(SalesPackageEntity entity);
    public SalesPackageEntity retrieveEntityById(Long id);
    public List<SalesPackageEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(SalesPackageEntity entity);
    public SalesPackageEntity retrieveEntity(String attribute, String value);
    public List<SalesPackageEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
