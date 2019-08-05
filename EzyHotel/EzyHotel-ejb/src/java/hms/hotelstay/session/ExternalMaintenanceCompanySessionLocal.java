/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.hotelstay.session;

import java.util.List;
import javax.ejb.Local;
import util.entity.ExternalMaintenanceCompanyEntity;
import util.exception.ExternalMaintenanceCompanyNotFoundException;

/**
 *
 * @author vincentyeo
 */
@Local
public interface ExternalMaintenanceCompanySessionLocal {
    public ExternalMaintenanceCompanyEntity createExternalMaintenanceCompany(ExternalMaintenanceCompanyEntity externalMaintenanceCompany);
    
    public ExternalMaintenanceCompanyEntity retrieveExternalMaintenanceCompanyById(Long id) throws ExternalMaintenanceCompanyNotFoundException;
    
    public List<ExternalMaintenanceCompanyEntity> retrieveExternalMaintenanceCompanyByExternalMaintenanceCompanyAttributes (ExternalMaintenanceCompanyEntity externalMaintenanceCompany);    
    
    public List<ExternalMaintenanceCompanyEntity> retrieveAllExternalMaintenanceCompanys();

    public void updateExternalMaintenanceCompany(ExternalMaintenanceCompanyEntity externalMaintenanceCompany);
    
    public void deleteExternalMaintenanceCompany(Long id) throws ExternalMaintenanceCompanyNotFoundException;      
}
