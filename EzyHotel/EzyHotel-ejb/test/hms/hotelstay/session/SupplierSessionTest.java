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
import util.entity.SupplierEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SupplierSessionTest {
    
    static EJBContainer container;
    static SupplierSessionLocal instance;
    static Long id = Long.valueOf("1");
    static String name = "Best Supplier Pte Ltd";
    static String email;
    static String phoneNum;
    static String company;
    static String address;    
    
    public SupplierSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (SupplierSessionLocal)container.getContext().lookup("java:global/classes/SupplierSession");        
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
     * Test of retrieveAllSuppliers method, of class SupplierSessionBean.
     */
    @Test
    public void testAARetrieveAllSuppliers() throws Exception {
        System.out.println("retrieveAllSuppliers");
        int result = instance.retrieveAllSuppliers().size();        
        assertTrue("number of Suppliers is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllSuppliers() throws Exception {
        System.out.println("retrieveAllSuppliers");
        int result = instance.retrieveAllSuppliers().size();        
        assertTrue("number of Suppliers is " + result, result == 0);
    }    
    
    /**
     * Test of createSupplier method, of class SupplierSessionBean.
     */
    @Test
    public void testACreateSupplier() throws Exception {
        System.out.println("createSupplier");
        SupplierEntity supplier = new SupplierEntity();
        supplier.setName(name);

        int sizeBefore = instance.retrieveAllSuppliers().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createSupplier(supplier);
        
        int sizeAfter = instance.retrieveAllSuppliers().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveSupplierById method, of class SupplierSessionBean.
     */
    @Test
    public void testBRetrieveSupplierById() throws Exception {
        System.out.println("retrieveSupplierById");
        SupplierEntity expResult = new SupplierEntity(name, email, phoneNum, company, address);
        
        SupplierEntity result = instance.retrieveSupplierById(id);
      
        result.setSupplierId(id);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveSupplierBySupplierAttributes method, of class SupplierSessionBean.
     */
    @Test
    public void testCRetrieveSupplierBySupplierAttributes() throws Exception {
        System.out.println("retrieveSupplierBySupplierAttributes");
        SupplierEntity supplier = new SupplierEntity();
        supplier.setName(name);
        
        String expResult = name;
        String result = instance.retrieveSupplierBySupplierAttributes(supplier).get(0).getName();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateSupplier method, of class SupplierSessionBean.
     */
    @Test
    public void testDUpdateSupplier() throws Exception {
        System.out.println("updateSupplier");
        SupplierEntity supplier = new SupplierEntity(name, email, phoneNum, company, address);
        supplier.setName("Cool Supply Pte. Ltd.");
        supplier.setSupplierId(id);

        instance.updateSupplier(supplier);
        
        String result = instance.retrieveSupplierById(id).getName();
        
        assertEquals("Cool Supply Pte. Ltd.", result);
    }

    /**
     * Test of deleteSupplier method, of class SupplierSessionBean.
     */
    @Test
    public void testEDeleteSupplier() throws Exception {
        System.out.println("deleteSupplier");
        int sizeBefore = instance.retrieveAllSuppliers().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteSupplier(id);
        
        int sizeAfter = instance.retrieveAllSuppliers().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
