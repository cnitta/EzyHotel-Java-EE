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
import util.entity.AugmentedRealityContentEntity;
import util.enumeration.ARTypeEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AugmentedRealityContentSessionTest {
    
    static EJBContainer container;
    static AugmentedRealityContentSessionLocal instance;
    static Long id = Long.valueOf("1");
    static String name = "AR Treasure";
    static String description = "";
    static ARTypeEnum aRTypeEnum = ARTypeEnum.MARKETING;
    static Date dateCreated = new Date();
    
    public AugmentedRealityContentSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (AugmentedRealityContentSessionLocal) container.getContext().lookup("java:global/classes/AugmentedRealityContentSession");
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
     * Test of retrieveAllAugmentedRealityContents method, of class AugmentedRealityContentSession.
     */
    @Test
    public void testAARetrieveAllAugmentedRealityContents() throws Exception {
        System.out.println("retrieveAllAugmentedRealityContents");
        int result = instance.retrieveAllAugmentedRealityContents().size();        
        assertTrue("number of AugmentedRealityContent is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllAugmentedRealityContents() throws Exception {
        System.out.println("retrieveAllAugmentedRealityContents");
        int result = instance.retrieveAllAugmentedRealityContents().size();        
        assertTrue("number of AugmentedRealityContent is " + result, result == 0);
    }    
    
    /**
     * Test of createAugmentedRealityContent method, of class AugmentedRealityContentSession.
     */
    @Test
    public void testACreateAugmentedRealityContent() throws Exception {
        System.out.println("createAugmentedRealityContent");
        AugmentedRealityContentEntity augmentedRealityContent = new AugmentedRealityContentEntity(name, description, aRTypeEnum, dateCreated);

        int sizeBefore = instance.retrieveAllAugmentedRealityContents().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createAugmentedRealityContent(augmentedRealityContent);
        
        int sizeAfter = instance.retrieveAllAugmentedRealityContents().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveAugmentedRealityContentById method, of class AugmentedRealityContentSession.
     */
    @Test
    public void testRetrieveAugmentedRealityContentById() throws Exception {
        System.out.println("retrieveAugmentedRealityContentById");
        AugmentedRealityContentEntity expResult = new AugmentedRealityContentEntity(name, description, aRTypeEnum, dateCreated);
        
        AugmentedRealityContentEntity result = instance.retrieveAugmentedRealityContentById(id);
      
        result.setAugmentedRealityContentId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAugmentedRealityContentByAugmentedRealityContentAttributes method, of class AugmentedRealityContentSession.
     */
    @Test
    public void testRetrieveAugmentedRealityContentByAugmentedRealityContentAttributes() throws Exception {
        System.out.println("retrieveAugmentedRealityContentByAugmentedRealityContentAttributes");
        AugmentedRealityContentEntity augmentedRealityContent = new AugmentedRealityContentEntity();
        augmentedRealityContent.setName(name);
        
        String expResult = name;
        String result = instance.retrieveAugmentedRealityContentByAugmentedRealityContentAttributes(augmentedRealityContent).get(0).getName();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateAugmentedRealityContent method, of class AugmentedRealityContentSession.
     */
    @Test
    public void testUpdateAugmentedRealityContent() throws Exception {
        System.out.println("updateAugmentedRealityContent");
        AugmentedRealityContentEntity augmentedRealityContent = new AugmentedRealityContentEntity(name, description, aRTypeEnum, dateCreated);
        augmentedRealityContent.setName("New Awesome AR Treasure");
        augmentedRealityContent.setAugmentedRealityContentId(id);

        instance.updateAugmentedRealityContent(augmentedRealityContent);
        
        String result = instance.retrieveAugmentedRealityContentById(id).getName();
        
        assertEquals(augmentedRealityContent.getName(), result);
    }

    /**
     * Test of deleteAugmentedRealityContent method, of class AugmentedRealityContentSession.
     */
    @Test
    public void testDeleteAugmentedRealityContent() throws Exception {
        System.out.println("deleteAugmentedRealityContent");
        int sizeBefore = instance.retrieveAllAugmentedRealityContents().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteAugmentedRealityContent(id);
        
        int sizeAfter = instance.retrieveAllAugmentedRealityContents().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
