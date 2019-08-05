/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author berni
 */
public class LoyaltyException extends Exception {

    /**
     * Creates a new instance of <code>LoyaltyException</code> without detail
     * message.
     */
    public LoyaltyException() {
    }

    /**
     * Constructs an instance of <code>LoyaltyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoyaltyException(String msg) {
        super(msg);
    }
    
    public LoyaltyException(String msg, Exception e) {
        super(msg, e);
    }    
}
