package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.UserException;
import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by otto-wagner on 02.07.2016.
 */
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(new Long(1));
        user.setEmail("otto-wagner@gmx.net");
    }

    @Test
    public void testLoadUser() throws Exception {
        when(userServiceMock.loadUser("otto-wagner@gmx.net")).thenReturn(user);
        User result = userController.loadUser("otto-wagner@gmx.net");
        assertNotNull(result);
        assertEquals(user, result);
        verify(userServiceMock).loadUser("otto-wagner@gmx.net");
    }

    @Test(expected = UserException.class)
    public void testLoadUser_ThrowsException() throws Exception {
        doThrow(new UserException()).when(userServiceMock).loadUser("otto-wagner@gmx.net");
        User result = userController.loadUser("otto-wagner@gmx.net");
        verify(userServiceMock).loadUser("otto-wagner@gmx.net");
    }

    @Test
    public void testSaveUser() throws Exception {
        when(userServiceMock.saveUser(user)).thenReturn(user);
        User result = userController.saveUser(user);
        assertNotNull(result);
        assertEquals(user, result);
        verify(userServiceMock).saveUser(user);
    }

    @Test(expected = UserException.class)
    public void testSaveUser_ThrowsException() throws Exception {
        doThrow(new UserException()).when(userServiceMock).saveUser(user);
        User result = userController.saveUser(user);
        verify(userServiceMock).saveUser(user);
    }

    @Test
    public void testGetLogin() throws Exception {
        when(userServiceMock.getLogin()).thenReturn(user);
        User result = userController.getLogin();
        assertNotNull(result);
        assertEquals(user, result);
        verify(userServiceMock).getLogin();
    }

    @Test(expected = UserException.class)
    public void testGetLogin_ThrowsException() throws Exception {
        doThrow(new UserException()).when(userServiceMock).getLogin();
        User result = userController.getLogin();
        verify(userServiceMock).getLogin();
    }
}