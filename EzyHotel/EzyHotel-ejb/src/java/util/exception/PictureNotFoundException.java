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
public class PictureNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>PictureNotFoundException</code> without
     * detail message.
     */
    public PictureNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PictureNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PictureNotFoundException(String msg) {
        super(msg);
    }
}
