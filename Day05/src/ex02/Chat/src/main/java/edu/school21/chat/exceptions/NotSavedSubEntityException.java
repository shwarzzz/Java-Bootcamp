package edu.school21.chat.exceptions;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException() {
        super();
    }

    public NotSavedSubEntityException(String message) {
        super(message);
    }
}
