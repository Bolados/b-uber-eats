package tech.omeganumeric.api.ubereats.exceptions;

/**
 * @author BSCAKO
 */
public class FileStorageException extends RuntimeException {

    private static final long serialVersionUID = 686949455657151694L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
