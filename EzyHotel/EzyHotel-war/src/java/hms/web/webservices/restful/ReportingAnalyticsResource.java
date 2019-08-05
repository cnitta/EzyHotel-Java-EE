/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.reportinganalytics.session.ReportingAnalyticsSessionLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("reportingAnalytics")

public class ReportingAnalyticsResource {

    ReportingAnalyticsSessionLocal reportingAnalyticsSessionLocal = lookupReportingAnalyticsSessionLocal();

    @Context
    private UriInfo context;

    public ReportingAnalyticsResource() {
    }

    @GET
    @Path("/{hotelId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void computeMetrics(Long hotelId) {
        reportingAnalyticsSessionLocal.averageDailyRate(hotelId);
    }

    private ReportingAnalyticsSessionLocal lookupReportingAnalyticsSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReportingAnalyticsSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/ReportingAnalyticsSession!hms.reportinganalytics.session.ReportingAnalyticsSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
