package com.morganoliveira.contabills.exceptions;

/**
 * Created by Morgan Oliveira on 17/04/2018.
 */

public class UsuarioExistente extends Exception {

    public UsuarioExistente(String message) {
        super(message);
    }

    public UsuarioExistente(String message, Throwable cause) {
        super(message, cause);
    }
}
