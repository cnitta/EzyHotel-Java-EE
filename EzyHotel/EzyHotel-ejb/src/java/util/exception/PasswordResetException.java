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
public class PasswordResetException extends Exception {

    /**
     * Creates a new instance of <code>PasswordResetException</code> without
     * detail message.
     */
    public PasswordResetException() {
    }

    /**
     * Constructs an instance of <code>PasswordResetException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PasswordResetException(String msg) {
        super(msg);
    }
    
    
    public PasswordResetException(String msg, Exception e) {
        super(msg, e);
    }    
}
