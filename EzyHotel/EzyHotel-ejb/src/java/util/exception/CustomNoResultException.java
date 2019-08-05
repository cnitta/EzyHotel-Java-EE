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
public class CustomNoResultException extends Exception {

    /**
     * Creates a new instance of <code>CustomNoResultException</code> without
     * detail message.
     */
    public CustomNoResultException() {
    }

    /**
     * Constructs an instance of <code>CustomNoResultException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomNoResultException(String msg) {
        super(msg);
    }
}
