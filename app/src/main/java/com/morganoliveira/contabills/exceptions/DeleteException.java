package com.morganoliveira.contabills.exceptions;

/**
 * Created by Diogo on 01/03/2018.
 */

public class DeleteException extends DefaultException {

    public DeleteException(String message) {
        super(message);
    }

    public DeleteException(String message, Throwable cause) {
        super(message, cause);
    }

}
