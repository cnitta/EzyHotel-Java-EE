/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.commoninfra.session;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Local;
import javax.ws.rs.core.GenericEntity;
import util.entity.CustomerEntity;
import util.entity.PaymentEntity;
import util.entity.RoomBookingEntity;
import util.entity.StaffEntity;
import util.exception.ChangePasswordException;
import util.exception.CustomerIsMemberException;
import util.exception.CustomerIsUnverifiedException;
import util.exception.CustomerNotSignUpException;
import util.exception.CustomerProfileConflictException;
import util.exception.InvalidAccessCodeException;
import util.exception.InvalidLoginCredentialException;
import util.exception.SignUpException;

/**
 *
 * @author berni
 */
@Local
public interface AccountSessionLocal {

    public CustomerEntity customerSignUp(CustomerEntity newMember) throws SignUpException, CustomerIsUnverifiedException, CustomerIsMemberException, CustomerProfileConflictException, NoSuchAlgorithmException, UnsupportedEncodingException;
    
    public boolean customerMemberConfirmation(String accessCode, Long customerId) throws InvalidAccessCodeException;
    
    public CustomerEntity customerLogin(String identityString, String hashedPassword) throws CustomerNotSignUpException, InvalidLoginCredentialException;
    
    public StaffEntity staffLogin(String username, String hashedPassword) throws InvalidLoginCredentialException;
    
    public String staffOnboardPwdGen(StaffEntity newStaff) throws NoSuchAlgorithmException, UnsupportedEncodingException;
   
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword, GenericEntity userEntity, Long userId) throws ChangePasswordException;

    public boolean passwordResetVerification(String accessCodeIdentifier, GenericEntity userEntity, Long userId) throws InvalidAccessCodeException;

    public void passwordReset(String newPassword, String confirmNewPassword, GenericEntity userEntity, Long userId) throws InvalidAccessCodeException;

    public void sendNotificationEmail(GenericEntity userEntity, String typeOfEmail, List<RoomBookingEntity> roomBookings, List<PaymentEntity> payments, String bookingType) throws NoSuchAlgorithmException, UnsupportedEncodingException;



    
}
