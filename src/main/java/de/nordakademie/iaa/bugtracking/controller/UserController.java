package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;

/**
 * REST controller for the user service.
 *
 * @author Otto Wagner
 */
@RestController
public class UserController {

    /**
     * The user service.
     */
    private UserService userService;

    /*
     *
     * @return the user.
     */
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public User loadUser(@RequestBody String email) {
//        return userService.loadUser(email);
//    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }
    /**
     * Saves the given user.
     *
     * @param user The user to be saved.
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void saveUser(@RequestBody User user) throws Exception {
        userService.saveUser(user);
    }

    /**
     * Deletes the room with the given identifier.
     *
     * @param email The user's identifier.
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void deleteUser(@RequestBody String email) throws Exception {
        userService.deleteUser(email);
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
