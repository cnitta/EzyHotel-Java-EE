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
import util.entity.AffiliateContentTagEntity;
import util.exception.AffiliateContentTagNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class AffiliateContentTagSession implements AffiliateContentTagSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public AffiliateContentTagEntity createAffiliateContentTag(AffiliateContentTagEntity affiliateContentTag) {
        em.persist(affiliateContentTag);
        em.flush();
        em.refresh(affiliateContentTag);
        return affiliateContentTag;
    }

    @Override
    public AffiliateContentTagEntity retrieveAffiliateContentTagById(Long id) throws AffiliateContentTagNotFoundException {
        AffiliateContentTagEntity affiliateContentTag = em.find(AffiliateContentTagEntity.class, id);
        if (affiliateContentTag != null) {
            return affiliateContentTag;
        } else {
            throw new AffiliateContentTagNotFoundException("AffiliateContentTag ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<AffiliateContentTagEntity> retrieveAffiliateContentTagByAffiliateContentTagAttributes(AffiliateContentTagEntity affiliateContentTag) {
     
        Query query = null;
        List<AffiliateContentTagEntity> affiliateContentTags;
        
        if(affiliateContentTag.getTagKeyword() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentTagEntity a WHERE a.tagKeyword LIKE :inKeyword");
            query.setParameter("inKeyword", "%" + affiliateContentTag.getTagKeyword() + "%");
        }
        if(affiliateContentTag.getDateCreated() != null)
        {
            query = em.createQuery("SELECT a FROM AffiliateContentTagEntity a WHERE a.dateCreated = :inDate");
            query.setParameter("inDate", affiliateContentTag.getDateCreated());
        }             
        else
        {
            return new ArrayList<>();
        }
        
        affiliateContentTags = query.getResultList();
        
        if(affiliateContentTags.size() == 0)
        { 
            return new ArrayList<>();
        }
                
        return affiliateContentTags;        
    }

    @Override
    public List<AffiliateContentTagEntity> retrieveAllAffiliateContentTags() {

        Query query = em.createQuery("SELECT a FROM AffiliateContentTagEntity a");
        
        List<AffiliateContentTagEntity> affiliateContentTags = query.getResultList();
        
        if(affiliateContentTags.isEmpty())
        {
            return new ArrayList<AffiliateContentTagEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateAffiliateContentTag(AffiliateContentTagEntity affiliateContentTag) {
        em.merge(affiliateContentTag);        
    }

    @Override
    public void deleteAffiliateContentTag(Long id) throws AffiliateContentTagNotFoundException {
        AffiliateContentTagEntity affiliateContentTag = retrieveAffiliateContentTagById(id);
        em.remove(affiliateContentTag);        
    }
}
