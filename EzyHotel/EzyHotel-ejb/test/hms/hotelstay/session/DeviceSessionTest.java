/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import util.entity.DeviceEntity;
import util.entity.RoomEntity;
import util.enumeration.DeviceCategoryEnum;
import util.enumeration.DeviceStateEnum;
import util.enumeration.DeviceStatusEnum;
import util.exception.DeviceNotFoundException;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceSessionTest {
    
    static EJBContainer container;
    static DeviceSessionLocal instance;
    static String serialNumber = "X0XXX0X000XX";
    static Long id = Long.valueOf("17");

    
    public DeviceSessionTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
           container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
           instance = (DeviceSessionLocal) container.getContext().lookup("java:global/classes/DeviceSession");           
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
     * Test of retrieveAllDevices method, of class DeviceSession.
     */
    @Test
    public void testARetrieveAllDevices() throws Exception {
        System.out.println("retrieveAllDevices");
        int expResult = 0;
        int result = instance.retrieveAllDevices().size();        
        assertEquals(expResult, result);
    } 
    
    /**
     * Test of retrieveDeviceById method, of class DeviceSession.
     */
    @Test(expected = DeviceNotFoundException.class)
    public void testBRetrieveDeviceById() throws Exception {
        System.out.println("retrieveDeviceById");

        instance.retrieveDeviceById(id);        
    }

    /**
     * Test of retrieveDeviceByDeviceAttributes method, of class DeviceSession.
     */
    @Test
    public void testCRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes");
        DeviceEntity device = new DeviceEntity(serialNumber, DeviceCategoryEnum.TABLET, "iPad Pro", "Apple Inc.");
        int expResult = 0;
        int result = instance.retrieveDeviceByDeviceAttributes(device).size();
       
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveDeviceByRoomAttributes method, of class DeviceSession.
     */
    @Test
    public void testDRetrieveDeviceByRoomAttributes() throws Exception {
        System.out.println("retrieveDeviceByRoomAttributes");
        RoomEntity room = new RoomEntity();
        int expResult = 0;
        int result = instance.retrieveDeviceByRoomAttributes(room).size();
        assertEquals(expResult, result);
    }    

    /**
     * Test of createDevice method, of class DeviceSession.
     */
    @Test
    public void testECreateDevice() throws Exception {
        System.out.println("createDevice");
        DeviceEntity device = new DeviceEntity(serialNumber, DeviceCategoryEnum.TABLET, "iPad Pro", "Apple Inc.");
        
        int sizeBefore = instance.retrieveAllDevices().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createDevice(device);
        
        int sizeAfter = instance.retrieveAllDevices().size();
                
        assertEquals(expResult, sizeAfter);
    }
    
    @Test
    public void testFRetrieveAllDevices() throws Exception {
        System.out.println("retrieveAllDevices");
       
        int result = instance.retrieveAllDevices().size();        
        assertTrue("number of devices is " + result, result > 0);
    } 

    @Test
    public void testFARetrieveAllDevices() throws Exception {
        System.out.println("retrieveAllDevices");
       
        int result = instance.retrieveAllDevices().size();        
        assertTrue("number of devices is " + result, result == 0);
    } 
    
    /**
     * Test of retrieveDeviceById method, of class DeviceSession.
     */
    @Test
    public void testGRetrieveDeviceById() throws Exception {
        System.out.println("retrieveDeviceById");
        
        DeviceEntity expResult = new DeviceEntity(serialNumber, DeviceCategoryEnum.TABLET, "iPad Pro", "Apple Inc.");
        
        DeviceEntity result = instance.retrieveDeviceById(id);
      
        result.setDeviceId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveDeviceByDeviceAttributes method, of class DeviceSession.
     */
    //test a particular attribute: SerialNumber
    @Test
    public void testHRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes- SerialNumber");
        DeviceEntity device = new DeviceEntity();
        device.setSerialNumber(serialNumber);
        String expResult = serialNumber;
        String result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getSerialNumber();
        assertEquals(expResult, result);
    }
    
    //test a particular attribute: Category
    @Test
    public void testIRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes- Category");
        DeviceEntity device = new DeviceEntity();
        device.setDeviceCategory(DeviceCategoryEnum.TABLET);
        DeviceCategoryEnum expResult = DeviceCategoryEnum.TABLET;
        DeviceCategoryEnum result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getDeviceCategory();
        assertEquals(expResult, result);
    }
    
    //test a particular attribute: Model
    @Test
    public void testJRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes - Model");
        DeviceEntity device = new DeviceEntity();
        device.setDeviceModel("iPad Pro");
        String expResult = "iPad Pro";
        String result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getDeviceModel();
        assertEquals(expResult, result);
    }
    
    //test a particular attribute: Status
    @Test
    public void testKRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes - Status");
        DeviceEntity device = new DeviceEntity();
        device.setDeviceStatus(DeviceStatusEnum.WORKING);
        DeviceStatusEnum expResult = DeviceStatusEnum.WORKING;
        DeviceStatusEnum result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getDeviceStatus();
        assertEquals(expResult, result);
    }   

    //test a particular attribute: Manufacturer
    @Test
    public void testLRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes - Manufacturer");
        DeviceEntity device = new DeviceEntity();
        device.setManufacturerName("Apple Inc.");
        String expResult = "Apple Inc.";
        String result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getManufacturerName();
        assertEquals(expResult, result);
    }
    
    //test a particular attribute: Status
    @Test
    public void testMRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes - State");
        DeviceEntity device = new DeviceEntity();
        device.setDeviceState(DeviceStateEnum.NOT_DEPLOYED);
        DeviceStateEnum expResult = DeviceStateEnum.NOT_DEPLOYED;
        DeviceStateEnum result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getDeviceState();
        assertEquals(expResult, result);
    }   
    
    //test a particular attribute: Date
    @Test
    public void testNRetrieveDeviceByDeviceAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes - Date");
        DeviceEntity device = new DeviceEntity();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
 
        device.setLastMaintenanceDate(df.parse("2019-02-19"));
        
        Boolean expResult = false;
        
        Date result = instance.retrieveDeviceByDeviceAttributes(device).get(0).getLastMaintenanceDate();
        
        assertEquals(expResult, result.before(device.getLastMaintenanceDate()));
    }     

    /**
     * Test of updateDevice method, of class DeviceSession.
     */
    @Test
    public void testOUpdateDevice() throws Exception {
        System.out.println("updateDevice");
        DeviceEntity device = new DeviceEntity(serialNumber, DeviceCategoryEnum.TABLET, "iPad Pro 2", "Apple Inc.");
        
        device.setDeviceId(id);

        instance.updateDevice(device);
        
        String result = instance.retrieveDeviceById(id).getDeviceModel();
        
        assertEquals(device.getDeviceModel(), result);
    }

    /**
     * Test of deleteDevice method, of class DeviceSession.
     */
    @Test
    public void testPDeleteDevice() throws Exception {
        System.out.println("deleteDevice");
        
        int sizeBefore = instance.retrieveAllDevices().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteDevice(id);
        
        int sizeAfter = instance.retrieveAllDevices().size();
        
        assertEquals(expResult, sizeAfter);        
    }
    
//    @Test
//    public void testDeleteDeviceBySerialNumber() throws Exception {
//        System.out.println("deleteDevice");
//        
//        instance.deleteDeviceBySerialNumber(serialNumber);
//    }
    
    /**
     * Test of retrieveDeviceByRoomAttributes method, of class DeviceSession.
     */
//    @Test
//    public void testMRetrieveDeviceByRoomAttributes() throws Exception {
//        System.out.println("retrieveDeviceByRoomAttributes");
//        RoomEntity room = new RoomEntity();     
//        
//        List<DeviceEntity> expResult = null;
//        List<DeviceEntity> result = instance.retrieveDeviceByRoomAttributes(room);
//        assertEquals(expResult, result);
//
//    } 
}
