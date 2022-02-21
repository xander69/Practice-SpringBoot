package org.xander.practice.webapp.exception;

public class UserAlreadyExistsException extends RegistrationException {
    public UserAlreadyExistsException(String username) {
        super(String.format("User with name '%s' already exists", username));
    }
}
