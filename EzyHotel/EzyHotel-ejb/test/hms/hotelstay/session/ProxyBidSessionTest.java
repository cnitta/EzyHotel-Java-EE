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
import util.entity.ProxyBidEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProxyBidSessionTest {
    static EJBContainer container;
    static ProxyBidSessionLocal instance;
    static Long id = Long.valueOf("13");
    static double bidAmount = 1.00;
    static Date bidDateTime = new Date();
    static Date startDateTime = new Date();
    static Date endDateTime = new Date();
    static Double maxBidAmount = 2.00;
    static Double increment = 0.5;
     
    public ProxyBidSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ProxyBidSessionLocal)container.getContext().lookup("java:global/classes/ProxyBidSession");        
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
     * Test of retrieveAllProxyBids method, of class ProxyBidSession.
     */
    @Test
    public void testAARetrieveAllProxyBids() throws Exception {
        System.out.println("retrieveAllProxyBids");
        
        int result = instance.retrieveAllProxyBids().size();
        assertTrue("number of devices is " + result, result > 0);
    } 
    
    /**
     * Test of retrieveAllProxyBids method, of class ProxyBidSession.
     */
    @Test
    public void testABRetrieveAllProxyBids() throws Exception {
        System.out.println("retrieveAllProxyBids");
        
        int result = instance.retrieveAllProxyBids().size();
        assertTrue("number of devices is " + result, result == 0);
    }     
    /**
     * Test of createProxyBid method, of class ProxyBidSession.
     */
    @Test
    public void testACreateProxyBid() throws Exception {
        System.out.println("createProxyBid");
        ProxyBidEntity proxyBid = new ProxyBidEntity(maxBidAmount, increment, bidAmount, bidDateTime, startDateTime, endDateTime, null);
        
        int sizeBefore = instance.retrieveAllProxyBids().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createProxyBid(proxyBid);
        
        int sizeAfter = instance.retrieveAllProxyBids().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveProxyBidById method, of class ProxyBidSession.
     */
    @Test
    public void testBRetrieveProxyBidById() throws Exception {
        System.out.println("retrieveProxyBidById");
        ProxyBidEntity expResult = new ProxyBidEntity(maxBidAmount, increment, bidAmount, bidDateTime, startDateTime, endDateTime, null);
        
        ProxyBidEntity result = (ProxyBidEntity) instance.retrieveProxyBidById(id);
      
        result.setBidId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveProxyBidByProxyBidAttributes method, of class ProxyBidSession.
     */
    @Test
    public void testCRetrieveProxyBidByProxyBidAttributes() throws Exception {
        System.out.println("retrieveProxyBidByProxyBidAttributes");
        System.out.println("retrieveBidByBidAttributes");
        ProxyBidEntity bid = new ProxyBidEntity(maxBidAmount, increment, bidAmount, bidDateTime, startDateTime, endDateTime, null);
        double expResult = bidAmount;
        double result = instance.retrieveProxyBidByProxyBidAttributes(bid).get(0).getBidAmount();
       
        assertTrue(expResult == result);
    }


    /**
     * Test of updateProxyBid method, of class ProxyBidSession.
     */
    @Test
    public void testDUpdateProxyBid() throws Exception {
        System.out.println("updateProxyBid");
        ProxyBidEntity bid = new ProxyBidEntity(maxBidAmount, increment, bidAmount, bidDateTime, startDateTime, endDateTime, null);
        
        bid.setBidId(id);

        instance.updateProxyBid(bid);
        
        double result = instance.retrieveProxyBidById(id).getBidAmount();
        
        assertTrue(bid.getBidAmount() == result);
    }

    /**
     * Test of deleteProxyBid method, of class ProxyBidSession.
     */
    @Test
    public void testEDeleteProxyBid() throws Exception {
        System.out.println("deleteProxyBid");
        int sizeBefore = instance.retrieveAllProxyBids().size();
        
        int expResult = sizeBefore - 1;      
        instance.deleteProxyBid(id);
        
        int sizeAfter = instance.retrieveAllProxyBids().size();
        
        assertEquals(expResult, sizeAfter); 
    }
    
}
