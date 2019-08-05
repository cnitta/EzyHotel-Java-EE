/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import hms.common.CrudService;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.TransactionEntity;

/**
 *
 * @author berni
 */
@Stateless
public class TransactionSession implements TransactionSessionLocal, CrudService<TransactionEntity> {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public TransactionEntity createEntity(TransactionEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public TransactionEntity retrieveEntityById(Long id) {
        TransactionEntity entity = em.find(TransactionEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("TransactionEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<TransactionEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM TransactionEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        TransactionEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(TransactionEntity entity) {
        em.merge(entity);
    }

    @Override
    public TransactionEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM TransactionEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (TransactionEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<TransactionEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM TransactionEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
