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
import util.entity.MerchandiseOrderDetailEntity;
import util.exception.MerchandiseOrderDetailNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Stateless
public class MerchandiseOrderDetailSession implements MerchandiseOrderDetailSessionLocal {
    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    public MerchandiseOrderDetailEntity createMerchandiseOrderDetail(MerchandiseOrderDetailEntity merchandiseOrderDetail) {
        em.persist(merchandiseOrderDetail);
        em.flush();
        em.refresh(merchandiseOrderDetail);
        return merchandiseOrderDetail;
    }

    @Override
    public MerchandiseOrderDetailEntity retrieveMerchandiseOrderDetailById(Long id) throws MerchandiseOrderDetailNotFoundException {
        MerchandiseOrderDetailEntity merchandiseOrderDetail = em.find(MerchandiseOrderDetailEntity.class, id);
        if (merchandiseOrderDetail != null) {
            return merchandiseOrderDetail;
        } else {
            throw new MerchandiseOrderDetailNotFoundException("MerchandiseOrderDetail ID " + id + " does not exist!");
        }        
    }

    @Override
    public List<MerchandiseOrderDetailEntity> retrieveMerchandiseOrderDetailByMerchandiseOrderDetailAttributes(MerchandiseOrderDetailEntity merchandiseOrderDetail) {
     
        Query query = null;
        List<MerchandiseOrderDetailEntity> merchandiseOrderDetails;
        
        if(merchandiseOrderDetail.getQuantity() != null)
        {
            query = em.createQuery("SELECT m FROM MerchandiseOrderDetailEntity m WHERE m.quantity = :inQuantity");
            query.setParameter("inQuantity", merchandiseOrderDetail.getQuantity());
        }        
        else
        {
            return new ArrayList<>();
        }
        
        merchandiseOrderDetails = query.getResultList();
        
        if(merchandiseOrderDetails.isEmpty())
        { 
            return new ArrayList<>();
        }
                
        return merchandiseOrderDetails;        
    }

    @Override
    public List<MerchandiseOrderDetailEntity> retrieveAllMerchandiseOrderDetails() {

        Query query = em.createQuery("SELECT a FROM MerchandiseOrderDetailEntity a");
        
        List<MerchandiseOrderDetailEntity> merchandiseOrderDetails = query.getResultList();

        
        if(merchandiseOrderDetails.size() == 0)
        {
            return new ArrayList<MerchandiseOrderDetailEntity>();
        }
        
        return query.getResultList();        
    }

    @Override
    public void updateMerchandiseOrderDetail(MerchandiseOrderDetailEntity merchandiseOrderDetail) {
        em.merge(merchandiseOrderDetail);        
    }

    @Override
    public void deleteMerchandiseOrderDetail(Long id) throws MerchandiseOrderDetailNotFoundException {
        MerchandiseOrderDetailEntity merchandiseOrderDetail = retrieveMerchandiseOrderDetailById(id);
        em.remove(merchandiseOrderDetail);        
    }

}
