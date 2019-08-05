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
public class BookmarkNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>BookmarkNotFoundException</code> without
     * detail message.
     */
    public BookmarkNotFoundException() {
    }

    /**
     * Constructs an instance of <code>BookmarkNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookmarkNotFoundException(String msg) {
        super(msg);
    }
}
