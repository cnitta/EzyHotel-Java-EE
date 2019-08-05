/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.PriceRateEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface PriceRateSessionLocal {

    public PriceRateEntity createEntity(PriceRateEntity entity);
    public PriceRateEntity retrieveEntityById(Long id);
    public List<PriceRateEntity> retrieveAllEntities();
    public List<PriceRateEntity> retrieveAllEntitiesByRoomTypeId(Long roomTypeId);
    public List<PriceRateEntity> retrieveAllEntitiesByFacilityId(Long facilityId);
    public List<PriceRateEntity> retrieveAllEntitiesByFacilityTypeId(Long facilityTypeId);
    public void deleteEntity(Long id);
    public void updateEntity(PriceRateEntity entity);
    public PriceRateEntity retrieveEntity(String attribute, String value);
    public List<PriceRateEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    public double dynamicPrice(double normalPrice, double rackPrice, int roomOccupancy);
}
