/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import util.entity.MerchandisePurchaseOrderEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchandisePurchaseOrderSessionTest {
    static EJBContainer container;
    static MerchandisePurchaseOrderSessionLocal instance;
    static Long id = Long.valueOf("1");
    private Date orderDate = new Date();
    
    public MerchandisePurchaseOrderSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MerchandisePurchaseOrderSessionLocal)container.getContext().lookup("java:global/classes/MerchandisePurchaseOrderSession");        
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
     * Test of retrieveAllMerchandisePurchaseOrders method, of class MerchandisePurchaseOrderSession.
     */
    @Test
    public void testAARetrieveAllMerchandisePurchaseOrders() throws Exception {
        System.out.println("retrieveAllMerchandisePurchaseOrders");
       
        int result = instance.retrieveAllMerchandisePurchaseOrders().size();        
        assertTrue("number of MerchandisePurchaseOrders is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllMerchandisePurchaseOrders() throws Exception {
        System.out.println("retrieveAllMerchandisePurchaseOrders");
       
        int result = instance.retrieveAllMerchandisePurchaseOrders().size();        
        assertTrue("number of MerchandisePurchaseOrders is " + result, result == 0);
    }
    
    /**
     * Test of createMerchandisePurchaseOrder method, of class MerchandisePurchaseOrderSession.
     */
    @Test
    public void testACreateMerchandisePurchaseOrder() throws Exception {
        System.out.println("createMerchandisePurchaseOrder");
       
        MerchandisePurchaseOrderEntity merchandisePurchaseOrder = new MerchandisePurchaseOrderEntity();
       merchandisePurchaseOrder.setOrderDate(orderDate);
       
        int sizeBefore = instance.retrieveAllMerchandisePurchaseOrders().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createMerchandisePurchaseOrder(merchandisePurchaseOrder);
        
        int sizeAfter = instance.retrieveAllMerchandisePurchaseOrders().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveMerchandisePurchaseOrderById method, of class MerchandisePurchaseOrderSession.
     */
    @Test
    public void testBRetrieveMerchandisePurchaseOrderById() throws Exception {
        System.out.println("retrieveMerchandisePurchaseOrderById");
       MerchandisePurchaseOrderEntity expResult = new MerchandisePurchaseOrderEntity();
       
       MerchandisePurchaseOrderEntity result = instance.retrieveMerchandisePurchaseOrderById(id);
      
        result.setPurchaseOrderId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes method, of class MerchandisePurchaseOrderSession.
     */
    @Test
    public void testCRetrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes() throws Exception {

        MerchandisePurchaseOrderEntity merchandisePurchaseOrder = new MerchandisePurchaseOrderEntity();
        merchandisePurchaseOrder.setOrderDate(orderDate);
        
        Date expResult = orderDate;
        
        List<MerchandisePurchaseOrderEntity> merchandisePurchaseOrders = instance.retrieveMerchandisePurchaseOrderByMerchandisePurchaseOrderAttributes(merchandisePurchaseOrder);
        
        System.out.println("merchandisePOs Size " + merchandisePurchaseOrders.size());
        
        Date result = merchandisePurchaseOrders.get(0).getOrderDate();
        assertEquals(expResult, result);
    }


    /**
     * Test of updateMerchandisePurchaseOrder method, of class MerchandisePurchaseOrderSession.
     */
    @Test
    public void testDUpdateMerchandisePurchaseOrder() throws Exception {
        System.out.println("updateMerchandisePurchaseOrder");
       MerchandisePurchaseOrderEntity merchandisePurchaseOrder = new MerchandisePurchaseOrderEntity();
       Date newDate = new Date();
       merchandisePurchaseOrder.setOrderDate(newDate);
       merchandisePurchaseOrder.setPurchaseOrderId(id);

        instance.updateMerchandisePurchaseOrder(merchandisePurchaseOrder);
        
        Date result = instance.retrieveMerchandisePurchaseOrderById(id).getOrderDate();
        
        assertEquals(newDate, result);
    }

    /**
     * Test of deleteMerchandisePurchaseOrder method, of class MerchandisePurchaseOrderSession.
     */
    @Test
    public void testEDeleteMerchandisePurchaseOrder() throws Exception {
        System.out.println("deleteMerchandisePurchaseOrder");
        int sizeBefore = instance.retrieveAllMerchandisePurchaseOrders().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteMerchandisePurchaseOrder(id);
        
        int sizeAfter = instance.retrieveAllMerchandisePurchaseOrders().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
