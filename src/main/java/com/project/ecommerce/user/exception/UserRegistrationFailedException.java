package com.project.ecommerce.user.exception;

public class UserRegistrationFailedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Unable to register the user at this time.";

    public UserRegistrationFailedException() {
        super(DEFAULT_MESSAGE);
    }

    public UserRegistrationFailedException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
