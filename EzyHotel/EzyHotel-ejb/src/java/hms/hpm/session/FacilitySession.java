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
import util.entity.FacilityEntity;
import util.entity.HotelEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Stateless
public class FacilitySession implements FacilitySessionLocal, CrudService<FacilityEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public FacilityEntity createEntity(FacilityEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public FacilityEntity retrieveEntityById(Long id) {
        FacilityEntity entity = em.find(FacilityEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("FacilityEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<FacilityEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM FacilityEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        FacilityEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(FacilityEntity entity) {
        retrieveEntityById(entity.getFacilityId());
        em.merge(entity);
    }

    @Override
    public FacilityEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM FacilityEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (FacilityEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<FacilityEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM FacilityEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
    public List<FacilityEntity> retrieveFacilitiesByHotelId(Long hId) {

        Query query = em.createQuery("SELECT f FROM FacilityEntity f WHERE f.hotel.hotelId =:hId");
        query.setParameter("hId", hId);

        List<FacilityEntity> facilities = query.getResultList();
        try {
            return facilities;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("There are no facilities in this hotel!");
        }
    }

    @Override
    public void addHotel(Long fId, HotelEntity h) {

        FacilityEntity fac = retrieveEntityById(fId);
        try {
            if (fac.getHotel() == null) {
                fac.setHotel(h);
            }

        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

}
