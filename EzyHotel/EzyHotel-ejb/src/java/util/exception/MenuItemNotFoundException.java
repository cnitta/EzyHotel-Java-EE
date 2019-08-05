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
public class MenuItemNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>MenuItemNotFoundException</code> without
     * detail message.
     */
    public MenuItemNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MenuItemNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MenuItemNotFoundException(String msg) {
        super(msg);
    }
}
