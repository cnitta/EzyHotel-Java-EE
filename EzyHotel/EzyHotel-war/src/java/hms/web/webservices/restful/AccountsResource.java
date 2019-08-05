/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.common.CommonMethods;
import hms.commoninfra.session.AccessCodeSessionLocal;
import hms.commoninfra.session.AccountSessionLocal;
import hms.commoninfra.session.LogSessionLocal;
import hms.hr.session.StaffSessionLocal;
import hms.prepostarrival.session.OnlineCustomerSessionLocal;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import util.entity.AccessCodeEntity;
import util.entity.CustomerEntity;
import util.entity.StaffEntity;
import util.enumeration.GenderEnum;
import util.enumeration.LogStatusEnum;
import util.enumeration.SystemCategoryEnum;
import util.exception.CustomerIsMemberException;
import util.exception.CustomerIsUnverifiedException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerNotSignUpException;
import util.exception.InvalidAccessCodeException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PasswordResetException;
import util.exception.SignUpException;
import util.exception.StaffNotFoundException;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("accounts")
public class AccountsResource {

    LogSessionLocal logSessionLocal = lookupLogSessionLocal();
    StaffSessionLocal staffSessionLocal = lookupStaffSessionLocal();
    OnlineCustomerSessionLocal onlineCustomerSessionLocal = lookupOnlineCustomerSessionLocal();

    AccessCodeSessionLocal accessCodeSessionLocal = lookupAccessCodeSessionLocal();

    AccountSessionLocal accountSessionLocal = lookupAccountSessionLocal();

    @Context
    private UriInfo context;

    private CommonMethods commons = new CommonMethods();

    /**
     * Creates a new instance of CustomerResource
     */
    public AccountsResource() {
    }

    /**
     * Retrieves representation of an instance of
     * hms.web.webservices.restful.AccountsResource
     *
     * @param customerJson
     * @return an instance of java.lang.String
     * @throws util.exception.SignUpException
     * @throws util.exception.CustomerIsUnverifiedException
     */
    @POST
    @Path("customerSignUp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerSignUp(JsonObject customerJson) throws SignUpException, CustomerIsUnverifiedException, CustomerIsMemberException {
        try {
            //if can retrieve
            List<CustomerEntity> getCustomer = onlineCustomerSessionLocal.retrieveCustomerByIdentityString(customerJson.getString("email"));
            if (!getCustomer.isEmpty()) {
                throw new CustomerIsMemberException("You are already a member. Please login with your account.");
            } else {

                if (!customerJson.getString("confirmedPassword").equals(customerJson.getString("hashedPassword"))) {
                    throw new Exception("Passwords provided do not match.");
                }

                CustomerEntity createCustomer = new CustomerEntity();
                createCustomer.setFirstName(customerJson.getString("firstName"));
                createCustomer.setLastName(customerJson.getString("lastName"));
                createCustomer.setEmail(customerJson.getString("email"));
                createCustomer.setPassword(customerJson.getString("hashedPassword"));
                createCustomer.setPhoneNum(customerJson.getString("phoneNum"));
                createCustomer.setCountry(customerJson.getString("country"));
                createCustomer.setCustIdentity(customerJson.getString("custIdentity"));
                switch (customerJson.getString("gender")) {
                    case "FEMALE":
                        createCustomer.setGender(GenderEnum.FEMALE);
                        break;
                    case "MALE":
                        createCustomer.setGender(GenderEnum.MALE);
                        break;
                }

                //Format the customer birthDate into Date
                String customerBirthDateString = customerJson.getString("birthDate");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate customerBirthDate = LocalDate.parse(customerBirthDateString, dateTimeFormatter);
                createCustomer.setBirthDate(Date.valueOf(customerBirthDate));

                createCustomer.setFirstJoined(Date.valueOf(LocalDate.now()));
                CustomerEntity customer = accountSessionLocal.customerSignUp(createCustomer);
                logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.PRE_POST_ARRIVAL_MS, LogStatusEnum.CREATE, new GenericEntity(customer, CustomerEntity.class), "SIGN_UP");
                return Response.status(Response.Status.CREATED).entity("Congratulations " + customer.getFirstName() + ", you have signed up successfully! Please verify your membership via your email.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    //TODO (FrontEnd): Upon clicking the link, show that the user has been confirmed, redirect to page to show confirmation done on customer portal, redirect to homepage of customer portal
    @GET
    @Path("confirmation/{hashedAccessCode}/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerMembershipConfirm(@PathParam("hashedAccessCode") String hashedAccessCode, @PathParam("customerId") Long customerId) throws InvalidAccessCodeException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try {

            boolean confirmationStatus = accountSessionLocal.customerMemberConfirmation(hashedAccessCode, customerId);
            if (confirmationStatus) {
                //Stated on 14 March 2019
                //TODO (FrontEnd): Create a page at frontend to show message, redirect to that page. Then can even redirect to the homepage of the Customer Portal
                final String BASE_URL = "http://localhost:81/confirmation.html";
                URI urlLink = UriBuilder.fromPath(BASE_URL).build();

                System.out.println("Redirect to:" + urlLink.toString());

                //delete the access code entity away
                AccessCodeEntity getAccessCode = accessCodeSessionLocal.retrieveAccessCodeByCustomerId(customerId);
                System.out.println("Access Code: " + getAccessCode.toString());
                accessCodeSessionLocal.deleteEntity(getAccessCode.getAccessCodeId());

                return Response.status(Response.Status.SEE_OTHER).location(URI.create(urlLink.toString())).build();
            } else {
                throw new InvalidAccessCodeException("The confirmation link is invalid or has expired, please request for a new confirmation link.");
            }

        } catch (NotFoundException | InvalidAccessCodeException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("customerLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(JsonObject loginCredential) throws InvalidLoginCredentialException, CustomerNotSignUpException {

        try {
            CustomerEntity customer = accountSessionLocal.customerLogin(loginCredential.getString("identityString"), loginCredential.getString("hashedPassword"));
            System.out.println("Customer " + customer.getEmail() + " with phone number " + customer.getPhoneNum() + " login successfully.");
            System.out.println(customer.toString());

            //Yea,as per Wai Kit's method, just return customerId, then in the customer profile page, retrieve the customer information
            //This can help with security, by retrieve what is needed at the moment
            JsonObjectBuilder customerJson = Json.createObjectBuilder().add("customerId", customer.getCustomerId())
                    .add("firstName", customer.getFirstName())
                    .add("lastName", customer.getLastName())
                    .add("firstJoined", customer.getFirstJoined().toString());
            logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.ALLOW, new GenericEntity(customer, CustomerEntity.class), "");
            return Response.status(Response.Status.OK).entity(customerJson.build()).build();
        } catch (InvalidLoginCredentialException | CustomerNotSignUpException e) {
            CustomerEntity customerAttempt = new CustomerEntity();
            customerAttempt.setEmail(loginCredential.getString("identityString"));
            logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.DENY, new GenericEntity(customerAttempt, CustomerEntity.class), "");
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }

    }

    /*
     * @param loginCredential
     * @return
     * @throws InvalidLoginCredentialException
     * @throws CustomerNotSignUpException
     */
    @POST
    @Path("staffLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response staffLogin(JsonObject loginCredential) throws InvalidLoginCredentialException {

        try {
            System.out.println("Username: " + loginCredential.getString("username") + ", password: " + loginCredential.getString("hashedPassword"));
            System.out.println("before login");
            StaffEntity staff = accountSessionLocal.staffLogin(loginCredential.getString("username"), loginCredential.getString("hashedPassword"));
            System.out.println("after login");
            staff.setPassword(null);
            JsonObjectBuilder staffIdJson = Json.createObjectBuilder().add("staffId", staff.getStaffId()).add("jobTitle", staff.getJobTitle()).add("department", staff.getDepartment().toString()).add("name", staff.getName());
            logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.ALLOW, new GenericEntity(staff, StaffEntity.class), ""); 
            return Response.status(Response.Status.OK).entity(staffIdJson.build()).build();
        } catch (InvalidLoginCredentialException e) {
            StaffEntity staffAttempt = new StaffEntity();
            staffAttempt.setUsername(loginCredential.getString("username"));
            logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.COMMON_INFRASTRUCTURE, LogStatusEnum.DENY, new GenericEntity(staffAttempt, StaffEntity.class), "");
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("passwordResetRequest/customer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerPasswordResetRequest(JsonObject passwordReq) throws CustomerNotSignUpException, CustomerIsMemberException, CustomerNotFoundException, PasswordResetException {
        try {
            CustomerEntity customer = onlineCustomerSessionLocal.retrieveEntity("email", passwordReq.getString("identityString"));

            CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("Running asynchronously for customer password reset");
                    accountSessionLocal.sendNotificationEmail(new GenericEntity(customer, CustomerEntity.class), "PWD_RESET", null, null, "");
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    Logger.getLogger(AccountsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return Response.status(Response.Status.OK).entity("A password reset email has been sent to you. Please check your email.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }

    }

    @POST
    @Path("passwordResetRequest/staff")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response staffPasswordResetRequest(JsonObject passwordReq) throws StaffNotFoundException, PasswordResetException {
        try {
            StaffEntity staff = staffSessionLocal.retrieveEntity("email", passwordReq.getString("identityString"));

            CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("Running asynchronously for staff password reset");
                    accountSessionLocal.sendNotificationEmail(new GenericEntity(staff, StaffEntity.class), "PWD_RESET", null, null, "");
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                    Logger.getLogger(AccountsResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return Response.status(Response.Status.OK).entity("A password reset email has been sent to you. Please check your email.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("passwordReset/customer/{hashedAccessCode}/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerPasswordResetRedirect(@PathParam("hashedAccessCode") String hashedAccessCode, @PathParam("customerId") Long customerId) {

        try {
            boolean customerPasswordResetStatus = accountSessionLocal.passwordResetVerification(hashedAccessCode, new GenericEntity(new CustomerEntity(), CustomerEntity.class), customerId);

            if (customerPasswordResetStatus) {
                //TODO: To redefine the link once Customer Portal is port over to new template
                final String BASE_URL = "http://localhost:81/";
                final String QUERY_PARAM = "PasswordReset/passwordReset.html";
                URI urlLink = UriBuilder.fromPath(BASE_URL).path(QUERY_PARAM).build();
                System.out.println("Password Reset Access Code is valid");

                //Redirect to CustomerPortal URI - Password Reset Page
                String finalLink = urlLink.toString() + "?" + hashedAccessCode + "&" + customerId.toString();
                System.out.println("Rediect to: " + finalLink);

                return Response.status(Response.Status.SEE_OTHER).location(URI.create(finalLink)).build();
                //return Response.status(Response.Status.OK).entity("Password has been resetted successfully").build();
            } else {
                //NOTE: Not included in 1st system release
                //Redirect to CustomerPortal URI - Password Reset Page -> Show resend password resend link
                final String BASE_URL = "http://localhost:81/";
                //TODO: To replace redirection path
                final String QUERY_PARAM = "PasswordReset/passwordResend.html";
                final String CUSTOMERID_PARAM = customerId.toString();
                URI urlLink = UriBuilder.fromPath(BASE_URL).path(QUERY_PARAM).path(CUSTOMERID_PARAM).build();

                System.out.println("Password Reset Access Code is invalid");
                System.out.println("Redirect to: " + urlLink);

                return Response.status(Response.Status.SEE_OTHER).location(urlLink).build();
                //return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Password Reset Link").build();
            }
        } catch (InvalidAccessCodeException | IllegalArgumentException | UriBuilderException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    //KIV: To create the set password page for Admin Portal when staff click on the email
    @GET
    @Path("onboarding")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void staffOnboardingRedirect(@PathParam("hashedAccessCode") String hashedAccessCode, @PathParam("staffId") Long staffId) {
        try {
            boolean staffPasswordResetStatus = accountSessionLocal.passwordResetVerification(hashedAccessCode, new GenericEntity(new CustomerEntity(), CustomerEntity.class), staffId);

            if (staffPasswordResetStatus) {
                System.out.println("Password Reset Access Code is valid");
                final String BASE_URL = "http://localhost:3000/";
                //TODO (Chinsong): Set the path to redirect to AdminPortal URI - Password Reset Page
                final String QUERY_PARAM = "PasswordReset/passwordReset.html";
                URI urlLink = UriBuilder.fromPath(BASE_URL).path(QUERY_PARAM).build();

     //return Response.status(Response.Status.SEE_OTHER).location(URI.create(finalLink)).build();
                //return Response.status(Response.Status.OK).entity("Password has been resetted successfully").build();
            } else {
                throw new InvalidAccessCodeException("The password link has expired.");
            }
        } catch (InvalidAccessCodeException | IllegalArgumentException | UriBuilderException e) {
            //return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    //KIV: To create the password reset page for Admin Portal
    @GET
    @Path("passwordReset/staff/{hashedAccessCode}/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void staffPasswordResetRedirect(@PathParam("hashedAccessCode") String hashedAccessCode, @PathParam("staffId") Long staffId) {

        try {
            boolean staffPasswordResetStatus = accountSessionLocal.passwordResetVerification(hashedAccessCode, new GenericEntity(new CustomerEntity(), CustomerEntity.class), staffId);

            if (staffPasswordResetStatus) {
                System.out.println("Password Reset Access Code is valid");
                final String BASE_URL = "http://localhost:3000/";
                //TODO (Chinsong): Set the path to redirect to AdminPortal URI - Password Reset Page
                final String QUERY_PARAM = "PasswordReset/passwordReset.html";
                URI urlLink = UriBuilder.fromPath(BASE_URL).path(QUERY_PARAM).build();

     //return Response.status(Response.Status.SEE_OTHER).location(URI.create(finalLink)).build();
                //return Response.status(Response.Status.OK).entity("Password has been resetted successfully").build();
            } else {
                throw new InvalidAccessCodeException("The password link has expired.");
            }
        } catch (InvalidAccessCodeException | IllegalArgumentException | UriBuilderException e) {
            //return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    //This is the actual changing of password from the password reset page
    @PUT
    @Path("passwordReset/customer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerPasswordReset(JsonObject passwordResetParams) {
        try {
            String hashedAccessCode = passwordResetParams.getString("accessCode");
            Integer customerId = passwordResetParams.getInt("customerId");
            String newPassword = passwordResetParams.getString("newPassword");
            String confirmNewPassword = passwordResetParams.getString("confirmNewPassword");

            Long customerIdLong = customerId.longValue();
            System.out.println("Customer Id: " + customerId);
            boolean customerPasswordResetStatus = accountSessionLocal.passwordResetVerification(hashedAccessCode, new GenericEntity(new CustomerEntity(), CustomerEntity.class), customerIdLong);
            if (customerPasswordResetStatus) {
                //Update the customer credentials
                accountSessionLocal.passwordReset(newPassword, confirmNewPassword, new GenericEntity(new CustomerEntity(), CustomerEntity.class), customerIdLong);
                return Response.status(Response.Status.OK).entity("You have successfully changed your password.").build();
            } else {
                throw new InvalidAccessCodeException("The password reset is not successful.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    //This is the actual changing of password from the password reset page
    @PUT
    @Path("passwordReset/staff")
    @Produces(MediaType.APPLICATION_JSON)
    public Response staffPasswordReset(JsonObject passwordResetParams) {
        try {
            String hashedAccessCode = passwordResetParams.getString("accessCode");
            Integer staffId = passwordResetParams.getInt("staffId");
            String newPassword = passwordResetParams.getString("newPassword");
            String confirmNewPassword = passwordResetParams.getString("confirmNewPassword");

            Long staffIdLong = staffId.longValue();
            System.out.println("staffId: " + staffId);
            boolean staffPasswordResetStatus = accountSessionLocal.passwordResetVerification(hashedAccessCode, new GenericEntity(new StaffEntity(), StaffEntity.class), staffIdLong);
            System.out.println("after status");
            System.out.println("status is " + staffPasswordResetStatus);
            if (staffPasswordResetStatus) {
                System.out.println("enter status");
                //Update the customer credentials
                accountSessionLocal.passwordReset(newPassword, confirmNewPassword, new GenericEntity(new StaffEntity(), StaffEntity.class), staffIdLong);

                //Then delete the access code away to so-called make it permanently expired
                AccessCodeEntity accessCodeToDelete = accessCodeSessionLocal.retrieveAccessCodeByStaffId(staffIdLong);
                accessCodeSessionLocal.deleteEntity(accessCodeToDelete.getAccessCodeId());
                System.out.println("finish delete");
                return Response.status(Response.Status.OK).entity("You have successfully changed your password.").build();
            } else {
                throw new InvalidAccessCodeException("The password reset is not successful.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/changePassword/customer/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerChangePassword(@PathParam("customerId") Long customerId, JsonObject newPasswordCredentials) {
        try {
            String currentPassword = newPasswordCredentials.getString("currentPassword");
            String newPassword = newPasswordCredentials.getString("newPassword");
            String confirmNewPassword = newPasswordCredentials.getString("confirmNewPassword");
            accountSessionLocal.changePassword(currentPassword, newPassword, confirmNewPassword, new GenericEntity(new CustomerEntity(), CustomerEntity.class), customerId);
            return Response.status(Response.Status.OK).entity("Password has been changed successfully").build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("changePassword/staff/{staffId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response staffChangePassword(@PathParam("staffId") Long staffId, JsonObject newPasswordCredentials) {
        try {
            String currentPassword = newPasswordCredentials.getString("currentPassword");
            String newPassword = newPasswordCredentials.getString("newPassword");
            String confirmNewPassword = newPasswordCredentials.getString("confirmNewPassword");
            accountSessionLocal.changePassword(currentPassword, newPassword, confirmNewPassword, new GenericEntity(new StaffEntity(), StaffEntity.class), staffId);
            return Response.status(Response.Status.OK).entity("Password has been changed successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    //============================================================================== ENTERPRISE BEANS ===============================================================
    private AccountSessionLocal lookupAccountSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AccountSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/AccountSession!hms.commoninfra.session.AccountSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AccessCodeSessionLocal lookupAccessCodeSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AccessCodeSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/AccessCodeSession!hms.commoninfra.session.AccessCodeSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private OnlineCustomerSessionLocal lookupOnlineCustomerSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OnlineCustomerSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/OnlineCustomerSession!hms.prepostarrival.session.OnlineCustomerSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private StaffSessionLocal lookupStaffSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StaffSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/StaffSession!hms.hr.session.StaffSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private LogSessionLocal lookupLogSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LogSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/LogSession!hms.commoninfra.session.LogSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
