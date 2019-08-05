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
public class ARGameStampBookNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>ARGameStampBookSessionNotFoundException</code> without detail
     * message.
     */
    public ARGameStampBookNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>ARGameStampBookSessionNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ARGameStampBookNotFoundException(String msg) {
        super(msg);
    }
}
