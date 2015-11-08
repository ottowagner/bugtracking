package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.security.AccountUserDetails;
import de.nordakademie.iaa.bugtracking.service.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public User getLogin() throws EntityNotFoundException {
        AccountUserDetails userDetails = (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
