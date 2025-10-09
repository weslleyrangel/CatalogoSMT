package com.example.Catalogo.exception;

/**
 * Exceção lançada quando se tenta criar um recurso que já existe.
 */
public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
    
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}