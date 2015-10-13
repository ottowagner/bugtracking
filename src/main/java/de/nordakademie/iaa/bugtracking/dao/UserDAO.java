package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The user DAO that manages all persistence functionality.
 *
 * @author Otto Wagner
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
    public User load(String email) {
        return entityManager.find(User.class, email);
    }

    /**
     * Stores the given user entity into the database.
     *
     * @param user The user to be saved.
     */
    public void save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
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
