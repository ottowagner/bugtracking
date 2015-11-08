package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for the comment service.
 *
 * @author Stephan Johan Ahrens, Otto Wagner
 */
@RestController
public class CommentController {

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
    public void saveComment(@PathVariable Long bugId, @RequestBody Comment comment) throws Exception {
        commentService.saveComment(bugId, comment);
    }

    /**
     * Deletes the comment with the given identifier.
     *
     * @param id The comment's identifier.
     */
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long id) throws Exception {
        commentService.deleteComment(id);
    }

    @Inject
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
