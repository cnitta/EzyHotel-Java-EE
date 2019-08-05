/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.CustomerEntity;
import util.entity.LoyaltyTransactionEntity;
import util.entity.PaymentEntity;

/**
 *
 * @author berni
 */
@Local
public interface LoyaltyTransactionSessionLocal {

    public LoyaltyTransactionEntity retrieveEntityById(Long id);

    public LoyaltyTransactionEntity createEntity(LoyaltyTransactionEntity entity);

    public List<LoyaltyTransactionEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public List<LoyaltyTransactionEntity> createLoyaltyTransactions(List<PaymentEntity> payments, CustomerEntity customer);

    public List<LoyaltyTransactionEntity> retrieveLoyaltyTransactionByLoyaltyId(Long loyaltyId);

    public void updateEntity(LoyaltyTransactionEntity entity);
    
}
