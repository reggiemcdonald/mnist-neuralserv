package org.reggiemcdonald.exception;

public class SessionNotFoundException extends NotFoundException {
    public SessionNotFoundException(int sessionId) {
        super(String.format("Session id %d not found.", sessionId));
    }
}
