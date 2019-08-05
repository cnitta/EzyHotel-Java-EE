/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.TransactionEntity;

/**
 *
 * @author berni
 */
@Local
public interface TransactionSessionLocal {

    public TransactionEntity createEntity(TransactionEntity entity);

    public TransactionEntity retrieveEntityById(Long id);

    public List<TransactionEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(TransactionEntity entity);

    public TransactionEntity retrieveEntity(String attribute, String value);

    public List<TransactionEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
