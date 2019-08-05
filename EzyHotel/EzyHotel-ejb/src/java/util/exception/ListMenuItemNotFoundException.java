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
public class ListMenuItemNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ListMenuItemNotFoundException</code>
     * without detail message.
     */
    public ListMenuItemNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ListMenuItemNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ListMenuItemNotFoundException(String msg) {
        super(msg);
    }
}
