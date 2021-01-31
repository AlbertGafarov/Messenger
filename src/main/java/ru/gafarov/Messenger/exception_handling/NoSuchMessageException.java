package ru.gafarov.Messenger.exception_handling;

public class NoSuchMessageException extends RuntimeException{

    public NoSuchMessageException(String message) {
        super(message);
    }
}
