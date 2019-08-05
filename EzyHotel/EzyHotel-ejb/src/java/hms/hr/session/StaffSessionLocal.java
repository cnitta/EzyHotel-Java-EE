/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import util.exception.StaffException;
import util.entity.StaffEntity;
import java.util.List;
import util.entity.HotelEntity;
import util.entity.LeaveEntity;
import util.exception.CustomNotFoundException;
import util.exception.LeaveException;


public interface StaffSessionLocal {

    public StaffEntity createEntity(StaffEntity entity)throws StaffException, NoSuchAlgorithmException,UnsupportedEncodingException;

    public StaffEntity retrieveEntityById(Long id)throws CustomNotFoundException;

    public List<StaffEntity> retrieveAllEntities();

    public void deleteEntity(Long id)throws CustomNotFoundException, StaffException;

    public void updateEntity(StaffEntity entity);

    public StaffEntity retrieveEntity(String attribute, String value)throws CustomNotFoundException;

    public List<StaffEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB)throws CustomNotFoundException;

    public void deactivateStaff(Long id) throws StaffException;

    public void activateStaff(Long id) throws StaffException;

    public void addHotel(Long sId, HotelEntity h) throws CustomNotFoundException, StaffException;

    public void removeHotel(Long sId) throws CustomNotFoundException, StaffException;

    public void editHotel(Long sId, HotelEntity h)throws CustomNotFoundException;

    public List<StaffEntity> retrieveActiveStaff();

    public void addLeave(Long sId, LeaveEntity l) throws CustomNotFoundException, LeaveException;

    public void removeLeave(Long sId, LeaveEntity l) throws CustomNotFoundException;

    public boolean checkAvailability(Long sId, Date date)throws CustomNotFoundException;
    
}
