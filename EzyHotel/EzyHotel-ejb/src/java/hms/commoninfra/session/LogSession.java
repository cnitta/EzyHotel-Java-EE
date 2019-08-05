/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.commoninfra.session;

import hms.common.CrudService;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericEntity;
import util.entity.CustomerEntity;
import util.entity.LogEntity;
import util.entity.RoomBookingEntity;
import util.entity.StaffEntity;
import util.enumeration.LogStatusEnum;
import static util.enumeration.LogStatusEnum.ALLOW;
import util.enumeration.SystemCategoryEnum;

/**
 *
 * @author berni
 */
@Stateless
public class LogSession implements LogSessionLocal, CrudService<LogEntity>, Serializable {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void centralLoggingGeneration(SystemCategoryEnum systemCategory, LogStatusEnum logStatus, GenericEntity entity, String specialCode)
    {
        LogEntity newLog = new LogEntity();
        newLog.setSystemCategory(systemCategory);
        newLog.setLogStatus(logStatus);
        
        switch(systemCategory)
        {
            case COMMON_INFRASTRUCTURE: //Deals with Customer/Staff Login, Password Reset, Change Password, Staff Onboarding Entity: Customer, Staff
                logGenForCommonInfrastructure(newLog, entity, specialCode);
                break;
             //Customer Sign Up, Create RoomBooking Updates
             case PRE_POST_ARRIVAL_MS:   //Customer Profile Update, Customer Sign Up
                logGenForPrePostArrival(newLog, entity, specialCode);
                break;
            case FRONTDESK_MS: // Deals with CRUD of customer and room booking physically allocated by Staff
                break;
                
            //Not going to cater to the rest of the system
                
        }
    }

//================================================ FRONT DESK =================================================================
    



//================================================ PRE/POST ARRIVAL ============================================================
    //SIGN_UP
    private void logGenForPrePostArrival(LogEntity log, GenericEntity entity, String specialCode)
    {     
        String message = "";
        System.out.println("Entity class check:" + entity.getEntity().toString());
        if(entity.getEntity().getClass().equals(CustomerEntity.class)){
            CustomerEntity customer = (CustomerEntity)entity.getEntity();
            
            if(specialCode.equals("SIGN_UP")){
                message = "Customer ID " + customer.getCustomerId() + ", " + customer.getFirstName() + " has signed up as member.";
            }else{
                switch(log.getLogStatus()){
                case CREATE: case UPDATE:
                    message = "Customer profile for " + customer.getFirstName() + " " + customer.getLastName() + " has been " + log.getLogStatus().toString().toLowerCase() + "d.";
                    break;
                case DELETE:
                    message = "Customer ID " + customer.getCustomerId() + ", " + customer.getFirstName() + " has terminated their membership.";
                    break;
                }
            }
  
        }else{
            
            RoomBookingEntity roomBooking = (RoomBookingEntity)entity.getEntity();
            if(specialCode.equals("BOOKING")){
                message = "Room Booking " + roomBooking.getReservationNumber() + " for room type - " + roomBooking.getRoomType().getName() + " has been " + log.getLogStatus().toString().toLowerCase() + "d.";
            }
             
        }
        
        log.setMessage(message);
        log.setDateTimestamp(new Timestamp(System.currentTimeMillis()));
        createLogRecord(log); 
    }



//================================================ COMMON INFRASTRUCTURE =======================================================
    private void logGenForCommonInfrastructure(LogEntity log, GenericEntity entity, String specialCode)
    {
        String message = "";
        
        if(!specialCode.equals("")){
            message = passwordChangeMessage(entity, specialCode);
        }else{
            switch(log.getLogStatus()){
                case ALLOW:
                    message = allowMessageCommonInfrastructure(entity);
                    break;
                case DENY:
                    message = denyMessageCommonInfrastructure(entity);
                    break;
                case CREATE: case DELETE: case UPDATE:
                    message = crudMessageForCommonInfrastructure(log, entity);
                    break;
            }        
        }

        log.setMessage(message);
        log.setDateTimestamp(new Timestamp(System.currentTimeMillis()));
        createLogRecord(log);
        
    }
    
    
    private String passwordChangeMessage(GenericEntity entity, String specialCode){
        String message = "", identity = "";
        
        StaffEntity staff = null;
        CustomerEntity customer = null;
        
        if(entity.getEntity().getClass().equals(StaffEntity.class)){
            staff = (StaffEntity)entity.getEntity();
            identity = "account " + staff.getUsername();
        }else{
            customer = (CustomerEntity)entity.getEntity();
            identity = "account " + customer.getEmail();
        }

        
        switch(specialCode)
        {
            case "PWD_RESET":
                message = "Password for " + identity + " is being been resetted.";
                break;
            case "CHANGE_PASSWORD":
                message = "Password for " + identity + " is being changed from the Customer Portal.";
                break;
        }   
        
        return message;
    }
    
    private String denyMessageCommonInfrastructure(GenericEntity entity)
    {
      
        String identity = "", finalMessage = "";
        
        if(entity.getEntity().getClass().equals(StaffEntity.class)){
            StaffEntity staff = (StaffEntity)entity.getEntity();
            identity = staff.getUsername();
        }else{
            CustomerEntity customer = (CustomerEntity)entity.getEntity();
            identity = customer.getEmail();
        }
        
        finalMessage = "User with the identity of " + identity + " has provided invalid login credentials.";
    
        return finalMessage;
    }


    private String allowMessageCommonInfrastructure(GenericEntity entity)
    {
 
        String identity = "", finalMessage = "";
        
        if(entity.getEntity().getClass().equals(StaffEntity.class)){
            StaffEntity staff = (StaffEntity)entity.getEntity();
            identity = staff.getUsername();
        }else{
            CustomerEntity customer = (CustomerEntity)entity.getEntity();
            identity = customer.getEmail();
        }
        
        finalMessage = "User with the identity of " + identity +  " has login successfully.";
    
        return finalMessage;
    }
   

    private String crudMessageForCommonInfrastructure(LogEntity log, GenericEntity entity)
    {
        String message = "";

        if(entity.getEntity().getClass().equals(StaffEntity.class)){
            StaffEntity staff = (StaffEntity)entity.getEntity();
            message = "Staff id " + staff.getStaffId() + " with username " + staff.getUsername() + " has been " + log.getLogStatus().toString().toLowerCase();
        }else{
            CustomerEntity customer = (CustomerEntity)entity.getEntity();
            message = "Customer id " + customer.getCustomerId() + ",  " + customer.getFirstName() + " has been " + log.getLogStatus().toString().toLowerCase();
        }
                
        return message;
    }

    
 //================================================ COMMON ENDPOINT OF FLOW =======================================================
    
    //Common endpoint, i.e. create the log
    private void createLogRecord(LogEntity newLog)
    {
           LogEntity logGenerated = createEntity(newLog);
           System.out.println(logGenerated.getLogiId() + ": " + logGenerated.getSystemCategory() + " - " + logGenerated.getMessage());
    }
    

//================ Codes generated with Generated Method Body Template ========================
    @Override
    public LogEntity createEntity(LogEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public LogEntity retrieveEntityById(Long id) {
        LogEntity entity = em.find(LogEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("LogEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<LogEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM LogEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        LogEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public LogEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM LogEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (LogEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<LogEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM LogEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    
    @Override
    public List<LogEntity> retrieveEntityByEitherFilters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM LogEntity c WHERE c." + attributeA + " =:inValueA OR c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public void updateEntity(LogEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
