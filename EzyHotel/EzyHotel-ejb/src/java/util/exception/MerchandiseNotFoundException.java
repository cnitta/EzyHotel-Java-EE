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
public class MerchandiseNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>MerchandiseNotFoundException</code>
     * without detail message.
     */
    public MerchandiseNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MerchandiseNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MerchandiseNotFoundException(String msg) {
        super(msg);
    }
}
