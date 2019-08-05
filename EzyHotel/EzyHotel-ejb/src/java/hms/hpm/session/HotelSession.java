/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hpm.session;

import hms.common.CrudService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.HotelEntity;

/**
 *
 * @author nittayawancharoenkharungrueang
 */
@Stateless
public class HotelSession implements HotelSessionLocal, CrudService<HotelEntity> {
    
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;


    @Override
    public HotelEntity createEntity(HotelEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public HotelEntity retrieveEntityById(Long id) {
        HotelEntity entity = em.find(HotelEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("HotelEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<HotelEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM HotelEntity c");
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        HotelEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(HotelEntity entity) {
        em.merge(entity);
    }

    @Override
    public HotelEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM HotelEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (HotelEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<HotelEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM HotelEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", valueA);
        query.setParameter("inValue", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<HotelEntity> retrieveHotelsByAttributes(String name, String address) {
        Query query = em.createQuery("SELECT h FROM HotelEntity h WHERE LOWER(h.name) LIKE :inName OR LOWER(h.address) LIKE :inAddress");
        query.setParameter("inName", "%" + name + "%");
        query.setParameter("inAddress", "%" + address + "%");
        
        if(query.getResultList().isEmpty())
        {
            return new ArrayList<>();
        }
        
        return query.getResultList();
    }
    
   
    
}
