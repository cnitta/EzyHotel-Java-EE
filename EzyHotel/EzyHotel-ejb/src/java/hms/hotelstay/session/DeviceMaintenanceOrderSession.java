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
import util.entity.DeviceMaintenanceOrderEntity;
import util.exception.DeviceMaintenanceOrderNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class DeviceMaintenanceOrderSession implements DeviceMaintenanceOrderSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public DeviceMaintenanceOrderEntity createDeviceMaintenanceOrder(DeviceMaintenanceOrderEntity deviceMaintenanceOrder) {
        em.persist(deviceMaintenanceOrder);
        em.flush();
        em.refresh(deviceMaintenanceOrder);
        return deviceMaintenanceOrder;
    }

    @Override
    public DeviceMaintenanceOrderEntity retrieveDeviceMaintenanceOrderById(Long id) throws DeviceMaintenanceOrderNotFoundException {
        DeviceMaintenanceOrderEntity deviceMaintenanceOrder = em.find(DeviceMaintenanceOrderEntity.class, id);
        if (deviceMaintenanceOrder != null) {
            return deviceMaintenanceOrder;
        } else {
            throw new DeviceMaintenanceOrderNotFoundException("DeviceMaintenanceOrder ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<DeviceMaintenanceOrderEntity> retrieveDeviceMaintenanceOrderByDeviceMaintenanceOrderAttributes(DeviceMaintenanceOrderEntity deviceMaintenanceOrder) {
     
        Query query = null;
        List<DeviceMaintenanceOrderEntity> deviceMaintenanceOrders;
        
        if(deviceMaintenanceOrder.getStartDate() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceMaintenanceOrderEntity d WHERE d.startDate = :inStartDate");
            query.setParameter("inStartDate", deviceMaintenanceOrder.getStartDate());
        }
        else if(deviceMaintenanceOrder.getEndDate() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceMaintenanceOrderEntity d WHERE d.endDate = :inEndDate");
            query.setParameter("inEndDate", deviceMaintenanceOrder.getEndDate());
        } 
        else if(deviceMaintenanceOrder.getMaintenanceOrderStateEnum() != null)
        {
            query = em.createQuery("SELECT d FROM DeviceMaintenanceOrderEntity d WHERE d.maintenanceOrderStateEnum = :inState");
            query.setParameter("inState", deviceMaintenanceOrder.getMaintenanceOrderStateEnum());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        deviceMaintenanceOrders = query.getResultList();
        
        if(deviceMaintenanceOrders.size() == 0)
        { 
            return new ArrayList<>();
        }
                
        return deviceMaintenanceOrders;        
    }

    @Override
    public List<DeviceMaintenanceOrderEntity> retrieveAllDeviceMaintenanceOrders() {

        Query query = em.createQuery("SELECT d FROM DeviceMaintenanceOrderEntity d");
        
        List<DeviceMaintenanceOrderEntity> deviceMaintenanceOrders = query.getResultList();

        
        if(deviceMaintenanceOrders.size() == 0)
        {
            return new ArrayList<DeviceMaintenanceOrderEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateDeviceMaintenanceOrder(DeviceMaintenanceOrderEntity deviceMaintenanceOrder) {
        em.merge(deviceMaintenanceOrder);        
    }

    @Override
    public void deleteDeviceMaintenanceOrder(Long id) throws DeviceMaintenanceOrderNotFoundException {
        DeviceMaintenanceOrderEntity deviceMaintenanceOrder = retrieveDeviceMaintenanceOrderById(id);
        em.remove(deviceMaintenanceOrder);        
    }
}
