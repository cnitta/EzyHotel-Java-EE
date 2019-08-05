/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.RoomBookingEntity;
import util.entity.RoomOrderEntity;
import util.exception.NoResultException;

/**
 *
 * @author Nicholas
 */
@Stateless
public class RoomOrderSession implements RoomOrderSessionLocal {

    @EJB
    private RoomBookingSessionLocal roomBookingSession;

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public RoomOrderEntity createEntity(RoomOrderEntity roomOrder) {
        System.out.println("*** Create Room Order Started ***\n");

        em.persist(roomOrder);
        em.flush();
        em.refresh(roomOrder);
        System.out.println("Print room number: " + roomOrder.getRoomNumber());
        //update of RoomBooking Record
        RoomBookingEntity booking = retrieveRoomNumberHK(roomOrder.getRoomNumber());
        booking.setRoomOrder(roomOrder);
        try {
            roomBookingSession.updateEntity(booking);
        } catch (NoResultException ex) {
            Logger.getLogger(RoomOrderSession.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("*** Create Room Order Ended ***\n");

        return roomOrder;
    }//end createEntity

    @Override
    public RoomBookingEntity retrieveRoomNumberHK(String roomNumber) {
        System.out.println("*** Retrieve Room Number for Housekeeping Started ***\n");
        Query q = em.createQuery("SELECT r FROM RoomBookingEntity r WHERE r.roomNumber = :rnId");

        q.setParameter("rnId", roomNumber);

        System.out.println("*** Retrieve Room Number for Housekeeping Ended ***\n");
        return (RoomBookingEntity) q.getSingleResult();
    }//end retrieveRoomNumberHK

    @Override
    public RoomOrderEntity retrieveRoomOrderById(Long rId) {
        System.out.println("*** Retrieve Room Order by Id Started ***\n");

        RoomOrderEntity roomOrder = em.find(RoomOrderEntity.class, rId);
        System.out.println("*** Retrieve Room Order by Id Ended ***\n");

        return roomOrder;

    }//end retrieveRoomOrderById

    @Override
    public List<RoomOrderEntity> retrieveAllEntites() {
        System.out.println("*** Retrieve all Room Order Started ***\n");

        Query q = em.createQuery("SELECT r FROM RoomOrderEntity  r");

        System.out.println("*** Retrieve All Room Order Ended ***\n");
        return q.getResultList();
    }//end retrieveAllEntity

    @Override
    public void deleteEntity(Long rId) {
        System.out.println("*** Delete Room Order Started ***\n");
        RoomOrderEntity dRoomOrder = retrieveRoomOrderById(rId);
        em.remove(dRoomOrder);
        System.out.println("*** Delete Room Order Ended ***\n");
    }//end deleteEntity

    @Override
    public void updateEntity(RoomOrderEntity entity) {
        retrieveRoomOrderById(entity.getRoomOrderId());
        em.merge(entity);
    }//end updateEntity

    @Override
    public List<RoomOrderEntity> retrieveUnpaidRoomOrders() {
        System.out.println("*** Retrieve All Unpaid Room Orders Started ***\n");
        //Gets todays date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        //create Empty list
        List<RoomOrderEntity> notPaidList = new ArrayList<RoomOrderEntity>();
        //retrieve all invoice in InvoiceEntity database
        List<RoomOrderEntity> orderList = retrieveAllEntites();
        //loop through the invoiceList
        for (int i = 0; i < orderList.size(); i++) {
            //retrieve invoiceDate
            Date currRoomOrderDate = orderList.get(i).getRoomOrderDate();
            //format to string
            String currStringDate = dateFormat.format(currRoomOrderDate);
            System.out.println("Curr RoomOrderEntity date: " + currStringDate);
            //add all invoice where status = "Unpaid"
            String currentStatus = orderList.get(i).getStatus();
            System.out.println("Current order Status in RoomOrderSession " + currentStatus);
            //compare invoice with status = "Unpaid" and add it to notPaidList 
            if (currentStatus.equals("Unpaid")) {
                //if equal, means customer are suppose to check-in today, add to list
                notPaidList.add(orderList.get(i));
            }
        }
        System.out.println("*** Retrieve  All Unpaid Room Orders  Ended ***\n");
        return notPaidList;
    }//end retrieveUnpaidRoomOrders

    @Override
    public RoomOrderEntity makePaymentSucc(Long roomOrderId) {
        System.out.println("*** Retrieve RoomOrder to set status Started in RoomOrderSession ***\n");
        RoomOrderEntity roomOrder = em.find(RoomOrderEntity.class, roomOrderId);
        System.out.println("retrieve roomOrder status: " + roomOrder.getStatus());
        if (roomOrder.getStatus().equals("Unpaid")) {
            roomOrder.setStatus("Paid");
        }
        System.out.println("retrieve roomOrder status" + roomOrder.getStatus());
        System.out.println("*** Retrieve roomOrder to set status Ended in RoomOrderSession  ***\n");
        return roomOrder;
    }

    @Override
    public RoomOrderEntity getRoomOrderByRoomNumber(String roomNumber) {
        Query q = em.createQuery("SELECT r FROM RoomOrderEntity r WHERE r.roomNumber = :rnId");
        q.setParameter("rnId", roomNumber);
        return (RoomOrderEntity) q.getSingleResult();
    }

}
