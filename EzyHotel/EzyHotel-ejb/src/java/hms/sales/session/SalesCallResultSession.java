/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.SalesCallResultEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class SalesCallResultSession implements SalesCallResultSessionLocal {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public SalesCallResultEntity createEntity(SalesCallResultEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public SalesCallResultEntity retrieveEntityById(Long id) {
        SalesCallResultEntity entity = em.find(SalesCallResultEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("SalesCallResultEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<SalesCallResultEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM SalesCallResultEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        SalesCallResultEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(SalesCallResultEntity entity) {
        retrieveEntityById(entity.getSalesCallResultId());
        em.merge(entity);
    }

    @Override
    public SalesCallResultEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM SalesCallResultEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (SalesCallResultEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<SalesCallResultEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM SalesCallResultEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
