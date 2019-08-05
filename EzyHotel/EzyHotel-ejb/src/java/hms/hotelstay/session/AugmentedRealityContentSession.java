/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.AugmentedRealityContentEntity;
import util.exception.AugmentedRealityContentNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class AugmentedRealityContentSession implements AugmentedRealityContentSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public AugmentedRealityContentEntity createAugmentedRealityContent(AugmentedRealityContentEntity augmentedRealityContent) {
        em.persist(augmentedRealityContent);
        em.flush();
        em.refresh(augmentedRealityContent);
        return augmentedRealityContent;
    }

    @Override
    public AugmentedRealityContentEntity retrieveAugmentedRealityContentById(Long id) throws AugmentedRealityContentNotFoundException {
        AugmentedRealityContentEntity augmentedRealityContent = em.find(AugmentedRealityContentEntity.class, id);
        if (augmentedRealityContent != null) {
            return augmentedRealityContent;
        } else {
            throw new AugmentedRealityContentNotFoundException("AugmentedRealityContent ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<AugmentedRealityContentEntity> retrieveAugmentedRealityContentByAugmentedRealityContentAttributes(AugmentedRealityContentEntity augmentedRealityContent) {
     
        Query query = null;
        List<AugmentedRealityContentEntity> augmentedRealityContents;
        
        if(augmentedRealityContent.getName() != null)
        {
            query = em.createQuery("SELECT a FROM AugmentedRealityContentEntity a WHERE a.name LIKE :inName");
            query.setParameter("inName", "%" + augmentedRealityContent.getName() + "%");
        }
        else if(augmentedRealityContent.getDescription() != null)
        {
            query = em.createQuery("SELECT a FROM AugmentedRealityContentEntity a WHERE a.description LIKE :inDescription");
            query.setParameter("inDescription", "%" + augmentedRealityContent.getDescription() + "%");
        } 
        else if(augmentedRealityContent.getDateCreated() != null)
        {
            query = em.createQuery("SELECT a FROM AugmentedRealityContentEntity a WHERE a.dateCreated = :inDate");
            query.setParameter("inDate", augmentedRealityContent.getDateCreated());
        }  
        else if(augmentedRealityContent.getaRTypeEnum() != null)
        {
            query = em.createQuery("SELECT a FROM AugmentedRealityContentEntity a WHERE a.aRTypeEnum = :inType");
            query.setParameter("inType", augmentedRealityContent.getaRTypeEnum());
        }         
        else
        {
            return new ArrayList<>();
        }
        
        augmentedRealityContents = query.getResultList();
        
        if(augmentedRealityContents.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return augmentedRealityContents;        
    }

    @Override
    public List<AugmentedRealityContentEntity> retrieveAllAugmentedRealityContents() {

        Query query = em.createQuery("SELECT a FROM AugmentedRealityContentEntity a");
        
        List<AugmentedRealityContentEntity> augmentedRealityContents = query.getResultList();

        
        if(augmentedRealityContents.size() == 0)
        {
            return new ArrayList<AugmentedRealityContentEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateAugmentedRealityContent(AugmentedRealityContentEntity augmentedRealityContent) {
        em.merge(augmentedRealityContent);        
    }

    @Override
    public void deleteAugmentedRealityContent(Long id) throws AugmentedRealityContentNotFoundException {
        AugmentedRealityContentEntity augmentedRealityContent = retrieveAugmentedRealityContentById(id);
        em.remove(augmentedRealityContent);        
    }
}
