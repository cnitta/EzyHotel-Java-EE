/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.text.ParseException;
import java.util.List;
import javax.ejb.Local;
import util.entity.CustomerEntity;
import util.entity.PaymentEntity;
import util.entity.RoomBookingEntity;
import util.exception.CustomerProfileConflictException;
import util.exception.RoomBookingException;

/**
 *
 * @author berni
 */
@Local
public interface OnlineRoomBookingSessionLocal {

    public void updateRoomBooking(RoomBookingEntity roomBooking);
    
    public RoomBookingEntity createEntity(RoomBookingEntity entity);

    public RoomBookingEntity retrieveEntityById(Long id);

    public List<RoomBookingEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public List<RoomBookingEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public List<RoomBookingEntity> retrieveEntityByEitherFilters(String attributeA, String valueA, String attributeB, String valueB);

    public RoomBookingEntity retrieveEntity(String attribute, String value);

    public PaymentEntity getPaymentByBookingId(Long bookingId) throws Exception;

    public PaymentEntity createPaymentTransactionForSingleBooking(String finalPrice, String cardHolderName, String hCreditCard, String creditCardExpiryDate, RoomBookingEntity newRoomBooking, CustomerEntity customer);

    public RoomBookingEntity createSingleRoomBooking(RoomBookingEntity newRoomBooking, CustomerEntity customer, String priceRateTitle, String finalPrice, String cardHolderName, String hCreditCard, String creditCardExpiryDate) throws CustomerProfileConflictException, ParseException, RoomBookingException;

    public List<PaymentEntity> createPaymentTransactionForGroupBooking(List<RoomBookingEntity> newRoomBookings, CustomerEntity customer, String cardHolderName, String hCreditCard, String creditCardExpiryDate);

    public List<RoomBookingEntity> createGoupRoomBooking(List<RoomBookingEntity> newRoomBookings, CustomerEntity customer, String cardHolderName, String hCreditCard, String creditCardExpiryDate, String bookingType) throws CustomerProfileConflictException, ParseException, RoomBookingException;

    public List<RoomBookingEntity> retrieveBookingsByReservationNumber(String reservationNumber);
}
