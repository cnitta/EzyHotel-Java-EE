/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.housekeeping.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import util.entity.InventoryEntity;

/**
 *
 * @author bryantan
 */
@Stateless
public class InventorySession implements InventorySessionLocal {

    @PersistenceContext 
    private EntityManager em;
    
    @Override
    public InventoryEntity createEntity(InventoryEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }
    
    @Override
    public void updateEntity(InventoryEntity entity) {
        em.merge(entity);
    }
    
    @Override
    public List<InventoryEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM InventoryEntity c");
        return query.getResultList();
    }
    
    @Override
    public void deleteEntity(Long id) {
        InventoryEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }
    
    @Override
    public InventoryEntity retrieveEntityById(Long id) {
        InventoryEntity entity = em.find(InventoryEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("InventoryEntity ID " + id + " does not exist!");
        }
    }
}
