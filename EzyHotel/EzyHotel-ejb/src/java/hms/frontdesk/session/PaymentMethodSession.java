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
import util.entity.PaymentMethodEntity;

/**
 *
 * @author berni
 */
@Stateless
public class PaymentMethodSession implements PaymentMethodSessionLocal, CrudService<PaymentMethodEntity> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public PaymentMethodEntity createEntity(PaymentMethodEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public PaymentMethodEntity retrieveEntityById(Long id) {
        PaymentMethodEntity entity = em.find(PaymentMethodEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("PaymentMethodEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<PaymentMethodEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM PaymentMethodEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        PaymentMethodEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(PaymentMethodEntity entity) {
        em.merge(entity);
    }

    @Override
    public PaymentMethodEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM PaymentMethodEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (PaymentMethodEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<PaymentMethodEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM PaymentMethodEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
