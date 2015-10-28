package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.UserDAO;
import de.nordakademie.iaa.bugtracking.model.User;

import javax.inject.Inject;

/**
 * user service implementation.
 *
 * @author Otto Wagner
 */
public class UserServiceImpl implements UserService {

    /**
     * The user DAO.
     */
    private UserDAO userDAO;

    @Override
    public User saveUser(User user) throws EntityAlreadyPresentException {
        return userDAO.save(user);
    }

    @Override
    public User loadUser(String email) {
        return userDAO.load(email);
    }

    @Override
    public void deleteUser(String email) throws EntityNotFoundException {
        User user = loadUser(email);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        userDAO.delete(user);
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
