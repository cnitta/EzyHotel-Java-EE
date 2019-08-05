/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author vincentyeo
 */
public class AffiliateNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AffiliateNotFoundException</code> without
     * detail message.
     */
    public AffiliateNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AffiliateNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AffiliateNotFoundException(String msg) {
        super(msg);
    }
}
