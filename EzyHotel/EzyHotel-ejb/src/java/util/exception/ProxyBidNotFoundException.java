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
public class ProxyBidNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ProxyBidNotFoundException</code> without
     * detail message.
     */
    public ProxyBidNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ProxyBidNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ProxyBidNotFoundException(String msg) {
        super(msg);
    }
}
