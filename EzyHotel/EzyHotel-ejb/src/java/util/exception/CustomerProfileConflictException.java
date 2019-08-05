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
public class CustomerProfileConflictException extends Exception {

    /**
     * Creates a new instance of <code>CustomerProfileConflictException</code>
     * without detail message.
     */
    public CustomerProfileConflictException() {
    }

    /**
     * Constructs an instance of <code>CustomerProfileConflictException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerProfileConflictException(String msg) {
        super(msg);
    }
    
    public CustomerProfileConflictException(String msg, Exception e){
        super(msg,e);
    }
}
