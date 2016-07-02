package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.CommentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.service.CommentService;
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
public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentServiceMock;

    Long bugId;
    Comment comment;
    List<Comment> commentList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bugId = new Long(1);
        comment = new Comment();
        comment.setId(bugId);
        comment.setTitle("Comment title");
        commentList = new ArrayList<>();
        commentList.add(comment);
    }

    @Test
    public void testListComments() throws Exception {
        when(commentServiceMock.listComments(bugId)).thenReturn(commentList);
        List<Comment> resultList = commentController.listComments(bugId);
        assertNotNull(resultList);
        assertEquals(commentList, resultList);
        verify(commentServiceMock).listComments(bugId);
    }

    @Test
    public void testSaveComment() throws Exception {
        doNothing().when(commentServiceMock).saveComment(bugId, comment);
        commentController.saveComment(bugId, comment);
        verify(commentServiceMock).saveComment(bugId, comment);
    }

    @Test(expected = CommentException.class)
    public void testSaveComment_ThrowsException() throws CommentException, EntityNotFoundException {
        doThrow(new CommentException()).when(commentServiceMock).saveComment(bugId, comment);
        commentController.saveComment(bugId, comment);
        verify(commentServiceMock).saveComment(bugId, comment);
    }
}