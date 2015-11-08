package de.nordakademie.iaa.bugtracking.service;

/**
 * Exception throws if toState of state is not valid.
 *
 * @author Johan Ahrens
 */
public class IlleagalToStateException extends Exception {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public IlleagalToStateException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public IlleagalToStateException(String message) {
        super(message);
    }
}
