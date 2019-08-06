package tech.omeganumeric.api.ubereats.webservice.security.authorizations.jwt;

public class JsonWebTokenException extends RuntimeException {

    private static final long serialVersionUID = -2806739563284198752L;

    public JsonWebTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonWebTokenException(String message) {
        super(message);
    }
}
