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
import util.entity.RoomAmenityEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Stateless
public class RoomAmenitySession implements RoomAmenitySessionLocal, CrudService<RoomAmenityEntity> {
    
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;
    
    @Override
    public RoomAmenityEntity createEntity(RoomAmenityEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public RoomAmenityEntity retrieveEntityById(Long id) {
        RoomAmenityEntity entity = em.find(RoomAmenityEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("RoomAmenityEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<RoomAmenityEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM RoomAmenityEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        RoomAmenityEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(RoomAmenityEntity entity) {
        em.merge(entity);
    }

    @Override
    public RoomAmenityEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM RoomAmenityEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (RoomAmenityEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomAmenityEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM RoomAmenityEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
