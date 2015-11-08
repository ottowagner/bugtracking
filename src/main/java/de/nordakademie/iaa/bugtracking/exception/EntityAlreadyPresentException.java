package de.nordakademie.iaa.bugtracking.exception;

/**
 * Exception indicating an already present entity.
 *
 * @author Johan Ahrens
 */
public class EntityAlreadyPresentException extends Exception {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public EntityAlreadyPresentException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityAlreadyPresentException(String message) {
        super(message);
    }
}
