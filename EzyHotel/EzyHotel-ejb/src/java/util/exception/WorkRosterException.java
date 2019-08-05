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
public class WorkRosterException extends Exception {

    /**
     * Creates a new instance of <code>WorkRosterException</code> without detail
     * message.
     */
    public WorkRosterException() {
    }

    /**
     * Constructs an instance of <code>WorkRosterException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WorkRosterException(String msg) {
        super(msg);
    }
}
