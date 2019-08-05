/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.BookmarkEntity;
import util.exception.BookmarkNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class BookmarkSession implements BookmarkSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public BookmarkEntity createBookmark(BookmarkEntity bookmark) {
        em.persist(bookmark);
        em.flush();
        em.refresh(bookmark);
        return bookmark;
    }

    @Override
    public BookmarkEntity retrieveBookmarkById(Long id) throws BookmarkNotFoundException {
        BookmarkEntity bookmark = em.find(BookmarkEntity.class, id);
        if (bookmark != null) {
            return bookmark;
        } else {
            throw new BookmarkNotFoundException("Bookmark ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<BookmarkEntity> retrieveBookmarkByBookmarkAttributes(BookmarkEntity bookmark) {
     
        Query query = null;
        List<BookmarkEntity> bookmarks;
        
        if(bookmark.getBookmarkStatusEnum() != null)
        {
            query = em.createQuery("SELECT b FROM BookmarkEntity b WHERE b.bookmarkStatusEnum = :inStatus");
            query.setParameter("inStatus", bookmark.getBookmarkStatusEnum());
        } 
        else if(bookmark.getDateBookmarked() != null)
        {
            query = em.createQuery("SELECT b FROM BookmarkEntity b WHERE b.dateBookmarked = :inDateBookmarked");
            query.setParameter("inDateBookmarked", bookmark.getDateBookmarked());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        bookmarks = query.getResultList();
        
        if(bookmarks.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return bookmarks;        
    }

    @Override
    public List<BookmarkEntity> retrieveAllBookmarks() {

        Query query = em.createQuery("SELECT a FROM BookmarkEntity a");
        
        List<BookmarkEntity> bookmarks = query.getResultList();

        
        if(bookmarks.size() == 0)
        {
            return new ArrayList<BookmarkEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateBookmark(BookmarkEntity bookmark) {
        em.merge(bookmark);        
    }

    @Override
    public void deleteBookmark(Long id) throws BookmarkNotFoundException {
        BookmarkEntity bookmark = retrieveBookmarkById(id);
        em.remove(bookmark);        
    }
}
