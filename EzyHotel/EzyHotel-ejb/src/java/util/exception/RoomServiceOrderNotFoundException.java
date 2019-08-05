/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author vincentyeo
 */
public class RoomServiceOrderNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>RoomServiceOrderNotFoundException</code>
     * without detail message.
     */
    public RoomServiceOrderNotFoundException() {
    }

    /**
     * Constructs an instance of <code>RoomServiceOrderNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RoomServiceOrderNotFoundException(String msg) {
        super(msg);
    }
}
