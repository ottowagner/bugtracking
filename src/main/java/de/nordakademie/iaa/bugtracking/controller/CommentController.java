package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.*;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * REST controller for the comment service.
 *
 * @author Stephan Johan Ahrens, Otto Wagner
 */
@RestController
public class CommentController {
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentException.class)
    public ErrorDetail myError(HttpServletRequest request, Exception exception) {
        ErrorDetail error = new ErrorDetail();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getLocalizedMessage());
        error.setUrl(request.getRequestURL().append("/exception/111").toString());
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
     */
    @RequestMapping(value = "/bugs/{bugId}/comments", method = RequestMethod.GET)
    public List<Comment> listComments(@PathVariable Long bugId) throws Exception {
        return commentService.listComments(bugId);
    }

    /**
     * Saves the given comment.
     *
     * @param comment The comment to be saved.
     */
    @RequestMapping(value = "/bugs/{bugId}/comments", method = RequestMethod.PUT)
    public void saveComment(@PathVariable Long bugId, @RequestBody Comment comment) {
        try {
            commentService.saveComment(bugId, comment);
        } catch (EntityNotFoundException e) {
            throw new CommentException("Der zugehörige Bug ist nicht vorhanden");
        }
    }

    /**
     * Deletes the comment with the given identifier.
     *
     * @param id The comment's identifier.
     */
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
        } catch (EntityNotFoundException e) {
            throw new CommentException("Kommentar nicht vorhanden");
        }
    }

    @Inject
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
