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
public class MerchandiseOrderItemNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>MerchandiseOrderItemNotFoundException</code> without detail
     * message.
     */
    public MerchandiseOrderItemNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>MerchandiseOrderItemNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public MerchandiseOrderItemNotFoundException(String msg) {
        super(msg);
    }
}
