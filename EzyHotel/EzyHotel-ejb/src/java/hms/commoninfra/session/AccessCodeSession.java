/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.commoninfra.session;

import hms.common.CommonMethods;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.AccessCodeEntity;
import util.entity.CustomerEntity;
import util.entity.StaffEntity;
import util.enumeration.AccessCodeTypeEnum;

/**
 *
 * @author berni
 */
@Stateless
public class AccessCodeSession implements AccessCodeSessionLocal{
    @EJB
    private LogSessionLocal logSessionLocal;
    
    @PersistenceContext
    private EntityManager em;

    private final CommonMethods commons = new CommonMethods();
    
    /*
    Create CREATE functions for:
      - Create Access Code to use in link for 2 events:
        a) Customer Membership Confirmation
        b) Password Reset
    */

    @Override
    public AccessCodeEntity createAccessCodeRecord(Object userEntity, AccessCodeEntity accessCode)
    {
        
        List<AccessCodeEntity> checkForExistingAccessCodes = new ArrayList<>();

 
        
        if(userEntity.getClass().equals(StaffEntity.class))
        {
            StaffEntity staff = (StaffEntity)userEntity;
            System.out.println("Type: " + accessCode.getType() + ", Staff User id: " + staff.getStaffId());
            checkForExistingAccessCodes = retrieveAccessCodeByStaffIdAndType(staff.getStaffId(),accessCode.getType());
        }
        else
        {
           CustomerEntity customer = (CustomerEntity)userEntity;
           System.out.println("Type: " + accessCode.getType() + ", Customer User id: " + customer.getCustomerId());
           checkForExistingAccessCodes = retrieveAccessCodeByStaffIdAndType(customer.getCustomerId(),accessCode.getType());
        }    
 
        System.out.println("Size: " + checkForExistingAccessCodes.size());
        if(!checkForExistingAccessCodes.isEmpty())
        {
            checkForExistingAccessCodes.forEach((accessCodeToDelete)->{
                deleteEntity(accessCodeToDelete.getAccessCodeId());
            });
            
        }
        
        AccessCodeEntity newAccessCodeRecord = checkCodeIdentifierUnique(accessCode,userEntity);
        newAccessCodeRecord = createEntity(newAccessCodeRecord);
        
        return newAccessCodeRecord;
    }
    
    
   @Override
   public List<AccessCodeEntity> retrieveAccessCodeByStaffIdAndType(Long staffId, AccessCodeTypeEnum accessCodeType)
   {
        String queryInput = "select a from AccessCodeEntity a where a.customer.customerId =:inValueA and a.type = util.enumeration.AccessCodeTypeEnum." + accessCodeType;
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", staffId);

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            return new ArrayList<>();
        }
   
   }
   
   @Override
   public List<AccessCodeEntity> retrieveAccessCodeByCustomerIdAndType(Long customerId, AccessCodeTypeEnum accessCodeType)
   {
        String queryInput = "select a from AccessCodeEntity a where a.customer.customerId =:inValueA and a.type = util.enumeration.AccessCodeTypeEnum." + accessCodeType;
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", customerId);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            return new ArrayList<>();
        }
   }
    
    @Override
    public AccessCodeEntity retrieveAccessCodeByCustomerId(Long customerId)
    {
        String queryInput = "SELECT c FROM AccessCodeEntity c WHERE c.customer.customerId =:inValue" ;
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", customerId);
        try {
            return (AccessCodeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    @Override
    public AccessCodeEntity retrieveAccessCodeByStaffId(Long staffId)
    {
        String queryInput = "SELECT c FROM AccessCodeEntity c WHERE c.staff.staffId =:inValue" ;
        Query query = em.createQuery(queryInput);
        System.out.println("saffId: " + staffId);
        query.setParameter("inValue", staffId);
        try {
            System.out.println("get accessCode");
            return (AccessCodeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }  
    
    
    //============================== SUPPORTING METHODS ==========================================
    
    private AccessCodeEntity checkCodeIdentifierUnique(AccessCodeEntity accessCode, Object userEntity){
        boolean checkCodeIdentifierIsUnique = false;
                
        //Check if the codeIdentifier
        AccessCodeEntity newAccessCodeRecord = new AccessCodeEntity();
        String getAccessCode = "";
        
        do
        {
            //Try to retrieve the access code in the database
            try{
                getAccessCode = retrieveEntity("codeIdentifier", accessCode.getCodeIdentifier()).getCodeIdentifier();
            }catch(Exception e){
                
                if(getAccessCode == null || getAccessCode.isEmpty()) //Means accessCode is unique
                {
                    //Create a newAccessCodeRecord
                    newAccessCodeRecord = createEntity(accessCode);
                    checkCodeIdentifierIsUnique = true;
                    

                    if(userEntity.getClass().equals(StaffEntity.class))
                    {
                        StaffEntity staff = (StaffEntity)userEntity;
                        accessCode.setStaff(staff);
                    }
                    else
                    {
                        CustomerEntity customer = (CustomerEntity)userEntity;
                        accessCode.setCustomer(customer);
                    }
 
                }
                else
                {
                    //Re-generate the code identifier
                    accessCode.setCodeIdentifier(commons.generateRandomAlphanumeric(10));
                }
            }
            
        }while(!checkCodeIdentifierIsUnique);
    
    
        return accessCode;
    
    }
    
    
    
    //================ Codes generated with Generated Method Body Template ========================
    
    
    
    @Override
    public AccessCodeEntity retrieveEntityById(Long id) {
        AccessCodeEntity entity = em.find(AccessCodeEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("AccessCodeEntity ID " + id + " does not exist!");
        }
    }
    @Override
    public AccessCodeEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM AccessCodeEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (AccessCodeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<AccessCodeEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM AccessCodeEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }    

    
    public AccessCodeEntity createEntity(AccessCodeEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    public List<AccessCodeEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM AccessCodeEntity c");
        return query.getResultList();
    }

    
    @Override
    public void deleteEntity(Long id) {
        AccessCodeEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    public void updateEntity(AccessCodeEntity entity) {
        em.merge(entity);
    }




}
