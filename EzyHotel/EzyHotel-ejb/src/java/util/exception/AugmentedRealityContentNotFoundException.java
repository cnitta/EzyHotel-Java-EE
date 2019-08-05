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
public class AugmentedRealityContentNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>AugmentedRealityContentNotFoundException</code> without detail
     * message.
     */
    public AugmentedRealityContentNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>AugmentedRealityContentNotFoundException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public AugmentedRealityContentNotFoundException(String msg) {
        super(msg);
    }
}
