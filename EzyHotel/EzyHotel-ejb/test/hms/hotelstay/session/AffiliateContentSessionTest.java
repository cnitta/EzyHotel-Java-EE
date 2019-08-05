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
import util.entity.AffiliateContentEntity;
import util.enumeration.AffiliateContentCategoryEnum;
import util.enumeration.AffiliateContentStateEnum;
import util.enumeration.AffiliateContentStatusEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AffiliateContentSessionTest {
    static EJBContainer container;
    static AffiliateContentSessionLocal instance;
    static Long id = Long.valueOf("1");
    static String title = "Get 50% off for Singapore Tourist Pass Now";
    static AffiliateContentCategoryEnum category =  AffiliateContentCategoryEnum.TOURIST_SITE;
    static String promoDescription = "We are offering 50% off for your first purchase of tourist passes now. The tourist entitles you to visit Singapore’s top attraction with great savings.";
//    static Integer currentRanking;
    static String promoCode = "KRHGSTB362"; 
    static Date promotionStartDate = new Date("01/01/2019"); 
    static Date promotionEndDate = new Date("30/06/2019");    
    static AffiliateContentStatusEnum affiliateContentStatus = AffiliateContentStatusEnum.APPROVED;    
    static AffiliateContentStateEnum affiliateContentState = AffiliateContentStateEnum.VISIBLE;    
    
    public AffiliateContentSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (AffiliateContentSessionLocal)container.getContext().lookup("java:global/classes/AffiliateContentSession");        
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
     * Test of retrieveAllAffiliateContents method, of class AffiliateContentSession.
     */
    @Test
    public void testAARetrieveAllAffiliateContents() throws Exception {
        System.out.println("retrieveAllAffiliateContents");
        int result = instance.retrieveAllAffiliateContents().size();        
        assertTrue("number of AffiliateContents is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllAffiliateContents() throws Exception {
        System.out.println("retrieveAllAffiliateContents");
        int result = instance.retrieveAllAffiliateContents().size();        
        assertTrue("number of AffiliateContents is " + result, result == 0);
    }    
    
    /**
     * Test of createAffiliateContent method, of class AffiliateContentSession.
     */
    @Test
    public void testACreateAffiliateContent() throws Exception {
        System.out.println("createAffiliateContent");
        AffiliateContentEntity affiliateContent = new AffiliateContentEntity(title, category, promoDescription, promoCode, promotionStartDate, promotionEndDate, affiliateContentStatus, affiliateContentState, null);
        
        int sizeBefore = instance.retrieveAllAffiliateContents().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createAffiliateContent(affiliateContent);
        
        int sizeAfter = instance.retrieveAllAffiliateContents().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveAffiliateContentById method, of class AffiliateContentSession.
     */
    @Test
    public void testBRetrieveAffiliateContentById() throws Exception {
        System.out.println("retrieveAffiliateContentById");
        AffiliateContentEntity expResult = new AffiliateContentEntity(title, category, promoDescription, promoCode, promotionStartDate, promotionEndDate, affiliateContentStatus, affiliateContentState, null);
           
//        AffiliateContentEntity expResult = new AffiliateContentEntity(title, category, promoDescription, currentRanking, promoCode, promotionStartDate, promotionEndDate, affiliateContentStatus, affiliateContentState, null);
        
        AffiliateContentEntity result = instance.retrieveAffiliateContentById(id);
      
        result.setAffiliateContentId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAffiliateContentByAffiliateContentAttributes method, of class AffiliateContentSession.
     */
    @Test
    public void testCRetrieveAffiliateContentByAffiliateContentAttributes() throws Exception {
        System.out.println("retrieveAffiliateContentByAffiliateContentAttributes");
        AffiliateContentEntity affiliateContent = new AffiliateContentEntity();
        affiliateContent.setTitle(title);
        
        String expResult = title;
        String result = instance.retrieveAffiliateContentByAffiliateContentAttributes(affiliateContent).get(0).getTitle();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateAffiliateContent method, of class AffiliateContentSession.
     */
    @Test
    public void testDUpdateAffiliateContent() throws Exception {
        System.out.println("updateAffiliateContent");
        AffiliateContentEntity affiliateContent = new AffiliateContentEntity();
        affiliateContent.setTitle("New Title");
        affiliateContent.setAffiliateContentId(id);        

        instance.updateAffiliateContent(affiliateContent);
        
        String result = instance.retrieveAffiliateContentById(id).getTitle();
        
        assertEquals("New Title", result);
    }

    /**
     * Test of deleteAffiliateContent method, of class AffiliateContentSession.
     */
    @Test
    public void testEDeleteAffiliateContent() throws Exception {
        System.out.println("deleteAffiliateContent");
        int sizeBefore = instance.retrieveAllAffiliateContents().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteAffiliateContent(id);
        
        int sizeAfter = instance.retrieveAllAffiliateContents().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
