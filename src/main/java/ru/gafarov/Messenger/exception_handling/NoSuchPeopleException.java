package ru.gafarov.Messenger.exception_handling;

public class NoSuchPeopleException extends RuntimeException{
    public NoSuchPeopleException(String message) {
        super(message);
    }
}
