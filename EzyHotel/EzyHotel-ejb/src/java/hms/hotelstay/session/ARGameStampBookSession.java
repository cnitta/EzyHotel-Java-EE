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
import util.entity.ARGameStampBookEntity;
import util.entity.CustomerEntity;
import util.exception.ARGameStampBookNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class ARGameStampBookSession implements ARGameStampBookSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public ARGameStampBookEntity createARGameStampBook(ARGameStampBookEntity aRGameStampBookSession) {
        em.persist(aRGameStampBookSession);
        em.flush();
        em.refresh(aRGameStampBookSession);
        return aRGameStampBookSession;
    }

    @Override
    public ARGameStampBookEntity retrieveARGameStampBookById(Long id) throws ARGameStampBookNotFoundException {
        ARGameStampBookEntity aRGameStampBookSession = em.find(ARGameStampBookEntity.class, id);
        if (aRGameStampBookSession != null) {
            return aRGameStampBookSession;
        } else {
            throw new ARGameStampBookNotFoundException("ARGameStampBookSession ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<ARGameStampBookEntity> retrieveARGameStampBookByARGameStampBookAttributes(ARGameStampBookEntity aRGameStampBookSession) {
     
        Query query = null;
        List<ARGameStampBookEntity> aRGameStampBookSessions;
        
        if(aRGameStampBookSession.getaRGameStampBookStatus() != null)
        {
            query = em.createQuery("SELECT a FROM ARGameStampBookEntity a WHERE a.aRGameStampBookStatus = :inStatus");
            query.setParameter("inStatus", aRGameStampBookSession.getaRGameStampBookStatus());
        }
        else if(aRGameStampBookSession.getStartDate() != null)
        {
            query = em.createQuery("SELECT a FROM ARGameStampBookEntity a WHERE a.startDate = :inStartDate");  
            query.setParameter("inStartDate", aRGameStampBookSession.getStartDate());
        }  
        else if(aRGameStampBookSession.getEndDate() != null)
        {
            query = em.createQuery("SELECT a FROM ARGameStampBookEntity a WHERE a.endDate = :inEndDate");  
            query.setParameter("inEndDate", aRGameStampBookSession.getEndDate());
        } 
        else if(aRGameStampBookSession.getRewardPoints() != null)
        {
            query = em.createQuery("SELECT a FROM ARGameStampBookEntity a WHERE a.rewardPoints = :inRewardPoints");  
            query.setParameter("inRewardPoints", aRGameStampBookSession.getRewardPoints());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        aRGameStampBookSessions = query.getResultList();
        
        if(aRGameStampBookSessions.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return aRGameStampBookSessions;        
    }
    
    @Override    
    public List<ARGameStampBookEntity> retrieveARGameStampBookByCustomerAttributes (CustomerEntity customer){
     
        Query query = null;
        List<ARGameStampBookEntity> aRGameStampBookSessions;
        
        if(customer.getCustomerId() != null)
        {
            query = em.createQuery("SELECT a FROM ARGameStampBookEntity a WHERE a.customer.customerId = :inId");
            query.setParameter("inId", customer.getCustomerId());
        }        
        else if(customer.getCustIdentity() != null)
        {
            query = em.createQuery("SELECT a FROM ARGameStampBookEntity a WHERE a.customer.custIdentity LIKE :inCustIdentity");
            query.setParameter("inCustIdentity", customer.getCustIdentity());
        }          
        else
        {
            return new ArrayList<>();
        }
        
        aRGameStampBookSessions = query.getResultList();
        
        if(aRGameStampBookSessions.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return aRGameStampBookSessions;      
    }  

    @Override
    public List<ARGameStampBookEntity> retrieveAllARGameStampBooks() {

        Query query = em.createQuery("SELECT a FROM ARGameStampBookEntity a");
        
        List<ARGameStampBookEntity> aRGameStampBookSessions = query.getResultList();

        
        if(aRGameStampBookSessions.size() == 0)
        {
            return new ArrayList<ARGameStampBookEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateARGameStampBook(ARGameStampBookEntity aRGameStampBookSession) {
        em.merge(aRGameStampBookSession);        
    }

    @Override
    public void deleteARGameStampBook(Long id) throws ARGameStampBookNotFoundException {
        ARGameStampBookEntity aRGameStampBookSession = retrieveARGameStampBookById(id);
        em.remove(aRGameStampBookSession);        
    }
}
