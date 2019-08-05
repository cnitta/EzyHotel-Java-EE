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
public class AffiliateContentTagNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>AffiliateContentTagNotFoundException</code> without detail message.
     */
    public AffiliateContentTagNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>AffiliateContentTagNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public AffiliateContentTagNotFoundException(String msg) {
        super(msg);
    }
}
