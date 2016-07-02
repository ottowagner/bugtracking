package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.StateException;
import de.nordakademie.iaa.bugtracking.model.State;
import de.nordakademie.iaa.bugtracking.service.StateService;
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
public class StateControllerTest {

    @InjectMocks
    private StateController stateController;

    @Mock
    private StateService stateServiceMock;

    Long stateId;
    Long bugId;
    State state;
    List<State> stateList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        stateId = new Long(1);
        bugId = new Long(2);
        state = new State();
        state.setId(stateId);
        state.setTitle("State title");
        stateList = new ArrayList<>();
        stateList.add(state);
    }

    @Test
    public void testLoadState() throws Exception {
        when(stateServiceMock.loadState(stateId)).thenReturn(state);
        State result = stateController.loadState(stateId);
        assertNotNull(result);
        assertEquals(state, result);
        verify(stateServiceMock).loadState(stateId);
    }

    @Test(expected = StateException.class)
    public void testLoadState_ThrowsException() throws Exception {
        doThrow(new StateException()).when(stateServiceMock).loadState(stateId);
        State result = stateController.loadState(stateId);
        verify(stateServiceMock).loadState(stateId);
    }

    @Test
    public void testListToStates() throws Exception {
        when(stateServiceMock.listToStates(bugId)).thenReturn(stateList);
        List<State> resultList = stateController.listToStates(bugId);
        assertNotNull(resultList);
        assertEquals(stateList, resultList);
        verify(stateServiceMock).listToStates(bugId);
    }

    @Test(expected = StateException.class)
    public void testListToStates_ThrowsException() throws Exception {
        doThrow(new StateException()).when(stateServiceMock).listToStates(bugId);
        List<State> resultList = stateController.listToStates(bugId);
        verify(stateServiceMock).listToStates(bugId);
    }
}