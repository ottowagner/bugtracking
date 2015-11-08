package de.nordakademie.iaa.bugtracking.exception;

/**
 * Bug exception
 *
 * @author Johan Ahrens
 */
public class BugException extends RuntimeException {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public BugException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public BugException(String message) {
        super(message);
    }
}
