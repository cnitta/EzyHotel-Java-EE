/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ConfirmationEmailEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface ConfirmationEmailSessionLocal {
    
    public ConfirmationEmailEntity createEntity(ConfirmationEmailEntity entity);
    public ConfirmationEmailEntity retrieveEntityById(Long id);
    public List<ConfirmationEmailEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(ConfirmationEmailEntity entity);
    public ConfirmationEmailEntity retrieveEntity(String attribute, String value);
    public List<ConfirmationEmailEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
