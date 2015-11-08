package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.model.User;

/**
 * Interface for the user service.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public interface UserService {

    /**
     * Stores the given room into the database.
     *
     * @param user The user to be saved.
     * @throws EntityAlreadyPresentException if a user with the email is already present in the database.
     */
    User saveUser(User user) throws EntityAlreadyPresentException;

    /**
     * Returns the room identified by the given id.
     *
     * @param email The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    User loadUser(String email) throws EntityNotFoundException;

    /**
     * Deletes the room with the given id.
     *
     * @param email The identifier.
     * @throws EntityNotFoundException if no room could be fount for the given email.
     */
    void deleteUser(String email) throws EntityNotFoundException;

    /**
     * Get the User's role for authentification
     *
     * @param email
     * @return
     * @throws EntityNotFoundException
     */
    String getRole(String email) throws EntityNotFoundException;

    /**
     * get the user currently logged in
     *
     * @return the user logged in
     * @throws EntityNotFoundException
     */
    public User getLogin() throws EntityNotFoundException;

}
