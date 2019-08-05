/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.RoomEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Local
public interface RoomSessionLocal {

    public RoomEntity createEntity(RoomEntity entity);

    public RoomEntity retrieveEntityById(Long id);

    public List<RoomEntity> retrieveAllEntities();

    public List<RoomEntity> retrieveAllRoomsByHotelId(Long hId);

    public void deleteEntity(Long id);

    public void updateEntity(RoomEntity entity);

    public RoomEntity retrieveEntity(String attribute, String value);

    public List<RoomEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public RoomEntity retrieveRoomId(String roomNo);

    public int countPre();

    public int countExe();

    public int countJun();

    public int countDex();

    public int countSur();

    public List<RoomEntity> unoccupiedRoomList();

    public List<RoomEntity> occupiedRoomList();
    
    public void setRoomToClean(Integer roomNumber);
    
    public RoomEntity retrieveRoomByRoomNumber(Integer roomNumber);

}

