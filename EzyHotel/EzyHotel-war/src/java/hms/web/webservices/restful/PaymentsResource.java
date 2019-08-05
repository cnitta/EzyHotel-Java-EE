/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.web.webservices.restful;

import hms.frontdesk.session.PaymentSessionLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author berni
 */
@Path("payments")
public class PaymentsResource {
    PaymentSessionLocal paymentSessionLocal = lookupPaymentSessionLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PaymentsResource
     */
    public PaymentsResource() {
    }


    private PaymentSessionLocal lookupPaymentSessionLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PaymentSessionLocal) c.lookup("java:global/EzyHotel/EzyHotel-ejb/PaymentSession!hms.frontdesk.session.PaymentSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
