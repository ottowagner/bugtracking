package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.UserDAO;
import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;

/**
 * user service implementation.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public class UserServiceImpl implements UserService {

    /**
     * The user DAO.
     */
    private UserDAO userDAO;

    /**
     * Stores the given User into the database.
     *
     * @param user The user to be saved.
     * @return the saved user.
     * @throws EntityAlreadyPresentException if a user with the email is already present in the database.
     */
    @Override
    public User saveUser(User user) throws EntityAlreadyPresentException {
        User loadedUser = userDAO.load(user.getEmail());
        if (loadedUser != null) {
            throw new EntityAlreadyPresentException("Die Email-Adresse ist bereits registriert");
        } else {
            userDAO.save(user);
        }
        return user;
    }

    /**
     * Returns the User identified by the given email.
     *
     * @param email The Users email
     * @return the found entity or {@code null} if no entity was found with given identifier.
     * @throws EntityNotFoundException When user does not exist
     */
    @Override
    public User loadUser(String email) throws EntityNotFoundException {
        User user = userDAO.load(email);
        if (user == null) throw new EntityNotFoundException("Benutzer nicht vorhanden");
        return user;
    }

    /**
     * Get the User's role for authentification
     *
     * @param email The Users email
     * @return the Role String
     * @throws EntityNotFoundException When user does not exist
     */
    @Override
    public String getRole(String email) throws EntityNotFoundException {
        User user = null;
        user = userDAO.load(email);
        if (user == null) throw new EntityNotFoundException("Benutzer nicht vorhanden");
        return user.getRole();
    }

    /**
     * get the user currently logged in
     *
     * @return the user logged in
     * @throws EntityNotFoundException When session is expired.
     */
    @Override
    public User getLogin() throws EntityNotFoundException {
        User user = null;
        user = userDAO.load(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null) throw new EntityNotFoundException("Session ist abgelaufen, bitte melden Sie sich an");
        return user;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
