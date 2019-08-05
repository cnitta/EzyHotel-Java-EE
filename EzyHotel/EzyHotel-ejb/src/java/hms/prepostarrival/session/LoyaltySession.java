/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import hms.common.CrudService;
import hms.commoninfra.session.AccountSession;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericEntity;
import util.entity.CustomerEntity;

import util.entity.LoyaltyEntity;
import util.entity.LoyaltyTransactionEntity;
import util.entity.PaymentEntity;
import util.exception.LoyaltyException;

/**
 *
 * @author berni
 */
@Stateless
public class LoyaltySession implements LoyaltySessionLocal, CrudService<LoyaltyEntity> {
    @EJB
    private LoyaltyTransactionSessionLocal loyaltyTransactionSessionLocal;
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<String> retrieveAllLoyaltyProgram() {
        Query query = em.createQuery("SELECT DISTINCT c.membershipType FROM LoyaltyEntity c");
        return query.getResultList();
    }

    @Override
    public List<LoyaltyEntity> retrieveAllLoyaltyByLoyaltyProgram(String membershipType) {
        Query query = em.createQuery("SELECT c FROM LoyaltyEntity c WHERE c.membershipType =:membershipTypeString");
        query.setParameter("membershipTypeString", membershipType);
        
        try{
           return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            return new ArrayList<>();
        }
        
    }
    
    @Override
    public LoyaltyEntity retrieveLoyaltyByCustomerId(Long customerId) throws LoyaltyException{
        String queryInput = "SELECT c FROM LoyaltyEntity c WHERE c.customer.customerId=:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", customerId);
        try {
            return (LoyaltyEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new LoyaltyException("Loyalty is not established or not found!");
        }
    }
    

    //This is when the customer make an online booking, the loyalty is being updated
    @Override
    public void loyaltyPointsAwarded(List<LoyaltyTransactionEntity> loyaltyTransactions, CustomerEntity customer) throws LoyaltyException{
        try{
            LoyaltyEntity customerLoyalty = retrieveLoyaltyByCustomerId(customer.getCustomerId());
            int totalPointsEarned = 0;
            for(LoyaltyTransactionEntity loyaltyTransaction: loyaltyTransactions){
                  totalPointsEarned = totalPointsEarned + Integer.parseInt(loyaltyTransaction.getTransactionPoint());
            }
        
            customerLoyalty.setMaxPoint(customerLoyalty.getMaxPoint() + totalPointsEarned);
            customerLoyalty.setCurrentPoint(customerLoyalty.getCurrentPoint() + totalPointsEarned);
            customerLoyalty = updateMembershipStatus(customerLoyalty);
            
            updateEntity(customerLoyalty);
        
        }catch(LoyaltyException e){
             throw new LoyaltyException("We are unable to award you loyalty points. Please approach the Guest Service at the reception for assistance.");
        }
    }
    
  
    //Call this after making payment for Admin Portal - KIV (Because if implement retrieval of customer profile works, 
    // means can use online room booking method to generate loyalty transaction and update loyalty points.
    
    @Override
    public LoyaltyTransactionEntity addLoyaltyPointsByCustomerId(Long customerId, PaymentEntity customerPayment) throws LoyaltyException{
        try{
            LoyaltyEntity customerLoyalty = retrieveLoyaltyByCustomerId(customerId);
            int pointsToAdd = customerPayment.getTotalAmount().setScale(0,RoundingMode.HALF_EVEN).intValueExact();
            customerLoyalty.setCurrentPoint(customerLoyalty.getCurrentPoint() + pointsToAdd);
            customerLoyalty.setMaxPoint(customerLoyalty.getMaxPoint() + pointsToAdd);
            
            String description = pointsToAdd + " points are awared for Booking " + customerPayment.getRoomBooking().getReservationNumber();
            LoyaltyTransactionEntity newLoyaltyTransaction = new LoyaltyTransactionEntity(String.valueOf(pointsToAdd),description, Date.valueOf(LocalDate.now()),customerLoyalty, customerPayment.getRoomBooking(), customerPayment);
            
            newLoyaltyTransaction = loyaltyTransactionSessionLocal.createEntity(newLoyaltyTransaction);
            System.out.println("Loyalty Transaction created successfully");
            
            //Update the customer's loyalty
            updateEntity(customerLoyalty);
            
            return newLoyaltyTransaction;
            
        }catch(LoyaltyException e){
             throw new LoyaltyException("We are unable to award you loyalty points. Please approach the Guest Service at the reception for assistance.");
        }
    }
    
    @Override
    public boolean deductLoyaltyPointsByCustomerId(Long customerId, int pointsToDeduct) throws LoyaltyException{
        //Long loyaltyPointId, Integer remainingPointsInTransaction - Those in the HashMap will be those redeemed fully.
        HashMap<Long, Integer> loyaltyTransactionIds = new HashMap<>(); 
        try{
            LoyaltyEntity customerLoyalty = retrieveLoyaltyByCustomerId(customerId);
            customerLoyalty.setCurrentPoint(customerLoyalty.getCurrentPoint() - pointsToDeduct);
            
            //Retrieve the customer's loyalty transactions
            List<LoyaltyTransactionEntity> customerLoyaltyTransactions = loyaltyTransactionSessionLocal.retrieveLoyaltyTransactionByLoyaltyId(customerLoyalty.getLoyaltyPointId());
            
            int remainingPointsInTransaction = 0;
            //Lopp through and deduct the points from the transactions
            for(LoyaltyTransactionEntity loyaltyTransaction : customerLoyaltyTransactions){
                System.out.println("Points To Deduct Remaining: " + pointsToDeduct);
                System.out.println("Points available in Loyalty Transaction Id " + loyaltyTransaction.getLoyaltyTransnId() + ": " + loyaltyTransaction.getTransactionPoint());
                remainingPointsInTransaction = Integer.valueOf(loyaltyTransaction.getRemainingPointForTransaction());
                
                if(remainingPointsInTransaction > pointsToDeduct){
                    remainingPointsInTransaction = remainingPointsInTransaction - pointsToDeduct;
                    System.out.println("Remaining Points in Ending Transaction: " + remainingPointsInTransaction);
                    //Update the loyalty transaction directly
                    loyaltyTransaction.setRemainingPointForTransaction(remainingPointsInTransaction);
                    loyaltyTransactionSessionLocal.updateEntity(loyaltyTransaction);
                    
                    return true;
                }else if(remainingPointsInTransaction == pointsToDeduct){
                    //Update the loyalty transaction directly
                    System.out.println("Points are equal for deduction, Loyalty Transaction Id: " + loyaltyTransaction.getLoyaltyTransnId());
                    remainingPointsInTransaction = remainingPointsInTransaction - pointsToDeduct;
                    loyaltyTransaction.setRemainingPointForTransaction(remainingPointsInTransaction);
                    loyaltyTransaction.setIsFullyRedeemed(true);
                    loyaltyTransactionSessionLocal.updateEntity(loyaltyTransaction);
                    
                    return true;
                }else{
                    //pointsInTransaction < pointsToDeduct
                    pointsToDeduct = pointsToDeduct - remainingPointsInTransaction;
                    loyaltyTransactionIds.put(loyaltyTransaction.getLoyaltyTransnId(), Integer.getInteger(loyaltyTransaction.getTransactionPoint()));
                }
                
            }
            
            if(!loyaltyTransactionIds.isEmpty()){
                //Do asynchrnonous updating of the transactions
                System.out.println("Updating loyalty transactions asynchronously");
                updateLoyaltyTransactionsAfterDeduction(loyaltyTransactionIds);
                return true;
            }
            
            
        }catch(LoyaltyException e){
             return false;
        }
        
        return false;
    }

    private void updateLoyaltyTransactionsAfterDeduction(HashMap<Long, Integer> loyaltyTransactionIdList){
        CompletableFuture.runAsync(()-> {
            try {
              System.out.println("In asynchronous CompletableFuture - Updating the status of the loyalty transactions");
              for(Long loyaltyTransId: loyaltyTransactionIdList.keySet()){
                  LoyaltyTransactionEntity loyaltyTransaction = loyaltyTransactionSessionLocal.retrieveEntityById(loyaltyTransId);
                  loyaltyTransaction.setRemainingPointForTransaction(loyaltyTransactionIdList.get(loyaltyTransId));
                  loyaltyTransaction.setIsFullyRedeemed(true);
                  loyaltyTransactionSessionLocal.updateEntity(loyaltyTransaction);
              }
                
            } catch (Exception ex) {
                System.out.println("Updating Loyalty Transaction faced an error: " + ex.getMessage());
                Logger.getLogger(LoyaltySession.class.getName()).log(Level.SEVERE, null, ex);
            }
        });    
    }
    
    
    
    private LoyaltyEntity updateMembershipStatus(LoyaltyEntity customerLoyalty){
        if(customerLoyalty.getMaxPoint() >= 1500){
            customerLoyalty.setMembershipType("GOLD");
        }else if(customerLoyalty.getMaxPoint() >= 500 && customerLoyalty.getMaxPoint() < 1500){
            customerLoyalty.setMembershipType("SILVER");
        }else{
            customerLoyalty.setMembershipType("NORMAL");
        }

        return customerLoyalty;
    }

    
    // ============================================ Generated Codes for CRUD ======================================================
    @Override
    public LoyaltyEntity createEntity(LoyaltyEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public LoyaltyEntity retrieveEntityById(Long id) {
        LoyaltyEntity entity = em.find(LoyaltyEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("LoyaltyEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<LoyaltyEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM LoyaltyEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        LoyaltyEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(LoyaltyEntity entity) {
        em.merge(entity);
    }

    @Override
    public LoyaltyEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM LoyaltyEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (LoyaltyEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<LoyaltyEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB,
            String valueB) {
        String queryInput = "SELECT c FROM LoyaltyEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB
                + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

}
