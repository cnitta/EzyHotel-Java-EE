/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.frontdesk.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.InvoiceEntity;

/**
 *
 * @author Nicholas
 */
@Local
public interface InvoiceSessionLocal {

    public InvoiceEntity createEntity(InvoiceEntity invoice);

    public InvoiceEntity retrieveInvoiceById(Long cId);

    public List<InvoiceEntity> retrieveAllEntites();

    public void deleteEntity(Long id);

    public void updateEntity(InvoiceEntity entity);

    public InvoiceEntity retrieveByInvoiceNumber(String invoiceNumber);

    public List<InvoiceEntity> retrieveUnpaidInvoice();

    public InvoiceEntity makePaymentSucc(Long invoiceId);

}
