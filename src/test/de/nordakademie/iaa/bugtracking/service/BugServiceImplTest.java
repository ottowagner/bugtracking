package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.controller.BugController;
import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.model.State;
import de.nordakademie.iaa.bugtracking.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by otto-wagner on 02.07.2016.
 */
public class BugServiceImplTest {

    @InjectMocks
    private BugServiceImpl bugService;
    @Mock
    private BugDAO bugDAOMock;
    @Mock
    private CommentService commentServiceMock;
    @Mock
    private StateService stateServiceMock;
    @Mock
    private UserService userServiceMock;

    Long bugId;
    Bug bugWithoutState;
    Bug bugWithState;
    List<Bug> bugList;
    State state;
    User user;
    Comment comment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        bugId = new Long(1);
        bugWithoutState = new Bug();
        bugWithoutState.setId(bugId);
        bugWithoutState.setTitle("Bug without state");

        state = new State();
        state.setId((long)1);
        state.setTitle("State title");

        bugWithState = new Bug();
        bugWithState.setId(bugId + 1);
        bugWithState.setTitle("Bug with state");
        bugWithState.setState(state);

        bugList = new ArrayList<>();
        bugList.add(bugWithoutState);
        bugList.add(bugWithState);

        user = new User();
        user.setId(new Long(1));
        user.setEmail("otto-wagner@gmx.net");

        comment = new Comment();
        comment.setId(bugId);
        comment.setTitle("Fehler wurde bearbeitet");
    }

    @Test
    public void testSaveBug_NewBug() throws Exception {
        when(stateServiceMock.loadState((long) 1)).thenReturn(state);
        when(userServiceMock.getLogin()).thenReturn(user);
        when(bugDAOMock.save(bugWithoutState)).thenReturn(bugWithoutState);

        Bug result = bugService.saveBug(bugWithoutState);

        assertNotNull(result);
        assertEquals(bugWithoutState, result);
        assertEquals(user, result.getAuthor());
        verify(stateServiceMock).loadState((long) 1);
        verify(userServiceMock).getLogin();
        verify(bugDAOMock).save(bugWithoutState);
    }

    @Test
    public void testSaveBug_ExistingBug() throws Exception {
        when(stateServiceMock.loadState((long) 1)).thenReturn(state);
        when(userServiceMock.getLogin()).thenReturn(user);
        when(bugDAOMock.save(bugWithState)).thenReturn(bugWithState);
        when(bugDAOMock.load(bugWithState.getId())).thenReturn(bugWithState);

        Bug result = bugService.saveBug(bugWithState);

        assertNotNull(result);
        assertEquals(bugWithState, result);
        verify(userServiceMock).getLogin();
        verify(bugDAOMock).save(bugWithState);
    }

    @Test
    public void testSetBugState() throws Exception {
//        TODO
    }

    @Test
    public void testListBugs() throws Exception {
//        TODO
    }

    @Test
    public void testLoadBug() throws Exception {
//        TODO
    }
}