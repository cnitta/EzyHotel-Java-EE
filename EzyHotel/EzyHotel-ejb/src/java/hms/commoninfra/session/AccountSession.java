/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.commoninfra.session;

import hms.common.CommonMethods;
import hms.common.EmailManager;
import hms.hr.session.StaffSessionLocal;
import hms.prepostarrival.session.OnlineCustomerSessionLocal;
import hms.prepostarrival.session.OnlineRoomBookingSessionLocal;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.GenericEntity;
import util.entity.AccessCodeEntity;
import util.entity.CustomerEntity;
import util.enumeration.CustomerMembershipEnum;
import util.exception.CustomerIsMemberException;
import util.exception.CustomerIsUnverifiedException;
import util.exception.CustomerNotSignUpException;
import util.exception.InvalidLoginCredentialException;
import util.exception.SignUpException;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import static util.entity.LoyaltyTransactionEntity_.roomBooking;
import util.entity.PaymentEntity;
import util.entity.RoomBookingEntity;
import util.entity.StaffEntity;
import util.enumeration.AccessCodeTypeEnum;
import util.enumeration.LogStatusEnum;
import util.enumeration.SystemCategoryEnum;
import util.exception.ChangePasswordException;
import util.exception.CustomNotFoundException;
import util.exception.CustomerProfileConflictException;
import util.exception.InvalidAccessCodeException;

@Stateless
public class AccountSession implements AccountSessionLocal {
    @EJB
    private OnlineRoomBookingSessionLocal onlineRoomBookingSessionLocal;
    @EJB
    private StaffSessionLocal staffSessionLocal;
    @EJB
    private LogSessionLocal logSessionLocal;
    @EJB
    private AccessCodeSessionLocal accessCodeSessionLocal;
    @EJB
    private OnlineCustomerSessionLocal onlineCustomerSessionLocal;

    
    
    
    @PersistenceContext
    private EntityManager em;

    //Used SecureRandom to generate the pepper
    private final String pepper = "9D7B8B99111153BC7338130D686CD393";
       
    //To send email notifications
    private final EmailManager emailManager = new EmailManager();
    
    private CommonMethods commons = new CommonMethods();
   
    @Override
    public CustomerEntity customerSignUp(CustomerEntity newMember) throws SignUpException, CustomerIsUnverifiedException, CustomerIsMemberException, CustomerProfileConflictException, NoSuchAlgorithmException, UnsupportedEncodingException
    {
        try
        {
             //Check if there is existing account for this member, by checking their membership status
            Pair<CustomerEntity, String> membershipStatus = onlineCustomerSessionLocal.checkCustomerMembership(newMember.getEmail());
            System.out.println(membershipStatus.getKey() + "," + membershipStatus.getValue());
            if(membershipStatus.getValue().equals("DOES NOT EXIST") || membershipStatus.getValue().equals("NON_MEMBER") )
            {
                newMember.setMembershipStatus(CustomerMembershipEnum.UNVERIFIED_MEMBER);
                newMember.setFirstJoined(Date.valueOf(LocalDate.now()));
                
                //Compute the hashed password with pepper before saving to DB
                String customerHashedPasswordWithPepper = computeHashedPasswordWithPepper(newMember.getPassword());
                newMember.setPassword(customerHashedPasswordWithPepper);
                onlineCustomerSessionLocal.saveCustomerProfile(newMember);
                
                CompletableFuture.runAsync(()-> {
                    try {
                        System.out.println("In asynchronous CompletableFuture - Customer Sign Up");
                        sendNotificationEmail(new GenericEntity(newMember, CustomerEntity.class), "CONFIRMATION", null,null,"");
                    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                        Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                return newMember;
            }
            else if (membershipStatus.getValue().equals("UNVERIFIED_MEMBER"))
            {
                throw new CustomerIsUnverifiedException();
            }
            else
            {
                throw new CustomerIsMemberException("Customer is already a member.");
            }

        }
        catch(CustomerIsMemberException  e)
        {
           throw new SignUpException("Opps...Sign up is not successfully. Please try again later. Thank you.");
        }
        catch(CustomerIsUnverifiedException e)
        {
           throw new CustomerIsUnverifiedException("You has already signed up as member. Please verify your account.");
        } catch (CustomerProfileConflictException ex) {
           throw new CustomerProfileConflictException("Customer profile with same email but different identification number found in record.");
        }
    }
    
    //This is when the user click on the confirmation link
    @Override
    public boolean customerMemberConfirmation(String hashedAccessCode, Long customerId) throws InvalidAccessCodeException
    {
        try{

          AccessCodeEntity getAccessCode = accessCodeSessionLocal.retrieveAccessCodeByCustomerId(customerId);
           
          String compareHashedAccessCode = commons.hashAccessCode(getAccessCode.getCodeIdentifier());
           
           //If able to retrieve, check if access code still valid
           if(LocalDateTime.now().compareTo(getAccessCode.getExpiryDateTime().toLocalDateTime()) < 0)
           {
               //If current time is not after the expiryDateTime, update the customer membership status
               onlineCustomerSessionLocal.confirmCustomerMembership(getAccessCode.getCustomer());
               
               return true;
           }
           else
           {
               //If not valid, delete the access code entity away
               accessCodeSessionLocal.deleteEntity(getAccessCode.getAccessCodeId());
               throw new InvalidAccessCodeException("The confirmation link has expired.");
           }
        
        }
        catch(Exception e)
        {
          return false;
        }

        
    }


   
    @Override
    public CustomerEntity customerLogin(String identityString, String hashedPassword) throws CustomerNotSignUpException,InvalidLoginCredentialException
    {
        
        try
        {
            //Check if we have this customer's detail in DB - Email or Phone Number
            
            //Check for customer's membership first
            Pair<CustomerEntity,String> membershipStatus = onlineCustomerSessionLocal.checkCustomerMembership(identityString);
            System.out.println("Check membership " + membershipStatus.toString());
            //If is MEMBER, check if provided valid credentials
            if(membershipStatus.getValue().equals("MEMBER"))
            {
                CustomerEntity getCustomer = membershipStatus.getKey();
                String passwordHash = computeHashedPasswordWithPepper(hashedPassword);
                             
                //Check if the password hashes match
                if(getCustomer.getPassword().equals(passwordHash))
                {
                    logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.ALLOW, new GenericEntity(getCustomer,CustomerEntity.class),"");
                    return getCustomer;
                }   
                else
                {
                    logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.DENY, new GenericEntity(getCustomer,CustomerEntity.class),"");
                    throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
                }
   
            }
            else if(membershipStatus.getValue().equals("UNVERIFIED_MEMBER"))
            {
                throw new CustomerIsUnverifiedException("Your account is unverified. Click to send reconfirmation email.");
            }
            else
            {    
                 throw new CustomerNotSignUpException("Invalid login credentials or you do not have an account");
            } 
        }
        catch(CustomerIsUnverifiedException | CustomerNotSignUpException | InvalidLoginCredentialException e)
        {
            throw new InvalidLoginCredentialException("Invalid login credentials or you do not have an account");
        }
    }

    
  //====================================== METHODS CATERING TO STAFF========================================
    @Override
    public StaffEntity staffLogin(String username, String hashedPassword) throws InvalidLoginCredentialException
    {
        
        try
        {
            System.out.println("enter before retrieve");
            StaffEntity getStaff = staffSessionLocal.retrieveEntity("username", username);
            System.out.println("enter after retrieve");
            String passwordHash = computeHashedPasswordWithPepper(hashedPassword);
            System.out.println("getStaff: " + getStaff.getPassword());
            System.out.println("getStaff PasswordHash: " + passwordHash);
            
            if(getStaff.getPassword().equals(passwordHash))
            {
                logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.ALLOW, new GenericEntity(getStaff,StaffEntity.class),"");
                return getStaff;
            }   
            else
            {
                logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.DENY, new GenericEntity(getStaff,StaffEntity.class),"");
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(CustomNotFoundException | InvalidLoginCredentialException e)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public String staffOnboardPwdGen(StaffEntity newStaff) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String hashedRandomPassword = commons.generateRandomPassword();
        
        String hashedPasswordWithPepper = computeHashedPasswordWithPepper(hashedRandomPassword);
        
        return hashedPasswordWithPepper;
    }
    
 //============================== METHODS CATERING TO BOTH CUSTOMER & STAFF =============================
    /*
        Type Of Email
        Customer: CONFIRMATION, PWD_RESET, (BOOKING, FEEDBACK) <- GENERAL for enum
        Staff: ONBOARDING, PWD_RESET (Both are considered PASSWORD RESET)
    */
    //For it to run asynchronously, this cannot have a return value
    @Override
    public void sendNotificationEmail(GenericEntity userEntity, String typeOfEmail, List<RoomBookingEntity> roomBookings, List<PaymentEntity> payments, String bookingType) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
      
        AccessCodeEntity accessCode = setAccessCodeEnum(typeOfEmail);

        accessCode = accessCodeSessionLocal.createAccessCodeRecord(userEntity.getEntity(), accessCode);   
        
        if(userEntity.getEntity().getClass().equals(StaffEntity.class))
        {
            System.out.println("Staff Email run asynchronously");
            StaffEntity staff = (StaffEntity) userEntity.getEntity();
            
            //Update the accessCode with longer duration before expiry
            Timestamp expiryDateTime = Timestamp.valueOf(LocalDateTime.now().plusHours(1));
            accessCode.setExpiryDateTime(expiryDateTime);
            
            emailManager.emailNotificationWithLinksForStaff(staff,staff.getEmail(),typeOfEmail,accessCode);
            
        }
        else
        {
              System.out.println("Customer Email run asynchronously");
              CustomerEntity customer = (CustomerEntity) userEntity.getEntity();
              switch(typeOfEmail)
              {
                  case "CONFIRMATION": case "PWD_RESET":
                      emailManager.emailNotificationWithLinksForCustomer(customer,customer.getEmail(), accessCode);
                      break;
                  case "BOOKING": case "FEEDBACK":
                      //PaymentEntity payment = new PaymentEntity();
                        try {
                            emailManager.emailGeneralNotificationForCustomer(customer, customer.getEmail(), typeOfEmail, roomBookings, payments, bookingType);
                            
                        } catch (Exception ex) {
                            Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     
                      break;
              }
        }
        
    }
    
    @Override
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword, GenericEntity userEntity, Long userId) throws ChangePasswordException
    {
        
        if(!newPassword.equals(confirmNewPassword))
        {
            throw new ChangePasswordException("Incorrect current password provided or New password and Confirm new password do not match");
        }
        else
        {
            try{
                String currentPasswordHashPepper = computeHashedPasswordWithPepper(currentPassword);
                
                if(userEntity.getEntity().getClass().equals(StaffEntity.class))
                {
                    System.out.println("Detect StaffEntity for Change Password");
                    StaffEntity staff = staffSessionLocal.retrieveEntityById(userId);

                    if(staff.getPassword().equals(currentPasswordHashPepper))
                    {
                        String newHashedPepperPassword = computeHashedPasswordWithPepper(newPassword);
                        staff.setPassword(newHashedPepperPassword);
                        staffSessionLocal.updateEntity(staff);
                        logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.ALLOW, new GenericEntity(staff,StaffEntity.class), "CHANGE_PASSWORD");
                    }
                    else
                    {
                        logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.DENY, new GenericEntity(staff,StaffEntity.class), "CHANGE_PASSWORD");
                        throw new ChangePasswordException("Incorrect current password provided or New password and Confirm new password do not match");
                    }
                }
                else
                {
                    System.out.println("Detect CustomerEntity for Change Password");
                    CustomerEntity customer = onlineCustomerSessionLocal.retrieveEntityById(userId);
                    
                    if(customer.getPassword().equals(currentPasswordHashPepper))
                    {
                        String newHashedPepperPassword = computeHashedPasswordWithPepper(newPassword);
                        customer.setPassword(newHashedPepperPassword);
                        onlineCustomerSessionLocal.updateEntity(customer);
                    }
                    else
                    {
                      throw new ChangePasswordException("Incorrect current password provided or New password and Confirm new password do not match");
                    }
                }
            }
            catch(CustomNotFoundException | ChangePasswordException e)
            {
                 throw new ChangePasswordException("Incorrect password provided.");
            }       

        }
    
    }
    

   @Override
   public boolean passwordResetVerification(String accessCodeIdentifier, GenericEntity userEntity, Long userId) throws InvalidAccessCodeException
   {
       try{
           AccessCodeEntity getAccessCode = new AccessCodeEntity();
           String hashAccessCodeIdentifier = "";
           if(userEntity.getEntity().getClass().equals(StaffEntity.class))
           {
               System.out.println("Detect Staff Entity to reset password");
               getAccessCode = accessCodeSessionLocal.retrieveAccessCodeByStaffId(userId);
               System.out.println("Access Code: " + getAccessCode);
               hashAccessCodeIdentifier = commons.hashAccessCode(getAccessCode.getCodeIdentifier());
           }
           else
           {
               System.out.println("Detect Customer Entity to reset password");
               getAccessCode = accessCodeSessionLocal.retrieveAccessCodeByCustomerId(userId);
               hashAccessCodeIdentifier = commons.hashAccessCode(getAccessCode.getCodeIdentifier());
           }
           
           System.out.println("HASHED FROM DB: " + hashAccessCodeIdentifier);
           System.out.println("HASHED FROM REST: "  + accessCodeIdentifier);
           if(hashAccessCodeIdentifier.equals(accessCodeIdentifier))
           {
              System.out.println("enter true"); 
               //If current time is not after the expiryDateTime, update the customer membership status 
              System.out.println("now"+LocalDateTime.now());
              System.out.println("time+"+getAccessCode.getExpiryDateTime().toLocalDateTime());
               return (LocalDateTime.now().compareTo(getAccessCode.getExpiryDateTime().toLocalDateTime())) < 0; 
           }
           else
           {
               System.out.println("enter false"); 
               return false;
           }
 
       }catch(Exception e){
           e.printStackTrace();
            throw new InvalidAccessCodeException("Access code is invalid or The password reset link has expired");
       }
       
   }   
    
   
   @Override
   public void passwordReset(String newPassword, String confirmNewPassword, GenericEntity userEntity, Long userId) throws InvalidAccessCodeException
   {
       
       if(!newPassword.equals(confirmNewPassword))
       {
            throw new InvalidAccessCodeException("The password reset link has expired or the passwords do not match.");
       }
       else
       {
           String newPasswordWithPepper = computeHashedPasswordWithPepper(newPassword);
           
            try{
                if(userEntity.getEntity().getClass().equals(StaffEntity.class))
                {
                    System.out.println("Detect StaffEntity for Password Reset");
                    StaffEntity staff = staffSessionLocal.retrieveEntityById(userId);
                    staff.setPassword(newPasswordWithPepper);
                    staffSessionLocal.updateEntity(staff);
                    
                    //AccessCodeEntity getAccessCodeToDelete = accessCodeSessionLocal.retrieveAccessCodeByStaffId(userId);
                    System.out.println("before delete");
                    //accessCodeSessionLocal.deleteEntity(getAccessCodeToDelete.getAccessCodeId());
                    System.out.println("after delete");
                }
                else
                {
                    System.out.println("Detect CustomerEntity for Password Reset");
                    CustomerEntity customer = onlineCustomerSessionLocal.retrieveEntityById(userId);
                    customer.setPassword(newPasswordWithPepper);
                    onlineCustomerSessionLocal.updateEntity(customer);
                    
                    AccessCodeEntity getAccessCodeToDelete = accessCodeSessionLocal.retrieveAccessCodeByCustomerId(userId);
                    accessCodeSessionLocal.deleteEntity(getAccessCodeToDelete.getAccessCodeId());
                }
            }catch(Exception e){
                throw new InvalidAccessCodeException("The password reset link has expired or the passwords do not match.");
            }
       }
   
   }
   
   
   
    
    
    
    // =========================================== SUPPORTING METHODS =======================================
    private AccessCodeEntity setAccessCodeEnum(String typeOfEmail)
    {
        AccessCodeEntity accessCode = new AccessCodeEntity();
        
        switch(typeOfEmail)
        {
            case "PWD_RESET": case "ONBOARDING":
                accessCode = commons.generateAccessCode(AccessCodeTypeEnum.PWD_RESET);
                break;
            
            case "CONFIRMATION":
                accessCode = commons.generateAccessCode(AccessCodeTypeEnum.CONFIRMATION);
                break;
            
            case "BOOKING": case "FEEDBACK":
                accessCode = commons.generateAccessCode(AccessCodeTypeEnum.GENERAL);
                break;
        }
        
        return accessCode;
    }
    
   
   //Password should have been hashed with crypto-js at the Customer Portal/ Admin Portal
   private String computeHashedPasswordWithPepper(String hashedPassword)
   {
       //Iterations to increase computational cost of password guessing, hashBytes is the length of the keysize
       int iterations = 800, hashBytes = 25; 
       byte[] salt = pepper.getBytes(StandardCharsets.UTF_8);
       
       //Using Password-Key Derivation Function PKDF2(crypto-js(password) + salt)
       PKCS5S2ParametersGenerator keyDerivationFunction = new PKCS5S2ParametersGenerator();
       keyDerivationFunction.init(hashedPassword.getBytes(StandardCharsets.UTF_8), salt, iterations);
       
       //Hash
       byte[] hashedPasswordWithPepper = ((KeyParameter) keyDerivationFunction.generateDerivedMacParameters(8*hashBytes)).getKey();
       
       //Convert to alphanumeric to be stored in the DB
       String hashedPasswordWithPepperString = new BigInteger(1, hashedPasswordWithPepper).toString(16).toUpperCase();
       
       System.out.println("Hashed Password with Pepper: " + hashedPasswordWithPepperString);
       
       return hashedPasswordWithPepperString;
   }
    
}
