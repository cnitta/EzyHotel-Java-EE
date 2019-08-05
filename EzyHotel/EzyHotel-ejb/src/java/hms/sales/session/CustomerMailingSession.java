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
import util.entity.CustomerMailingEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class CustomerMailingSession implements CustomerMailingSessionLocal {

    @PersistenceContext
    private EntityManager em;
    @Override
    public CustomerMailingEntity createEntity(CustomerMailingEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public CustomerMailingEntity retrieveEntityById(Long id) {
        CustomerMailingEntity entity = em.find(CustomerMailingEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("CustomerMailingEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<CustomerMailingEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM CustomerMailingEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        CustomerMailingEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(CustomerMailingEntity entity) {
        retrieveEntityById(entity.getCustomerMailingId());
        em.merge(entity);
    }

    @Override
    public CustomerMailingEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM CustomerMailingEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (CustomerMailingEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<CustomerMailingEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM CustomerMailingEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
