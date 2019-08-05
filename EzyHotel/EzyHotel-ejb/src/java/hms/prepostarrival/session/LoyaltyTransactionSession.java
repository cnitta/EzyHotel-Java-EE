/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import hms.common.CommonMethods;
import hms.common.CrudService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
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
public class LoyaltyTransactionSession implements LoyaltyTransactionSessionLocal, CrudService<LoyaltyTransactionEntity> {
    @EJB
    private LoyaltySessionLocal loyaltySessionLocal;
    
    @PersistenceContext
    private EntityManager em;

    private CommonMethods commons = new CommonMethods();
    
    @Override
    public List<LoyaltyTransactionEntity> createLoyaltyTransactions(List<PaymentEntity> payments, CustomerEntity customer){
        List<LoyaltyTransactionEntity> loyaltyTransactionsCreated = new ArrayList<>();
        LoyaltyEntity customerLoyalty = new LoyaltyEntity();
        try{
            System.out.println("Customer has Loyalty created previously.");
            customerLoyalty = loyaltySessionLocal.retrieveLoyaltyByCustomerId(customer.getCustomerId()); 
            loyaltyTransactionsCreated = generateLoyaltyTransactions(payments, customerLoyalty);

        }catch(LoyaltyException e){
            
            System.out.println("Customer does not have Loyalty created previously.");
            //Customer does not have an existing loyalty 
            LocalDate now = LocalDate.now();
            customerLoyalty = new LoyaltyEntity(0, 0, "NORMAL",Date.valueOf(now), customer);
            customerLoyalty = loyaltySessionLocal.createEntity(customerLoyalty);
            loyaltyTransactionsCreated = generateLoyaltyTransactions(payments, customerLoyalty);
            System.out.println("loyalty transactions' size: " + loyaltyTransactionsCreated.size());
        }catch(Exception e){
            System.out.println("Other ERRROR: " + e.getMessage());
        }
       

        return loyaltyTransactionsCreated;
    }
 
    
    @Override
    public List<LoyaltyTransactionEntity> retrieveLoyaltyTransactionByLoyaltyId(Long loyaltyId){
        List<LoyaltyTransactionEntity> loyaltyTransactions = new ArrayList<>();
     
        String queryInput = "SELECT c FROM LoyaltyTransactionEntity c WHERE c.loyalty.loyaltyPointId =:inValue AND c.isFullyRedeemed != 1";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", loyaltyId);
        try {
            List<LoyaltyTransactionEntity> results = query.getResultList();
            System.out.println("Before sorting: " + results.toString());
            results.sort( Comparator.comparing(dateValue -> dateValue.getDateCreated()));
            System.out.println("After sorting: " + results.toString());
            return results;
        } catch (NoResultException | NonUniqueResultException ex) {
            return loyaltyTransactions; //Return empty arraylist
        } 
        
    }
    
    
    private List<LoyaltyTransactionEntity> generateLoyaltyTransactions(List<PaymentEntity> payments, LoyaltyEntity customerLoyalty){
        List<LoyaltyTransactionEntity> loyaltyTransactionsCreated = new ArrayList<>();
        String transactionPoints = "",description = "";
        LoyaltyTransactionEntity newLoyaltyTransaction = new LoyaltyTransactionEntity();  
            //Check if is birthday month, then check membership tier
            boolean isCustomerBirthdayMonth = commons.checkIsBirthdayMonth(customerLoyalty.getCustomer());
            System.out.println("Birthday Month for Customer: " + isCustomerBirthdayMonth);
        for(PaymentEntity payment : payments){
            
            if(isCustomerBirthdayMonth && customerLoyalty.getMembershipType().equals("GOLD")){
               transactionPoints = String.valueOf(payment.getTotalAmount().multiply(new BigDecimal(2)).setScale(0, RoundingMode.HALF_EVEN));
               description = "Happy Birthday! 2X points awarded! " + transactionPoints + " points are awarded for Booking " + payment.getRoomBooking().getReservationNumber(); 
            }else{
               //Create loyalty transaction for Customer for each payment made
                transactionPoints = String.valueOf(payment.getTotalAmount().setScale(0, RoundingMode.HALF_EVEN));
                description = transactionPoints + " points are awarded for Booking " + payment.getRoomBooking().getReservationNumber(); 
            }
            
            newLoyaltyTransaction = new LoyaltyTransactionEntity(transactionPoints, description, Date.valueOf(LocalDate.now()), customerLoyalty, payment.getRoomBooking(), payment);
            newLoyaltyTransaction = createEntity(newLoyaltyTransaction);
            System.out.println("Loyalty Transaction created for " + newLoyaltyTransaction.getLoyalty().getCustomer().getFirstName());
            loyaltyTransactionsCreated.add(newLoyaltyTransaction);
            
        }
        return loyaltyTransactionsCreated;
    }
    
 
    // ============================================ Generated Codes for CRUD ======================================================
    @Override
    public LoyaltyTransactionEntity createEntity(LoyaltyTransactionEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public LoyaltyTransactionEntity retrieveEntityById(Long id) {
        LoyaltyTransactionEntity entity = em.find(LoyaltyTransactionEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("LoyaltyTransactionEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<LoyaltyTransactionEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM LoyaltyTransactionEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        LoyaltyTransactionEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(LoyaltyTransactionEntity entity) {
        em.merge(entity);
    }

    @Override
    public LoyaltyTransactionEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM LoyaltyTransactionEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (LoyaltyTransactionEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<LoyaltyTransactionEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM LoyaltyTransactionEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
