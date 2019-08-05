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
public class StaffException extends Exception {

    /**
     * Creates a new instance of <code>StaffException</code> without detail
     * message.
     */
    public StaffException() {
    }

    /**
     * Constructs an instance of <code>StaffException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public StaffException(String msg) {
        super(msg);
    }
}
