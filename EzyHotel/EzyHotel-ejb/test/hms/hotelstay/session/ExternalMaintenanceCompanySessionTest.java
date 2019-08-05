/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

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
import util.entity.ExternalMaintenanceCompanyEntity;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExternalMaintenanceCompanySessionTest {
    static EJBContainer container;
    static ExternalMaintenanceCompanySessionLocal instance;
    static Long id = Long.valueOf("1");
    static String companyName = "Good Maintain Company Pte. Ltd.";
    static String address;
    static String pocName;
    static String contactNum;
    static String email;
    static String businessEntityNumber;
    
    public ExternalMaintenanceCompanySessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ExternalMaintenanceCompanySessionLocal)container.getContext().lookup("java:global/classes/ExternalMaintenanceCompanySession");        
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
     * Test of retrieveAllExternalMaintenanceCompanys method, of class ExternalMaintenanceCompanySession.
     */
    @Test
    public void testAARetrieveAllExternalMaintenanceCompanys() throws Exception {
        System.out.println("retrieveAllExternalMaintenanceCompanys");
        int result = instance.retrieveAllExternalMaintenanceCompanys().size();        
        assertTrue("number of ExternalMaintenanceCompanys is " + result, result > 0);
    }
    
    @Test
    public void testABRetrieveAllExternalMaintenanceCompanys() throws Exception {
        System.out.println("retrieveAllExternalMaintenanceCompanys");
        int result = instance.retrieveAllExternalMaintenanceCompanys().size();        
        assertTrue("number of ExternalMaintenanceCompanys is " + result, result == 0);
    }    
    
    /**
     * Test of createExternalMaintenanceCompany method, of class ExternalMaintenanceCompanySession.
     */
    @Test
    public void testACreateExternalMaintenanceCompany() throws Exception {
        System.out.println("createExternalMaintenanceCompany");
        ExternalMaintenanceCompanyEntity externalMaintenanceCompany = new ExternalMaintenanceCompanyEntity();
        externalMaintenanceCompany.setCompanyName(companyName);
        
        int sizeBefore = instance.retrieveAllExternalMaintenanceCompanys().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createExternalMaintenanceCompany(externalMaintenanceCompany);
        
        int sizeAfter = instance.retrieveAllExternalMaintenanceCompanys().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveExternalMaintenanceCompanyById method, of class ExternalMaintenanceCompanySession.
     */
    @Test
    public void testBRetrieveExternalMaintenanceCompanyById() throws Exception {
        System.out.println("retrieveExternalMaintenanceCompanyById");
        ExternalMaintenanceCompanyEntity expResult = new ExternalMaintenanceCompanyEntity();
        
        ExternalMaintenanceCompanyEntity result = instance.retrieveExternalMaintenanceCompanyById(id);
      
        result.setExternalMaintenanceCompanyId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes method, of class ExternalMaintenanceCompanySession.
     */
    @Test
    public void testCRetrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes() throws Exception {
        System.out.println("retrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes");
        ExternalMaintenanceCompanyEntity externalMaintenanceCompany = new ExternalMaintenanceCompanyEntity();
        externalMaintenanceCompany.setCompanyName(companyName);
        
        String expResult = companyName;
        String result = instance.retrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes(externalMaintenanceCompany).get(0).getCompanyName();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateExternalMaintenanceCompany method, of class ExternalMaintenanceCompanySession.
     */
    @Test
    public void testDUpdateExternalMaintenanceCompany() throws Exception {
        System.out.println("updateExternalMaintenanceCompany");
        ExternalMaintenanceCompanyEntity externalMaintenanceCompany = new ExternalMaintenanceCompanyEntity();
        externalMaintenanceCompany.setCompanyName("Better Maintain Company Pte Ltd.");
        externalMaintenanceCompany.setExternalMaintenanceCompanyId(id);

        instance.updateExternalMaintenanceCompany(externalMaintenanceCompany);
        
        String result = instance.retrieveExternalMaintenanceCompanyById(id).getCompanyName();
        
        assertEquals("Better Maintain Company Pte Ltd.", result);
    }

    /**
     * Test of deleteExternalMaintenanceCompany method, of class ExternalMaintenanceCompanySession.
     */
    @Test
    public void testEDeleteExternalMaintenanceCompany() throws Exception {
        System.out.println("deleteExternalMaintenanceCompany");
        int sizeBefore = instance.retrieveAllExternalMaintenanceCompanys().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteExternalMaintenanceCompany(id);
        
        int sizeAfter = instance.retrieveAllExternalMaintenanceCompanys().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
