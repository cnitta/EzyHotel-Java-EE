/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ProgramEntryEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface ProgramEntrySessionLocal {
    
    public ProgramEntryEntity createEntity(ProgramEntryEntity entity);
    public ProgramEntryEntity retrieveEntityById(Long id);
    public List<ProgramEntryEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(ProgramEntryEntity entity);
    public ProgramEntryEntity retrieveEntity(String attribute, String value);
    public List<ProgramEntryEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
