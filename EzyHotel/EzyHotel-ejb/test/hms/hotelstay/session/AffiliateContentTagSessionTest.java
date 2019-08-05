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
import util.entity.AffiliateContentTagEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AffiliateContentTagSessionTest {
    static EJBContainer container;
    static AffiliateContentTagSessionLocal instance;
    static Long id = Long.valueOf("1");
    static String tagKeyword = "holiday";
    static Date dateCreated = new Date();
    
    public AffiliateContentTagSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (AffiliateContentTagSessionLocal)container.getContext().lookup("java:global/classes/AffiliateContentTagSession");
        
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
     * Test of retrieveAllAffiliateContentTags method, of class AffiliateContentTagSession.
     */
    @Test
    public void testAARetrieveAllAffiliateContentTags() throws Exception {
        System.out.println("retrieveAllAffiliateContentTags");
        int result = instance.retrieveAllAffiliateContentTags().size();        
        assertTrue("number of AffiliateContentTags is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllAffiliateContentTags() throws Exception {
        System.out.println("retrieveAllAffiliateContentTags");
        int result = instance.retrieveAllAffiliateContentTags().size();        
        assertTrue("number of AffiliateContentTags is " + result, result == 0);
    }    
    
    /**
     * Test of createAffiliateContentTag method, of class AffiliateContentTagSession.
     */
    @Test
    public void testACreateAffiliateContentTag() throws Exception {
        System.out.println("createAffiliateContentTag");
        AffiliateContentTagEntity affiliateContentTag = new AffiliateContentTagEntity(tagKeyword, dateCreated, null);
        int sizeBefore = instance.retrieveAllAffiliateContentTags().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createAffiliateContentTag(affiliateContentTag);
        
        int sizeAfter = instance.retrieveAllAffiliateContentTags().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveAffiliateContentTagById method, of class AffiliateContentTagSession.
     */
    @Test
    public void testBRetrieveAffiliateContentTagById() throws Exception {
        System.out.println("retrieveAffiliateContentTagById");
        AffiliateContentTagEntity expResult = new AffiliateContentTagEntity(tagKeyword, dateCreated, null);
        
        AffiliateContentTagEntity result = instance.retrieveAffiliateContentTagById(id);
      
        result.setTagId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAffiliateContentTagByAffiliateContentTagAttributes method, of class AffiliateContentTagSession.
     */
    @Test
    public void testCRetrieveAffiliateContentTagByAffiliateContentTagAttributes() throws Exception {
        System.out.println("retrieveAffiliateContentTagByAffiliateContentTagAttributes");
        AffiliateContentTagEntity affiliateContentTag = new AffiliateContentTagEntity();
        affiliateContentTag.setTagKeyword(tagKeyword);
        
        String expResult = tagKeyword;
        String result = instance.retrieveAffiliateContentTagByAffiliateContentTagAttributes(affiliateContentTag).get(0).getTagKeyword();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateAffiliateContentTag method, of class AffiliateContentTagSession.
     */
    @Test
    public void testDUpdateAffiliateContentTag() throws Exception {
        System.out.println("updateAffiliateContentTag");
        AffiliateContentTagEntity affiliateContentTag = new AffiliateContentTagEntity();
        affiliateContentTag.setTagKeyword("dating");
        affiliateContentTag.setTagId(id);

        instance.updateAffiliateContentTag(affiliateContentTag);
        
        String result = instance.retrieveAffiliateContentTagById(id).getTagKeyword();
        
        assertEquals("dating", result);
    }

    /**
     * Test of deleteAffiliateContentTag method, of class AffiliateContentTagSession.
     */
    @Test
    public void testEDeleteAffiliateContentTag() throws Exception {
        System.out.println("deleteAffiliateContentTag");
        int sizeBefore = instance.retrieveAllAffiliateContentTags().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteAffiliateContentTag(id);
        
        int sizeAfter = instance.retrieveAllAffiliateContentTags().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
