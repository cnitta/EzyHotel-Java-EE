/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ARContentViewEntity;
import util.exception.ARContentViewNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface ARContentViewSessionLocal {
     public ARContentViewEntity createARContentView(ARContentViewEntity aRContentView);
    
    public ARContentViewEntity retrieveARContentViewById(Long id) throws ARContentViewNotFoundException;
    
    public List<ARContentViewEntity> retrieveARContentViewByARContentViewAttributes (ARContentViewEntity aRContentView);    
    
    public List<ARContentViewEntity> retrieveAllARContentViews();

    public void updateARContentView(ARContentViewEntity aRContentView);
    
    public void deleteARContentView(Long id) throws ARContentViewNotFoundException;    
}
