/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.PaymentMethodEntity;

/**
 *
 * @author berni
 */
@Local
public interface PaymentMethodSessionLocal {

    public PaymentMethodEntity createEntity(PaymentMethodEntity entity);

    public PaymentMethodEntity retrieveEntityById(Long id);

    public List<PaymentMethodEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(PaymentMethodEntity entity);
    
}
