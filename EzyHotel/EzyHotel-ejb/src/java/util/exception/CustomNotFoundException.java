/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author USER
 */
public class CustomNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>CustomNoFoundException</code> without
     * detail message.
     */
    public CustomNotFoundException() {
    }

    /**
     * Constructs an instance of <code>CustomNoFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomNotFoundException(String msg) {
        super(msg);
    }
}
