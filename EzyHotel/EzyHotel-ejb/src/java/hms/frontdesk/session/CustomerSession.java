/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import hms.common.EmailManager;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.CustomerEntity;
import util.entity.LoyaltyEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.RoomTypeEntity;
import util.enumeration.CustomerMembershipEnum;
import util.enumeration.RoomBookingStatusEnum;
import util.enumeration.RoomStatusEnum;
import util.exception.NoResultException;

/**
 *
 * @author Nicholas Nah
 */
@Stateless
public class CustomerSession implements CustomerSessionLocal {

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    //To send email notifications
    private final EmailManager emailManager = new EmailManager();

    @Override
    //check if identity is unique
    public CustomerEntity createEntity(CustomerEntity customer) {
        System.out.println("*** Create Customer Started ***\n");

        em.persist(customer);
        em.flush();
        em.refresh(customer);

        System.out.println("*** Create Customer Ended ***\n");

        return customer;
    }//end createEntity

    @Override
    public CustomerEntity retrieveEntityById(Long cId) throws NoResultException {
        System.out.println("*** Retrieve Customer By Id Started ***\n");

        CustomerEntity customer = em.find(CustomerEntity.class, cId);

        if (customer != null) {
            System.out.println("*** Retrieve Customer By Id Ended ***\n");

            return customer;
        } else {
            throw new NoResultException("Customer with id: " + cId + " does not exist!");
        }
    }//end retrieveEntityById

    @Override
    public CustomerEntity retrieveForHouseKeeping(Long cId) {
        System.out.println("*** Retrieve ForHouseKeeping By Id Started ***\n");

        CustomerEntity customer = em.find(CustomerEntity.class, cId);
        System.out.println("*** Retrieve retrieveForHouseKeeping By Id Ended ***\n");

        return customer;

    }//end retrieveForHouseKeeping

    @Override
    public List<CustomerEntity> retrieveAllEntites() {
        System.out.println("*** Retrieve all Customers Started ***\n");

        Query q = em.createQuery("SELECT c FROM CustomerEntity  c");

        System.out.println("*** Retrieve All Customers Ended ***\n");
        return q.getResultList();
    }//end retrieveAllEntity

    @Override
    public void deleteEntity(Long id) throws NoResultException {
        System.out.println("*** Delete Customer Started ***\n");
        CustomerEntity dCust = retrieveEntityById(id);
        em.remove(dCust);
    }//end deleteEntity

    @Override
    public void updateEntity(CustomerEntity entity) throws NoResultException {
        retrieveEntityById(entity.getCustomerId());
        em.merge(entity);
    }//end updateEntity

    @Override
    public CustomerEntity retrieveCustId(String identity) throws NoResultException {

        System.out.println("*** Retrieve Customer By Identity Started ***\n");

        Query q = em.createQuery("SELECT c FROM CustomerEntity c WHERE LOWER(c.custIdentity) = :inId");

        q.setParameter("inId", identity.toLowerCase());

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve Customer By Identity  Ended ***\n");
            return (CustomerEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve Customer Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }

    }//end retrieveCustId

    @Override
    public CustomerEntity retrieveEntityByPhoneNumber(String phoneNumber) throws NoResultException {

        System.out.println("*** Retrieve Customer By Phone Number Started ***\n");

        Query q = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.phoneNum = :inId");

        q.setParameter("inId", phoneNumber);

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve Customer By Identity  Ended ***\n");
            return (CustomerEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve Customer Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }
    }

    @Override
    public RoomBookingEntity retrieveBookingByCustId(String identity) throws NoResultException {

        System.out.println("*** Retrieve Booking By Customer Identity Started ***\n");

        //retrieve latest booking of cutomer
        Query q = em.createQuery("SELECT b FROM RoomBookingEntity b "
                + "WHERE LOWER(b.customer.custIdentity) = :inId"
                + " ORDER BY b.bookingId DESC");

        q.setParameter("inId", identity.toLowerCase());
        // retrieve only one record
        q.setMaxResults(1);

        //test printing booking record
        System.out.println("Booking id: " + q.getSingleResult());

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve Booking By Customer Identity  Ended ***\n");
            return (RoomBookingEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve Booking by Customer Identity Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }

    }//end retrieveBookingByCustId

    @Override
    public RoomEntity retrieveRoomId(String roomNo) {

        System.out.println("*** Retrieve Room ID By number Started ***\n");
        //convert from string to int
        int convertToInt = Integer.parseInt(roomNo);

        Query q = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomUnitNumber = :inId");

        q.setParameter("inId", convertToInt);

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve Room ID By number Ended ***\n");
            return (RoomEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve Room ID by number Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }
    }//end retrieveRoomId

    @Override
    //Check in algo, return room booking entity
    public RoomBookingEntity checkIn(String identity, String roomNo) throws Exception {
        try {
            System.out.println("*** Customer Check-In Started ***\n");
            RoomBookingEntity checkInCust = retrieveBookingByCustId(identity);
            if (checkInCust.getCustomer().getCustomerId() == null) {
                //means there is no customer that make this booking.
                System.out.println("Customer does not exist!");
            }
            checkInCust.setRoomNumber(roomNo);
            //set room to be occupied and status to be check in
            checkInCust.setStatus(RoomBookingStatusEnum.CHECKED_IN);
            //retrieve the Room to set the status
            RoomEntity chosenRoom = retrieveRoomId(roomNo);
            chosenRoom.setStatus(RoomStatusEnum.OCCUPIED);
            //TODO IMPORTANT**: Remember to diassociate the relationship between the RoomType to remove cyclic redundancy  
            //RoomTypeEntity typeRoom = typeRoom.getRooms()

            System.out.println("*** Customer Check-In Ended ***\n");
            return checkInCust;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }//end checkIn

    @Override
    public RoomBookingEntity checkOut(String identity) throws Exception {
        try {
            System.out.println("*** Customer Check-Out Started ***\n");
            //get booking details by retrieving customer identity
            RoomBookingEntity checkoutCust = retrieveBookingByCustId(identity);
            if (checkoutCust.getCustomer().getCustomerId() == null) {
                //means there is no such customer that make this booking.
                System.out.println("Customer does not exist!");
            }

            // set status to check out
            checkoutCust.setStatus(RoomBookingStatusEnum.CHECKED_OUT);
            System.out.println("Customer: " + checkoutCust.getCustomer().getCustIdentity() + "has Check Out!");
            System.out.println("*** Customer Check-Out Ended ***\n");
            return checkoutCust;

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }//end checkOut

    @Override
    public void terminateCustomerMembership(Long customerId) throws NoResultException {
        //retrieve the customerEntity Id
        CustomerEntity customerToUpdate = retrieveEntityById(customerId);

        //Update resepective values to be of NON_MEMBER status
        customerToUpdate.setMembershipStatus(CustomerMembershipEnum.NON_MEMBER);
        customerToUpdate.setPassword("");

        //TODO: To find loyalty by customer id and delete it from DB 
        //All Loyalty point will be lost and non redeemable upon terminating the membership
        //reset the Loyalty points
        //customerToUpdate.setLoyalty(new LoyaltyEntity());
    }

    @Override
    public void sendEmailNotificationRB(RoomBookingEntity booking, String bookingStatus) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        System.out.println("*** Send Email Notification Started ***\n");
        System.out.println(booking.getCustomer().getFirstName() + " booking status is: " + bookingStatus);
        emailManager.emailGeneralNotificationForRoomBooking(booking, booking.getCustomer().getEmail(), bookingStatus);
        System.out.println("*** Send Email Notification Ended ***\n");
    }

}
