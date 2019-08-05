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
public class ResendConfirmationEmailException extends Exception {

    /**
     * Creates a new instance of <code>ResendConfirmationEmailException</code>
     * without detail message.
     */
    public ResendConfirmationEmailException() {
    }

    /**
     * Constructs an instance of <code>ResendConfirmationEmailException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ResendConfirmationEmailException(String msg) {
        super(msg);
    }
    
    public ResendConfirmationEmailException(String msg, Exception e) {
        super(msg, e);
    }    
}
