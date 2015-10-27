package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.service.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //    TODO: Muss ersetzt werden (ist denke zu unsicher). Brauche ich gerade, weil wir noch keine richtige authentifikation haben
//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    public User loadUser(@RequestBody User user) throws Exception {
//        return userService.loadUser(user.getEmail());
//    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public boolean userExists(@RequestBody String eMail) throws EntityNotFoundException {
        return userService.userExists(eMail);
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public boolean login(@RequestParam(value="email", required = true) String eMail, @RequestParam(value="credentials", required = true) String credentialsString) throws Exception {

        User user;
        if (eMail != null) {
            user = userService.loadUser(eMail);

            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.getEmail(), user.getPassword());
            if (credentialsString.equals(credentials.toString())) {
                return true;
            }
            throw new Exception("Falsches Passwort");
        }
        throw new Exception("Benutzer nicht vorhanden");
    }


    /**
     * Saves the given user.
     *
     * @param user The user to be saved.
     */
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User saveUser(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }

    /**
     * Deletes the user with the given identifier.
     *
     * @param email The user's identifier.
     */
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public void deleteUser(@RequestBody String email) throws Exception {
        userService.deleteUser(email);
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
