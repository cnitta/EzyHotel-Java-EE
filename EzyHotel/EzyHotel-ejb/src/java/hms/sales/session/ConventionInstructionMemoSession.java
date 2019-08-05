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
import util.entity.ConventionInstructionMemoEntity;

/**
 *
 * @author Wai Kit
 */
@Stateless
public class ConventionInstructionMemoSession implements ConventionInstructionMemoSessionLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public ConventionInstructionMemoEntity createEntity(ConventionInstructionMemoEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public ConventionInstructionMemoEntity retrieveEntityById(Long id) {
        ConventionInstructionMemoEntity entity = em.find(ConventionInstructionMemoEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("ConventionInstructionMemoEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<ConventionInstructionMemoEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM ConventionInstructionMemoEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        ConventionInstructionMemoEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(ConventionInstructionMemoEntity entity) {
        retrieveEntityById(entity.getConventionInstructionMemoId());
        em.merge(entity);
    }

    @Override
    public ConventionInstructionMemoEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM ConventionInstructionMemoEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (ConventionInstructionMemoEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<ConventionInstructionMemoEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM ConventionInstructionMemoEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
