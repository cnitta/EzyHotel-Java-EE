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
public class InvalidAccessCodeException extends Exception {

    /**
     * Creates a new instance of <code>InvalidAccessCodeException</code> without
     * detail message.
     */
    public InvalidAccessCodeException() {
    }

    /**
     * Constructs an instance of <code>InvalidAccessCodeException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidAccessCodeException(String msg) {
        super(msg);
    }
    
    public InvalidAccessCodeException(String msg, Exception e) {
        super(msg, e);
    }    
}
