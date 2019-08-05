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
import util.entity.MenuItemEntity;
import util.enumeration.MenuItemCategoryEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuItemSessionTest {
    static EJBContainer container;
    static MenuItemSessionLocal instance;  
    static Long id = Long.valueOf("1");
    static String menuItemName = "Lemon Barley Drink";
    static String description = "Lemon Barley Drink that is locally homebrew and made fresh.";
    static Double unitPrice = 1.50; 
    static MenuItemCategoryEnum category = MenuItemCategoryEnum.DRINKS;
    
    public MenuItemSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MenuItemSessionLocal)container.getContext().lookup("java:global/classes/MenuItemSession");        
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
     * Test of retrieveAllMenuItems method, of class MenuItemSession.
     */
    @Test
    public void testAARetrieveAllMenuItems() throws Exception {
        System.out.println("retrieveAllMenuItems");
        int result = instance.retrieveAllMenuItems().size();        
        assertTrue("number of devices is " + result, result > 0);
    } 
    
    /**
     * Test of retrieveAllMenuItems method, of class MenuItemSession.
     */
    @Test
    public void testABRetrieveAllMenuItems() throws Exception {
        System.out.println("retrieveAllMenuItems");
        int result = instance.retrieveAllMenuItems().size();        
        assertTrue("number of devices is " + result, result == 0);
    }      

    /**
     * Test of createMenuItem method, of class MenuItemSession.
     */
    @Test
    public void testACreateMenuItem() throws Exception {
        System.out.println("createMenuItem");
        MenuItemEntity menuItem = new MenuItemEntity(menuItemName, description, unitPrice, category);
        
        int sizeBefore = instance.retrieveAllMenuItems().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createMenuItem(menuItem);
        
        int sizeAfter = instance.retrieveAllMenuItems().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveMenuItemById method, of class MenuItemSession.
     */
    @Test
    public void testBRetrieveMenuItemById() throws Exception {
        System.out.println("retrieveMenuItemById");
        MenuItemEntity expResult = new MenuItemEntity(menuItemName, description, unitPrice, category);
        
        MenuItemEntity result = instance.retrieveMenuItemById(id);
      
        result.setMenuItemId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMenuItemByMenuItemAttributes method, of class MenuItemSession.
     */
    @Test
    public void testCRetrieveMenuItemByMenuItemAttributes() throws Exception {
        System.out.println("retrieveMenuItemByMenuItemAttributes");
        MenuItemEntity menuItem = new MenuItemEntity(menuItemName, description, unitPrice, category);
        String expResult = menuItemName;
        String result = instance.retrieveMenuItemByMenuItemAttributes(menuItem).get(0).getMenuItemName();
       
        assertEquals(expResult, result);
    }
    
    /**
     * Test of retrieveMenuItemByMenuItemAttributes method, of class MenuItemSession.
     */
    @Test
    public void testDRetrieveMenuItemByMenuItemAttributes() throws Exception {
        System.out.println("retrieveMenuItemByMenuItemAttributes");
        MenuItemEntity menuItem = new MenuItemEntity(menuItemName, description, unitPrice, category);
        String expResult = description;
        String result = instance.retrieveMenuItemByMenuItemAttributes(menuItem).get(0).getDescription();
       
        assertEquals(expResult, result);
    }
    
    /**
     * Test of retrieveMenuItemByMenuItemAttributes method, of class MenuItemSession.
     */
    @Test
    public void testERetrieveMenuItemByMenuItemAttributes() throws Exception {
        System.out.println("retrieveMenuItemByMenuItemAttributes");
        MenuItemEntity menuItem = new MenuItemEntity(menuItemName, description, unitPrice, category);
        Double expResult = unitPrice;
        Double result = instance.retrieveMenuItemByMenuItemAttributes(menuItem).get(0).getUnitPrice();
       
        assertTrue(expResult == result);
    }    


    /**
     * Test of updateMenuItem method, of class MenuItemSession.
     */
    @Test
    public void testFUpdateMenuItem() throws Exception {
        System.out.println("updateMenuItem");
        MenuItemEntity menuItem = new MenuItemEntity(menuItemName, description, unitPrice, category);
        
        menuItem.setMenuItemId(id);

        instance.updateMenuItem(menuItem);
        
        Double result = instance.retrieveMenuItemById(id).getUnitPrice();
        
        assertTrue(menuItem.getUnitPrice() == result);
    }

    /**
     * Test of deleteMenuItem method, of class MenuItemSession.
     */
    @Test
    public void testGDeleteMenuItem() throws Exception {
        System.out.println("deleteMenuItem");
        int sizeBefore = instance.retrieveAllMenuItems().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteMenuItem(id);
        
        int sizeAfter = instance.retrieveAllMenuItems().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
