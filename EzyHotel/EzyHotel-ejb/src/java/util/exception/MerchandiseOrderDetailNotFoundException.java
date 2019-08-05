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
public class MerchandiseOrderDetailNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>MerchandiseOrderDetailNotFoundException</code> without detail
     * message.
     */
    public MerchandiseOrderDetailNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>MerchandiseOrderDetailNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public MerchandiseOrderDetailNotFoundException(String msg) {
        super(msg);
    }
}
