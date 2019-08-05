/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.FacilityEntity;
import util.entity.HotelEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Local
public interface FacilitySessionLocal {
     
    public FacilityEntity createEntity(FacilityEntity entity);
    public void addHotel(Long fId, HotelEntity entity);
    public FacilityEntity retrieveEntityById(Long id);
    public List<FacilityEntity> retrieveFacilitiesByHotelId(Long hId);
    public List<FacilityEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(FacilityEntity entity);
    public FacilityEntity retrieveEntity(String attribute, String value);
    public List<FacilityEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
