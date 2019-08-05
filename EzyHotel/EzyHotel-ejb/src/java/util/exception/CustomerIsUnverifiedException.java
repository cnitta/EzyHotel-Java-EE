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
public class CustomerIsUnverifiedException extends Exception {

    /**
     * Creates a new instance of <code>CustomerIsUnverifiedException</code>
     * without detail message.
     */
    public CustomerIsUnverifiedException() {
    }

    /**
     * Constructs an instance of <code>CustomerIsUnverifiedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomerIsUnverifiedException(String msg) {
        super(msg);
    }
    
    public CustomerIsUnverifiedException(String msg, Exception e) {
        super(msg,e);
    }      
    
}
