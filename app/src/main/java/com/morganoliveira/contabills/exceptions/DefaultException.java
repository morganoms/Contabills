package com.morganoliveira.contabills.exceptions;

/**
 * Created by Diogo on 01/03/2018.
 */

public class DefaultException extends Exception {

    public DefaultException(String message) {
        super(message);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }

}
