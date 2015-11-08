package de.nordakademie.iaa.bugtracking.exception;

/**
 * Comment Exception
 *
 * @author Johan Ahrens
 */
public class CommentException extends RuntimeException {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public CommentException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public CommentException(String message) {
        super(message);
    }
}
