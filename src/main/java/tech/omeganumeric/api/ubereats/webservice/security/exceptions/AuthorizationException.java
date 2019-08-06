package tech.omeganumeric.api.ubereats.webservice.security.exceptions;

public class AuthorizationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -2806739563284198752L;

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
