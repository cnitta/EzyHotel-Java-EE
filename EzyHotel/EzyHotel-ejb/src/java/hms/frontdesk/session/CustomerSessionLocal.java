/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Local;
import util.entity.CustomerEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.exception.NoResultException;

/**
 *
 * @author Nicholas Nah
 */
@Local
public interface CustomerSessionLocal {

    public CustomerEntity createEntity(CustomerEntity customer);

    public CustomerEntity retrieveEntityById(Long cId) throws NoResultException;

    public CustomerEntity retrieveEntityByPhoneNumber(String phoneNumber) throws NoResultException;

    public List<CustomerEntity> retrieveAllEntites();

    public void deleteEntity(Long id) throws NoResultException;

    public void updateEntity(CustomerEntity entity) throws NoResultException;

    public CustomerEntity retrieveCustId(String identity) throws NoResultException;

    public CustomerEntity retrieveForHouseKeeping(Long cId);

    public RoomBookingEntity retrieveBookingByCustId(String identity) throws NoResultException;

    public RoomEntity retrieveRoomId(String roomNo);

    public RoomBookingEntity checkIn(String identity, String roomNo) throws Exception;

    public RoomBookingEntity checkOut(String identity) throws Exception;

    public void terminateCustomerMembership(Long customerId) throws NoResultException;

    public void sendEmailNotificationRB(RoomBookingEntity booking, String bookingStatus) throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
