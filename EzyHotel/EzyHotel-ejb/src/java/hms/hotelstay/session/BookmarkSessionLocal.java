/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.BookmarkEntity;
import util.exception.BookmarkNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface BookmarkSessionLocal {
    public BookmarkEntity createBookmark(BookmarkEntity bookmark);
    
    public BookmarkEntity retrieveBookmarkById(Long id) throws BookmarkNotFoundException;
    
    public List<BookmarkEntity> retrieveBookmarkByBookmarkAttributes (BookmarkEntity bookmark);    
    
    public List<BookmarkEntity> retrieveAllBookmarks();

    public void updateBookmark(BookmarkEntity bookmark);
    
    public void deleteBookmark(Long id) throws BookmarkNotFoundException;     
}
