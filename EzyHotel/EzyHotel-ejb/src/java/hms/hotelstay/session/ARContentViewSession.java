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
import util.entity.ARContentViewEntity;
import util.exception.ARContentViewNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class ARContentViewSession implements ARContentViewSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public ARContentViewEntity createARContentView(ARContentViewEntity aRContentView) {
        em.persist(aRContentView);
        em.flush();
        em.refresh(aRContentView);
        return aRContentView;
    }

    @Override
    public ARContentViewEntity retrieveARContentViewById(Long id) throws ARContentViewNotFoundException {
        ARContentViewEntity aRContentView = em.find(ARContentViewEntity.class, id);
        if (aRContentView != null) {
            return aRContentView;
        } else {
            throw new ARContentViewNotFoundException("ARContentView ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<ARContentViewEntity> retrieveARContentViewByARContentViewAttributes(ARContentViewEntity aRContentView) {
     
        Query query = null;
        List<ARContentViewEntity> aRContentViews;
        
        if(aRContentView.getStartDate() != null)
        {
            query = em.createQuery("SELECT a FROM ARContentViewEntity a WHERE a.startDate = :inStartDate");
            query.setParameter("inStartDate", aRContentView.getStartDate());
        }  
        else if(aRContentView.getEndDate() != null)
        {
            query = em.createQuery("SELECT a FROM ARContentViewEntity a WHERE a.endDate = :inEndDate");
            query.setParameter("inEndDate", aRContentView.getEndDate());
        } 
//        else if(aRContentView.getViewDuration() != null)
//        {
//            query = em.createQuery("SELECT a FROM ARContentViewEntity a WHERE a.viewDuration = :inDuration");
//            query.setParameter("inDuration", aRContentView.getViewDuration());
//        }         
        else
        {
            return new ArrayList<>();
        }
        
        aRContentViews = query.getResultList();
        
        if(aRContentViews.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return aRContentViews;        
    }

    @Override
    public List<ARContentViewEntity> retrieveAllARContentViews() {

        Query query = em.createQuery("SELECT a FROM ARContentViewEntity a");
        
        List<ARContentViewEntity> aRContentViews = query.getResultList();

        
        if(aRContentViews.size() == 0)
        {
            return new ArrayList<ARContentViewEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateARContentView(ARContentViewEntity aRContentView) {
        em.merge(aRContentView);        
    }

    @Override
    public void deleteARContentView(Long id) throws ARContentViewNotFoundException {
        ARContentViewEntity aRContentView = retrieveARContentViewById(id);
        em.remove(aRContentView);        
    }
}
