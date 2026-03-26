package ru.sibiryaq.exception;

public class PlayerValidationException extends RuntimeException {
    public PlayerValidationException(String message) {
        super(message);
    }
}
