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
public class TrainingRosterException extends Exception {

    /**
     * Creates a new instance of <code>TrainingRosterException</code> without
     * detail message.
     */
    public TrainingRosterException() {
    }

    /**
     * Constructs an instance of <code>TrainingRosterException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TrainingRosterException(String msg) {
        super(msg);
    }
}
