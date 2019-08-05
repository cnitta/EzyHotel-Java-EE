/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.entity.InvoiceEntity;

/**
 *
 * @author Nicholas
 */
@Stateless
public class InvoiceSession implements InvoiceSessionLocal {

    @PersistenceContext(unitName = "EzyHotel-ejbPU")
    private EntityManager em;

    @Override
    //check if identity is unique
    public InvoiceEntity createEntity(InvoiceEntity invoice) {
        System.out.println("*** Create Invoice Started ***\n");

        em.persist(invoice);
        em.flush();
        em.refresh(invoice);

        System.out.println("*** Create Invoice Ended ***\n");

        return invoice;
    }//end createEntity

    @Override
    public InvoiceEntity retrieveInvoiceById(Long cId) {
        System.out.println("*** Retrieve invoice by Id Started ***\n");

        InvoiceEntity invoice = em.find(InvoiceEntity.class, cId);
        System.out.println("*** Retrieve invoice by Id Ended ***\n");

        return invoice;

    }//end retrieveInvoiceById

    @Override
    public List<InvoiceEntity> retrieveAllEntites() {
        System.out.println("*** Retrieve all Invoice Started ***\n");

        Query q = em.createQuery("SELECT i FROM InvoiceEntity  i");

        System.out.println("*** Retrieve All Invoice Ended ***\n");
        return q.getResultList();
    }//end retrieveAllEntity

    @Override
    public void deleteEntity(Long id) {
        System.out.println("*** Delete Invoice Started ***\n");
        InvoiceEntity dInvoice = retrieveInvoiceById(id);
        em.remove(dInvoice);
        System.out.println("*** Delete Invoice Ended ***\n");
    }//end deleteEntity

    @Override
    public void updateEntity(InvoiceEntity entity) {
        retrieveInvoiceById(entity.getInvoiceId());
        em.merge(entity);
    }//end updateEntity

    @Override
    public InvoiceEntity retrieveByInvoiceNumber(String invoiceNumber) {

        System.out.println("*** Retrieve invoice by number Started ***\n");

        Query q = em.createQuery("SELECT i FROM InvoiceEntity i WHERE i.invoiceNo = :inId");

        q.setParameter("inId", invoiceNumber);

        if (q.getSingleResult() != null) {
            System.out.println("*** Retrieve invoice by number Ended ***\n");
            return (InvoiceEntity) q.getSingleResult();
        } else {
            System.out.println("*** Retrieve invoice by number Exception Thrown ***\n");
//            throw new NoResultException("Customer with Identity: " + custIdentity + " does not exist!");
            return null;
        }

    }//end retrieveByInvoiceNumber

    @Override
    public List<InvoiceEntity> retrieveUnpaidInvoice() {
        System.out.println("*** Retrieve All Unpaid Invoice Started ***\n");
        //Gets todays date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        //create Empty list
        List<InvoiceEntity> notPaidList = new ArrayList<InvoiceEntity>();
        //retrieve all invoice in InvoiceEntity database
        List<InvoiceEntity> invoiceList = retrieveAllEntites();
        //loop through the invoiceList
        for (int i = 0; i < invoiceList.size(); i++) {
            //retrieve invoiceDate
            Date currInvoiceDate = invoiceList.get(i).getInvoiceDate();
            //format to string
            String currStringDate = dateFormat.format(currInvoiceDate);
            System.out.println("Curr Invoice date: " + currStringDate);
            //add all invoice where status = "Unpaid"
            String currentStatus = invoiceList.get(i).getStatus();
            System.out.println("Current Invoice Status in InvoiceSession " + currentStatus);
            //compare invoice with status = "Unpaid" and add it to notPaidList 
            if (currentStatus.equals("Unpaid")) {
                //if equal, means customer are suppose to check-in today, add to list
                notPaidList.add(invoiceList.get(i));
            }
        }
        System.out.println("*** Retrieve  All Unpaid Invoice Ended ***\n");
        return notPaidList;
    }//end retrieveUnpaidInvoice

    @Override
    public InvoiceEntity makePaymentSucc(Long invoiceId) {
        System.out.println("*** Retrieve Invoice to set status Started in InvoiceSession ***\n");
        InvoiceEntity invoice = em.find(InvoiceEntity.class, invoiceId);
        System.out.println("retrieve invoice status: " + invoice.getStatus());
        if (invoice.getStatus().equals("Unpaid")) {
            invoice.setStatus("Paid");
        }
        System.out.println("retrieve invoice status" + invoice.getStatus());
        System.out.println("*** Retrieve Invoice to set status Ended in InvoiceSession  ***\n");
        return invoice;
    }

}
