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
import util.entity.PaymentEntity;

/**
 *
 * @author berni
 */
@Stateless
public class PaymentSession implements PaymentSessionLocal, CrudService<PaymentEntity> {
    @PersistenceContext
    private EntityManager em;


    
    @Override
    public List<PaymentEntity> getPaymentByHotel(Long hotelId){
        System.out.println("In Get Payment By Hotel, hotelId = " + hotelId);
        String queryInput = "SELECT c FROM PaymentEntity c WHERE c.roomBooking.roomType.hotel.hotelId =:hotelId";
        Query query = em.createQuery(queryInput);
        query.setParameter("hotelId", hotelId);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    
    @Override
    public PaymentEntity createEntity(PaymentEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public PaymentEntity retrieveEntityById(Long id) {
        PaymentEntity entity = em.find(PaymentEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("PaymentEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<PaymentEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM PaymentEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        PaymentEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(PaymentEntity entity) {
        em.merge(entity);
    }

    @Override
    public PaymentEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM PaymentEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (PaymentEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    
    @Override
    public PaymentEntity retrieveEntityById(String attribute, Long id){
        String queryInput = "SELECT c FROM PaymentEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", id);
        try {
            return (PaymentEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }
    

    @Override
    public List<PaymentEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM PaymentEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
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
