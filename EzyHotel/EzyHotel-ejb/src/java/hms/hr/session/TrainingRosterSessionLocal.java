/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hr.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.StaffEntity;
import util.entity.TrainingRosterEntity;
import util.exception.CustomNotFoundException;
import util.exception.TrainingRosterException;
import util.exception.WorkRosterException;

/**
 *
 * @author USER
 */
@Local
public interface TrainingRosterSessionLocal {

    public TrainingRosterEntity retrieveEntityById(Long id);

    public List<TrainingRosterEntity> retrieveAllEntities();

    public void deleteEntity(Long id);

    public void updateEntity(TrainingRosterEntity entity);

    public TrainingRosterEntity retrieveEntity(String attribute, String value);

    public List<TrainingRosterEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);

    public TrainingRosterEntity createEntity(TrainingRosterEntity entity);

    public void addStaffToRoster(Long tid, StaffEntity staff) throws CustomNotFoundException, TrainingRosterException;

    public void removeStaffFromRoster(Long tid, StaffEntity staff) throws CustomNotFoundException, TrainingRosterException;


    
}
