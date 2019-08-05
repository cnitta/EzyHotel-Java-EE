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
import util.entity.CallReportEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class CallReportSession implements CallReportSessionLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public CallReportEntity createEntity(CallReportEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public CallReportEntity retrieveEntityById(Long id) {
        CallReportEntity entity = em.find(CallReportEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("CallReportEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<CallReportEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM CallReportEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        CallReportEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(CallReportEntity entity) {
        retrieveEntityById(entity.getCallReportId());
        em.merge(entity);
    }

    @Override
    public CallReportEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM CallReportEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (CallReportEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<CallReportEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM CallReportEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
