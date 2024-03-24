package edu.school21.chat.exceptions;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException() {
        super();
    }

    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
