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
import util.entity.ARContentViewEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ARContentViewSessionTest {
    static EJBContainer container;
    static ARContentViewSessionLocal instance;
    static Long id = Long.valueOf("1");
    static Date startDate = new Date();
    static Date endDate = new Date();  
    
    public ARContentViewSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ARContentViewSessionLocal)container.getContext().lookup("java:global/classes/ARContentViewSession");
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
     * Test of retrieveAllARContentViews method, of class ARContentViewSession.
     */
    @Test
    public void testAARetrieveAllARContentViews() throws Exception {
        System.out.println("retrieveAllARContentViews");
        int result = instance.retrieveAllARContentViews().size();        
        assertTrue("number of ARContentViews is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllARContentViews() throws Exception {
        System.out.println("retrieveAllARContentViews");
        int result = instance.retrieveAllARContentViews().size();        
        assertTrue("number of ARContentViews is " + result, result == 0);
    }    
    
    /**
     * Test of createARContentView method, of class ARContentViewSession.
     */
    @Test
    public void testACreateARContentView() throws Exception {
        System.out.println("createARContentView");
        ARContentViewEntity aRContentView = new ARContentViewEntity(null, startDate, endDate, null);

        int sizeBefore = instance.retrieveAllARContentViews().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createARContentView(aRContentView);
        
        int sizeAfter = instance.retrieveAllARContentViews().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveARContentViewById method, of class ARContentViewSession.
     */
    @Test
    public void testBRetrieveARContentViewById() throws Exception {
        System.out.println("retrieveARContentViewById");
        ARContentViewEntity expResult = new ARContentViewEntity(null, startDate, endDate, null);
        
        ARContentViewEntity result = instance.retrieveARContentViewById(id);
      
        result.setViewId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveARContentViewByARContentViewAttributes method, of class ARContentViewSession.
     */
    @Test
    public void testCRetrieveARContentViewByARContentViewAttributes() throws Exception {
        System.out.println("retrieveARContentViewByARContentViewAttributes");

        ARContentViewEntity aRContentView = new ARContentViewEntity(null, startDate, endDate, null);
        aRContentView.setStartDate(startDate);
        
        Date expResult = startDate;
        Date result = instance.retrieveARContentViewByARContentViewAttributes(aRContentView).get(0).getStartDate();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateARContentView method, of class ARContentViewSession.
     */
    @Test
    public void testDUpdateARContentView() throws Exception {
        System.out.println("updateARContentView");
        ARContentViewEntity aRContentView = new ARContentViewEntity(null, startDate, endDate, null);
        aRContentView.setViewId(id);
        Date newDate = new Date();
        aRContentView.setEndDate(newDate);

        instance.updateARContentView(aRContentView);
        
        Date result = instance.retrieveARContentViewById(id).getEndDate();
        
        assertEquals(aRContentView.getEndDate(), result);
    }

    /**
     * Test of deleteARContentView method, of class ARContentViewSession.
     */
    @Test
    public void testEDeleteARContentView() throws Exception {
        System.out.println("deleteARContentView");
        int sizeBefore = instance.retrieveAllARContentViews().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteARContentView(id);
        
        int sizeAfter = instance.retrieveAllARContentViews().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
