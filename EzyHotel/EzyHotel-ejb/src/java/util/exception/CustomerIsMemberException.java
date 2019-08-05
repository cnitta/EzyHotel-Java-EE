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
public class CustomerIsMemberException extends Exception {

    /**
     * Creates a new instance of <code>CustomerIsMemberException</code> without
     * detail message.
     */
    public CustomerIsMemberException() {
    }

    /**
     * Constructs an instance of <code>CustomerIsMemberException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerIsMemberException(String msg) {
        super(msg);
    }
    
    public CustomerIsMemberException(String msg, Exception e) {
        super(msg,e);
    }    
    
}
