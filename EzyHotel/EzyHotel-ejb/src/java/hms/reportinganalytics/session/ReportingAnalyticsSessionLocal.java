/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.reportinganalytics.session;

import javax.ejb.Local;

/**
 *
 * @author berni
 */
@Local
public interface ReportingAnalyticsSessionLocal {
    
    public void averageDailyRate(Long hotelId);
    
}
