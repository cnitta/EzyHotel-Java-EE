/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ReserveFacilityEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface ReserveFacilitySessionLocal {
    
    public ReserveFacilityEntity createEntity(ReserveFacilityEntity entity);
    public ReserveFacilityEntity retrieveEntityById(Long id);
    public List<ReserveFacilityEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(ReserveFacilityEntity entity);
    public ReserveFacilityEntity retrieveEntity(String attribute, String value);
    public List<ReserveFacilityEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

}
