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
import util.entity.AffiliateContentViewEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AffiliateContentViewSessionTest {
    static EJBContainer container;
    static AffiliateContentViewSessionLocal instance;
    static Long id = Long.valueOf("1");
    static Date startDate = new Date();
    static Date endDate = new Date();   
    
    public AffiliateContentViewSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (AffiliateContentViewSessionLocal)container.getContext().lookup("java:global/classes/AffiliateContentViewSession");
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
     * Test of retrieveAllAffiliateContentViews method, of class AffiliateContentViewSession.
     */
    @Test
    public void testAARetrieveAllAffiliateContentViews() throws Exception {
        System.out.println("retrieveAllAffiliateContentViews");
        int result = instance.retrieveAllAffiliateContentViews().size();        
        assertTrue("number of AffiliateContentViews is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllAffiliateContentViews() throws Exception {
        System.out.println("retrieveAllAffiliateContentViews");
        int result = instance.retrieveAllAffiliateContentViews().size();        
        assertTrue("number of AffiliateContentViews is " + result, result == 0);
    }    
    
    /**
     * Test of createAffiliateContentView method, of class AffiliateContentViewSession.
     */
    @Test
    public void testACreateAffiliateContentView() throws Exception {
        System.out.println("createAffiliateContentView");
        AffiliateContentViewEntity affiliateContentView = new AffiliateContentViewEntity();

        int sizeBefore = instance.retrieveAllAffiliateContentViews().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createAffiliateContentView(affiliateContentView);
        
        int sizeAfter = instance.retrieveAllAffiliateContentViews().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveAffiliateContentViewById method, of class AffiliateContentViewSession.
     */
    @Test
    public void testBRetrieveAffiliateContentViewById() throws Exception {
        System.out.println("retrieveAffiliateContentViewById");
        AffiliateContentViewEntity expResult = new AffiliateContentViewEntity(startDate, endDate, null);
        AffiliateContentViewEntity result = instance.retrieveAffiliateContentViewById(id);
      
        result.setAffiliateContent(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAffiliateContentViewByAffiliateContentViewAttributes method, of class AffiliateContentViewSession.
     */
    @Test
    public void testCRetrieveAffiliateContentViewByAffiliateContentViewAttributes() throws Exception {
        System.out.println("retrieveAffiliateContentViewByAffiliateContentViewAttributes");
        AffiliateContentViewEntity affiliateContentView = new AffiliateContentViewEntity();
        affiliateContentView.setStartDate(startDate);
        
        Date expResult = startDate;
        Date result = instance.retrieveAffiliateContentViewByAffiliateContentViewAttributes(affiliateContentView).get(0).getStartDate();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateAffiliateContentView method, of class AffiliateContentViewSession.
     */
    @Test
    public void testDUpdateAffiliateContentView() throws Exception {
        System.out.println("updateAffiliateContentView");
        AffiliateContentViewEntity affiliateContentView = new AffiliateContentViewEntity();
        Date newDate = new Date();
        affiliateContentView.setEndDate(newDate);
        affiliateContentView.setViewId(id);

        instance.updateAffiliateContentView(affiliateContentView);
        
        Date result = instance.retrieveAffiliateContentViewById(id).getEndDate();
        
        assertEquals(newDate, result);
    }

    /**
     * Test of deleteAffiliateContentView method, of class AffiliateContentViewSession.
     */
    @Test
    public void testEDeleteAffiliateContentView() throws Exception {
        System.out.println("deleteAffiliateContentView");
        int sizeBefore = instance.retrieveAllAffiliateContentViews().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteAffiliateContentView(id);
        
        int sizeAfter = instance.retrieveAllAffiliateContentViews().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
