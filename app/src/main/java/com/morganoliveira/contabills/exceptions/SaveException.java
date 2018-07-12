package com.morganoliveira.contabills.exceptions;

/**
 * Created by Diogo on 01/03/2018.
 */

public class SaveException extends DefaultException {

    public SaveException(String message) {
        super(message);
    }

    public SaveException(String message, Throwable cause) {
        super(message, cause);
    }

}
