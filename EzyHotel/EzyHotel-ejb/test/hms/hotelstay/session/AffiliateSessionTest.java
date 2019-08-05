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
import util.entity.AffiliateEntity;
import util.enumeration.AffiliateStatusEnum;
import util.enumeration.AffiliateTypeEnum;
import util.exception.AffiliateNotFoundException;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AffiliateSessionTest {
    
    static EJBContainer container;
    static AffiliateSessionLocal instance;
    static Long id = Long.valueOf("8");
    static String affiliateName = "Singapore Tourism Board";
    static String organizationEntityNumber = " A0182934";
    static String bizAddress = "Tourism Court 1 Orchard Spring Lane 247729";
    static String representativeName = "Mr. Tan Chin Kwok";
    static String email = "tanchinkwow@stb.gov.sg";
    static String contactNum = "64565789";
    
    
    public AffiliateSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (AffiliateSessionLocal) container.getContext().lookup("java:global/classes/AffiliateSession");        
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

    @Test
    public void testAARetrieveAllAffiliates() throws Exception {
        System.out.println("retrieveAllAffiliates");
       
        int result = instance.retrieveAllAffiliates().size();        
        assertTrue("number of affiliates is " + result, result > 0);
    } 

    @Test
    public void testABRetrieveAllAffiliates() throws Exception {
        System.out.println("retrieveAllAffiliates");
       
        int result = instance.retrieveAllAffiliates().size();        
        assertTrue("number of affiliates is " + result, result == 0);
    } 

    @Test(expected = AffiliateNotFoundException.class)
    public void testBRetrieveAffiliateById() throws Exception {
        System.out.println("retrieveAffiliateById");

        instance.retrieveAffiliateById(id);
    }
    
    @Test
    public void testCRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveDeviceByDeviceAttributes");
        AffiliateEntity affiliate = new AffiliateEntity(affiliateName, organizationEntityNumber, bizAddress, representativeName, email, contactNum, AffiliateTypeEnum.GOVERNMENT);
        int expResult = 0;
        int result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).size();
       
        assertEquals(expResult, result);
    }   
    
    @Test
    public void testDCreateAffiliate() throws Exception {
        System.out.println("createAffiliate");
        AffiliateEntity affiliate = new AffiliateEntity(affiliateName, organizationEntityNumber, bizAddress, representativeName, email, contactNum, AffiliateTypeEnum.GOVERNMENT);

        int sizeBefore = instance.retrieveAllAffiliates().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createAffiliate(affiliate);
        
        int sizeAfter = instance.retrieveAllAffiliates().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveAffiliateById method, of class AffiliateSession.
     */
    @Test
    public void testERetrieveAffiliateById() throws Exception {
        System.out.println("retrieveAffiliateById");
        
        AffiliateEntity expResult = new AffiliateEntity(affiliateName, organizationEntityNumber, bizAddress, representativeName, email, contactNum,  AffiliateTypeEnum.GOVERNMENT);
        
        AffiliateEntity result = instance.retrieveAffiliateById(id);
      
        result.setAffiliateId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: affiliateName
    @Test
    public void testFRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - affiliateName");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setAffiliateName(affiliateName);
        
        String expResult = affiliateName;
        String result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getAffiliateName();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: organizationEntityNumber
    @Test
    public void testGRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - organizationEntityNumber");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setOrganizationEntityNumber(organizationEntityNumber);
        
        String expResult = organizationEntityNumber;
        String result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getOrganizationEntityNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: businessAddress
    @Test
    public void testHRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - businessAddress");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setBusinessAddress(bizAddress);
        
        String expResult = bizAddress;
        String result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getBusinessAddress();
        assertEquals(expResult, result);
    } 

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: representative
    @Test
    public void testIRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - representative");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setRepresentativeName(representativeName);
        
        String expResult = representativeName;
        String result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getRepresentativeName();
        assertEquals(expResult, result);
    }     

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: email
    @Test
    public void testJRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - email");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setEmail(email);
        
        String expResult = email;
        String result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getEmail();
        assertEquals(expResult, result);
    }  

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: contactNumber
    @Test
    public void testKRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - contactNumber");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setContactNumber(contactNum);
        
        String expResult = contactNum;
        String result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getContactNumber();
        assertEquals(expResult, result);
    }     

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: premium customer
//    @Test
//    public void testLRetrieveAffiliateByAffiliateAttributes() throws Exception {
//        System.out.println("retrieveAffiliateByAffiliateAttributes - isPremium");
//        AffiliateEntity affiliate = new AffiliateEntity();
//        affiliate.setIsPremium(Boolean.TRUE);
//        
//        Boolean expResult = Boolean.TRUE;
//        Boolean result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getIsPremium();
//        assertEquals(expResult, result);
//    } 

    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: affiliateType
    @Test
    public void testMRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - affiliateType");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setAffiliateType(AffiliateTypeEnum.GOVERNMENT);
        
        AffiliateTypeEnum expResult = AffiliateTypeEnum.GOVERNMENT;
        AffiliateTypeEnum result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getAffiliateType();
        assertEquals(expResult, result);
    }     
 
    /**
     * Test of retrieveAffiliateByAffiliateAttributes method, of class AffiliateSession.
     */
    //test a particular attribute: affiliateStatus
    @Test
    public void testNRetrieveAffiliateByAffiliateAttributes() throws Exception {
        System.out.println("retrieveAffiliateByAffiliateAttributes - affiliateStatus");
        AffiliateEntity affiliate = new AffiliateEntity();
        affiliate.setAffiliateStatus(AffiliateStatusEnum.PENDING);
        
        AffiliateStatusEnum expResult = AffiliateStatusEnum.PENDING;
        AffiliateStatusEnum result = instance.retrieveAffiliateByAffiliateAttributes(affiliate).get(0).getAffiliateStatus();
        assertEquals(expResult, result);
    } 
    

    /**
     * Test of updateAffiliate method, of class AffiliateSession.
     */
    @Test
    public void testOUpdateAffiliate() throws Exception {
        System.out.println("updateAffiliate");
        AffiliateEntity affiliate = new AffiliateEntity(affiliateName, organizationEntityNumber, bizAddress, representativeName, email, contactNum, AffiliateTypeEnum.PRIVATE);
        
        affiliate.setAffiliateId(id);

        instance.updateAffiliate(affiliate);
        
        AffiliateTypeEnum result = instance.retrieveAffiliateById(id).getAffiliateType();
        
        assertEquals(affiliate.getAffiliateType(), result);
    }

    /**
     * Test of deleteAffiliate method, of class AffiliateSession.
     */
    @Test
    public void testPDeleteAffiliate() throws Exception {
        
        int sizeBefore = instance.retrieveAllAffiliates().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteAffiliate(id);
        
        int sizeAfter = instance.retrieveAllAffiliates().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
