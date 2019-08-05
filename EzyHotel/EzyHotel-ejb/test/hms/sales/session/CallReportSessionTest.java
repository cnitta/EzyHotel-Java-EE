/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.Date;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.entity.CallReportEntity;

/**
 *
 * @author Wai Kit
 */
public class CallReportSessionTest {
    static EJBContainer container;
    static CallReportSessionLocal instance;
    
    public CallReportSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createEntity method, of class CallReportSession.
     */
    @Test
    public void testCreateEntity() throws Exception {
        System.out.println("createEntity");
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (CallReportSessionLocal) container.getContext().lookup("java:global/classes/CallReportSession");

        int sizeBefore = instance.retrieveAllEntities().size();
        //CallReportEntity cp = new CallReportEntity("Avensys Consulting Pte Ltd", "Singapore", LocalDate.now(), "64539075", "Convention Booking", "Anmol Singh", "Group and Banquet", "Banquet size 200", "Banquet leads");
        CallReportEntity cp = new CallReportEntity();
        cp.setFrom("Avensys Consulting Pte Ltd");
        cp.setCity("Singapore");
        cp.setCallDate(new Date());
        cp.setTelephoneNum("64539075");
        cp.setRegarding("Convention Booking");
        cp.setPersonContacted("Anmol Singh");
        cp.setTitle("Group and Banquet");
        cp.setRemarks("Banquet size 200");
        cp.setFollowup("Banquet leads");
        instance.createEntity(cp);
        int sizeAfter = instance.retrieveAllEntities().size();

        int exp = sizeBefore + 1;
        System.out.println(exp);
        System.out.println(sizeAfter);
        assertEquals("Create Call Report Entity Case", exp, sizeAfter);
        container.close();
    }
    
    @Test
    public void testDeleteEntity() throws Exception {
            System.out.println("deleteEntity");
            container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
            instance = (CallReportSessionLocal)container.getContext().lookup("java:global/classes/CallReportSession");

            int sizeBefore = instance.retrieveAllEntities().size();
            int reportId = sizeBefore + 1;
            //CallReportEntity cp = new CallReportEntity("Avensys Consulting Pte Ltd", "Singapore", LocalDate.now(), "64539075", "Convention Booking", "Anmol Singh", "Group and Banquet", "Banquet size 200", "Banquet leads");
            CallReportEntity cp = new CallReportEntity();
            cp.setCallReportId(new Long(reportId));
            cp.setFrom("Avensys Consulting Pte Ltd");
            cp.setCity("Singapore");
            cp.setCallDate(new Date());
            cp.setTelephoneNum("64539075");
            cp.setRegarding("Convention Booking");
            cp.setPersonContacted("Anmol Singh");
            cp.setTitle("Group and Banquet");
            cp.setRemarks("Banquet size 200");
            cp.setFollowup("Banquet leads");
            instance.createEntity(cp);
           
            instance.deleteEntity(new Long(reportId));
            
            int sizeAfterDelete = instance.retrieveAllEntities().size();
            
            assertEquals("Delete Call Report Entity Case", sizeBefore, sizeAfterDelete);
            
            container.close();
    }
}
