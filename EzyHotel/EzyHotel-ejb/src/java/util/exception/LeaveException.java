/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author USER
 * */
public class LeaveException extends Exception {

    /**
     * Creates a new instance of <code>LeaveException</code> without detail
     * message.
     */
    public LeaveException() {
    }

    /**
     * Constructs an instance of <code>LeaveException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LeaveException(String msg) {
        super(msg);
    }
}
