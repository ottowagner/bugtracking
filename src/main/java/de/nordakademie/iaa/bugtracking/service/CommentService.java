package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.Comment;

import java.util.List;

/**
 * Interface for the comment service.
 */
public interface CommentService {

    /**
     * Stores the given comment into the database.
     *
     * @param bugId The identifier of the bug. comment The comment to be saved.
     * @throws EntityAlreadyPresentException if a comment with the same comment number is already present in the
     *                                       database.
     */
    void saveComment(Long bugId, Comment comment) throws EntityNotFoundException;

    /**
     * List all comments currently stored in the database.
     *
     * @param bugId The identifier of the bug.
     * @return a list of comment entities of the bug. If no comment was found an empty list is returned.
     */
    List<Comment> listComments(Long bugId) throws EntityNotFoundException;
}
