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
public class RoomBookingException extends Exception {

    /**
     * Creates a new instance of <code>RoomBookingException</code> without
     * detail message.
     */
    public RoomBookingException() {
    }

    /**
     * Constructs an instance of <code>RoomBookingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomBookingException(String msg) {
        super(msg);
    }
    
    public RoomBookingException(String msg, Exception e){
        super(msg,e);
    }
}
