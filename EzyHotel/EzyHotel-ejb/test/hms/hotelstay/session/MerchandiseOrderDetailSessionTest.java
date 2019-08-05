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
import util.entity.MerchandiseOrderDetailEntity;
import util.exception.MerchandiseOrderDetailNotFoundException;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchandiseOrderDetailSessionTest {
    static EJBContainer container;
    static MerchandiseOrderDetailSessionLocal instance;  
    static Long id = Long.valueOf("1");
    static Integer quantity = 100;
    
    public MerchandiseOrderDetailSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (MerchandiseOrderDetailSessionLocal)container.getContext().lookup("java:global/classes/MerchandiseOrderDetailSession");       
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
     * Test of retrieveAllMerchandiseOrderDetails method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testAARetrieveAllMerchandiseOrderDetails()  {
        System.out.println("retrieveAllMerchandiseOrderDetails");
        int result = instance.retrieveAllMerchandiseOrderDetails().size();        
        assertTrue("number of Merchandise is " + result, result > 0);
        
    }
    
    /**
     * Test of retrieveAllMerchandiseOrderDetails method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testABRetrieveAllMerchandiseOrderDetails()  {
        System.out.println("retrieveAllMerchandiseOrderDetails");
        int result = instance.retrieveAllMerchandiseOrderDetails().size();        
        assertTrue("number of Merchandise is " + result, result > 0);
        
    }    
    /**
     * Test of createMerchandiseOrderDetail method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testCCreateMerchandiseOrderDetail()  {
        System.out.println("createMerchandiseOrderDetail");
        MerchandiseOrderDetailEntity merchandiseOrderDetail = new MerchandiseOrderDetailEntity();
        merchandiseOrderDetail.setQuantity(quantity);
        
        int sizeBefore = instance.retrieveAllMerchandiseOrderDetails().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createMerchandiseOrderDetail(merchandiseOrderDetail);
        
        int sizeAfter = instance.retrieveAllMerchandiseOrderDetails().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveMerchandiseOrderDetailById method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testDRetrieveMerchandiseOrderDetailById() throws MerchandiseOrderDetailNotFoundException {
        System.out.println("retrieveMerchandiseOrderDetailById");
        MerchandiseOrderDetailEntity expResult = new MerchandiseOrderDetailEntity();
        
        MerchandiseOrderDetailEntity result = instance.retrieveMerchandiseOrderDetailById(id);
      
        result.setOrderDetailId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveMerchandiseOrderDetailByMerchandiseOrderDetailAttributes method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testERetrieveMerchandiseOrderDetailByMerchandiseOrderDetailAttributes() throws Exception {
        System.out.println("retrieveMerchandiseOrderDetailByMerchandiseOrderDetailAttributes");
        MerchandiseOrderDetailEntity merchandiseOrderDetail = new MerchandiseOrderDetailEntity();
        merchandiseOrderDetail.setQuantity(quantity);
        
        Integer expResult = quantity;
        Integer result = instance.retrieveMerchandiseOrderDetailByMerchandiseOrderDetailAttributes(merchandiseOrderDetail).get(0).getQuantity();
        assertEquals(expResult, result);
    }


    /**
     * Test of updateMerchandiseOrderDetail method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testFUpdateMerchandiseOrderDetail() throws MerchandiseOrderDetailNotFoundException{
        System.out.println("updateMerchandiseOrderDetail");
        MerchandiseOrderDetailEntity merchandiseOrderDetail = new MerchandiseOrderDetailEntity();
        merchandiseOrderDetail.setQuantity(quantity);
        
        merchandiseOrderDetail.setOrderDetailId(id);

        instance.updateMerchandiseOrderDetail(merchandiseOrderDetail);
        
        Integer result = instance.retrieveMerchandiseOrderDetailById(id).getQuantity();
        
        assertEquals(merchandiseOrderDetail.getQuantity(), result);
    }

    /**
     * Test of deleteMerchandiseOrderDetail method, of class MerchandiseOrderDetailSession.
     */
    @Test
    public void testGDeleteMerchandiseOrderDetail() throws MerchandiseOrderDetailNotFoundException{
        System.out.println("deleteMerchandiseOrderDetail");
        int sizeBefore = instance.retrieveAllMerchandiseOrderDetails().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteMerchandiseOrderDetail(id);
        
        int sizeAfter = instance.retrieveAllMerchandiseOrderDetails().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
