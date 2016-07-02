package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.BugException;
import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.service.BugService;
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
public class BugControllerTest {

    @InjectMocks
    private BugController bugController;

    @Mock
    private BugService bugServiceMock;

    Long bugId;
    Bug bug;
    List<Bug> bugList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        bugId = new Long(1);
        bug = new Bug();
        bug.setId(bugId);
        bug.setTitle("Bug title");
        bugList = new ArrayList<>();
        bugList.add(bug);
    }

    @Test
    public void testListBugs() throws Exception {
        when(bugServiceMock.listBugs()).thenReturn(bugList);
        List<Bug> resultList = bugController.listBugs();
        assertNotNull(resultList);
        assertEquals(bugList, resultList);
        verify(bugServiceMock).listBugs();
    }

    @Test
    public void testLoadBug() throws Exception {
        when(bugServiceMock.loadBug(bugId)).thenReturn(bug);
        Bug result = bugController.loadBug(bugId);
        assertEquals(bug, result);
        verify(bugServiceMock).loadBug(bugId);
    }

    @Test(expected = BugException.class)
    public void testLoadBug_ThrowsException() throws Exception {
        doThrow(new EntityNotFoundException()).when(bugServiceMock).loadBug(bugId);
        Bug result = bugController.loadBug(bugId);
        verify(bugServiceMock).loadBug(bugId);
    }

    @Test
    public void testSaveBug() throws Exception {
        when(bugServiceMock.saveBug(bug)).thenReturn(bug);
        Bug result = bugController.saveBug(bug);
        assertEquals(bug, result);
        verify(bugServiceMock).saveBug(bug);
    }

    @Test(expected = BugException.class)
    public void testSaveComment_ThrowsException() throws EntityNotFoundException, EntityAlreadyPresentException {
        doThrow(new BugException()).when(bugServiceMock).saveBug(bug);
        bugController.saveBug(bug);
        verify(bugServiceMock).saveBug(bug);
    }
}