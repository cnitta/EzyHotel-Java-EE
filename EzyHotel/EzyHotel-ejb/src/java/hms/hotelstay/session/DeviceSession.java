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
import util.entity.DeviceEntity;
import util.entity.RoomEntity;
import util.entity.StaffEntity;
import util.exception.DeviceNotFoundException;
import util.exception.RoomNotFoundException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class DeviceSession implements DeviceSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public DeviceEntity createDevice(DeviceEntity device) {
        em.persist(device);
        em.flush();
        em.refresh(device);
        return device;
    }

    @Override
    public DeviceEntity retrieveDeviceById(Long id) throws DeviceNotFoundException {
        DeviceEntity device = em.find(DeviceEntity.class, id);
        
        if(device == null)
        {
           throw new DeviceNotFoundException("Device with deviceId " + id + " Not Found");
        }
        
        return device;
    }

    @Override
    public List<DeviceEntity> retrieveDeviceByDeviceAttributes(DeviceEntity device){
     
        Query query = null;
        List<DeviceEntity> devices;
        
        if(device.getDeviceCategory() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.deviceCategory=:inCategory");
            query.setParameter("inCategory", device.getDeviceCategory());
        }
        else if(device.getDeviceModel() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.deviceModel LIKE :inModel");  
            query.setParameter("inModel", "%" + device.getDeviceModel() + "%");
        }
        else if(device.getDeviceState() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.deviceState = :inState");
            query.setParameter("inState", device.getDeviceState());
        }
        else if(device.getDeviceStatus()!= null)
        {
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.deviceStatus = :inStatus"); 
            query.setParameter("inStatus", device.getDeviceStatus());
        }   
        else if(device.getManufacturerName() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.manufacturerName LIKE :inManufacturerName");
            query.setParameter("inManufacturerName", "%" + device.getManufacturerName() + "%");
        }
        else if(device.getSerialNumber()!= null)
        {
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.serialNumber LIKE :inSerialNumber");
            query.setParameter("inSerialNumber", "%" + device.getSerialNumber() + "%");
        }
        else if(device.getLastMaintenanceDate()!= null)
        {            
            System.out.println(device.getLastMaintenanceDate().toString());
            query = em.createQuery("SELECT d FROM DeviceEntity d WHERE d.lastMaintenanceDate >= :inMaintenanceDate");    
            query.setParameter("inMaintenanceDate", device.getLastMaintenanceDate());
        }       
        else
        {
            return new ArrayList<>();
        }
        
        devices = query.getResultList();
        
        if(devices.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return devices;
    }

    @Override
    public List<DeviceEntity> retrieveDeviceByRoomAttributes(RoomEntity room){
        
        String queryString = "SELECT d FROM DeviceEntity d WHERE d.Room.roomUnitNumber =:inRoomNumber OR d.Room.roomId =:inRoomId";
        
        Query query = em.createQuery(queryString);
        query.setParameter("inRoomNumber", room.getRoomUnitNumber());
        query.setParameter("inRoomId", room.getRoomId());
        
        List<DeviceEntity> devices = query.getResultList();
        
        if(devices.size() == 0)
        { 
            return new ArrayList<>();
        }
                
        return devices;
    }

    @Override
    public List<DeviceEntity> retrieveAllDevices(){
        System.out.println("***DeviceSession retrieveAllDevices Started***");
        Query query = em.createQuery("SELECT d FROM DeviceEntity d");
        
        List<DeviceEntity> devices = query.getResultList();
        System.out.println("***DeviceSession devices.size() " + devices.size() + "***");
        
        if(devices.size() == 0)
        {
            return new ArrayList<DeviceEntity>();
        }
        
        return query.getResultList();
    }

    @Override
    public void updateDevice(DeviceEntity device) {        
        em.merge(device);
    }
    
    @Override
    public List<RoomEntity> retrieveRoomsWithDevice()
    {        
        Query query = em.createQuery("SELECT r FROM DeviceEntity d, RoomEntity r WHERE r = d.Room");
        List<RoomEntity> rooms = query.getResultList();
        
        if(rooms.size() == 0)
        { 
            return new ArrayList<>();
        }
        
        return query.getResultList();
    }
    
    public Boolean assignDeviceWithRoom(Long dId, Long rId) throws DeviceNotFoundException, RoomNotFoundException{
        DeviceEntity device = em.find(DeviceEntity.class, dId);
        RoomEntity room = em.find(RoomEntity.class, rId);
        
        if(device == null)
        {
            throw new DeviceNotFoundException("Device Not Found");
        }
        if(room == null)
        {
            throw new RoomNotFoundException("Room Not Found");
        }
        
        device.setRoom(room);
        
        return true;
        
    }
    
    public Boolean unassignDeviceWithRoom(Long dId) throws DeviceNotFoundException{
        DeviceEntity device = em.find(DeviceEntity.class, dId);
        
        if(device == null)
        {
            throw new DeviceNotFoundException("Device Not Found");
        }        
        
        device.setRoom(null);
        
        return true;
    }

    @Override
    public void deleteDevice(Long id) throws DeviceNotFoundException {
        DeviceEntity device = retrieveDeviceById(id);
        em.remove(device);
    }
    
    @Override
    public void deleteDeviceBySerialNumber(String serialNumber) throws DeviceNotFoundException {
        DeviceEntity device = new DeviceEntity();
        device.setSerialNumber(serialNumber);
        
        List<DeviceEntity> devices  = retrieveDeviceByDeviceAttributes(device);
        
        em.remove(devices.get(0));
    }    

}
