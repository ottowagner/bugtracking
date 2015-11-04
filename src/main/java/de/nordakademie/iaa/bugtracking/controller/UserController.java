package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.service.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.service.UserService;
import javassist.bytecode.ByteArray;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public User loadUser(@RequestBody String eMail) throws EntityNotFoundException {
        return userService.loadUser(eMail);
    }

    /**
     * Saves the given user.
     *
     * @param user The user to be saved.
     */
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @PreAuthorize("permitAll")
    public User saveUser(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
