/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common.session;

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
import util.entity.PictureEntity;
import util.enumeration.PictureStatusEnum;
import util.exception.PictureNotFoundException;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PictureSessionTest {
    static EJBContainer container;
    static PictureSessionLocal instance;
    static Long id = Long.valueOf("6");
    static String picName = "bracelet";
    static String pictureFilePath = "/hpm/";
    static String picDescription = "floral braclet";    
    static Date dateTaken = new Date();
    static String fileName = picName + dateTaken.toString().trim();
    static PictureStatusEnum picStatus = PictureStatusEnum.VISIBLE;
    
    
    public PictureSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (PictureSessionLocal)container.getContext().lookup("java:global/classes/PictureSession");
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
    public void testAARetrieveAllPictures() throws Exception {
        System.out.println("retrieveAllPictures");
       
        int result = instance.retrieveAllPictures().size();        
        assertTrue("number of pictures is " + result, result > 0);
    } 

    @Test
    public void testABRetrieveAllPictures() throws Exception {
        System.out.println("retrieveAllPictures");
       
        int result = instance.retrieveAllPictures().size();        
        assertTrue("number of pictures is " + result, result == 0);
    } 
    
    /**
     * Test of retrievePictureById method, of class PictureSession.
     */
    @Test(expected = PictureNotFoundException.class)
    public void testBRetrievePictureById() throws Exception {
        System.out.println("retrievePictureById");
        
        instance.retrievePictureById(id);
    }  

    /**
     * Test of retrievePicturesByPictureAttributes method, of class PictureSession.
     */
    @Test
    public void testCRetrievePicturesByPictureAttributes() throws Exception {
        System.out.println("retrievePicturesByPictureAttributes");
//        PictureEntity picture = new PictureEntity(picName, pictureFilePath, picDescription, fileName, dateTaken);
        PictureEntity picture = new PictureEntity(pictureFilePath, fileName, picStatus);
        
        int expResult = 0;
        int result = instance.retrievePicturesByPictureAttributes(picture).size();
       
        assertEquals(expResult, result);
    }    
    

    /**
     * Test of createPicture method, of class PictureSession.
     */
    @Test
    public void testDCreatePicture() throws Exception {
        System.out.println("createPicture");
//        PictureEntity picture = new PictureEntity(picName, pictureFilePath, picDescription, fileName, dateTaken);
        
        PictureEntity picture = new PictureEntity(pictureFilePath, fileName, picStatus);
        
        int sizeBefore = instance.retrieveAllPictures().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createPicture(picture);
        
        int sizeAfter = instance.retrieveAllPictures().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrievePictureById method, of class PictureSession.
     */
    @Test
    public void testERetrievePictureById() throws Exception {
        System.out.println("retrievePictureById");
//        PictureEntity expResult = new PictureEntity(picName, pictureFilePath, picDescription, fileName, dateTaken);
        
        PictureEntity expResult = new PictureEntity(pictureFilePath, fileName, picStatus);
        
        PictureEntity result = instance.retrievePictureById(id);
      
        result.setPicId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrievePicturesByPictureAttributes method, of class PictureSession.
     */
    //test a particular attribute: name
//    @Test
//    public void testFRetrievePicturesByPictureAttributes() throws Exception {
//        System.out.println("retrievePicturesByPictureAttributes - name");
//        PictureEntity picture = new PictureEntity();
//        picture.setPicName(picName);
//        
//        String expResult = picName;
//        String result = instance.retrievePicturesByPictureAttributes(picture).get(0).getPicName();
//        assertEquals(expResult, result);
//    }
    
    //test a particular attribute: filePath
    @Test
    public void testGRetrievePicturesByPictureAttributes() throws Exception {
        System.out.println("retrievePicturesByPictureAttributes - filepath");
        PictureEntity picture = new PictureEntity();
        picture.setPictureFilePath(pictureFilePath);
        
        String expResult = pictureFilePath;
        String result = instance.retrievePicturesByPictureAttributes(picture).get(0).getPictureFilePath();
        assertEquals(expResult, result);
    }

    //test a particular attribute: description
//    @Test
//    public void testHRetrievePicturesByPictureAttributes() throws Exception {
//        System.out.println("retrievePicturesByPictureAttributes - description");
//        PictureEntity picture = new PictureEntity();
//        picture.setPicDescription(picDescription);
//        
//        String expResult = picDescription;
//        String result = instance.retrievePicturesByPictureAttributes(picture).get(0).getPicDescription();
//        assertEquals(expResult, result);
//    }
    
    //test a particular attribute: fileName
    @Test
    public void testIRetrievePicturesByPictureAttributes() throws Exception {
        System.out.println("retrievePicturesByPictureAttributes - fileName");
        PictureEntity picture = new PictureEntity();
        picture.setFileName(fileName);
        
        String expResult = fileName;
        String result = instance.retrievePicturesByPictureAttributes(picture).get(0).getFileName();
        assertEquals(expResult, result);
    }  

    //test a particular attribute: date
//    @Test
//    public void testJRetrievePicturesByPictureAttributes() throws Exception {
//        System.out.println("retrievePicturesByPictureAttributes - date");
//        PictureEntity picture = new PictureEntity();
//        picture.setDateTaken(dateTaken);
//        
//        Date expResult = dateTaken;
//        Date result = instance.retrievePicturesByPictureAttributes(picture).get(0).getDateTaken();
//        assertEquals(expResult, result);
//    }   
    
    //test a particular attribute: date
    @Test
    public void testKRetrievePicturesByPictureAttributes() throws Exception {
        System.out.println("retrievePicturesByPictureAttributes - date");
        PictureEntity picture = new PictureEntity();
        picture.setPicStatus(picStatus);
        
        PictureStatusEnum expResult = picStatus;
        PictureStatusEnum result = instance.retrievePicturesByPictureAttributes(picture).get(0).getPicStatus();
        assertEquals(expResult, result);
    }     


    /**
     * Test of updatePicture method, of class PictureSession.
     */
    @Test
    public void testLUpdatePicture() throws Exception {
        System.out.println("updatePicture");
//        PictureEntity picture = new PictureEntity(picName, pictureFilePath, picDescription, fileName, dateTaken);        
        PictureEntity picture = new PictureEntity(pictureFilePath, fileName, picStatus);
        picture.setPicId(id);
        picture.setPicStatus(PictureStatusEnum.NOT_VISIBLE);
        
        instance.updatePicture(picture);
        
        PictureStatusEnum result = instance.retrievePictureById(id).getPicStatus();
        
        assertEquals(picture.getPicStatus(), result);
    }

    /**
     * Test of deletePicture method, of class PictureSession.
     */
    @Test
    public void testMDeletePicture() throws Exception {
        System.out.println("deletePicture");
        int sizeBefore = instance.retrieveAllPictures().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deletePicture(id);
        
        int sizeAfter = instance.retrieveAllPictures().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
