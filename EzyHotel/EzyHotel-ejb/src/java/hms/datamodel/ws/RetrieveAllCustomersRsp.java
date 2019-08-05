/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.datamodel.ws;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import util.entity.CustomerEntity;

/**
 *
 * @author berni
 */
@XmlRootElement
@XmlType(name = "retrieveAllCustomersRsp", propOrder = {
    "customers"
})
public class RetrieveAllCustomersRsp {
    private static final long serialVersionUID = 1L;
    private List<CustomerEntity> customers;

    public RetrieveAllCustomersRsp(List<CustomerEntity> customers) {
        this.customers = customers;
    }

    public RetrieveAllCustomersRsp() {
    }

    public List<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerEntity> customers) {
        this.customers = customers;
    }
    
    
}
