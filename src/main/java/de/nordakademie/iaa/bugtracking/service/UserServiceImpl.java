package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.UserDAO;
import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.security.AccountUserDetails;
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

    @Override
    public User saveUser(User user) throws EntityAlreadyPresentException {
        return userDAO.save(user);
    }

    @Override
    public User loadUser(String email) throws EntityNotFoundException {
        return userDAO.load(email);
    }

    @Override
    public void deleteUser(String email) throws EntityNotFoundException {
        User user = loadUser(email);
        userDAO.delete(user);
    }

    @Override
    public String getRole(String email) throws EntityNotFoundException {
        User user = loadUser(email);
        return user.getRole();
    }

    @Override
    public User getLogin() throws EntityNotFoundException{
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return loadUser(userDetails.getUsername());
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
