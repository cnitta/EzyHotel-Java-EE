/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.CallReportEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface CallReportSessionLocal {
    
    public CallReportEntity createEntity(CallReportEntity entity);
    public CallReportEntity retrieveEntityById(Long id);
    public List<CallReportEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(CallReportEntity entity);
    public CallReportEntity retrieveEntity(String attribute, String value);
    public List<CallReportEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
