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
import util.entity.SalesCallGuidelineEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class SalesCallGuidelineSession implements SalesCallGuidelineSessionLocal {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public SalesCallGuidelineEntity createEntity(SalesCallGuidelineEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public SalesCallGuidelineEntity retrieveEntityById(Long id) {
        SalesCallGuidelineEntity entity = em.find(SalesCallGuidelineEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("SalesCallGuidelineEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<SalesCallGuidelineEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM SalesCallGuidelineEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        SalesCallGuidelineEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(SalesCallGuidelineEntity entity) {
        retrieveEntityById(entity.getSalesCallGuidelineId());
        em.merge(entity);
    }

    @Override
    public SalesCallGuidelineEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM SalesCallGuidelineEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (SalesCallGuidelineEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<SalesCallGuidelineEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM SalesCallGuidelineEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
