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
import util.entity.BookmarkEntity;
import util.enumeration.BookmarkStatusEnum;

/**
 *
 * @author vincentyeo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookmarkSessionTest {
    static EJBContainer container;
    static BookmarkSessionLocal instance;
    static Long id = Long.valueOf("1");
    static Date dateBookmarked = new Date();
    static BookmarkStatusEnum bookmarkStatusEnum = BookmarkStatusEnum.ACTIVE;
    
    public BookmarkSessionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (BookmarkSessionLocal)container.getContext().lookup("java:global/classes/BookmarkSession");
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
     * Test of retrieveAllBookmarks method, of class BookmarkSession.
     */
    @Test
    public void testAARetrieveAllBookmarks() throws Exception {
        System.out.println("retrieveAllBookmarks");
        int result = instance.retrieveAllBookmarks().size();        
        assertTrue("number of Bookmark is " + result, result > 0);
    }

    @Test
    public void testABRetrieveAllBookmarks() throws Exception {
        System.out.println("retrieveAllBookmarks");
        int result = instance.retrieveAllBookmarks().size();        
        assertTrue("number of Bookmark is " + result, result == 0);
    }    
    
    /**
     * Test of createBookmark method, of class BookmarkSession.
     */
    @Test
    public void testACreateBookmark() throws Exception {
        System.out.println("createBookmark");
        BookmarkEntity bookmark = new BookmarkEntity(dateBookmarked, bookmarkStatusEnum);

        int sizeBefore = instance.retrieveAllBookmarks().size();
        
        int expResult = sizeBefore + 1;
        
        instance.createBookmark(bookmark);
        
        int sizeAfter = instance.retrieveAllBookmarks().size();
                
        assertEquals(expResult, sizeAfter);
    }

    /**
     * Test of retrieveBookmarkById method, of class BookmarkSession.
     */
    @Test
    public void testBRetrieveBookmarkById() throws Exception {
        System.out.println("retrieveBookmarkById");
        BookmarkEntity expResult = new BookmarkEntity(dateBookmarked, bookmarkStatusEnum);
        
        BookmarkEntity result = instance.retrieveBookmarkById(id);
      
        result.setBookmarkId(null);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of retrieveBookmarkByBookmarkAttributes method, of class BookmarkSession.
     */
    @Test
    public void testCRetrieveBookmarkByBookmarkAttributes() throws Exception {
        System.out.println("retrieveBookmarkByBookmarkAttributes");
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setDateBookmarked(dateBookmarked);
        
        Date expResult = dateBookmarked;
        Date result = instance.retrieveBookmarkByBookmarkAttributes(bookmark).get(0).getDateBookmarked();
        assertEquals(expResult, result);
    }



    /**
     * Test of updateBookmark method, of class BookmarkSession.
     */
    @Test
    public void testUpdateBookmark() throws Exception {
        System.out.println("updateBookmark");
        BookmarkEntity bookmark = new BookmarkEntity(dateBookmarked, bookmarkStatusEnum);
        bookmark.setBookmarkStatusEnum(BookmarkStatusEnum.INACTIVE);
        bookmark.setBookmarkId(id);

        instance.updateBookmark(bookmark);
        
        BookmarkStatusEnum result = instance.retrieveBookmarkById(id).getBookmarkStatusEnum();
        
        assertEquals(bookmark.getBookmarkStatusEnum(), result);
    }

    /**
     * Test of deleteBookmark method, of class BookmarkSession.
     */
    @Test
    public void testDeleteBookmark() throws Exception {
        System.out.println("deleteBookmark");
        int sizeBefore = instance.retrieveAllBookmarks().size();
        
        int expResult = sizeBefore - 1;
       
        instance.deleteBookmark(id);
        
        int sizeAfter = instance.retrieveAllBookmarks().size();
        
        assertEquals(expResult, sizeAfter);
    }
    
}
