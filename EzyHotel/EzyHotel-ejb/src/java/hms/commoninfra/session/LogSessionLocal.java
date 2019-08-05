/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.commoninfra.session;

import java.util.List;
import javax.ejb.Local;
import javax.ws.rs.core.GenericEntity;
import util.entity.AccessCodeEntity;
import util.entity.CustomerEntity;
import util.entity.FacilityEntity;
import util.entity.HotelEntity;
import util.entity.LogEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomEntity;
import util.entity.RoomTypeEntity;
import util.entity.StaffEntity;
import util.enumeration.LogStatusEnum;
import util.enumeration.SystemCategoryEnum;

/**
 *
 * @author berni
 */
@Local
public interface LogSessionLocal {


    public LogEntity createEntity(LogEntity entity);

    public LogEntity retrieveEntityById(Long id);

    public List<LogEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public List<LogEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public List<LogEntity> retrieveEntityByEitherFilters(String attributeA, String valueA, String attributeB, String valueB);

    public LogEntity retrieveEntity(String attribute, String value);

    public void centralLoggingGeneration(SystemCategoryEnum systemCategory, LogStatusEnum logStatus, GenericEntity entity, String specialCode);


}
