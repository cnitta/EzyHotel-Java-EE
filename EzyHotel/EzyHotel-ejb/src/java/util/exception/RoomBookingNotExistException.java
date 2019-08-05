/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author vincentyeo
 * @author berni
 */
public class RoomBookingNotExistException extends Exception {

    /**
     * Creates a new instance of <code>RoomBookingNotExistException</code>
     * without detail message.
     */
    public RoomBookingNotExistException() {
    }

    /**
     * Constructs an instance of <code>RoomBookingNotExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomBookingNotExistException(String msg) {
        super(msg);
    }

    public RoomBookingNotExistException(String msg, Exception e) {
        super(msg, e);
    }    
}
