/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common.session;

import java.io.IOException;
import java.util.List;
import javax.ejb.Local;
import util.entity.PictureEntity;
import util.exception.PictureNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface PictureSessionLocal {
    public PictureEntity createPicture(PictureEntity picture);
    
    public PictureEntity retrievePictureById(Long id) throws PictureNotFoundException;
    
    public List<PictureEntity> retrievePicturesByPictureAttributes (PictureEntity picture);    
    
    public List<PictureEntity> retrieveAllPictures();
    
    public String getPictureBase64String(String pictureFilePath) throws IOException;

    public void updatePicture(PictureEntity picture);
    
    public void deletePicture(Long id) throws PictureNotFoundException; 
    
}
