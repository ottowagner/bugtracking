package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.CommentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.exception.ErrorDetail;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for the comment service.
 *
 * @author Johan Ahrens, Otto Wagner
 */
@RestController
public class CommentController {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentException.class)
    public ErrorDetail myError(Exception exception) {
        ErrorDetail error = new ErrorDetail();
        error.setMessage(exception.getLocalizedMessage());
        return error;
    }

    /**
     * The comment service.
     */
    private CommentService commentService;

    /**
     * List all existing comments.
     *
     * @return the list of comments.
     * @throws Exception when bug not exist.
     */
    @RequestMapping(value = "/bugs/{bugId}/comments", method = RequestMethod.GET)
    public List<Comment> listComments(@PathVariable Long bugId) throws Exception {
        return commentService.listComments(bugId);
    }

    /**
     * Saves the given comment.
     *
     * @param comment The comment to be saved.
     * @throws CommentException when bug not exist.
     */
    @RequestMapping(value = "/bugs/{bugId}/comments", method = RequestMethod.PUT)
    public void saveComment(@PathVariable Long bugId, @RequestBody Comment comment) {
        try {
            commentService.saveComment(bugId, comment);
        } catch (EntityNotFoundException e) {
            throw new CommentException("Der Fehler mit der ID " + bugId + " ist nicht vorhanden");
        }
    }

    @Inject
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
