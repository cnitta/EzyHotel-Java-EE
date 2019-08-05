/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.prepostarrival.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.FeedbackEntity;

/**
 *
 * @author berni
 */
@Local
public interface FeedbackSessionLocal {

    public FeedbackEntity createEntity(FeedbackEntity entity);

    public FeedbackEntity retrieveEntityById(Long id);

    public List<FeedbackEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(FeedbackEntity entity);

    public List<FeedbackEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
    
}
