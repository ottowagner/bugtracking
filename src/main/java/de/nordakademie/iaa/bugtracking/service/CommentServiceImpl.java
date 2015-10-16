package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.CommentDAO;
import de.nordakademie.iaa.bugtracking.model.Comment;

import javax.inject.Inject;
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
    public void saveComment(Comment comment) throws EntityAlreadyPresentException {
        comment.setCreationDate("03.01.2015");
        commentDAO.save(comment);
    }
//
//    public void createComment(Comment comment, Long bugId) throws EntityNotFoundException {
//        Bug bug = bugService.loadBug(bugId);
//        if (bug == null) {
//            throw new EntityNotFoundException("Bug not found");
//        }
//
//        Comment comment = new Comment();
//
//        lecture.setBegin(startTime);
//        lecture.setEnd(endTime);
//        lecture.setCourse(course);
//        lecture.setRoom(room);
//        lectureDAO.save(lecture);
//    }

    @Override
    public List<Comment> listComments() {
        return commentDAO.findAll();
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
}
