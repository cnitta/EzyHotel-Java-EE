package hms.hpm.session;

import hms.common.CrudService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.RoomEntity;
import util.enumeration.RoomStatusEnum;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Stateless
public class RoomSession implements RoomSessionLocal, CrudService<RoomEntity> {

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public RoomEntity createEntity(RoomEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public RoomEntity retrieveEntityById(Long id) {
        RoomEntity entity = em.find(RoomEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("RoomEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<RoomEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM RoomEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        RoomEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(RoomEntity entity) {
        em.merge(entity);
    }

    @Override
    public RoomEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM RoomEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (RoomEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM RoomEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomEntity> unoccupiedRoomList() {
        //retrieve All rooms in RoomEntity database
        List<RoomEntity> retrieveAllRooms = retrieveAllEntities();
        //retrieve All unoccupied rooms
        String roomStatus = "UNOCCUPIED";
        RoomStatusEnum rStatus = RoomStatusEnum.valueOf(roomStatus);
        //Create a empty list to put all unoccupied rooms
        List<RoomEntity> unoccupiedList = new ArrayList<RoomEntity>();
        //loop thru
        for (int i = 0; i < retrieveAllRooms.size(); i++) {
            RoomStatusEnum currRoom = retrieveAllRooms.get(i).getStatus();
            if (rStatus == currRoom) {
                //status = unoccupied, add to list
                unoccupiedList.add(retrieveAllRooms.get(i));
            }
        }
        return unoccupiedList;
    }//end unoccupiedRoomList

    @Override
    public int countSur() {
        List<RoomEntity> unoccupiedList = unoccupiedRoomList();
        String code = "SUR";
        List<RoomEntity> surList = new ArrayList<RoomEntity>();
        //loop thru unoccupiedList to see SUR roomType
        for (int k = 0; k < unoccupiedList.size(); k++) {
            String curRoomTypeCode = unoccupiedList.get(k).getRoomType().getRoomTypecode();
            if (curRoomTypeCode.equals(code)) {
                surList.add(unoccupiedList.get(k));
            }
        }
        int count = surList.size();
        return count;
    }//end countSur

    @Override
    public int countDex() {
        List<RoomEntity> unoccupiedList = unoccupiedRoomList();
        String code = "DEX";
        List<RoomEntity> dexList = new ArrayList<RoomEntity>();
        //loop thru unoccupiedList to see SUR roomType
        for (int k = 0; k < unoccupiedList.size(); k++) {
            String curRoomTypeCode = unoccupiedList.get(k).getRoomType().getRoomTypecode();
            if (curRoomTypeCode.equals(code)) {
                dexList.add(unoccupiedList.get(k));
            }
        }
        int count = dexList.size();
        return count;
    }//end countDex

    @Override
    public int countJun() {
        List<RoomEntity> unoccupiedList = unoccupiedRoomList();
        String code = "JUN";
        List<RoomEntity> junList = new ArrayList<RoomEntity>();
        //loop thru unoccupiedList to see SUR roomType
        for (int k = 0; k < unoccupiedList.size(); k++) {
            String curRoomTypeCode = unoccupiedList.get(k).getRoomType().getRoomTypecode();
            if (curRoomTypeCode.equals(code)) {
                junList.add(unoccupiedList.get(k));
            }
        }
        int count = junList.size();
        return count;
    }//end countJun

    @Override
    public int countExe() {
        List<RoomEntity> unoccupiedList = unoccupiedRoomList();
        String code = "EXE";
        List<RoomEntity> exeList = new ArrayList<RoomEntity>();
        //loop thru unoccupiedList to see SUR roomType
        for (int k = 0; k < unoccupiedList.size(); k++) {
            String curRoomTypeCode = unoccupiedList.get(k).getRoomType().getRoomTypecode();
            if (curRoomTypeCode.equals(code)) {
                exeList.add(unoccupiedList.get(k));
            }
        }
        int count = exeList.size();
        return count;
    }//end countExe

    @Override
    public int countPre() {
        List<RoomEntity> unoccupiedList = unoccupiedRoomList();
        String code = "PRE";
        List<RoomEntity> preList = new ArrayList<RoomEntity>();
        //loop thru unoccupiedList to see SUR roomType
        for (int k = 0; k < unoccupiedList.size(); k++) {
            String curRoomTypeCode = unoccupiedList.get(k).getRoomType().getRoomTypecode();
            if (curRoomTypeCode.equals(code)) {
                preList.add(unoccupiedList.get(k));
            }
        }
        int count = preList.size();
        return count;
    }//end countPre

    @Override
    public RoomEntity retrieveRoomId(String roomNo) {

        System.out.println("*** Retrieve Room ID By number Started ***\n");
        //convert from string to int
        int convertToInt = Integer.parseInt(roomNo);

        Query q = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomUnitNumber = :inId");

        q.setParameter("inId", convertToInt);

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve Room ID By number Ended ***\n");
            return (RoomEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve Room ID by number Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }
    }

    @Override
    public List<RoomEntity> retrieveAllRoomsByHotelId(Long hId) {

        Query query = em.createQuery("SELECT rm FROM RoomEntity rm JOIN rm.roomType rt WHERE rt.hotel.hotelId=:hId");
        query.setParameter("hId", hId);

        List<RoomEntity> rooms = query.getResultList();
        System.out.println("ROOMS BY HOTEL ID: " + rooms);
        try {
            return rooms;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("There are no rooms in this hotel!");
        }
    }
    @Override
    public List<RoomEntity> occupiedRoomList() {
        //retrieve All rooms in RoomEntity database
        List<RoomEntity> retrieveAllRooms = retrieveAllEntities();
        //retrieve All unoccupied rooms
        String roomStatus = "OCCUPIED";
        RoomStatusEnum rStatus = RoomStatusEnum.valueOf(roomStatus);
        //Create a empty list to put all unoccupied rooms
        List<RoomEntity> occupiedList = new ArrayList<RoomEntity>();
        //loop thru
        for (int i = 0; i < retrieveAllRooms.size(); i++) {
            RoomStatusEnum currRoom = retrieveAllRooms.get(i).getStatus();
            if (rStatus == currRoom) {
                //status = unoccupied, add to list
                occupiedList.add(retrieveAllRooms.get(i));
            }
        }
        return occupiedList;
    }

    @Override
    public void setRoomToClean(Integer roomNumber) {
        Query query = em.createQuery("SELECT rm FROM RoomEntity rm WHERE rm.roomUnitNumber= :value");
        query.setParameter("value", roomNumber);
        RoomEntity room = (RoomEntity) query.getSingleResult();
        room.setCleaningStatus("Clean");
        updateEntity(room);
    }

    @Override
    public RoomEntity retrieveRoomByRoomNumber(Integer roomNumber) {
        Query query = em.createQuery("SELECT rm FROM RoomEntity rm WHERE rm.roomUnitNumber= :value");
        query.setParameter("value", roomNumber);
        return (RoomEntity) query.getSingleResult();
    }
}
