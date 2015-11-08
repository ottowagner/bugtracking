package de.nordakademie.iaa.bugtracking.exception;

/**
 * User exception
 *
 * @author Johan Ahrens
 */
public class UserException extends RuntimeException {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UserException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public UserException(String message) {
        super(message);
    }
}
