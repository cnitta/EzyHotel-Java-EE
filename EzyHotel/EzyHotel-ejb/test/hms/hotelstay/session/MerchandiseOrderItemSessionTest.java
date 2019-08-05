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
import util.entity.MerchandiseOrderItemEntity;
import util.exception.MerchandiseOrderItemNotFoundException;

/**
 *
 * @author vincentyeo
 */
public class MerchandiseOrderItemSessionTest {
    static EJBContainer container;
    static MerchandiseOrderItemSessionLocal instance;
    static Long id = Long.valueOf("1");
    static String name = "Floral bracelet";
    static String description = "Good Floral Bracelet";
    static Double unitPrice = 1.00;
    
    public MerchandiseOrderItemSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MerchandiseOrderItemSessionLocal)container.getContext().lookup("java:global/classes/MerchandiseOrderItemSession");

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of retrieveAllMerchandiseOrderItems method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testAARetrieveAllMerchandiseOrderItems(){
        System.out.println("retrieveAllMerchandiseOrderItems");
   
        int result = instance.retrieveAllMerchandiseOrderItems().size();        
        assertTrue("number of MerchandiseOrderItems is " + result, result > 0);
    } 
    
    /**
     * Test of retrieveAllMerchandiseOrderItems method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testABRetrieveAllMerchandiseOrderItems() {
        System.out.println("retrieveAllMerchandiseOrderItems");
       
        int result = instance.retrieveAllMerchandiseOrderItems().size();        
        assertTrue("number of MerchandiseOrderItems is " + result, result == 0);
    }     
    
    /**
     * Test of createMerchandiseOrderItem method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testACreateMerchandiseOrderItem() {
        System.out.println("createMerchandiseOrderItem");
        MerchandiseOrderItemEntity merchandiseOrderItem = new MerchandiseOrderItemEntity();
        merchandiseOrderItem.setName(name);
        merchandiseOrderItem.setDescription(description);
        merchandiseOrderItem.setUnitPrice(unitPrice);        
        
        int sizeBefore = instance.retrieveAllMerchandiseOrderItems().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createMerchandiseOrderItem(merchandiseOrderItem);
        
        int sizeAfter = instance.retrieveAllMerchandiseOrderItems().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveMerchandiseOrderItemById method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testBRetrieveMerchandiseOrderItemById() throws MerchandiseOrderItemNotFoundException {
        System.out.println("retrieveMerchandiseOrderItemById");
        
        MerchandiseOrderItemEntity expResult = new MerchandiseOrderItemEntity();
        
        MerchandiseOrderItemEntity result = instance.retrieveMerchandiseOrderItemById(id);
      
        result.setMerchandise(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testCRetrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes() {
        System.out.println("retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes");
        MerchandiseOrderItemEntity merchandiseOrderItem = new MerchandiseOrderItemEntity();
        merchandiseOrderItem.setName(name);
        
        String expResult = name;
        String result = instance.retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes(merchandiseOrderItem).get(0).getName();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testDRetrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes() {
        System.out.println("retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes");
        MerchandiseOrderItemEntity merchandiseOrderItem = new MerchandiseOrderItemEntity();
        merchandiseOrderItem.setDescription(description);
        
        String expResult = description;
        String result = instance.retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes(merchandiseOrderItem).get(0).getDescription();
        assertEquals(expResult, result);
    }
    
     /**
     * Test of retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testERetrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes() {
        System.out.println("retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes");
        MerchandiseOrderItemEntity merchandiseOrderItem = new MerchandiseOrderItemEntity();
        merchandiseOrderItem.setUnitPrice(unitPrice);
        
        double expResult = unitPrice;
        double result = instance.retrieveMerchandiseOrderItemByMerchandiseOrderItemAttributes(merchandiseOrderItem).get(0).getUnitPrice();
        assertTrue(expResult == result);
    }

    /**
     * Test of updateMerchandiseOrderItem method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testDUpdateMerchandiseOrderItem() throws MerchandiseOrderItemNotFoundException {
        System.out.println("updateMerchandiseOrderItem");
        MerchandiseOrderItemEntity merchandiseOrderItem = new MerchandiseOrderItemEntity();
        merchandiseOrderItem.setName("newName");
        merchandiseOrderItem.setOrderItemId(id);

        instance.updateMerchandiseOrderItem(merchandiseOrderItem);
        
        String result = instance.retrieveMerchandiseOrderItemById(id).getName();
        
        assertEquals("newName", result);
    }

    /**
     * Test of deleteMerchandiseOrderItem method, of class MerchandiseOrderItemSession.
     */
    @Test
    public void testEDeleteMerchandiseOrderItem() throws MerchandiseOrderItemNotFoundException {
        System.out.println("deleteMerchandiseOrderItem");
        int sizeBefore = instance.retrieveAllMerchandiseOrderItems().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteMerchandiseOrderItem(id);
        
        int sizeAfter = instance.retrieveAllMerchandiseOrderItems().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}

   
