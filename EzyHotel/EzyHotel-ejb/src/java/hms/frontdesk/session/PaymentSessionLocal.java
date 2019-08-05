/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.PaymentEntity;

/**
 *
 * @author berni
 */
@Local
public interface PaymentSessionLocal {

    public PaymentEntity createEntity(PaymentEntity entity);

    public PaymentEntity retrieveEntityById(Long id);

    public List<PaymentEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(PaymentEntity entity);

    public PaymentEntity retrieveEntity(String attribute, String value);

    public List<PaymentEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public PaymentEntity retrieveEntityById(String attribute, Long id);

    public List<PaymentEntity> getPaymentByHotel(Long hotelId);
    
}
