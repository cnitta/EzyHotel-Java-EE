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
import util.entity.MerchandiseEntity;
import util.enumeration.MerchandiseStatusEnum;
import util.exception.MerchandiseNotFoundException;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchandiseSessionTest {
    
    static EJBContainer container;
    static MerchandiseSessionLocal instance;
    static Long id = Long.valueOf("2");
    static String name = "Exclusive Floral Bracelet";
    static String description = "Our Exclusive Floral Bracelet for our exclusive members of Kent Ridge Hotel Group. Comes with the floral design of our diverse local flowers.";
    static Integer costPoints = 300;
    static Integer maxCostPriceLimit = 400;
    static Integer quantityOnHand = 100;
    static MerchandiseStatusEnum merchandiseStatus = MerchandiseStatusEnum.NOT_ON_SALE;
    static Integer poTriggerLevel = 50;
    static Boolean isTriggerOn = Boolean.TRUE;
    
    public MerchandiseSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MerchandiseSessionLocal)container.getContext().lookup("java:global/classes/MerchandiseSession");
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

    @Test
    public void testAARetrieveAllMerchandises() throws Exception {
        System.out.println("retrieveAllMerchandises");
       
        int result = instance.retrieveAllMerchandises().size();        
        assertTrue("number of Merchandise is " + result, result > 0);
    } 

    @Test
    public void testABRetrieveAllAffiliates() throws Exception {
        System.out.println("retrieveAllMerchandise");
       
        int result = instance.retrieveAllMerchandises().size();        
        assertTrue("number of Merchandise is " + result, result == 0);
    }
    
    @Test(expected = MerchandiseNotFoundException.class)
    public void testBRetrieveMerchandiseById() throws Exception {
        System.out.println("retrieveMerchandiseById");

        instance.retrieveMerchandiseById(id);
    }    
    
    @Test
    public void testCRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        int expResult = 0;
        int result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).size();
       
        assertEquals(expResult, result);
    }  

    /**
     * Test of createMerchandise method, of class MerchandiseSession.
     */
    @Test
    public void testDCreateMerchandise() throws Exception {
        System.out.println("createMerchandise");
        MerchandiseEntity merchandise = new MerchandiseEntity(name, description, costPoints, maxCostPriceLimit, quantityOnHand, poTriggerLevel, isTriggerOn);

        int sizeBefore = instance.retrieveAllMerchandises().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createMerchandise(merchandise);
        
        int sizeAfter = instance.retrieveAllMerchandises().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveMerchandiseById method, of class MerchandiseSession.
     */
    @Test
    public void testERetrieveMerchandiseById() throws Exception {
        System.out.println("retrieveMerchandiseById");
        
        MerchandiseEntity expResult = new MerchandiseEntity(name, description, costPoints, maxCostPriceLimit, quantityOnHand, poTriggerLevel, isTriggerOn);
        
        MerchandiseEntity result = instance.retrieveMerchandiseById(id);
      
        result.setMerchandiseId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: name
    @Test
    public void testFRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - name");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setName(name);
        
        String expResult = name;
        String result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: description
    @Test
    public void testGRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - description");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setDescription(description);
        
        String expResult = description;
        String result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getDescription();
        assertEquals(expResult, result);
    }  
    
    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: costPoints
    @Test
    public void testHRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - costPoints");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setCostPoints(costPoints);
        
        Integer expResult = costPoints;
        Integer result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getCostPoints();
        assertEquals(expResult, result);
    }     

        /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: maxCostPrice
    @Test
    public void testIRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - maxCostPrice");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setMaxCostPriceLimit(maxCostPriceLimit);
        
        Integer expResult = maxCostPriceLimit;
        Integer result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getMaxCostPriceLimit();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: quantity
    @Test
    public void testJRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - quantity");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setQuantityOnHand(quantityOnHand);
        
        Integer expResult = quantityOnHand;
        Integer result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getQuantityOnHand();
        assertEquals(expResult, result);
    }  

    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: poTriggerLevel
    @Test
    public void testKRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - poTriggerLevel");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setPoTriggerLevel(poTriggerLevel);
        
        Integer expResult = poTriggerLevel;
        Integer result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getPoTriggerLevel();
        assertEquals(expResult, result);
    }     

    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: isTriggerOn
    @Test
    public void testLRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - trigger");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setIsTriggerOn(isTriggerOn);
        
        Boolean expResult = isTriggerOn;
        Boolean result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getIsTriggerOn();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseByMerchandiseAttributes method, of class MerchandiseSession.
     */
    //test a particular attribute: status
    @Test
    public void testMRetrieveMerchandiseByMerchandiseAttributes() throws Exception {
        System.out.println("retrieveMerchandiseByMerchandiseAttributes - status");
        MerchandiseEntity merchandise = new MerchandiseEntity();
        merchandise.setMerchandiseStatus(merchandiseStatus);
        
        MerchandiseStatusEnum expResult = merchandiseStatus;
        MerchandiseStatusEnum result = instance.retrieveMerchandiseByMerchandiseAttributes(merchandise).get(0).getMerchandiseStatus();
        assertEquals(expResult, result);
    }  
    
    
    /**
     * Test of updateMerchandise method, of class MerchandiseSession.
     */
    @Test
    public void testNUpdateMerchandise() throws Exception {
        System.out.println("updateMerchandise");
        MerchandiseEntity merchandise = new MerchandiseEntity(name, description, costPoints, maxCostPriceLimit, quantityOnHand, poTriggerLevel, isTriggerOn);
        merchandise.setMerchandiseId(id);

        instance.updateMerchandise(merchandise);
        
        Boolean result = instance.retrieveMerchandiseById(id).getIsTriggerOn();
        
        assertEquals(merchandise.getIsTriggerOn(), result);
    }

    /**
     * Test of deleteMerchandise method, of class MerchandiseSession.
     */
    @Test
    public void testODeleteMerchandise() throws Exception {
        System.out.println("deleteMerchandise");
        int sizeBefore = instance.retrieveAllMerchandises().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteMerchandise(id);
        
        int sizeAfter = instance.retrieveAllMerchandises().size();
        
        assertEquals(expResult, sizeAfter);
    }
}
