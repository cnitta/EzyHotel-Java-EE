/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import util.entity.MerchandiseOrderEntity;
import util.entity.RoomEntity;
import util.enumeration.MerchandiseOrderStatusEnum;
import util.exception.MerchandiseOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchandiseOrderSessionTest {
    static EJBContainer container;
    static MerchandiseOrderSessionLocal instance;
    static Long id = Long.valueOf("4");
    static MerchandiseOrderStatusEnum merchandiseOrderStatus = MerchandiseOrderStatusEnum.PENDING;
    static Integer quantityRedeemed = 1;
    
    public MerchandiseOrderSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MerchandiseOrderSessionLocal)container.getContext().lookup("java:global/classes/MerchandiseOrderSession");
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
     * Test of retrieveAllMerchandiseOrders method, of class DeviceSession.
     */
    @Test
    public void testARetrieveAllMerchandiseOrders(){
        System.out.println("retrieveAllMerchandiseOrders");
        int expResult = 0;
        int result = instance.retrieveAllMerchandiseOrders().size();        
        assertEquals(expResult, result);
    } 
    
    /**
     * Test of retrieveMerchandiseOrderById method, of class DeviceSession.
     */
    @Test(expected = MerchandiseOrderNotFoundException.class)
    public void testBRetrieveMerchandiseOrderById() throws Exception {
        System.out.println("retrieveMerchandiseOrderById");

        instance.retrieveMerchandiseOrderById(id);        
    }
    
    /**
     * Test of createMerchandiseOrder method, of class MerchandiseOrderSession.
     */
    @Test
    public void testCCreateMerchandiseOrder() throws Exception {
        System.out.println("createMerchandiseOrder");
        MerchandiseOrderEntity merchandiseOrder = new MerchandiseOrderEntity();
        
        int sizeBefore = instance.retrieveAllMerchandiseOrders().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createMerchandiseOrder(merchandiseOrder);
        
        int sizeAfter = instance.retrieveAllMerchandiseOrders().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveMerchandiseOrderById method, of class MerchandiseOrderSession.
     */
    @Test
    public void testDRetrieveMerchandiseOrderById() throws Exception {
        System.out.println("retrieveMerchandiseOrderById");
        
        MerchandiseOrderEntity expResult = new MerchandiseOrderEntity(null, quantityRedeemed, merchandiseOrderStatus, null);
        
        MerchandiseOrderEntity result = instance.retrieveMerchandiseOrderById(id);
      
        result.setMerchandiseOrderEntityId(null);
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of retrieveDeviceByRoomAttributes method, of class DeviceSession.
     */
    @Test
    public void testERetrieveMerchandiseOrderByRoomAttributes() throws Exception {
        System.out.println("retrieveMerchandiseOrderByRoomAttributes");
        RoomEntity room = new RoomEntity();
        int expResult = 0;
        int result = instance.retrieveMerchandiseOrderByRoomAttributes(room).size();
        assertEquals(expResult, result);
    }      

    /**
     * Test of retrieveMerchandiseOrderByMerchandiseOrderAttributes method, of class MerchandiseOrderSession.
     */
    //test a particular attribute: status
    @Test
    public void testFRetrieveMerchandiseOrderByMerchandiseOrderAttributes() throws Exception {
        System.out.println("retrieveMerchandiseOrderByMerchandiseOrderAttributes - status");
        MerchandiseOrderEntity merchandiseOrder = new MerchandiseOrderEntity();
        
        merchandiseOrder.setStatus(merchandiseOrderStatus);
        
        MerchandiseOrderStatusEnum expResult = merchandiseOrderStatus;
        MerchandiseOrderStatusEnum result = instance.retrieveMerchandiseOrderByMerchandiseOrderAttributes(merchandiseOrder).get(0).getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseOrderByMerchandiseOrderAttributes method, of class MerchandiseOrderSession.
     */
    //test a particular attribute: quantity
    @Test
    public void testGRetrieveMerchandiseOrderByMerchandiseOrderAttributes() throws Exception {
        System.out.println("retrieveMerchandiseOrderByMerchandiseOrderAttributes - qty");
        MerchandiseOrderEntity merchandiseOrder = new MerchandiseOrderEntity();
        
        merchandiseOrder.setQuantityRedeemed(quantityRedeemed);
        
        Integer expResult = quantityRedeemed;
        Integer result = instance.retrieveMerchandiseOrderByMerchandiseOrderAttributes(merchandiseOrder).get(0).getQuantityRedeemed();
        assertEquals(expResult, result);
    }

    /**
     * Test of updateMerchandiseOrder method, of class MerchandiseOrderSession.
     */
    @Test
    public void testHUpdateMerchandiseOrder() throws Exception {
        System.out.println("updateMerchandiseOrder");
        MerchandiseOrderEntity merchandiseOrder = new MerchandiseOrderEntity(null, quantityRedeemed, merchandiseOrderStatus, null);
        merchandiseOrder.setMerchandiseOrderEntityId(id);

        instance.updateMerchandiseOrder(merchandiseOrder);
        
        MerchandiseOrderStatusEnum result = instance.retrieveMerchandiseOrderById(id).getStatus();
        
        assertEquals(merchandiseOrder.getStatus(), result);
    }

    /**
     * Test of deleteMerchandiseOrder method, of class MerchandiseOrderSession.
     */
    @Test
    public void testIDeleteMerchandiseOrder() throws Exception {
        System.out.println("deleteMerchandiseOrder");
        
        int sizeBefore = instance.retrieveAllMerchandiseOrders().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteMerchandiseOrder(id);
        
        int sizeAfter = instance.retrieveAllMerchandiseOrders().size();
        
        assertEquals(expResult, sizeAfter);   
    }
    
}
