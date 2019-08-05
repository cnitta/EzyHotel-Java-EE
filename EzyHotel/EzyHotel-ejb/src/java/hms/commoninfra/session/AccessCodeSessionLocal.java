/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.commoninfra.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.AccessCodeEntity;
import util.entity.CustomerEntity;
import util.enumeration.AccessCodeTypeEnum;

/**
 *
 * @author berni
 */
@Local
public interface AccessCodeSessionLocal {


    public AccessCodeEntity retrieveEntityById(Long id);

    public AccessCodeEntity retrieveEntity(String attribute, String value);

    public List<AccessCodeEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public AccessCodeEntity createAccessCodeRecord(Object userEntity, AccessCodeEntity accessCode);

    public void deleteEntity(Long id);

    public AccessCodeEntity retrieveAccessCodeByCustomerId(Long customerId);

    public AccessCodeEntity retrieveAccessCodeByStaffId(Long staffId);

    public List<AccessCodeEntity> retrieveAccessCodeByCustomerIdAndType(Long customerId, AccessCodeTypeEnum accessCodeType);

    public List<AccessCodeEntity> retrieveAccessCodeByStaffIdAndType(Long staffId, AccessCodeTypeEnum accessCodeType);

    
}
