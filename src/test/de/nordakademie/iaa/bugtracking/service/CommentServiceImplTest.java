package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.CommentDAO;
import de.nordakademie.iaa.bugtracking.exception.CommentException;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by otto-wagner on 02.07.2016.
 */
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentDAO commentDAOMock;
    @Mock
    private BugService bugServiceMock;
    @Mock
    private UserService userServiceMock;

    Bug bug;
    Comment comment;
    User user;

    List<Comment> commentList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        bug = new Bug();
        bug.setId((long) 1);
        bug.setTitle("Bug");

        comment = new Comment();
        comment.setId((long) 1);
        comment.setTitle("Comment");

        commentList = new ArrayList<>();
        commentList.add(comment);

        user = new User();
        user.setId((long) 1);
        user.setEmail("otto-wagner@gmx.net");
    }

    @Test
    public void testSaveComment() throws Exception {
        when(bugServiceMock.loadBug(bug.getId())).thenReturn(bug);
        commentService.saveComment(bug.getId(), comment);
        verify(commentDAOMock).save(comment);
    }

    @Test(expected = CommentException.class)
    public void testSaveComment_ThrowsException() throws Exception {
        doThrow(new CommentException()).when(bugServiceMock).loadBug(bug.getId());
        commentService.saveComment(bug.getId(), comment);
        verify(commentDAOMock).save(comment);
    }

    @Test
    public void testListComments() throws Exception {
        when(commentDAOMock.findAllByBug(bug)).thenReturn(commentList);
        when(bugServiceMock.loadBug(bug.getId())).thenReturn(bug);

        List<Comment> resultList = commentService.listComments(bug.getId());

        assertNotNull(resultList);
        assertEquals(commentList, resultList);
        verify(commentDAOMock).findAllByBug(bug);
        verify(bugServiceMock).loadBug(bug.getId());
    }
}