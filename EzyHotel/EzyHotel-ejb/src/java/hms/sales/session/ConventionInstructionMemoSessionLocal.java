/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.sales.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ConventionInstructionMemoEntity;

/**
 *
 * @author Wai Kit
 */
@Local
public interface ConventionInstructionMemoSessionLocal {

    public ConventionInstructionMemoEntity createEntity(ConventionInstructionMemoEntity entity);
    public ConventionInstructionMemoEntity retrieveEntityById(Long id);
    public List<ConventionInstructionMemoEntity> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(ConventionInstructionMemoEntity entity);
    public ConventionInstructionMemoEntity retrieveEntity(String attribute, String value);
    public List<ConventionInstructionMemoEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
