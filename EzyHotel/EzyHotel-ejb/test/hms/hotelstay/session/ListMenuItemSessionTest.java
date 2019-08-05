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
import util.entity.ListMenuItemEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListMenuItemSessionTest {
    static EJBContainer container;
    static ListMenuItemSessionLocal instance; 
    static Long id = Long.valueOf("1");
    static Integer quantity = 1;    
    static Double subtotal = 1.50; 
    
    public ListMenuItemSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ListMenuItemSessionLocal)container.getContext().lookup("java:global/classes/ListMenuItemSession");
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
     * Test of retrieveAllListMenuItems method, of class ListMenuItemSession.
     */
    @Test
    public void testAARetrieveAllListMenuItems() throws Exception {
        System.out.println("retrieveAllListMenuItems");
        int result = instance.retrieveAllListMenuItems().size();        
        assertTrue("number of devices is " + result, result > 0);
    } 
    
    @Test
    public void testABRetrieveAllListMenuItems() throws Exception {
        System.out.println("retrieveAllListMenuItems");
        int result = instance.retrieveAllListMenuItems().size();        
        assertTrue("number of devices is " + result, result == 0);
    }      

    /**
     * Test of createListMenuItem method, of class ListMenuItemSession.
     */
    @Test
    public void testACreateListMenuItem() throws Exception {
        System.out.println("createListMenuItem");
        ListMenuItemEntity listMenuItem = new ListMenuItemEntity(null, subtotal, quantity);
        
        int sizeBefore = instance.retrieveAllListMenuItems().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createListMenuItem(listMenuItem);
        
        int sizeAfter = instance.retrieveAllListMenuItems().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveListMenuItemById method, of class ListMenuItemSession.
     */
    @Test
    public void testBRetrieveListMenuItemById() throws Exception {
        System.out.println("retrieveListMenuItemById");
        ListMenuItemEntity expResult = new ListMenuItemEntity(null, subtotal, quantity);
        
        ListMenuItemEntity result = instance.retrieveListMenuItemById(id);
      
        result.setListMenuItemId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveListMenuItemByListMenuItemAttributes method, of class ListMenuItemSession.
     */
    @Test
    public void testCRetrieveListMenuItemByListMenuItemAttributes() throws Exception {
        System.out.println("retrieveListMenuItemByListMenuItemAttributes - subtotal");
        ListMenuItemEntity listMenuItem = new ListMenuItemEntity(null, subtotal, quantity);
        double expResult = subtotal;
        double result = instance.retrieveListMenuItemByListMenuItemAttributes(listMenuItem).get(0).getSubtotal();
       
        assertTrue(expResult == result);
    }
    
    /**
     * Test of retrieveListMenuItemByListMenuItemAttributes method, of class ListMenuItemSession.
     */
    @Test
    public void testDRetrieveListMenuItemByListMenuItemAttributes() throws Exception {
        System.out.println("retrieveListMenuItemByListMenuItemAttributes - quantity");
        ListMenuItemEntity listMenuItem = new ListMenuItemEntity(null, subtotal, quantity);
        Integer expResult = quantity;
        Integer result = instance.retrieveListMenuItemByListMenuItemAttributes(listMenuItem).get(0).getQuantity();
       
        assertTrue(expResult == result);
    }    

    /**
     * Test of updateListMenuItem method, of class ListMenuItemSession.
     */
    @Test
    public void testEUpdateListMenuItem() throws Exception {
        System.out.println("updateListMenuItem");
        ListMenuItemEntity listMenuItem = new ListMenuItemEntity(null, subtotal, quantity);
        
        listMenuItem.setListMenuItemId(id);

        instance.updateListMenuItem(listMenuItem);
        
        Double result = instance.retrieveListMenuItemById(id).getSubtotal();
        
        assertTrue(listMenuItem.getSubtotal() == result);
    }

    /**
     * Test of deleteListMenuItem method, of class ListMenuItemSession.
     */
    @Test
    public void testFDeleteListMenuItem() throws Exception {
        System.out.println("deleteListMenuItem");
        int sizeBefore = instance.retrieveAllListMenuItems().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteListMenuItem(id);
        
        int sizeAfter = instance.retrieveAllListMenuItems().size();
        
        assertEquals(expResult, sizeAfter); 
    }
    
}
