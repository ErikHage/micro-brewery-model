package com.tfr.microbrew.exception;

/**
 *
 *
 * Created by Erik on 4/22/2017.
 */
public class InventoryException extends RuntimeException {

    public InventoryException() {
        super();
    }

    public InventoryException(String message) {
        super(message);
    }

    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
