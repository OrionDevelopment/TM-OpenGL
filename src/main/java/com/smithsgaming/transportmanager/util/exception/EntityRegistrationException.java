package com.smithsgaming.transportmanager.util.exception;

/**
 * Created by Tim on 07/04/2016.
 */
public class EntityRegistrationException extends RuntimeException {

    public EntityRegistrationException() {
        super();
    }

    public EntityRegistrationException(String s) {
        super(s);
    }

    public EntityRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityRegistrationException(Throwable cause) {
        super(cause);
    }
}
