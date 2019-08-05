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
public class MerchandiseOrderNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>MerchandiseOrderNotFoundException</code>
     * without detail message.
     */
    public MerchandiseOrderNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MerchandiseOrderNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MerchandiseOrderNotFoundException(String msg) {
        super(msg);
    }
}
