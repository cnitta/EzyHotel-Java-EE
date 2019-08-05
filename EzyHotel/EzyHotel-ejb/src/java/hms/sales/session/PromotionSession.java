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
import util.entity.PromotionEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class PromotionSession implements PromotionSessionLocal {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public PromotionEntity createEntity(PromotionEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public PromotionEntity retrieveEntityById(Long id) {
        PromotionEntity entity = em.find(PromotionEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("PromotionEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<PromotionEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM PromotionEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        PromotionEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(PromotionEntity entity) {
        retrieveEntityById(entity.getPromotionId());
        em.merge(entity);
    }

    @Override
    public PromotionEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM PromotionEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (PromotionEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<PromotionEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM PromotionEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
