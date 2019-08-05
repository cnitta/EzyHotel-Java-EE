/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.CustomerEntity;
import util.entity.LoyaltyEntity;
import util.entity.LoyaltyTransactionEntity;
import util.entity.PaymentEntity;
import util.exception.LoyaltyException;

/**
 *
 * @author berni
 */
@Local
public interface LoyaltySessionLocal {

    public LoyaltyEntity createEntity(LoyaltyEntity entity);

    public LoyaltyEntity retrieveEntityById(Long id);

    public List<LoyaltyEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(LoyaltyEntity entity);

    public LoyaltyEntity retrieveEntity(String attribute, String value);

    public List<LoyaltyEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public List<String> retrieveAllLoyaltyProgram();

    public List<LoyaltyEntity> retrieveAllLoyaltyByLoyaltyProgram(String membershipType);

    public LoyaltyEntity retrieveLoyaltyByCustomerId(Long customerId) throws LoyaltyException;

    public void loyaltyPointsAwarded(List<LoyaltyTransactionEntity> loyaltyTransactions, CustomerEntity customer) throws LoyaltyException;

    public LoyaltyTransactionEntity addLoyaltyPointsByCustomerId(Long customerId, PaymentEntity customerPayment) throws LoyaltyException;

    public boolean deductLoyaltyPointsByCustomerId(Long customerId, int pointsToDeduct) throws LoyaltyException;
    
}
