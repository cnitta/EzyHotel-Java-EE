/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ARGameStampBookEntity;
import util.entity.CustomerEntity;
import util.exception.ARGameStampBookNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface ARGameStampBookSessionLocal {
    public ARGameStampBookEntity createARGameStampBook(ARGameStampBookEntity aRGameStampBookSession);
    
    public ARGameStampBookEntity retrieveARGameStampBookById(Long id) throws ARGameStampBookNotFoundException;
    
    public List<ARGameStampBookEntity> retrieveARGameStampBookByARGameStampBookAttributes (ARGameStampBookEntity aRGameStampBook);    

    public List<ARGameStampBookEntity> retrieveARGameStampBookByCustomerAttributes (CustomerEntity customer);  
    
    public List<ARGameStampBookEntity> retrieveAllARGameStampBooks();

    public void updateARGameStampBook(ARGameStampBookEntity aRGameStampBook);
    
    public void deleteARGameStampBook(Long id) throws ARGameStampBookNotFoundException;     
}
