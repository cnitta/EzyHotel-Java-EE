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
public class CustomerNotSignUpException extends Exception {

    /**
     * Creates a new instance of <code>CustomerNotSignUpException</code> without
     * detail message.
     */
    public CustomerNotSignUpException() {
    }

    /**
     * Constructs an instance of <code>CustomerNotSignUpException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerNotSignUpException(String msg) {
        super(msg);
    }
    
    public CustomerNotSignUpException(String msg, Exception e) {
        super(msg, e);
    }
    
}
