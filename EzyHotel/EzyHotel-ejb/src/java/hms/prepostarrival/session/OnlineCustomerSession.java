/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import hms.common.CrudService;
import hms.commoninfra.session.LogSessionLocal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
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
import util.entity.RoomBookingEntity;
import util.enumeration.CustomerMembershipEnum;
import util.enumeration.LogStatusEnum;
import util.enumeration.SystemCategoryEnum;
import util.exception.CustomerProfileConflictException;

/**
 *
 * @author berni
 */
@Stateless
public class OnlineCustomerSession implements OnlineCustomerSessionLocal, CrudService<CustomerEntity> {
    @EJB
    private LoyaltySessionLocal loyaltySessionLocal;
    
    @EJB
    private LogSessionLocal logSessionLocal;
    
    @PersistenceContext
    private EntityManager em;
     
    @Override
    public boolean saveCustomerProfile(CustomerEntity customer) throws CustomerProfileConflictException
    {
            //Status to check if save record successfully
            boolean isSaved = false;
            
            //First check if there is an existing customer with the following profile
            try{
               CustomerEntity getCustomer = retrieveEntity("email",customer.getEmail());
                getCustomer.setBirthDate(customer.getBirthDate());
                getCustomer.setCustIdentity(customer.getCustIdentity());
              
                getCustomer.setFirstName(customer.getFirstName());
                getCustomer.setLastName(customer.getLastName());

                //if the customer previously made a booking and hence already has a customer profile, merge
                updateEntity(getCustomer);
                isSaved = true;

                //Create a log method for updating the customer profile
                logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.PRE_POST_ARRIVAL_MS, LogStatusEnum.UPDATE, new GenericEntity(customer, CustomerEntity.class) , "");

            }
            catch(NotFoundException e)
            {
                CustomerEntity customerProfile = createEntity(customer);
                isSaved = true;
                //Create a log method for creating a new customer profile
                logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.PRE_POST_ARRIVAL_MS, LogStatusEnum.CREATE, new GenericEntity(customer, CustomerEntity.class) , "");
            }

           return isSaved;

    }
    
    /**
     *
     * @param identityString
     * @return
     */
    @Override
    public Pair<CustomerEntity, String> checkCustomerMembership(String identityString)
    {
        //Retrieve the CustomerEntity
        List<CustomerEntity> getCustomerList = retrieveEntityByEitherFilters("email", identityString, "phoneNum", identityString);
        String membershipStatus = "DOES NOT EXIST";    
        CustomerEntity getCustomer = new CustomerEntity();
        
        if(!getCustomerList.isEmpty())
        {
            getCustomer = getCustomerList.get(0);
        }

        switch(getCustomer.getMembershipStatus())
        {
            case MEMBER:
                membershipStatus = "MEMBER";
                break;
            case UNVERIFIED_MEMBER:
                membershipStatus = "UNVERIFIED_MEMBER";
                break;
            case NON_MEMBER:
                membershipStatus = "NON_MEMBER";
                break;
            default:
                membershipStatus = "DOES NOT EXIST";
                break;
        }

        return new Pair(getCustomer, membershipStatus);
    }

    @Override
    public List<CustomerEntity> retrieveCustomerByIdentityString(String identityString)
    {
        try{
        //Identity String refers to the email or phone number
            List<CustomerEntity> getCustomerList = retrieveEntityByEitherFilters("email", identityString, "phoneNum", identityString);
            return getCustomerList;
        }catch(Exception e){
             List<CustomerEntity> getCustomerList = new ArrayList<>();
             return getCustomerList;
        }
        
    }

    @Override
    public void confirmCustomerMembership(CustomerEntity member)
    {
        //Update customer's membership status
        member.setMembershipStatus(CustomerMembershipEnum.MEMBER);
        updateEntity(member);
        
        //Create a new loyalty for customer 
        LoyaltyEntity newLoyalty = new LoyaltyEntity(0,0,"NORMAL",Date.valueOf(LocalDate.now()),member);
        newLoyalty = loyaltySessionLocal.createEntity(newLoyalty);

    }
    
    
    @Override
    public void terminateCustomerMembership(Long customerId)
    {
        //Retrieve the CustomerEntity by id
        CustomerEntity customerToUpdate = retrieveEntityById(customerId);
        
        //Update the respective values to be of NON_MEMBER status
        customerToUpdate.setMembershipStatus(CustomerMembershipEnum.NON_MEMBER);
        customerToUpdate.setPassword("");
        
        //All loyalty points will be lost and non-redeemable upon terminating the membership.
        LoyaltyEntity customerLoyalty = loyaltySessionLocal.retrieveEntity("customer.customerId", customerId.toString());
        loyaltySessionLocal.deleteEntity(customerLoyalty.getLoyaltyPointId());
    
    }
    
    
    
    
    
    /*
    ================ Codes generated with Generated Method Body Template ========================
    */
    
    /**
     *
     * @param entity
     * @return
     */
    @Override
    public CustomerEntity createEntity(CustomerEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public CustomerEntity retrieveEntityById(Long id) {
        CustomerEntity entity = em.find(CustomerEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("CustomerEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<CustomerEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM CustomerEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        CustomerEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(CustomerEntity entity) {
        em.merge(entity);
    }

    @Override
    public CustomerEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM CustomerEntity c WHERE c." + attribute + " =:inValue";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (CustomerEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<CustomerEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB){
        String queryInput = "SELECT c FROM CustomerEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    @Override
    public List<CustomerEntity> retrieveEntityByEitherFilters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM CustomerEntity c WHERE c." + attributeA + " =:inValueA OR c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }   

    @Override
    public List<RoomBookingEntity> retrieveRoomBookingsByCustomerId(Long customerId) {
//        System.out.println("customerId" + customerId);
        Query query = em.createQuery("SELECT r FROM RoomBookingEntity r WHERE r.customer.customerId = :inCustomerId ORDER BY r.checkInDateTime");
        query.setParameter("inCustomerId", customerId);

        List<RoomBookingEntity> roombookings = query.getResultList();
        
        for (RoomBookingEntity roombooking : roombookings) {
                System.out.println("date: " + roombooking.getCheckInDateTime().toString());    
        }

       
        if(roombookings.isEmpty())
        {
            return new ArrayList<RoomBookingEntity>();
        }
        
        return roombookings;
    }
    
    

}
