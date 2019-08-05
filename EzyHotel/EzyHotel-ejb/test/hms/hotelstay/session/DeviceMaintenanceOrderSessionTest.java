/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.Date;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import util.entity.DeviceMaintenanceOrderEntity;
import util.enumeration.MaintenanceOrderStateEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceMaintenanceOrderSessionTest {
    static EJBContainer container;
    static DeviceMaintenanceOrderSessionLocal instance;
    static Long id = Long.valueOf("1");
    static Date startDate = new Date();
    static Date endDate = new Date();
    static MaintenanceOrderStateEnum maintenanceOrderStateEnum = MaintenanceOrderStateEnum.PENDING;
    
    public DeviceMaintenanceOrderSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (DeviceMaintenanceOrderSessionLocal)container.getContext().lookup("java:global/classes/DeviceMaintenanceOrderSession");
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
     * Test of retrieveAllDeviceMaintenanceOrders method, of class DeviceMaintenanceOrderSession.
     */
    @Test
    public void testAARetrieveAllDeviceMaintenanceOrders() throws Exception {
        System.out.println("retrieveAllDeviceMaintenanceOrders");
        int result = instance.retrieveAllDeviceMaintenanceOrders().size();        
        assertTrue("number of DeviceMaintenanceOrders is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllDeviceMaintenanceOrders() throws Exception {
        System.out.println("retrieveAllDeviceMaintenanceOrders");
        int result = instance.retrieveAllDeviceMaintenanceOrders().size();        
        assertTrue("number of DeviceMaintenanceOrders is " + result, result == 0);
    }    
    
    /**
     * Test of createDeviceMaintenanceOrder method, of class DeviceMaintenanceOrderSession.
     */
    @Test
    public void testACreateDeviceMaintenanceOrder() throws Exception {
        System.out.println("createDeviceMaintenanceOrder");
        DeviceMaintenanceOrderEntity deviceMaintenanceOrder = new DeviceMaintenanceOrderEntity();

        int sizeBefore = instance.retrieveAllDeviceMaintenanceOrders().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createDeviceMaintenanceOrder(deviceMaintenanceOrder);
        
        int sizeAfter = instance.retrieveAllDeviceMaintenanceOrders().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveDeviceMaintenanceOrderById method, of class DeviceMaintenanceOrderSession.
     */
    @Test
    public void testBRetrieveDeviceMaintenanceOrderById() throws Exception {
        System.out.println("retrieveDeviceMaintenanceOrderById");
        DeviceMaintenanceOrderEntity expResult = new DeviceMaintenanceOrderEntity();
        
        DeviceMaintenanceOrderEntity result = instance.retrieveDeviceMaintenanceOrderById(id);
      
        result.setMaintenanceOrderId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveDeviceMaintenanceOrderByDeviceMaintenanceOrderAttributes method, of class DeviceMaintenanceOrderSession.
     */
    @Test
    public void testCRetrieveDeviceMaintenanceOrderByDeviceMaintenanceOrderAttributes() throws Exception {
        System.out.println("retrieveDeviceMaintenanceOrderByDeviceMaintenanceOrderAttributes");
        DeviceMaintenanceOrderEntity deviceMaintenanceOrder = new DeviceMaintenanceOrderEntity();
        deviceMaintenanceOrder.setStartDate(startDate);

        Date expResult = startDate;        
        Date result = instance.retrieveDeviceMaintenanceOrderByDeviceMaintenanceOrderAttributes(deviceMaintenanceOrder).get(0).getStartDate();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateDeviceMaintenanceOrder method, of class DeviceMaintenanceOrderSession.
     */
    @Test
    public void testDUpdateDeviceMaintenanceOrder() throws Exception {
        System.out.println("updateDeviceMaintenanceOrder");
        DeviceMaintenanceOrderEntity deviceMaintenanceOrder = new DeviceMaintenanceOrderEntity();
        deviceMaintenanceOrder.setMaintenanceOrderId(id);
        Date newDate = new Date();
        deviceMaintenanceOrder.setStartDate(newDate);
        instance.updateDeviceMaintenanceOrder(deviceMaintenanceOrder);
        
        Date result = instance.retrieveDeviceMaintenanceOrderById(id).getStartDate();
        
        assertEquals(newDate, result);
    }

    /**
     * Test of deleteDeviceMaintenanceOrder method, of class DeviceMaintenanceOrderSession.
     */
    @Test
    public void testEDeleteDeviceMaintenanceOrder() throws Exception {
        System.out.println("deleteDeviceMaintenanceOrder");
        int sizeBefore = instance.retrieveAllDeviceMaintenanceOrders().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteDeviceMaintenanceOrder(id);
        
        int sizeAfter = instance.retrieveAllDeviceMaintenanceOrders().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
