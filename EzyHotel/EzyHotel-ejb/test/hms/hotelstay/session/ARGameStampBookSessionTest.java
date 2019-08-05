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
import util.entity.ARGameStampBookEntity;
import util.entity.CustomerEntity;
import util.enumeration.ARGameStampBookStatusEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ARGameStampBookSessionTest {
    
    static EJBContainer container;
    static ARGameStampBookSessionLocal instance;
    static Long id = Long.valueOf("1");
    static Date startDate = new Date();
    static Date endDate = new Date();
    static Integer rewardPoints = 100;
    static ARGameStampBookStatusEnum aRGameStampBookStatus = ARGameStampBookStatusEnum.DRAFT;    
    
    public ARGameStampBookSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ARGameStampBookSessionLocal)container.getContext().lookup("java:global/classes/ARGameStampBookSession");        
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
     * Test of retrieveAllARGameStampBookSessions method, of class ARGameStampBookSession.
     */
    @Test
    public void testAARetrieveAllARGameStampBookSessions() throws Exception {
        System.out.println("retrieveAllARGameStampBookSessions");
        int result = instance.retrieveAllARGameStampBooks().size();        
        assertTrue("number of Merchandise is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllARGameStampBookSessions() throws Exception {
        System.out.println("retrieveAllARGameStampBookSessions");
        int result = instance.retrieveAllARGameStampBooks().size();        
        assertTrue("number of Merchandise is " + result, result == 0);
    }    
    
    /**
     * Test of createARGameStampBookSession method, of class ARGameStampBookSession.
     */
    @Test
    public void testACreateARGameStampBookSession() throws Exception {
        System.out.println("createARGameStampBookSession");
        ARGameStampBookEntity aRGameStampBook = new ARGameStampBookEntity();

        int sizeBefore = instance.retrieveAllARGameStampBooks().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createARGameStampBook(aRGameStampBook);
        
        int sizeAfter = instance.retrieveAllARGameStampBooks().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveARGameStampBookSessionById method, of class ARGameStampBookSession.
     */
    @Test
    public void testBRetrieveARGameStampBookSessionById() throws Exception {
        System.out.println("retrieveARGameStampBookSessionById");
        ARGameStampBookEntity expResult = new ARGameStampBookEntity(startDate, endDate, rewardPoints, aRGameStampBookStatus);
        
        ARGameStampBookEntity result = instance.retrieveARGameStampBookById(id);
      
        result.setArGameStampBookId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveARGameStampBookSessionByARGameStampBookSessionAttributes method, of class ARGameStampBookSession.
     */
    @Test
    public void testCRetrieveARGameStampBookSessionByARGameStampBookSessionAttributes() throws Exception {
        System.out.println("retrieveARGameStampBookSessionByARGameStampBookSessionAttributes");
        ARGameStampBookEntity aRGameStampBook = new ARGameStampBookEntity();
        aRGameStampBook.setStartDate(startDate);
        
        Date expResult = startDate;
        Date result = instance.retrieveARGameStampBookByARGameStampBookAttributes(aRGameStampBook).get(0).getStartDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveARGameStampBookSessionByCustomerAttributes method, of class ARGameStampBookSession.
     */
//    @Test
//    public void testDRetrieveARGameStampBookSessionByCustomerAttributes() throws Exception {
//        System.out.println("retrieveARGameStampBookSessionByCustomerAttributes");
//        CustomerEntity customer = new CustomerEntity();
//        customer.setCustIdentity("");
//        
//        expResult = aRGameStampBookStatus;
//        ARGameStampBookStatusEnum result = instance.retrieveARGameStampBookByARGameStampBookAttributes(aRGameStampBook).get(0).getaRGameStampBookStatus();
//        assertEquals(expResult, result);
//    }



    /**
     * Test of updateARGameStampBookSession method, of class ARGameStampBookSession.
     */
    @Test
    public void testEUpdateARGameStampBookSession() throws Exception {
        System.out.println("updateARGameStampBookSession");
        ARGameStampBookEntity aRGameStampBook = new ARGameStampBookEntity();
        aRGameStampBook.setArGameStampBookId(id);
        aRGameStampBook.setaRGameStampBookStatus(ARGameStampBookStatusEnum.LIVE);
        instance.updateARGameStampBook(aRGameStampBook);
        
        
        ARGameStampBookStatusEnum result = instance.retrieveARGameStampBookById(id).getaRGameStampBookStatus();
        
        assertEquals(aRGameStampBook.getaRGameStampBookStatus(), result);
    }

    /**
     * Test of deleteARGameStampBookSession method, of class ARGameStampBookSession.
     */
    @Test
    public void testFDeleteARGameStampBookSession() throws Exception {
        System.out.println("deleteARGameStampBookSession");
        int sizeBefore = instance.retrieveAllARGameStampBooks().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteARGameStampBook(id);
        
        int sizeAfter = instance.retrieveAllARGameStampBooks().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
