package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The user DAO that manages all persistence functionality.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public class UserDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * Returns the user identified by the given email.
     *
     * @param email The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    public User load(String email) throws EntityNotFoundException {
        List<User> userList = entityManager.createQuery(
                "select user from User user where user.email = :email")
                .setParameter("email", email).getResultList();
        if (userList == null || userList.size() == 0)
            throw new EntityNotFoundException("user not found");
        return userList.get(0);
    }

    /**
     * Stores the given user entity into the database.
     *
     * @param user The user to be saved.
     */
    public User save(User user) throws EntityAlreadyPresentException {
        try {
            load(user.getEmail());
        } catch (EntityNotFoundException e) {
            if (user.getId() == null) {
                entityManager.persist(user);
            } else {
                entityManager.merge(user);
            }
            return user;
        }
        throw new EntityAlreadyPresentException("Der Benutzer ist bereits vorhanden");
    }

    /**
     * Deletes the given user.
     *
     * @param user The user to be deleted.
     */
    public void delete(User user) {
        entityManager.remove(user);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
