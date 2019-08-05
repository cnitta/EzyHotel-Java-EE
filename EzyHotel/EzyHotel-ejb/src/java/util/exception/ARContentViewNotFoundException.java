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
public class ARContentViewNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ARContentViewNotFoundException</code>
     * without detail message.
     */
    public ARContentViewNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ARContentViewNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ARContentViewNotFoundException(String msg) {
        super(msg);
    }
}
