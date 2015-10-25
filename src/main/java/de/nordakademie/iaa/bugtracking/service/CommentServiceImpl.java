package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.CommentDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Comment service implementation.
 *
 * @author Otto Wagner
 */
public class CommentServiceImpl implements CommentService {

    /**
     * The comment DAO.
     */
    private CommentDAO commentDAO;

    /**
     * The course service.
     */
    private BugService bugService;

    //    @Override
    public void saveComment(Long bugId, Comment comment) throws EntityAlreadyPresentException, EntityNotFoundException {
        Bug bug = bugService.loadBug(bugId);
        Date creationDate = new Date();

        comment.setBug(bug);
        comment.setCreationDate(creationDate);
        commentDAO.save(comment);
    }

    @Override
    public List<Comment> listComments(Long bugId) throws EntityNotFoundException {
        Bug bug = bugService.loadBug(bugId);

        return commentDAO.findAllByBug(bug);
    }

    @Override
    public Comment loadComment(Long id) {
        return commentDAO.load(id);
    }

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

}
