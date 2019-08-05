/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common.session;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.PictureEntity;
import util.exception.PictureNotFoundException;


/**
 *
 * @author vincentyeo
 */
@Stateless
public class PictureSession implements PictureSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public PictureEntity createPicture(PictureEntity picture) {
        em.persist(picture);
        em.flush();
        em.refresh(picture);
        return picture;        
    }

    @Override
    public PictureEntity retrievePictureById(Long id) throws PictureNotFoundException {
        PictureEntity picture = em.find(PictureEntity.class, id);
        if (picture != null) {
            return picture;
        } else {
            throw new PictureNotFoundException("Picture ID " + id + " does not exist!");
        }
    }
    
    @Override
    public String getPictureBase64String(String pictureFilePath) throws IOException
    {
        System.out.println("PictureSession - pictureFilePath: " + pictureFilePath);
        //read files to get bytes

        File file =  new File(pictureFilePath);
//        System.out.println("PictureSession - file done ");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        
        byte[] data = new byte[(int) file.length()];
//        System.out.println("PictureSession - byte data[]  instantiate ");
        
        
        inputStream.read(data);
        //convert to encoded astring and export
        byte[] encodedBytes = Base64.getEncoder().encode(data);
        String encodedString =  new String(encodedBytes, StandardCharsets.UTF_8);
//        System.out.println("encodedString: " + encodedString);
        
        return encodedString;
    }
     

    @Override
    public List<PictureEntity> retrievePicturesByPictureAttributes(PictureEntity picture) {
        
        Query query = null;
        List<PictureEntity> pictures;
        
//        if(picture.getPicName()!= null)
//        {
//            query = em.createQuery("SELECT p FROM PictureEntity p WHERE p.picName LIKE :inName");
//            query.setParameter("inName", picture.getPicName());
//        }        
//        else 
        if(picture.getPictureFilePath() != null)
        {
            query = em.createQuery("SELECT p FROM PictureEntity p WHERE p.pictureFilePath = :inFilePath");
            query.setParameter("inFilePath", picture.getPictureFilePath());
        }
//        else if(picture.getPicDescription() != null)
//        {
//            query = em.createQuery("SELECT p FROM PictureEntity p WHERE p.picDescription = :inDescription");
//            query.setParameter("inDescription", picture.getPicDescription());
//        }        
        else if(picture.getFileName() != null)
        {
            query = em.createQuery("SELECT p FROM PictureEntity p WHERE p.fileName = :inFileName");
            query.setParameter("inFileName", picture.getFileName());
        }    
        else if(picture.getPicStatus() != null)
        {
            query = em.createQuery("SELECT p FROM PictureEntity p WHERE p.picStatus = :inPicStatus");
            query.setParameter("inPicStatus", picture.getPicStatus());
        }
//        else if(picture.getDateTaken() != null)
//        {
//            query = em.createQuery("SELECT p FROM PictureEntity p WHERE p.DateTaken = :inDateTaken");
//            query.setParameter("inDateTaken", picture.getDateTaken());
//        }         
        else
        {
            return new ArrayList<>();
        }
        
        pictures = query.getResultList();
        
        if(pictures.isEmpty())
        { 
            return new ArrayList<>();
        }
        
        return pictures;
    }

    @Override
    public List<PictureEntity> retrieveAllPictures() {
        System.out.println("***PictureSession retrieveAllPictures Started***");
        Query query = em.createQuery("SELECT p FROM PictureEntity p");
        
        List<PictureEntity> pictures = query.getResultList();
        System.out.println("***PictureSession pictures.size() " + pictures.size() + "***");
        
        if(pictures.size() == 0)
        {
            return new ArrayList<PictureEntity>();
        }
        
        return query.getResultList();
    }

    @Override
    public void updatePicture(PictureEntity picture) {
        em.merge(picture);        
    }

    @Override
    public void deletePicture(Long id) throws PictureNotFoundException {
        PictureEntity picture = retrievePictureById(id);
        em.remove(picture);        
    }

}
