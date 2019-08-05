/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.enumeration.PictureStatusEnum;


/**
 *
 * @author vincentyeo
 */
@Entity
public class PictureEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picId;
    private String pictureFilePath; //e.g. local
    private String fileName;   
   
    @Enumerated(EnumType.STRING)
    private PictureStatusEnum picStatus;
    
    private String image;

    public PictureEntity() {
    }

    public PictureEntity(String pictureFilePath, String fileName, PictureStatusEnum picStatus) {
        this.pictureFilePath = pictureFilePath;
        this.fileName = fileName;
        this.picStatus = picStatus;
    }

//    public PictureEntity(String picName, String pictureFilePath, String picDescription, String fileName, Date DateTaken) {
////        this.picName = picName;
//        this.pictureFilePath = pictureFilePath;
////        this.picDescription = picDescription;
//        this.fileName = fileName;
//        this.DateTaken = DateTaken;
//        this.picStatus = PictureStatusEnum.VISIBLE;
//    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

//    public String getPicName() {
//        return picName;
//    }
//
//    public void setPicName(String picName) {
//        this.picName = picName;
//    }

    public String getPictureFilePath() {
        return pictureFilePath;
    }

    public void setPictureFilePath(String pictureFilePath) {
        this.pictureFilePath = pictureFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PictureStatusEnum getPicStatus() {
        return picStatus;
    }

    public void setPicStatus(PictureStatusEnum picStatus) {
        this.picStatus = picStatus;
    }       

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPicId() != null ? getPicId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the picId fields are not set
        if (!(object instanceof PictureEntity)) {
            return false;
        }
        PictureEntity other = (PictureEntity) object;
        if ((this.getPicId() == null && other.getPicId() != null) || (this.getPicId() != null && !this.picId.equals(other.picId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.PictureEntity[ id=" + getPicId() + " ]";
    }
    
}
