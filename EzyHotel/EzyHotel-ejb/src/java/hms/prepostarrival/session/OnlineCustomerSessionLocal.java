/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.util.List;
import javafx.util.Pair;
import javax.ejb.Local;
import util.entity.CustomerEntity;
import util.entity.RoomBookingEntity;
import util.exception.CustomerProfileConflictException;

/**
 *
 * @author berni
 */
@Local
public interface OnlineCustomerSessionLocal {

    public CustomerEntity createEntity(CustomerEntity entity);

    public CustomerEntity retrieveEntityById(Long id);

    public List<CustomerEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(CustomerEntity entity);

    public List<CustomerEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public CustomerEntity retrieveEntity(String attribute, String value);
    
    public boolean saveCustomerProfile(CustomerEntity newCustomerProfile) throws CustomerProfileConflictException;

    public void terminateCustomerMembership(Long customerId);

    public void confirmCustomerMembership(CustomerEntity member);

    public List<CustomerEntity> retrieveEntityByEitherFilters(String attributeA, String valueA, String attributeB, String valueB);

    public Pair<CustomerEntity, String> checkCustomerMembership(String identityString);

    public List<CustomerEntity> retrieveCustomerByIdentityString(String identityString);
    
    public List<RoomBookingEntity> retrieveRoomBookingsByCustomerId(Long customerId);
    
}
