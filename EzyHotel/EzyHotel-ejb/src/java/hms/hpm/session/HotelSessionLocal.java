/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.HotelEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Local
public interface HotelSessionLocal {
    public HotelEntity createEntity(HotelEntity entity);
    public HotelEntity retrieveEntityById(Long id);
    public List<HotelEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(HotelEntity entity);
    public HotelEntity retrieveEntity(String attribute, String value);
    public List<HotelEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    public List<HotelEntity> retrieveHotelsByAttributes(String name, String address);
}
