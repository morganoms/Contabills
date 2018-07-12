package com.morganoliveira.contabills.exceptions;

/**
 * Created by Morgan Oliveira on 17/04/2018.
 */

public class CategoriaExistente extends Exception {

    public CategoriaExistente(String message) {
        super(message);
    }

    public CategoriaExistente(String message, Throwable cause) {
        super(message, cause);
    }
}
