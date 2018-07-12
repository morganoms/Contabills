package com.morganoliveira.contabills.exceptions;

/**
 * Created by Diogo on 01/03/2018.
 */

public class SelectException extends DefaultException {

    public SelectException(String message) {
        super(message);
    }

    public SelectException(String message, Throwable cause) {
        super(message, cause);
    }

}
