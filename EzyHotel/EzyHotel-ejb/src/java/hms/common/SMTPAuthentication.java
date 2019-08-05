/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common;
import javax.mail.*;

/**
 *
 * @author berni
 */
public class SMTPAuthentication extends Authenticator{
    
    private final static String smtpUser = "ezyhotel.noreply@gmail.com";
    private final static String smtpPassword = "shtoreuxltyjfmqk";

    public SMTPAuthentication() {
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(smtpUser, smtpPassword);
    }
}
