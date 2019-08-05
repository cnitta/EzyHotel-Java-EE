/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.CustomerMailingEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface CustomerMailingSessionLocal {
    public CustomerMailingEntity createEntity(CustomerMailingEntity entity);
    public CustomerMailingEntity retrieveEntityById(Long id);
    public List<CustomerMailingEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(CustomerMailingEntity entity);
    public CustomerMailingEntity retrieveEntity(String attribute, String value);
    public List<CustomerMailingEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
