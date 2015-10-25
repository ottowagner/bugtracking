package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.model.Comment;

import java.util.List;

/**
 * Interface for the comment service.
 *
 * @author Otto Wagner
 */
public interface CommentService {

    /**
     * Stores the given comment into the database.
     *
     * @param comment The comment to be saved.
     * @throws EntityAlreadyPresentException if a comment with the same comment number is already present in the database.
     */
    void saveComment(Long bugId, Comment comment) throws EntityAlreadyPresentException, EntityNotFoundException;

    /**
     * List all comments currently stored in the database.
     *
     * @param bugId The identifier of the bug.
     * @return a list of comment entities of the bug. If no comment was found an empty list is
     * returned.
     */
    List<Comment> listComments(Long bugId) throws EntityNotFoundException;

    /**
     * Returns the comment identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Comment loadComment(Long id);

    /**
     * Deletes the comment with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no comment could be fount for the given id.
     */
    void deleteComment(Long id) throws EntityNotFoundException;

}
