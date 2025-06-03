package org.example.auth;

public class NotEnoughCreditsException extends RuntimeException {
    public NotEnoughCreditsException(String message) {
        super(message);
    }
}
