/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.entity.ConfirmationEmailEntity;

/**
 *
 * @author Wai Kit
 */
public class ConfirmationEmailSessionTest {

    static EJBContainer container;
    static ConfirmationEmailSessionLocal instance;

    public ConfirmationEmailSessionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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

    @Test
    public void testCreateEntity() throws Exception {
        System.out.println("createEntity");
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ConfirmationEmailSessionLocal) container.getContext().lookup("java:global/classes/ConfirmationEmailSession");

        int sizeBefore = instance.retrieveAllEntities().size();
        //CallReportEntity cp = new CallReportEntity("Avensys Consulting Pte Ltd", "Singapore", LocalDate.now(), "64539075", "Convention Booking", "Anmol Singh", "Group and Banquet", "Banquet size 200", "Banquet leads");
        ConfirmationEmailEntity ce = new ConfirmationEmailEntity();
        ce.setAssociateEecutiveEmailAddress("singh@avensys.com.sg");
        ce.setDateInQuestion("20/05/2019");
//        ce.setAlternativeDate(LocalDate.parse("2019-05-25"));
        ce.setDaysOfTheWeek("Saturday");
        ce.setRoomsReserved(3);
        ce.setSuitesReserved(0);
        ce.setmRoomsReserved(2);
        ce.setArrPatterns("Arriving at around 5pm");
        ce.setReviewProgram("6pm - Start conference meeting");
        instance.createEntity(ce);
        int sizeAfter = instance.retrieveAllEntities().size();

        int exp = sizeBefore + 1;
        System.out.println(exp);
        System.out.println(sizeAfter);
        assertEquals("Confirmation Email Entity Case", exp, sizeAfter);
        container.close();
    }

    @Test
    public void testDeleteEntity() throws Exception {
        System.out.println("deleteEntity");
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ConfirmationEmailSessionLocal) container.getContext().lookup("java:global/classes/ConfirmationEmailSession");

        int sizeBefore = instance.retrieveAllEntities().size();
        int reportId = sizeBefore + 1;
        //CallReportEntity cp = new CallReportEntity("Avensys Consulting Pte Ltd", "Singapore", LocalDate.now(), "64539075", "Convention Booking", "Anmol Singh", "Group and Banquet", "Banquet size 200", "Banquet leads");
        ConfirmationEmailEntity ce = new ConfirmationEmailEntity();
        ce.setAssociateEecutiveEmailAddress("singh@avensys.com.sg");
        ce.setDateInQuestion("20/05/2019");
//        ce.setAlternativeDate(LocalDate.parse("2019-05-25"));
        ce.setDaysOfTheWeek("Saturday");
        ce.setRoomsReserved(3);
        ce.setSuitesReserved(0);
        ce.setmRoomsReserved(2);
        ce.setArrPatterns("Arriving at around 5pm");
        ce.setReviewProgram("6pm - Start conference meeting");
        instance.createEntity(ce);

        instance.deleteEntity(new Long(reportId));

        int sizeAfterDelete = instance.retrieveAllEntities().size();

        assertEquals("Confirmation Email Entity Case", sizeBefore, sizeAfterDelete);

        container.close();
    }
}
