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
import util.entity.RoomAssetEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Stateless
public class RoomAssetSession implements RoomAssetSessionLocal, CrudService<RoomAssetEntity> {
    
    
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public RoomAssetEntity createEntity(RoomAssetEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public RoomAssetEntity retrieveEntityById(Long id) {
        RoomAssetEntity entity = em.find(RoomAssetEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("RoomAssetEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<RoomAssetEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM RoomAssetEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        RoomAssetEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(RoomAssetEntity entity) {
        em.merge(entity);
    }

    @Override
    public RoomAssetEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM RoomAssetEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (RoomAssetEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomAssetEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM RoomAssetEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    
}
