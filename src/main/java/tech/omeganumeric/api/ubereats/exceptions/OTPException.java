package tech.omeganumeric.api.ubereats.exceptions;

public class OTPException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -2806739563284198752L;

    public OTPException(String message, Throwable cause) {
        super(message, cause);
    }

    public OTPException(String message) {
        super(message);
    }
}
