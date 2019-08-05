/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import util.entity.CustomerEntity;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import util.entity.AccessCodeEntity;
import util.entity.BookOnBehalfEntity;
import util.entity.PaymentEntity;
import util.entity.RoomBookingEntity;
import util.entity.StaffEntity;
import util.enumeration.AccessCodeTypeEnum;
import util.enumeration.CustomerMembershipEnum;

/**
 *
 * @author berni
 */
public class EmailManager {

    private final String emailServerName = "smtp.gmail.com";
    private final String mailer = "GmailSMTP";
    private final String senderAddress = "ezyhotel.noreply@gmail.com";

    private CommonMethods commons = new CommonMethods();

    public EmailManager() {
    }

    /*
     * TODO: c) Booking Receipt d) Notify Customer of their loyalty points + go
     * customer portal do feedback survey
     */
    // =================================================== FOR STAFFS ===========================================================
    // Staff: ONBOARDING, PASSWORD_RESET
    // Both utilize links
    public void emailNotificationWithLinksForStaff(StaffEntity staff, String receiverAddress, String purpose,
            AccessCodeEntity accessCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String subjectMessage = "";
        switch (purpose) {
        case "ONBOARDING":
            subjectMessage = "[KRHG] Staff Onboarding";
            break;
        case "PWD_RESET":
            subjectMessage = "[KRHG] Admin Portal Password Reset";
            break;
        }
        String emailBody = constructEmailBodyWithLinkForStaff(staff, purpose, accessCode);
        sendEmail(emailBody, senderAddress, receiverAddress, subjectMessage);

    }

    private String constructEmailBodyWithLinkForStaff(StaffEntity staff, String purpose, AccessCodeEntity accessCode)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String emailBody = "", content = "", typeOfLink = "";
        // String link = "http://localhost:8080/EzyHotel-war/webresources";
        String link = "http://localhost:3000";
        String greetings = "Hi " + staff.getName() + ", \n\n";
        emailBody += greetings;

        String hashedAccessCode = commons.hashAccessCode(accessCode.getCodeIdentifier());
        if (purpose.equals("ONBOARDING")) {
            content = "Welcome onboard to EzyHotel Staff Portal! To set your password, click the link below: \n\n";
            typeOfLink = "/accounts/onboarding";
        } else {
            content = "We receive a request to reset this account's password on EzyHotel Admin Portal. Please note that the link will only be valid for 30 minutes. To reset your password, click the link below: \n\n";
            typeOfLink = "/accounts/passwordReset/staff";
        }

        link += typeOfLink + "/" + hashedAccessCode + "/" + staff.getStaffId();

        emailBody += content;
        emailBody += link + "\n\n";

        String signOff = "Kind Regards, \n" + "KRHG Team";
        emailBody += signOff;

        return emailBody;
    }

    // =================================================== FOR CUSTOMERS ===========================================================
    // Customer: CONFIRMATION, PASSWORD RESET, BOOKING, FEEDBACK
    // Methods for Customer to construct subject message, body content, with or without capabiliy URLs

    // FrontDesk Staff -> Room Booking Check in and Check Out Email
    public void emailGeneralNotificationForRoomBooking(RoomBookingEntity roomBooking, String receiverAddress,
            String bookingStatus) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("*** Send General Notification For Room Booking at EmailManager Started ***\n");
        // Check type of message to construct
        // use a sample email
        receiverAddress = "nicholas.nahcy@gmail.com";
        String senderAddress = "ezyhotel.noreply@gmail.com";
        String emailBody = emailGeneralNotificationForRB(roomBooking, bookingStatus, receiverAddress);
        System.out.println("New Email Address: " + receiverAddress);
        String subjectMessage = " ";
        // System.out.println("Booking status at email manager: " + bookingStatus);
        switch (bookingStatus) {
        case "CHECKED_IN":
            System.out.println("Enter check in at subject message");
            subjectMessage = "[KRHG] Check-In successful";
            break;
        case "CHECKED_OUT":
            System.out.println("Enter check out at subject message");
            subjectMessage = "[KRHG] Check-Out successful!";
            break;
        }

        sendEmail(emailBody, senderAddress, receiverAddress, subjectMessage);
        System.out.println("*** Send General Notification For Room Booking at EmailManager Ended ***\n");
    }

    // FrontDesk Staff -> Room Booking Body Email
    private String emailGeneralNotificationForRB(RoomBookingEntity roomBooking, String bookingStatus,
            String receiverAddress) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("*** Send Body Notification For Room Booking at EmailManager Started ***\n");
        
        AccessCodeEntity accessCode = commons.generateAccessCode(AccessCodeTypeEnum.FEEDBACK);
        String hashedAccessCode = commons.hashAccessCode(accessCode.getCodeIdentifier());
        //String link = "http://localhost:8080/EzyHotel-war/webresources";
        String link = "http://ezyhotel.serveo.net/EzyHotel-war/webresources";
        String emailBody = " ", content = " ";
        
        // convert period of stay to string
        Integer obj = new Integer(roomBooking.getNumOfDays());
        String noStayDays = obj.toString();
        switch (bookingStatus) {
        case "CHECKED_IN":
            System.out.println("Enter Check In case");
            content = commons.emailBodyForRoomBookingReceipt(roomBooking, bookingStatus,"");
            emailBody += content;

            break;

        case "CHECKED_OUT":
            System.out.println("Enter Check Out case");

            link += "/feedbacks/" +  hashedAccessCode + "/" + roomBooking.getCustomer().getCustomerId();
            content = commons.emailBodyForRoomBookingReceipt(roomBooking, bookingStatus, link);
            emailBody += content;
            break;
        }

        System.out.println("Email body: " + emailBody);
        System.out.println("*** Send Body Notification For Room Booking at EmailManager Ended ***\n");
        return emailBody;
    }

    public void emailNotificationWithLinksForCustomer(CustomerEntity newCustomer, String receiverAddress,
            AccessCodeEntity accessCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Check type of message to construct
        String emailBody = constructEmailBodyWithLinksForCustomer(newCustomer, receiverAddress, accessCode);

        String subjectMessage = "";

        switch (accessCode.getType()) {
        case PWD_RESET:
            subjectMessage = "[KRHG] Customer Password Reset";
            break;
        case CONFIRMATION:
            subjectMessage = "[KRHG] Customer Membership Confirmation";
            break;
        }        


        // Send the email
        sendEmail(emailBody, receiverAddress, newCustomer.getEmail(), subjectMessage);
    }

    // Customer: CONFIRMATION, PASSWORD RESET, BOOKING, FEEDBACK
    // Methods for Customer to construct subject message, body content, with or
    // without capabiliy URLs
    public void emailGeneralNotificationForCustomer(CustomerEntity newCustomer, String senderAddress, String purpose,
            List<RoomBookingEntity> roomBookings, List<PaymentEntity> payments, String bookingType) {
        // Check type of message to construct
        String emailBody = constructGeneralEmailBodyForCustomer(newCustomer, senderAddress, purpose, roomBookings, payments, bookingType);

        String subjectMessage = "";
        switch (purpose) {
        case "BOOKING":
            subjectMessage = "[KRHG] Here's your booking receipt";
            break;
        }

        // Send the email to the Customer/Organizer first
        sendEmail(emailBody, senderAddress, newCustomer.getEmail(), subjectMessage);

        if (bookingType.equals("BOOK_ON_BEHALF_BOOKING")) {
            String guestEmailBody = commons.emailBodyForBookOnBehalfGuestReceipt(roomBookings.get(0), newCustomer,
                    payments.get(0).getTotalAmount());
            sendEmail(guestEmailBody, senderAddress, ((BookOnBehalfEntity) roomBookings.get(0)).getGuestEmail(),
                    subjectMessage);
        }
    }

    // Construct email body for receipt and feedback survey notification
    private String constructGeneralEmailBodyForCustomer(CustomerEntity newCustomer, String receiverAddress,
            String purpose, List<RoomBookingEntity> roomBookings, List<PaymentEntity> payments, String bookingType) {
        String emailBody = "", content = "";

        switch (purpose) {

            case "BOOKING":
                System.out.println("Room Booking in constructGeneralEmailBodyForCustomer: " + roomBookings.toString());
                content = formatContentByBookingTypes(roomBookings, newCustomer, bookingType, payments);
                break;
        }

        emailBody += content;
        return emailBody;

    }

    private String formatContentByBookingTypes(List<RoomBookingEntity> roomBookings, CustomerEntity newCustomer,
            String bookingType, List<PaymentEntity> payments) {

        System.out.println( "==================================== In the Format Content By Booking Type =================================");
        System.out.println("RoomBookings: " + roomBookings.toString());
        String hotelName = "", hotelAddress = "", fullHotelLocation = "", content = "";
        // Book from same hotel
        hotelName = roomBookings.get(0).getRoomType().getHotel().getName();
        hotelAddress = roomBookings.get(0).getRoomType().getHotel().getAddress();
        fullHotelLocation = hotelName + ", " + hotelAddress;

        System.out.println("Booking Type: " + bookingType);
        if ((bookingType.equals("CORP_BOOKING") || bookingType.equals("GROUP_BOOKING"))) {
            // Group Booking
            if (bookingType.equals("CORP_BOOKING")) {
                // Corporate Booking
                System.out.println("In the Corp template");
                content = commons.emailBodyForCorpBookingReceipt(roomBookings, payments, newCustomer);
            } else {
                // Normal Group Booking
                System.out.println("In the Group template");
                content = commons.emailBodyForGroupBookingReceipt(roomBookings, payments, newCustomer);
            }

        } else {

            if (bookingType.equals("BOOK_ON_BEHALF_BOOKING")) {
                content = commons.emailBodyForBookOnBehalfReceipt(roomBookings.get(0), newCustomer,payments.get(0).getTotalAmount());
            } else {
                content = commons.emailBodyForSingleBookingReceipt(roomBookings.get(0), newCustomer,payments.get(0).getTotalAmount());
            }

        }

        return content;

    }

    // Construct email body for Customer Account Confirmation and Password Reset
    private String constructEmailBodyWithLinksForCustomer(CustomerEntity newCustomer, String receiverAddress,
            AccessCodeEntity accessCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String emailBody = "", content = "", typeOfLink = "";

        String link = "https://ezyhotel-j2e.serveo.net/EzyHotel-war/webresources/";
        // Hash the access code
        String hashedAccessCode = commons.hashAccessCode(accessCode.getCodeIdentifier());

        // Only the content & link differs
        if (accessCode.getType().equals(AccessCodeTypeEnum.CONFIRMATION)) {
            typeOfLink = "/accounts/confirmation";
            link += typeOfLink + "/" + hashedAccessCode + "/" + newCustomer.getCustomerId();

            content = commons.emailBodyForSignUp(newCustomer.getFirstName(), link);
        } else {
            // Password Reset
            // Added the id for checking programmatically that the access code is tampered
            content = "We receive a request to reset this account password on KRHG EzyHotel Customer Portal. Please note that the link will only be valid for 30 minutes. To reset your password, simply click the link below: \n\n";
            typeOfLink = "/accounts/passwordReset/customer";

            // Append the customerId get from pathparam
            link += typeOfLink + "/" + hashedAccessCode + "/" + newCustomer.getCustomerId();
        }

        if (accessCode.getType().equals(AccessCodeTypeEnum.PWD_RESET)) {
            emailBody += content;

            emailBody += link + "\n\n";

            String signOff = "Kind Regards, \n" + "KRHG Team";
            emailBody += signOff;
        } else {
            emailBody += content;
        }

        return emailBody;
    }

    // ==================================== Common Endpoint ====================================================
    private void sendEmail(String emailBody, String senderAddress, String receiverAddress, String subjectMessage) {
        System.out.println("Subject Message Test: " + subjectMessage);
        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");
            Authenticator auth = new SMTPAuthentication();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);

            Message msg = new MimeMessage(session);
            msg.setFrom(InternetAddress.parse(senderAddress, false)[0]);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress, false));
            // msg.addRecipient(Message.RecipientType.CC,<variable for email to CC>);
            msg.setSubject(subjectMessage);
            msg.setContent(emailBody, "text/html; charset=utf-8");
            msg.setHeader("X-Mailer", mailer);

            Date timeStamp = new Date();
            msg.setSentDate(timeStamp);

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in email manager: " + e.getMessage() + ", " + e.getCause());

        }

    }

}
