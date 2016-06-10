package de.nordakademie.iaa.bugtracking.exception;

/**
 * Exception throws if toState of state is not valid.
 */
public class StateException extends RuntimeException {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public StateException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public StateException(String message) {
        super(message);
    }
}
