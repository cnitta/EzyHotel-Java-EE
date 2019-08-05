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
public class DeviceNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>DeviceNotFoundException</code> without
     * detail message.
     */
    public DeviceNotFoundException() {
    }

    /**
     * Constructs an instance of <code>DeviceNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeviceNotFoundException(String msg) {
        super(msg);
    }
}
