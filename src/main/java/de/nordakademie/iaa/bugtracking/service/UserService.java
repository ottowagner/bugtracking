package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.User;

/**
 * Interface for the user service.
 */
public interface UserService {

    /**
     * Stores the given User into the database.
     *
     * @param user The user to be saved.
     * @return the saved user.
     * @throws EntityAlreadyPresentException if a user with the email is already present in the database.
     */
    User saveUser(User user) throws EntityAlreadyPresentException;

    /**
     * Returns the User identified by the given email.
     *
     * @param email The Users email
     * @return the found entity or {@code null} if no entity was found with given identifier.
     * @throws EntityNotFoundException When user does not exist
     */
    User loadUser(String email) throws EntityNotFoundException;

    /**
     * Get the User's role for authentification
     *
     * @param email The Users email
     * @return the Role String
     * @throws EntityNotFoundException When user does not exist
     */
    String getRole(String email) throws EntityNotFoundException;

    /**
     * get the user currently logged in
     *
     * @return the user logged in
     * @throws EntityNotFoundException When session is expired.
     */
    public User getLogin() throws EntityNotFoundException;

}
