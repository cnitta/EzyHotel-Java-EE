/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.ArrayList;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import util.entity.RoomServiceOrderEntity;
import util.enumeration.RoomServiceOrderStatusEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoomServiceOrderSessionTest {
    static EJBContainer container;
    static RoomServiceOrderSessionLocal instance; 
    static Long id = Long.valueOf("1");
    static Double totalPrice = 50.00;
    static String comments = "";
    static RoomServiceOrderStatusEnum status = RoomServiceOrderStatusEnum.PENDING;
    
    public RoomServiceOrderSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (RoomServiceOrderSessionLocal)container.getContext().lookup("java:global/classes/RoomServiceOrderSession");
    }
    
    @AfterClass
    public static void tearDownClass() {
        container.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of retrieveAllRoomServiceOrders method, of class RoomServiceOrderSession.
     */
    @Test
    public void testAARetrieveAllRoomServiceOrders() throws Exception {
        System.out.println("retrieveAllRoomServiceOrders");
        int result = instance.retrieveAllRoomServiceOrders().size();        
        assertTrue("number of devices is " + result, result > 0);
    } 
    
    @Test
    public void testABRetrieveAllRoomServiceOrders() throws Exception {
        System.out.println("retrieveAllRoomServiceOrders");
        int result = instance.retrieveAllRoomServiceOrders().size();        
        assertTrue("number of devices is " + result, result == 0);
    }      

    /**
     * Test of createRoomServiceOrder method, of class RoomServiceOrderSession.
     */
    @Test
    public void testACreateRoomServiceOrder() throws Exception {
        System.out.println("createRoomServiceOrder");
        
        RoomServiceOrderEntity roomServiceOrder = new RoomServiceOrderEntity(totalPrice, status, comments, null, new ArrayList<>());
        
        int sizeBefore = instance.retrieveAllRoomServiceOrders().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createRoomServiceOrder(roomServiceOrder);
        
        int sizeAfter = instance.retrieveAllRoomServiceOrders().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveRoomServiceOrderById method, of class RoomServiceOrderSession.
     */
    @Test
    public void testBRetrieveRoomServiceOrderById() throws Exception {
        System.out.println("retrieveRoomServiceOrderById");
        
        RoomServiceOrderEntity expResult = new RoomServiceOrderEntity(totalPrice, status, comments, null, new ArrayList<>());;
        
        RoomServiceOrderEntity result = instance.retrieveRoomServiceOrderById(id);
      
        result.setRoomServiceOrderId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveRoomServiceOrderByRoomServiceOrderAttributes method, of class RoomServiceOrderSession.
     */
    @Test
    public void testCRetrieveRoomServiceOrderByRoomServiceOrderAttributes() throws Exception {
        System.out.println("retrieveRoomServiceOrderByRoomServiceOrderAttributes");
        RoomServiceOrderEntity roomServiceOrder = new RoomServiceOrderEntity(totalPrice, status, comments, null, new ArrayList<>());;
        
        
        Double expResult = totalPrice;
        Double result = instance.retrieveRoomServiceOrderByRoomServiceOrderAttributes(roomServiceOrder).get(0).getTotalPrice();
       
        assertTrue(expResult == result);
    }



//    /**
//     * Test of retrieveRoomServiceOrderByRoomAttributes method, of class RoomServiceOrderSession.
//     */
//    @Test
//    public void testRetrieveRoomServiceOrderByRoomAttributes() throws Exception {
//        System.out.println("retrieveRoomServiceOrderByRoomAttributes");
//        RoomEntity room = new RoomEntity();
//        room.setRoomUnitNumber(101);
//        
//        Double expResult = ;
//        Double result = instance.retrieveRoomServiceOrderByRoomServiceOrderAttributes(bid).get(0).getBidAmount();
//       
//        assertTrue(expResult == result);
//    }

    /**
     * Test of updateRoomServiceOrder method, of class RoomServiceOrderSession.
     */
    @Test
    public void testDUpdateRoomServiceOrder() throws Exception {
        System.out.println("updateRoomServiceOrder");
        RoomServiceOrderEntity roomServiceOrder = new RoomServiceOrderEntity(totalPrice, status, comments, null, new ArrayList<>());;
        
        roomServiceOrder.setRoomServiceOrderId(id);

        instance.updateRoomServiceOrder(roomServiceOrder);
        
        Double result = instance.retrieveRoomServiceOrderById(id).getTotalPrice();
        
        assertTrue(roomServiceOrder.getTotalPrice() == result);
    }

    /**
     * Test of deleteRoomServiceOrder method, of class RoomServiceOrderSession.
     */
    @Test
    public void testEDeleteRoomServiceOrder() throws Exception {
        System.out.println("deleteRoomServiceOrder");
        int sizeBefore = instance.retrieveAllRoomServiceOrders().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteRoomServiceOrder(id);
        
        int sizeAfter = instance.retrieveAllRoomServiceOrders().size();
        
        assertEquals(expResult, sizeAfter);  
    }
    
}
