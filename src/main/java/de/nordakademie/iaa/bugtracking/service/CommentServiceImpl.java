package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.CommentDAO;
import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Comment service implementation.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public class CommentServiceImpl implements CommentService {

    /**
     * The comment DAO.
     */
    private CommentDAO commentDAO;

    /**
     * The bug service.
     */
    private BugService bugService;

    /**
     * The user service.
     */
    private UserService userService;

    /**
     * saves a comment under a given bugID
     * @param bugId
     * @param comment The comment to be saved.
     * @throws EntityAlreadyPresentException
     * @throws EntityNotFoundException
     */
    @Override
    public void saveComment(Long bugId, Comment comment) throws EntityNotFoundException {
        Bug bug = bugService.loadBug(bugId);
        Date creationDate = new Date();

        comment.setBug(bug);
        comment.setAuthor(userService.getLogin());
        comment.setCreationDate(creationDate);
        commentDAO.save(comment);
    }

    /**
     * lists all comments for a bug
     * @param bugId The identifier of the bug.
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public List<Comment> listComments(Long bugId) throws EntityNotFoundException {
        Bug bug = bugService.loadBug(bugId);

        return commentDAO.findAllByBug(bug);
    }

    /**
     * load a comment by id
     * @param id The identifier.
     * @return
     */
    @Override
    public Comment loadComment(Long id) {
        return commentDAO.load(id);
    }

    /**
     * delete a comment
     * @param id The identifier.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteComment(Long id) throws EntityNotFoundException {
        Comment comment = loadComment(id);
        if (comment == null) {
            throw new EntityNotFoundException();
        }
        commentDAO.delete(comment);
    }

    @Inject
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Inject
    public void setBugService(BugService bugService) {
        this.bugService = bugService;
    }

    @Inject
    public void setUserService(UserService userService) {this.userService = userService;}

}
