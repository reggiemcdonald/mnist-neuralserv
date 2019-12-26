package org.reggiemcdonald.api.exception;

public class InsufficientPrivilegesException extends Exception {
    public InsufficientPrivilegesException() {
        super("Insufficient privileges to access requested resource");
    }
}
