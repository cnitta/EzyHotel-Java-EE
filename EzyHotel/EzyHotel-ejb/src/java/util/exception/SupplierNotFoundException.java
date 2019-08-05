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
public class SupplierNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SupplierNotFoundException</code> without
     * detail message.
     */
    public SupplierNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SupplierNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SupplierNotFoundException(String msg) {
        super(msg);
    }
}
