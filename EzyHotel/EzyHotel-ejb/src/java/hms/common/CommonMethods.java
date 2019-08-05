/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import util.entity.AccessCodeEntity;
import util.entity.BookOnBehalfEntity;
import util.entity.CustomerEntity;
import util.entity.GroupBookingEntity;
import util.entity.PaymentEntity;
import util.entity.RoomBookingEntity;
import util.enumeration.AccessCodeTypeEnum;

/**
 *
 * @author berni
 */
public class CommonMethods implements Serializable {

    private final String signUpTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Customer Sign Up</title> <style type='text/css'> body{margin: 0; padding: 0; min-width: 100%!important;}.content{width: 100%; max-width: 600px; border: 1px solid #EAE2D1;}.main{padding: 30px 0; color: #acbac4; line-height: 20px; font-family: sans-serif;}.main a{color: #acbac4; text-decoration: none;}.eheader{padding: 20px;}.innerpadding{padding: 30px;}.borderbottom{border-bottom: 1px solid #e6eff2;}.title{text-align: center; text-transform: uppercase;}.title a{font-size: 30px; line-height: 40px; color: #fff;}.subhead{text-align: center; font-size: 12px; color: #fff;}.h1{text-align: center; font-size: 30px; color: #fff;}.h2{padding: 0 0 15px 0; font-size: 16px; line-height: 28px; font-weight: bold; color: #000000;}.h3{font-size: 15px; text-decoration: underline;}.bodycopy{font-size: 14px; line-height: 22px; color: #000000;}.details{font-size: 14px;}.mssg{font-size: 12px; text-align: center;}.footer{padding: 20px 30px 15px 30px; border-top: 1px solid #f5f5f5;}.footer a{color: #1dc1f8;}.footercopy{font-size: 15px; color: #777777;}.footercopy a{color: #1dc1f8;}.social a{font-size: 14px;}@media screen and (max-width: 600px){.main{padding: 0;}}</style></head><body yahoo bgcolor='#f5eddb'> <table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'> <tr> <td> <table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'> <tr> <td bgcolor='#1dc1f8' class='eheader'> <table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td height='70'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='title' style='padding: 5px 0 0 0;'> <a href='#'>KRHG Membership Confirmation</a> </td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='h2'>Hello #CUSTOMER_FIRSTNAME,</td></tr><tr> <td class='bodycopy'>Thank you for signing up as a KRHG member! To activate your account, simply click the link below:</td></tr><tr><td><br></td></tr><tr> <td class='bodycopy'><a href='#LINK'> #LINK </a></td></tr></table> </td></tr><tr> <td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr> <td class='footer' bgcolor='#f5f5f5'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved. </td></tr></table> </td></tr></table> </td></tr></table></body></html>";
    private final String singleBookingReceiptTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Room Booking Details</title> <style type='text/css'> body{margin: 0; padding: 0; min-width: 100%!important;}.content{width: 100%; max-width: 600px; border: 1px solid #EAE2D1;}.main{padding: 30px 0; color: #acbac4; line-height: 20px; font-family: sans-serif;}.main a{color: #acbac4; text-decoration: none;}.eheader{padding: 20px;}.innerpadding{padding: 30px;}.borderbottom{border-bottom: 1px solid #e6eff2; color: #000000;}.title_reservationNum{font-weight: bold; text-align: left; text-transform: uppercase; padding-bottom: 10px;}.title{text-align: center; text-transform: uppercase;}.title p{font-size: 30px; line-height: 15px; color: #fff;}.title a{font-size: 30px; line-height: 40px; color: #fff;}.subhead{text-align: center; font-size: 8px; color: #fff;}.h1{text-align: center; font-size: 30px; color: #fff;}.h2{padding: 0 0 15px 0; font-size: 16px; line-height: 28px; font-weight: bold; color: #000000;}.h3{font-size: 15px; text-decoration: underline;}.bodycopy{font-size: 14px; line-height: 22px; color: #000000;}.details{font-size: 14px;}.mssg{font-size: 12px; text-align: center;}.footer{padding: 20px 30px 15px 30px; border-top: 1px solid #f5f5f5;}.footer a{color: #1dc1f8;}.footercopy{font-size: 15px; color: #777777;}.footercopy a{color: #1dc1f8;}.social a{font-size: 14px;}@media screen and (max-width: 600px){.main{padding: 0;}}</style></head><body yahoo bgcolor='#f5eddb'> <table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'> <tr> <td> <table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'> <tr> <td bgcolor='#1dc1f8' class='eheader'> <table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td height='70'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='title' style='padding: 5px 0 0 0;'> <a>Room Booking Details</a> </td></tr><tr> <td class='subhead' style='padding: 0 0 0 3px;'> *Please note that check in is after 12PM and check out is before 11AM. </td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='h2'>Dear #CUSTOMER_FIRSTNAME,</td></tr><tr> <td class='bodycopy'> <p>Thank you for choosing to stay with us. We are excited for your arrival on #CHECKIN_DATE as you are.</p><p>If you have any question please don't hesitate to contact us via email #HOTEL_EMAIL or via phone number #HOTEL_PHONENUM</p><p>See you soon!</p></td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td class='title_reservationNum'>Reservation Number: #RESERVATION_NUM</td></tr><tr> <td class='h3'>Room Booking Details:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>First Name</td><td>#CUSTOMER_FIRSTNAME</td></tr><tr> <td>Last Name</td><td>#CUSTOMER_LASTNAME</td></tr><tr> <td>Email</td><td>#CUSTOMER_EMAIL</td></tr><tr> <td>Phone Number</td><td>#CUSTOMER_PHONENUM</td></tr><tr> <td>Hotel</td><td>#HOTEL</td></tr><tr> <td>Room Type</td><td>#ROOMTYPE</td></tr><tr> <td>Adults</td><td>#ADULT_COUNT</td></tr><tr> <td>Children</td><td>#CHILD_COUNT</td></tr><tr> <td>Arrival Date</td><td>#CHECKIN_DATE</td></tr><tr> <td>Departure Date</td><td>#CHECKOUT_DATE</td></tr><tr> <td>Special Requests</td><td>#SPECIALREQUEST</td></tr><tr> <td>Total Payment</td><td>#TOTAL_PAYMENT</td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr> <td class='footer' bgcolor='#f5f5f5'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved. </td></tr></table> </td></tr></table> </td></tr></table></body></html>";
    private final String bookOnBehalfReceiptTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Room Booking Details</title> <style type='text/css'> body{margin: 0; padding: 0; min-width: 100%!important;}.content{width: 100%; max-width: 600px; border: 1px solid #EAE2D1;}.main{padding: 30px 0; color: #acbac4; line-height: 20px; font-family: sans-serif;}.main a{color: #acbac4; text-decoration: none;}.eheader{padding: 20px;}.innerpadding{padding: 30px;}.borderbottom{border-bottom: 1px solid #e6eff2; color: #000000;}.title_reservationNum{font-weight: bold; text-align: left; text-transform: uppercase; padding-bottom: 10px;}.title{text-align: center; text-transform: uppercase;}.title p{font-size: 30px; line-height: 15px; color: #fff;}.title a{font-size: 30px; line-height: 40px; color: #fff;}.subhead{text-align: center; font-size: 8px; color: #fff;}.h1{text-align: center; font-size: 30px; color: #fff;}.h2{padding: 0 0 15px 0; font-size: 16px; line-height: 28px; font-weight: bold; color: #000000;}.h3{font-size: 15px; text-decoration: underline;}.bodycopy{font-size: 14px; line-height: 22px; color: #000000;}.details{font-size: 14px;}.mssg{font-size: 12px; text-align: center;}.footer{padding: 20px 30px 15px 30px; border-top: 1px solid #f5f5f5;}.footer a{color: #1dc1f8;}.footercopy{font-size: 15px; color: #777777;}.footercopy a{color: #1dc1f8;}.social a{font-size: 14px;}@media screen and (max-width: 600px){.main{padding: 0;}}</style></head><body yahoo bgcolor='#f5eddb'> <table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'> <tr> <td> <table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'> <tr> <td bgcolor='#1dc1f8' class='eheader'> <table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td height='70'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='title' style='padding: 5px 0 0 0;'> <a>Room Booking Details</a> </td></tr><tr> <td class='subhead' style='padding: 0 0 0 3px;'> *Please note that check in is after 12PM and check out is before 11AM. </td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='h2'>Dear #CUSTOMER_FIRSTNAME,</td></tr><tr> <td class='bodycopy'> <p>Thank you for choosing to stay with us. You have booked this room on behalf of <strong>#GUEST_FIRSTNAME #GUEST_LASTNAME</strong>. An email has also been sent to the guest.</p><p>We are excited for his/her arrival on #CHECKIN_DATE.</p><p>If you have any question please don't hesitate to contact us via email #HOTEL_EMAIL or via phone number #HOTEL_PHONENUM</p><p>See you soon!</p></td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td class='title_reservationNum'>Reservation Number: #RESERVATION_NUM</td></tr><tr> <td class='h3'>Room Booking Details:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>First Name</td><td>#CUSTOMER_FIRSTNAME</td></tr><tr> <td>Last Name</td><td>#CUSTOMER_LASTNAME</td></tr><tr> <td>Email</td><td>#CUSTOMER_EMAIL</td></tr><tr> <td>Phone Number</td><td>#CUSTOMER_PHONENUM</td></tr><tr> <td>Hotel</td><td>#HOTEL</td></tr><tr> <td>Room Type</td><td>#ROOMTYPE</td></tr><tr> <td>Adults</td><td>#ADULT_COUNT</td></tr><tr> <td>Children</td><td>#CHILD_COUNT</td></tr><tr> <td>Arrival Date</td><td>#CHECKIN_DATE</td></tr><tr> <td>Departure Date</td><td>#CHECKOUT_DATE</td></tr><tr> <td>Special Requests</td><td>#SPECIALREQUEST</td></tr><tr> <td>Total Payment</td><td>#TOTAL_PAYMENT</td></tr></table> </td></tr><tr> <td class='h3'>Book on Behalf:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>Guest's First Name</td><td>#GUEST_FIRSTNAME</td></tr><tr> <td>Guest's Last Name</td><td>#GUEST_LASTNAME</td></tr><tr> <td>Guest's Email</td><td>#GUEST_EMAIL</td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr> <td class='footer' bgcolor='#f5f5f5'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved. </td></tr></table> </td></tr></table> </td></tr></table></body></html>";
    private final String groupBookingReceiptTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Room Booking Details</title> <style type='text/css'> body{margin: 0; padding: 0; min-width: 100%!important;}.content{width: 100%; max-width: 600px; border: 1px solid #EAE2D1;}.main{padding: 30px 0; color: #acbac4; line-height: 20px; font-family: sans-serif;}.main a{color: #acbac4; text-decoration: none;}.eheader{padding: 20px;}.innerpadding{padding: 30px;}.borderbottom{border-bottom: 1px solid #e6eff2; color: #000000;}.title_reservationNum{font-weight: bold; text-align: left; text-transform: uppercase; padding-bottom: 10px;}.title{text-align: center; text-transform: uppercase;}.title p{font-size: 30px; line-height: 15px; color: #fff;}.title a{font-size: 30px; line-height: 40px; color: #fff;}.subhead{text-align: center; font-size: 8px; color: #fff;}.h1{text-align: center; font-size: 30px; color: #fff;}.h2{padding: 0 0 15px 0; font-size: 16px; line-height: 28px; font-weight: bold; color: #000000;}.h3{font-size: 15px; text-decoration: underline;}.bodycopy{font-size: 14px; line-height: 22px; color: #000000;}.details{font-size: 14px;}.mssg{font-size: 12px; text-align: center;}.footer{padding: 20px 30px 15px 30px; border-top: 1px solid #f5f5f5;}.footer a{color: #1dc1f8;}.footercopy{font-size: 15px; color: #777777;}.footercopy a{color: #1dc1f8;}.social a{font-size: 14px;}@media screen and (max-width: 600px){.main{padding: 0;}}</style></head><body yahoo bgcolor='#f5eddb'> <table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'> <tr> <td> <table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'> <tr> <td bgcolor='#1dc1f8' class='eheader'> <table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td height='70'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='title' style='padding: 5px 0 0 0;'> <a>Room Booking Details</a> </td></tr><tr> <td class='subhead' style='padding: 0 0 0 3px;'> *Please note that check in is after 12PM and check out is before 11AM. </td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='h2'>Dear #CUSTOMER_FIRSTNAME,</td></tr><tr> <td class='bodycopy'> <p>Thank you for choosing to stay with us. We are excited for your arrival on #CHECKIN_DATE as you are.</p><p>If you have any question please don't hesitate to contact us via email #HOTEL_EMAIL or via phone number #HOTEL_PHONENUM</p><p>See you soon!</p></td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td class='title_reservationNum'>Reservation Number: #RESERVATION_NUM</td></tr><tr> <td class='h3'>Room Booking Details:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>First Name</td><td>#CUSTOMER_FIRSTNAME</td></tr><tr> <td>Last Name</td><td>#CUSTOMER_LASTNAME</td></tr><tr> <td>Email</td><td>#CUSTOMER_EMAIL</td></tr><tr> <td>Phone Number</td><td>#CUSTOMER_PHONENUM</td></tr><tr> <td>Hotel</td><td>#HOTEL</td></tr><tr> <td>Adults</td><td>#ADULT_COUNT</td></tr><tr> <td>Children</td><td>#CHILD_COUNT</td></tr><tr> <td>Arrival Date</td><td>#CHECKIN_DATE</td></tr><tr> <td>Departure Date</td><td>#CHECKOUT_DATE</td></tr><tr> <td>Special Requests</td><td>#SPECIALREQUEST</td></tr></table> </td></tr><tr> <td class='h3'>Number of Rooms Per Type:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='1' cellpadding='2' cellspacing='0' style='width: 100%;'> <tr> <td>Superior</td><td>#SUR_COUNT</td><td>$ #SUR_AMT</td></tr><tr> <td>Deluxe</td><td>#DEX_COUNT</td><td>$ #DEX_AMT</td></tr><tr> <td>Junior Suite</td><td>#JUN_COUNT</td><td>$ #JUN_AMT</td></tr><tr> <td>Executive Suite</td><td>#EXE_COUNT</td><td>$ #EXE_AMT</td></tr><tr> <td>President Suite</td><td>#PRE_COUNT</td><td>$ #PRE_AMT</td></tr><tr> <td colspan='2'>Total Payment</td><td>$ #TOTAL_PAYMENT</td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr> <td class='footer' bgcolor='#f5f5f5'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved. </td></tr></table> </td></tr></table> </td></tr></table></body></html>";
    private final String corpBookingReceiptTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Room Booking Details</title> <style type='text/css'> body{margin: 0; padding: 0; min-width: 100%!important;}.content{width: 100%; max-width: 600px; border: 1px solid #EAE2D1;}.main{padding: 30px 0; color: #acbac4; line-height: 20px; font-family: sans-serif;}.main a{color: #acbac4; text-decoration: none;}.eheader{padding: 20px;}.innerpadding{padding: 30px;}.borderbottom{border-bottom: 1px solid #e6eff2; color: #000000;}.title_reservationNum{font-weight: bold; text-align: left; text-transform: uppercase; padding-bottom: 10px;}.title{text-align: center; text-transform: uppercase;}.title p{font-size: 30px; line-height: 15px; color: #fff;}.title a{font-size: 30px; line-height: 40px; color: #fff;}.subhead{text-align: center; font-size: 8px; color: #fff;}.h1{text-align: center; font-size: 30px; color: #fff;}.h2{padding: 0 0 15px 0; font-size: 16px; line-height: 28px; font-weight: bold; color: #000000;}.h3{font-size: 15px; text-decoration: underline;}.bodycopy{font-size: 14px; line-height: 22px; color: #000000;}.details{font-size: 14px;}.mssg{font-size: 12px; text-align: center;}.footer{padding: 20px 30px 15px 30px; border-top: 1px solid #f5f5f5;}.footer a{color: #1dc1f8;}.footercopy{font-size: 15px; color: #777777;}.footercopy a{color: #1dc1f8;}.social a{font-size: 14px;}@media screen and (max-width: 600px){.main{padding: 0;}}</style></head><body yahoo bgcolor='#f5eddb'> <table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'> <tr> <td> <table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'> <tr> <td bgcolor='#1dc1f8' class='eheader'> <table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td height='70'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='title' style='padding: 5px 0 0 0;'> <a>Room Booking Details</a> </td></tr><tr> <td class='subhead' style='padding: 0 0 0 3px;'> *Please note that check in is after 12PM and check out is before 11AM. </td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='h2'>Dear #CUSTOMER_FIRSTNAME,</td></tr><tr> <td class='bodycopy'> <p>Thank you for choosing to stay with us. We are excited for your arrival on #CHECKIN_DATE as you are.</p><p>If you have any question please don't hesitate to contact us via email #HOTEL_EMAIL or via phone number #HOTEL_PHONENUM</p><p>See you soon!</p></td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td class='title_reservationNum'>Reservation Number: #RESERVATION_NUM</td></tr><tr> <td class='h3'>Room Booking Details:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>First Name</td><td>#CUSTOMER_FIRSTNAME</td></tr><tr> <td>Last Name</td><td>#CUSTOMER_LASTNAME</td></tr><tr> <td>Email</td><td>#CUSTOMER_EMAIL</td></tr><tr> <td>Phone Number</td><td>#CUSTOMER_PHONENUM</td></tr><tr> <td>Organization</td><td>#ORG</td></tr><tr> <td>Hotel</td><td>#HOTEL</td></tr><tr> <td>Adults</td><td>#ADULT_COUNT</td></tr><tr> <td>Children</td><td>#CHILD_COUNT</td></tr><tr> <td>Arrival Date</td><td>#CHECKIN_DATE</td></tr><tr> <td>Departure Date</td><td>#CHECKOUT_DATE</td></tr><tr> <td>Special Requests</td><td>#SPECIALREQUEST</td></tr></table> </td></tr><tr> <td class='h3'>Number of Rooms Per Type:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='1' cellpadding='2' cellspacing='0' style='width: 100%;'> <tr> <td>Superior</td><td>#SUR_COUNT</td><td>$ #SUR_AMT</td></tr><tr> <td>Deluxe</td><td>#DEX_COUNT</td><td>$ #DEX_AMT</td></tr><tr> <td>Junior Suite</td><td>#JUN_COUNT</td><td>$ #JUN_AMT</td></tr><tr> <td>Executive Suite</td><td>#EXE_COUNT</td><td>$ #EXE_AMT</td></tr><tr> <td>President Suite</td><td>#PRE_COUNT</td><td>$ #PRE_AMT</td></tr><tr> <td colspan='2'>Total Payment</td><td>$ #TOTAL_PAYMENT</td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr> <td class='footer' bgcolor='#f5f5f5'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved. </td></tr></table> </td></tr></table> </td></tr></table></body></html>";
    private final String bookOnBehalfGuestReceiptTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/> <title>Room Booking Details</title> <style type='text/css'> body{margin: 0; padding: 0; min-width: 100%!important;}.content{width: 100%; max-width: 600px; border: 1px solid #EAE2D1;}.main{padding: 30px 0; color: #acbac4; line-height: 20px; font-family: sans-serif;}.main a{color: #acbac4; text-decoration: none;}.eheader{padding: 20px;}.innerpadding{padding: 30px;}.borderbottom{border-bottom: 1px solid #e6eff2; color: #000000;}.title_reservationNum{font-weight: bold; text-align: left; text-transform: uppercase; padding-bottom: 10px;}.title{text-align: center; text-transform: uppercase;}.title p{font-size: 30px; line-height: 15px; color: #fff;}.title a{font-size: 30px; line-height: 40px; color: #fff;}.subhead{text-align: center; font-size: 8px; color: #fff;}.h1{text-align: center; font-size: 30px; color: #fff;}.h2{padding: 0 0 15px 0; font-size: 16px; line-height: 28px; font-weight: bold; color: #000000;}.h3{font-size: 15px; text-decoration: underline;}.bodycopy{font-size: 14px; line-height: 22px; color: #000000;}.details{font-size: 14px;}.mssg{font-size: 12px; text-align: center;}.footer{padding: 20px 30px 15px 30px; border-top: 1px solid #f5f5f5;}.footer a{color: #1dc1f8;}.footercopy{font-size: 15px; color: #777777;}.footercopy a{color: #1dc1f8;}.social a{font-size: 14px;}@media screen and (max-width: 600px){.main{padding: 0;}}</style></head><body yahoo bgcolor='#f5eddb'> <table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'> <tr> <td> <table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'> <tr> <td bgcolor='#1dc1f8' class='eheader'> <table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td height='70'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='title' style='padding: 5px 0 0 0;'> <a>Room Booking Details</a> </td></tr><tr> <td class='subhead' style='padding: 0 0 0 3px;'> *Please note that check in is after 12PM and check out is before 11AM. </td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td class='h2'>Dear #GUEST_FIRSTNAME,</td></tr><tr> <td class='bodycopy'> <p>Thank you for choosing to stay with us. <strong>#CUSTOMER_FIRSTNAME #CUSTOMER_LASTNAME</strong> has booked this room on behalf of you. This email has been sent to you to provide you the details of the booking reservation.</p><p>We are excited for your arrival on #CHECKIN_DATE.</p><p>If you have any question please don't hesitate to contact us via email #HOTEL_EMAIL or via phone number #HOTEL_PHONENUM</p><p>See you soon!</p></td></tr></table> </td></tr><tr> <td class='innerpadding borderbottom'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td class='title_reservationNum'>Reservation Number: #RESERVATION_NUM</td></tr><tr> <td class='h3'>Room Booking Details:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>First Name</td><td>#GUEST_FIRSTNAME</td></tr><tr> <td>Last Name</td><td>#GUEST_LASTNAME</td></tr><tr> <td>Email</td><td>#GUEST_EMAIL</td></tr><tr> <td>Hotel</td><td>#HOTEL</td></tr><tr> <td>Room Type</td><td>#ROOMTYPE</td></tr><tr> <td>Adults</td><td>#ADULT_COUNT</td></tr><tr> <td>Children</td><td>#CHILD_COUNT</td></tr><tr> <td>Arrival Date</td><td>#CHECKIN_DATE</td></tr><tr> <td>Departure Date</td><td>#CHECKOUT_DATE</td></tr><tr> <td>Special Requests</td><td>#SPECIALREQUEST</td></tr></table> </td></tr><tr> <td class='h3'>Booked By:</td></tr><tr> <td class='innerpadding details'> <table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'> <tr> <td>Organizer's First Name</td><td>#CUSTOMER_FIRSTNAME</td></tr><tr> <td>Organizer's Last Name</td><td>#CUSTOMER_LASTNAME</td></tr><tr> <td>Organizer's Email</td><td>#CUSTOMER_EMAIL</td></tr></table> </td></tr></table> </td></tr><tr> <td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr> <td class='footer' bgcolor='#f5f5f5'> <table width='100%' border='0' cellspacing='0' cellpadding='0'> <tr> <td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved. </td></tr></table> </td></tr></table> </td></tr></table></body></html>";
    private final String roomBookingReceiptTemplate = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title>Room Booking Receipt</title><style type='text/css'>body{margin:0;padding:0;min-width:100%!important}.content{width:100%;max-width:600px;border:1px solid #EAE2D1}.main{padding:30px 0;color:#acbac4;line-height:20px;font-family:sans-serif}.main a{color:#acbac4;text-decoration:none}.eheader{padding:20px}.innerpadding{padding:30px}.borderbottom{border-bottom:1px solid #e6eff2;color:#000}.title{text-align:center;text-transform:uppercase}.title p{font-size:30px;line-height:15px;color:#fff}.title a{font-size:30px;line-height:40px;color:#fff}.subhead{text-align:center;font-size:8px;color:#fff}.h1{text-align:center;font-size:30px;color:#fff}.h2{padding:0 0 15px 0;font-size:16px;line-height:28px;font-weight:bold;color:#000}.h3{font-size:15px;text-decoration:underline}.bodycopy{font-size:14px;line-height:22px;color:#000}.details{font-size:14px}.mssg{font-size:12px;text-align:center}.footer{padding:20px 30px 15px 30px;border-top:1px solid #f5f5f5}.footer a{color:#1dc1f8}.footercopy{font-size:15px;color:#777}.footercopy a{color:#1dc1f8}.social a{font-size:14px}@media screen and (max-width: 600px){.main{padding:0}}</style></head><body yahoo bgcolor='#f5eddb'><table width='100%' bgcolor='#f5eddb' class='main' border='0' cellpadding='0' cellspacing='0'><tr><td><table bgcolor='#ffffff' class='content' align='center' cellpadding='0' cellspacing='0' border='0'><tr><td bgcolor='#1dc1f8' class='eheader'><table class='col425' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'><tr><td height='70'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td class='title' style='padding: 5px 0 0 0;'> <a>Room Booking Receipt</a></td></tr><tr><td class='subhead' style='padding: 0 0 0 3px;'> *Please note that check in is after 12PM and check out is before 11AM.</td></tr></table></td></tr></table></td></tr><tr><td class='innerpadding borderbottom'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td class='h2'>Dear #CUSTOMER_FIRSTNAME,</td></tr><tr><td class='bodycopy'><p>You have #ROOM_STATUS successfully. We hope you have a wonderful experience with us. Please help us fill up the feedback by clicking the link below:</p><p>#LINK</p><p>Thank You!</p></td></tr></table></td></tr><tr><td class='innerpadding borderbottom'><table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'><tr><td class='h3'>Room Booking Details:</td></tr><tr><td class='innerpadding details'><table class='col380' align='left' border='0' cellpadding='0' cellspacing='0' style='width: 100%;'><tr><td>First Name</td><td>#CUSTOMER_FIRSTNAME</td></tr><tr><td>Last Name</td><td>#CUSTOMER_LASTNAME</td></tr><tr><td>Arrival Date</td><td>#CHECKINDATE</td></tr><tr><td>Departure Date</td><td>#CHECKOUTDATE</td></tr><tr><td>Customer Identity</td><td>#CUSTOMER_ID</td></tr><tr><td>Room Number</td><td>#ROOM_NO</td></tr></table></td></tr></table></td></tr><tr><td class='innerpadding bodycopy mssg'> Thank You, <br>KRHG Team</td></tr><tr><td class='footer' bgcolor='#f5f5f5'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center' class='footercopy'> 2019 &#169; <a href='localhost:81'>EzyHotel - KRHG</a> All Rights Reserved.</td></tr></table></td></tr></table></td></tr></table></body></html>";
    public CommonMethods() {
    }

    // Byte is 2 characters
    public String generateRandomAlphanumeric(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        String accessCode = new BigInteger(1, randomBytes).toString(16).toUpperCase(); // hex encoding to get alphabets

        return accessCode;
    }

    public String hashAccessCode(String accessCodeIdentifier)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(accessCodeIdentifier.getBytes("UTF-8"));
        byte[] digest = messageDigest.digest();
        String hashedAccessCode = new BigInteger(1, digest).toString(16).toUpperCase();
        return hashedAccessCode;
    }

    public String generateRandomPassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String randomPassword = generateRandomAlphanumeric(16);

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(randomPassword.getBytes("UTF-8"));
        byte[] digest = messageDigest.digest();

        String hashedRandomPassword = new BigInteger(1, digest).toString(16).toUpperCase();
        return hashedRandomPassword;
    }

    public boolean checkIsBirthdayMonth(CustomerEntity customer) {
        Calendar customerBirthdate = Calendar.getInstance();
        System.out.println("Customer Birthday: " + customer.getBirthDate());
        customerBirthdate.setTime(customer.getBirthDate());

        int month = customerBirthdate.get(Calendar.MONTH) + 1;
        System.out.println("Month = " + LocalDateTime.now().getMonthValue());
        System.out.println("Customer Bday Month = " + month);
        return LocalDateTime.now().getMonthValue() == month;
    }

    public AccessCodeEntity generateAccessCode(AccessCodeTypeEnum accessCodeType) {
        AccessCodeEntity accessCode = new AccessCodeEntity();
        accessCode.setType(accessCodeType);
        Timestamp expiryDateTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(30));
        accessCode.setExpiryDateTime(expiryDateTime);
        return accessCode;
    }

    public String emailBodyForSignUp(String customerFirstName, String signUpLink) {
        String filledUpTemplate = signUpTemplate;
        filledUpTemplate = filledUpTemplate.replaceFirst("#CUSTOMER_FIRSTNAME", customerFirstName);
        filledUpTemplate = filledUpTemplate.replace("#LINK", signUpLink);
        return filledUpTemplate;
    }

    // Booking Receipt when the customer first make the booking
    public String emailBodyForSingleBookingReceipt(RoomBookingEntity roomBooking, CustomerEntity customer,
            BigDecimal totalAmount) {

        String filledUpTemplate = singleBookingReceiptTemplate;
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_PHONENUM",
                roomBooking.getRoomType().getHotel().getTelephoneNumber());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_EMAIL",
                roomBooking.getRoomType().getHotel().getEmail());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL", roomBooking.getRoomType().getHotel().getName());

        System.out.println("Customer Information: " + customer.toString());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_FIRSTNAME", customer.getFirstName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_LASTNAME", customer.getLastName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_EMAIL", customer.getEmail());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_PHONENUM", customer.getPhoneNum());

        filledUpTemplate = filledUpTemplate.replace("#RESERVATION_NUM", roomBooking.getReservationNumber());
        filledUpTemplate = filledUpTemplate.replace("#ROOMTYPE", roomBooking.getRoomType().getName());
        filledUpTemplate = filledUpTemplate.replace("#ADULT_COUNT", String.valueOf(roomBooking.getAdultCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHILD_COUNT", String.valueOf(roomBooking.getChildCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKIN_DATE", dateFormat(roomBooking.getCheckInDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKOUT_DATE", dateFormat(roomBooking.getCheckOutDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#TOTAL_PAYMENT",
                "$ " + totalAmount.setScale(2, RoundingMode.HALF_EVEN));
        filledUpTemplate = filledUpTemplate.replace("#SPECIALREQUEST", roomBooking.getSpecialRequests());

        return filledUpTemplate;
    }

    public String emailBodyForBookOnBehalfReceipt(RoomBookingEntity booking, CustomerEntity customer,
            BigDecimal totalAmount) {
        String filledUpTemplate = bookOnBehalfReceiptTemplate;
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_PHONENUM",
                booking.getRoomType().getHotel().getTelephoneNumber());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_EMAIL", booking.getRoomType().getHotel().getEmail());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL", booking.getRoomType().getHotel().getName());

        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_FIRSTNAME", customer.getFirstName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_LASTNAME", customer.getLastName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_EMAIL", customer.getEmail());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_PHONENUM", customer.getPhoneNum());

        filledUpTemplate = filledUpTemplate.replace("#RESERVATION_NUM", booking.getReservationNumber());
        filledUpTemplate = filledUpTemplate.replace("#ROOMTYPE", booking.getRoomType().getName());
        filledUpTemplate = filledUpTemplate.replace("#ADULT_COUNT", String.valueOf(booking.getAdultCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHILD_COUNT", String.valueOf(booking.getChildCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKIN_DATE", dateFormat(booking.getCheckInDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKOUT_DATE", dateFormat(booking.getCheckOutDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#TOTAL_PAYMENT",
                "$ " + totalAmount.setScale(2, RoundingMode.HALF_EVEN));
        filledUpTemplate = filledUpTemplate.replace("#SPECIALREQUEST", booking.getSpecialRequests());

        filledUpTemplate = filledUpTemplate.replace("#GUEST_FIRSTNAME",
                ((BookOnBehalfEntity) booking).getGuestFirstName());
        filledUpTemplate = filledUpTemplate.replace("#GUEST_LASTNAME",
                ((BookOnBehalfEntity) booking).getGuestLastName());
        filledUpTemplate = filledUpTemplate.replaceFirst("#GUEST_EMAIL",
                ((BookOnBehalfEntity) booking).getGuestEmail());
        return filledUpTemplate;
    }

    public String emailBodyForBookOnBehalfGuestReceipt(RoomBookingEntity booking, CustomerEntity customer,
            BigDecimal totalAmount) {
        String filledUpTemplate = bookOnBehalfGuestReceiptTemplate;

        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_PHONENUM",
                booking.getRoomType().getHotel().getTelephoneNumber());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_EMAIL", booking.getRoomType().getHotel().getEmail());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL", booking.getRoomType().getHotel().getName());

        filledUpTemplate = filledUpTemplate.replace("#GUEST_FIRSTNAME",
                ((BookOnBehalfEntity) booking).getGuestFirstName());
        filledUpTemplate = filledUpTemplate.replace("#GUEST_LASTNAME",
                ((BookOnBehalfEntity) booking).getGuestLastName());
        filledUpTemplate = filledUpTemplate.replaceFirst("#GUEST_EMAIL",
                ((BookOnBehalfEntity) booking).getGuestEmail());

        filledUpTemplate = filledUpTemplate.replace("#RESERVATION_NUM", booking.getReservationNumber());
        filledUpTemplate = filledUpTemplate.replace("#ROOMTYPE", booking.getRoomType().getName());
        filledUpTemplate = filledUpTemplate.replace("#ADULT_COUNT", String.valueOf(booking.getAdultCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHILD_COUNT", String.valueOf(booking.getChildCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKIN_DATE", dateFormat(booking.getCheckInDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKOUT_DATE", dateFormat(booking.getCheckOutDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#TOTAL_PAYMENT",
                "$ " + totalAmount.setScale(2, RoundingMode.HALF_EVEN));
        filledUpTemplate = filledUpTemplate.replace("#SPECIALREQUEST", booking.getSpecialRequests());

        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_FIRSTNAME", customer.getFirstName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_LASTNAME", customer.getLastName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_EMAIL", customer.getEmail());

        return filledUpTemplate;
    }

    public String emailBodyForGroupBookingReceipt(List<RoomBookingEntity> bookings, List<PaymentEntity> payments,
            CustomerEntity customer) {
        String filledUpTemplate = groupBookingReceiptTemplate;

        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_PHONENUM",
                bookings.get(0).getRoomType().getHotel().getTelephoneNumber());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_EMAIL",
                bookings.get(0).getRoomType().getHotel().getEmail());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL", bookings.get(0).getRoomType().getHotel().getName());

        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_FIRSTNAME", customer.getFirstName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_LASTNAME", customer.getLastName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_EMAIL", customer.getEmail());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_PHONENUM", customer.getPhoneNum());

        // Can just get the first item in list, because all the same across the bookings
        filledUpTemplate = filledUpTemplate.replace("#CHECKIN_DATE", dateFormat(bookings.get(0).getCheckInDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKOUT_DATE",
                dateFormat(bookings.get(0).getCheckOutDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#RESERVATION_NUM", bookings.get(0).getReservationNumber());
        filledUpTemplate = filledUpTemplate.replace("#ADULT_COUNT", String.valueOf(bookings.get(0).getAdultCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHILD_COUNT", String.valueOf(bookings.get(0).getChildCount()));
        filledUpTemplate = filledUpTemplate.replace("#SPECIALREQUEST", bookings.get(0).getSpecialRequests());

        // Need to count 1) the number of rooms per type, 2)calculate the total payment
        // per room, 3) The total payment
        HashMap<String, Triple> roomCountCostList = countRoomsPerType(payments);
        filledUpTemplate = filledUpRoomTypeCostTable(roomCountCostList, filledUpTemplate);
        filledUpTemplate = cleanUpRoomTypeCostTable(filledUpTemplate);

        return filledUpTemplate;

    }

    public String emailBodyForCorpBookingReceipt(List<RoomBookingEntity> bookings, List<PaymentEntity> payments,
            CustomerEntity customer) {
        String filledUpTemplate = corpBookingReceiptTemplate;
        System.out.println("Organization Name: " + ((GroupBookingEntity) bookings.get(0)).getCompanyName());
        filledUpTemplate = filledUpTemplate.replaceFirst("#ORG",
                ((GroupBookingEntity) bookings.get(0)).getCompanyName());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_PHONENUM",
                bookings.get(0).getRoomType().getHotel().getTelephoneNumber());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL_EMAIL",
                bookings.get(0).getRoomType().getHotel().getEmail());
        filledUpTemplate = filledUpTemplate.replaceFirst("#HOTEL", bookings.get(0).getRoomType().getHotel().getName());

        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_FIRSTNAME", customer.getFirstName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_LASTNAME", customer.getLastName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_EMAIL", customer.getEmail());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_PHONENUM", customer.getPhoneNum());

        // Can just get the first item in list, because all the same across the bookings
        filledUpTemplate = filledUpTemplate.replace("#CHECKIN_DATE", dateFormat(bookings.get(0).getCheckInDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKOUT_DATE",
                dateFormat(bookings.get(0).getCheckOutDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#RESERVATION_NUM", bookings.get(0).getReservationNumber());
        filledUpTemplate = filledUpTemplate.replace("#ADULT_COUNT", String.valueOf(bookings.get(0).getAdultCount()));
        filledUpTemplate = filledUpTemplate.replace("#CHILD_COUNT", String.valueOf(bookings.get(0).getChildCount()));
        filledUpTemplate = filledUpTemplate.replace("#SPECIALREQUEST", bookings.get(0).getSpecialRequests());

        // Need to count 1) the number of rooms per type, 2)calculate the total payment
        // per room, 3) The total payment
        HashMap<String, Triple> roomCountCostList = countRoomsPerType(payments);
        filledUpTemplate = filledUpRoomTypeCostTable(roomCountCostList, filledUpTemplate);
        filledUpTemplate = cleanUpRoomTypeCostTable(filledUpTemplate);
        return filledUpTemplate;
    }

    // Room Booking receipt for check in and check out
    public String emailBodyForRoomBookingReceipt(RoomBookingEntity booking, String bookingStatus, String feedbackLink) {

        String filledUpTemplate = roomBookingReceiptTemplate;
        filledUpTemplate = filledUpTemplate.replace("#ROOM_STATUS", bookingStatusFormat(bookingStatus));
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_FIRSTNAME", booking.getCustomer().getFirstName());
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_LASTNAME", booking.getCustomer().getLastName());
        filledUpTemplate = filledUpTemplate.replace("#CHECKINDATE", dateFormat(booking.getCheckInDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CHECKOUTDATE", dateFormat(booking.getCheckOutDateTime()));
        filledUpTemplate = filledUpTemplate.replace("#CUSTOMER_ID", booking.getCustomer().getCustIdentity());
        filledUpTemplate = filledUpTemplate.replace("#ROOM_NO", booking.getRoomNumber());
        
        
        if(bookingStatus.equals("CHECKED_OUT")){
            filledUpTemplate = filledUpTemplate.replace("#LINK", feedbackLink);
        }else{
            filledUpTemplate = filledUpTemplate.replace("#LINK", feedbackLink);
        }
        
        
        System.out.println(filledUpTemplate);
        return filledUpTemplate;
    }

    private String filledUpRoomTypeCostTable(HashMap<String, Triple> roomCountCostList, String filledUpTemplate) {
        BigDecimal finalAmountForRoomType = BigDecimal.ZERO, totalPayment = BigDecimal.ZERO;
        for (String key : roomCountCostList.keySet()) {
            BigDecimal roomTypeCost = (BigDecimal) roomCountCostList.get(key).getValueThird();
            int roomTypeCount = (int) roomCountCostList.get(key).getValueSecond();
            System.out.println("RoomTypeCost: " + roomTypeCost + ", roomTypeCount: " + roomTypeCount);
            switch (key) {
            case "SUR":
                System.out.println("In SUR");
                filledUpTemplate = filledUpTemplate.replaceFirst("#SUR_COUNT",
                        roomCountCostList.get(key).getValueSecond().toString());
                finalAmountForRoomType = (roomTypeCost).multiply(new BigDecimal(roomTypeCount));
                System.out.println("Final Amt for Room Type SUR: " + finalAmountForRoomType);
                filledUpTemplate = filledUpTemplate.replaceFirst("#SUR_AMT", String.valueOf(finalAmountForRoomType));
                break;
            case "DEX":
                System.out.println("In DEX");
                filledUpTemplate = filledUpTemplate.replaceFirst("#DEX_COUNT",
                        roomCountCostList.get(key).getValueSecond().toString());
                finalAmountForRoomType = (roomTypeCost).multiply(new BigDecimal(roomTypeCount));
                System.out.println("Final Amt for Room Type DEX: " + finalAmountForRoomType);
                filledUpTemplate = filledUpTemplate.replaceFirst("#DEX_AMT", String.valueOf(finalAmountForRoomType));
                break;
            case "JUN":
                System.out.println("In JUN");
                filledUpTemplate = filledUpTemplate.replaceFirst("#JUN_COUNT",
                        roomCountCostList.get(key).getValueSecond().toString());
                finalAmountForRoomType = (roomTypeCost).multiply(new BigDecimal(roomTypeCount));
                System.out.println("Final Amt for Room Type JUN: " + finalAmountForRoomType);
                filledUpTemplate = filledUpTemplate.replaceFirst("#JUN_AMT", String.valueOf(finalAmountForRoomType));
                break;
            case "PRE":
                System.out.println("In PRE");
                filledUpTemplate = filledUpTemplate.replaceFirst("#PRE_COUNT",
                        roomCountCostList.get(key).getValueSecond().toString());
                finalAmountForRoomType = (roomTypeCost).multiply(new BigDecimal(roomTypeCount));
                System.out.println("Final Amt for Room Type PRE: " + finalAmountForRoomType);
                filledUpTemplate = filledUpTemplate.replaceFirst("#PRE_AMT", String.valueOf(finalAmountForRoomType));
                break;
            case "EXE":
                System.out.println("In EXE");
                filledUpTemplate = filledUpTemplate.replaceFirst("#EXE_COUNT",
                        roomCountCostList.get(key).getValueSecond().toString());
                finalAmountForRoomType = (roomTypeCost).multiply(new BigDecimal(roomTypeCount));
                System.out.println("Final Amt for Room Type EXE: " + finalAmountForRoomType);
                filledUpTemplate = filledUpTemplate.replaceFirst("#EXE_AMT", String.valueOf(finalAmountForRoomType));
                break;
            }

            totalPayment = totalPayment.add(finalAmountForRoomType);
        }

        filledUpTemplate = filledUpTemplate.replaceFirst("#TOTAL_PAYMENT", String.valueOf(totalPayment));

        return filledUpTemplate;

    }

    // Remove those tags that are not filled
    private String cleanUpRoomTypeCostTable(String filledUpTemplate) {

        filledUpTemplate = filledUpTemplate.replace("#SUR_COUNT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#SUR_AMT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#DEX_COUNT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#DEX_AMT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#JUN_COUNT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#JUN_AMT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#PRE_COUNT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#PRE_AMT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#EXE_COUNT", String.valueOf(0));
        filledUpTemplate = filledUpTemplate.replace("#EXE_AMT", String.valueOf(0));
        return filledUpTemplate;
    }

    // valueFirst: String roomTypeCode, valueSecond: int roomTypeCount, valueThird: BigDecimal priceRate
    private HashMap<String, Triple> countRoomsPerType(List<PaymentEntity> payments) {
        HashMap<String, Triple> roomTypeCountCost = new HashMap<>();

        for (PaymentEntity payment : payments) {

            String key = payment.getRoomBooking().getRoomTypeCode();
            if (!roomTypeCountCost.containsKey(key)) {
                BigDecimal amount = payment.getTotalAmount().setScale(2, RoundingMode.HALF_EVEN);
                roomTypeCountCost.put(key, new Triple(key, 1, payment.getTotalAmount()));
            } else {
                Triple updatedTriple = roomTypeCountCost.get(key);
                updatedTriple.setValueSecond(((int) updatedTriple.getValueSecond()) + 1);

                roomTypeCountCost.replace(key, updatedTriple);
            }
        }

        System.out.println("HashMap - Count Room Types + per room cost");
        System.out.println(roomTypeCountCost.toString());

        return roomTypeCountCost;
    }

    private String bookingStatusFormat(String bookingStatus) {
        String readableBookingStatusString = "";
        switch (bookingStatus) {
        case "CHECKED_IN":
            readableBookingStatusString = "checked in";
            break;
        case "CHECKED_OUT":
            readableBookingStatusString = "checked out";
            break;
        }
        return readableBookingStatusString;
    }

    public String dateFormat(Date dateToFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(dateToFormat);
        return formattedDate;
    }

    public class Triple<A, B, C> {
        private A valueFirst;
        private B valueSecond;
        private C valueThird;

        public Triple(A roomTypeCode, B roomTypeCount, C totalPaymentForRoomType) {
            this.valueFirst = roomTypeCode;
            this.valueSecond = roomTypeCount;
            this.valueThird = totalPaymentForRoomType;
        }

        public Triple() {
        }

        @Override
        public String toString() {
            return "Triple{" + "roomTypeCode=" + valueFirst + ", roomTypeCount=" + valueSecond
                    + ", totalPaymentForRoomType=" + valueThird + '}';
        }

        public A getValueFirst() {
            return valueFirst;
        }

        public void setValueFirst(A valueFirst) {
            this.valueFirst = valueFirst;
        }

        public B getValueSecond() {
            return valueSecond;
        }

        public void setValueSecond(B valueSecond) {
            this.valueSecond = valueSecond;
        }

        public C getValueThird() {
            return valueThird;
        }

        public void setValueThird(C valueThird) {
            this.valueThird = valueThird;
        }

    }

}
