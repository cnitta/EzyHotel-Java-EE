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
import util.entity.BidEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BidSessionTest {
    static EJBContainer container;
    static BidSessionLocal instance;
    static Long id = Long.valueOf("5");
    static double bidAmount = 1.00;
    static Date bidDateTime = new Date();
    static Date startDateTime = new Date();
    static Date endDateTime = new Date();
    
    public BidSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
       container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
       instance = (BidSessionLocal)container.getContext().lookup("java:global/classes/BidSession");
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
     * Test of retrieveAllBids method, of class BidSession.
     */
    @Test
    public void testAARetrieveAllBids() {
        System.out.println("retrieveAllBids");
        int result = instance.retrieveAllBids().size();        
        assertTrue("number of devices is " + result, result > 0);
    }
    
    /**
     * Test of retrieveAllBids method, of class BidSession.
     */
    @Test
    public void testABRetrieveAllBids()  {
        System.out.println("retrieveAllBids");
        int result = instance.retrieveAllBids().size();        
        assertTrue("number of devices is " + result, result == 0);
    }    
    
    /**
     * Test of createBid method, of class BidSession.
     */
    @Test
    public void testACreateBid() throws Exception {
        System.out.println("createBid");
        
        BidEntity bid = new BidEntity(bidAmount, bidDateTime, startDateTime, endDateTime, null);
        
        int sizeBefore = instance.retrieveAllBids().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createBid(bid);
        
        int sizeAfter = instance.retrieveAllBids().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveBidById method, of class BidSession.
     */
    @Test
    public void testBRetrieveBidById() throws Exception {
        System.out.println("retrieveBidById");
        
        BidEntity expResult = new BidEntity(bidAmount, bidDateTime, startDateTime, endDateTime, null);
        
        BidEntity result = instance.retrieveBidById(id);
      
        result.setBidId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveBidByBidAttributes method, of class BidSession.
     */
    //for attributes: bidAmount
    @Test
    public void testCRetrieveBidByBidAttributes() throws Exception {
        System.out.println("retrieveBidByBidAttributes");
        BidEntity bid = new BidEntity(bidAmount, bidDateTime, startDateTime, endDateTime, null);
        double expResult = bidAmount;
        double result = instance.retrieveBidByBidAttributes(bid).get(0).getBidAmount();
       
        assertTrue(expResult == result);
    }

    /**
     * Test of updateBid method, of class BidSession.
     */
    @Test
    public void testDUpdateBid() throws Exception {
        System.out.println("updateBid");
        BidEntity bid = new BidEntity(9.00, bidDateTime, startDateTime, endDateTime, null);
        
        bid.setBidId(id);

        instance.updateBid(bid);
        
        double result = instance.retrieveBidById(id).getBidAmount();
        
        assertTrue(bid.getBidAmount() == result);
    }

    /**
     * Test of deleteBid method, of class BidSession.
     */
    @Test
    public void testEDeleteBid() throws Exception {
        System.out.println("deleteBid");
        int sizeBefore = instance.retrieveAllBids().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteBid(id);
        
        int sizeAfter = instance.retrieveAllBids().size();
        
        assertEquals(expResult, sizeAfter);    
    }
    
}
