/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import hms.common.CrudService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.FeedbackEntity;

/**
 *
 * @author berni
 */
@Stateless
public class FeedbackSession implements FeedbackSessionLocal, CrudService<FeedbackEntity> {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public FeedbackEntity createEntity(FeedbackEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public FeedbackEntity retrieveEntityById(Long id) {
        FeedbackEntity entity = em.find(FeedbackEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("FeedbackEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<FeedbackEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM FeedbackEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        FeedbackEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(FeedbackEntity entity) {
        em.merge(entity);
    }

    @Override
    public FeedbackEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM FeedbackEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (FeedbackEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<FeedbackEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM FeedbackEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }


}
