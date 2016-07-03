package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.exception.BugException;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
    State state;
    User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        bugId = new Long(1);
        bugWithoutState = new Bug();
        bugWithoutState.setId(bugId);
        bugWithoutState.setTitle("Bug without state");

        state = new State();
        state.setId((long) 1);
        state.setTitle("State title");

        bugWithState = new Bug();
        bugWithState.setId(bugId + 1);
        bugWithState.setTitle("Bug with state");
        bugWithState.setState(state);

        user = new User();
        user.setId(new Long(1));
        user.setEmail("otto-wagner@gmx.net");
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
        State toState = new State();
        toState.setId((long) 2);
        toState.setTitle("ToState title");

        Set<Long> toStateIds = new HashSet<Long>();
        toStateIds.add(toState.getId());

        state.setToStateId(toStateIds);

        when(bugDAOMock.load(bugId)).thenReturn(bugWithState);
        when(stateServiceMock.loadState(toState.getId())).thenReturn(toState);

        when(userServiceMock.getLogin()).thenReturn(user);
        when(bugDAOMock.save(bugWithState)).thenReturn(bugWithState);

        Bug result = bugService.setBugState(bugId, toState.getId());

        assertNotNull(result);
        assertEquals(bugWithState, result);
        verify(stateServiceMock).loadState(toState.getId());
        verify(bugDAOMock).save(bugWithState);
    }

    @Test
    public void testListBugs() throws Exception {
        List<Bug> bugList = new ArrayList<>();
        bugList.add(bugWithState);

        when(bugDAOMock.findAll()).thenReturn(bugList);

        List<Bug> resultList = bugService.listBugs();
        assertNotNull(resultList);
        assertEquals(bugList, resultList);

        verify(bugDAOMock).findAll();
    }

    @Test
    public void testLoadBug() throws Exception {
        when(bugDAOMock.load(bugId)).thenReturn(bugWithState);

        Bug result = bugService.loadBug(bugId);

        assertNotNull(result);
        assertEquals(bugWithState, result);

        verify(bugDAOMock).load(bugId);
    }

    @Test(expected = BugException.class)
    public void testLoadBug_ThrowsException() throws Exception {
        doThrow(new BugException()).when(bugDAOMock).load(bugId);
        Bug result = bugService.loadBug(bugId);

        verify(bugDAOMock).load(bugId);
    }
}