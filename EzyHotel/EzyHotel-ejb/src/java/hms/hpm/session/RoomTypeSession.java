/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import hms.common.CrudService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.RoomTypeEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Stateless
public class RoomTypeSession implements RoomTypeSessionLocal, CrudService<RoomTypeEntity> {

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public RoomTypeEntity createEntity(RoomTypeEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public RoomTypeEntity retrieveEntityById(Long id) {
        RoomTypeEntity entity = em.find(RoomTypeEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("RoomTypeEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<RoomTypeEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT rT FROM RoomTypeEntity rT");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        RoomTypeEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(RoomTypeEntity entity) {
        em.merge(entity);
    }

    @Override
    public RoomTypeEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM RoomTypeEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (RoomTypeEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomTypeEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM RoomTypeEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomTypeEntity> retrieveAllRoomTypesByHotelId(Long hId) {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.hotel.hotelId=:hId");
        query.setParameter("hId", hId);

        List<RoomTypeEntity> roomTypes = query.getResultList();
        try {
            return roomTypes;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("There are no room types in this hotel!");
        }
    }

}
