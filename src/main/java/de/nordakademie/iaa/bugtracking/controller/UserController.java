package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.exception.ErrorDetail;
import de.nordakademie.iaa.bugtracking.exception.UserException;
import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * REST controller for the user service.
 *
 * @author Otto Wagner, Johan Ahrens
 */
@RestController
public class UserController {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public ErrorDetail myError(Exception exception) {
        ErrorDetail error = new ErrorDetail();
        error.setMessage(exception.getLocalizedMessage());
        return error;
    }

    /**
     * The user service.
     */
    private UserService userService;

    /**
     * Load user by email
     *
     * @param eMail The User's eMail.
     * @return the user.
     * @throws UserException when User not exist.
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public User loadUser(@RequestBody String eMail) {
        try {
            return userService.loadUser(eMail);
        } catch (EntityNotFoundException e) {
            throw new UserException("Benutzer " + eMail + " ist nicht vorhanden");
        }
    }

    /**
     * Saves the given user.
     *
     * @param user The user to be saved.
     * @return the user.
     * @throws UserException when User already exist.
     */
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @PreAuthorize("permitAll")
    public User saveUser(@RequestBody User user) {
        try {
            return userService.saveUser(user);
        } catch (EntityAlreadyPresentException e) {
            throw new UserException("Benutzer " + user.getEmail() + " ist bereits vorhanden");
        }
    }

    /**
     * returns User currently logged in
     *
     * @return User currently logged in
     * @throws UserException when session is expired.
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public User getLogin() {
        try {
            return userService.getLogin();
        } catch (EntityNotFoundException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
