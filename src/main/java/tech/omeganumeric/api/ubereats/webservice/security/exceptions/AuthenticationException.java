package tech.omeganumeric.api.ubereats.webservice.security.exceptions;

public class AuthenticationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -2806739563284198752L;

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
