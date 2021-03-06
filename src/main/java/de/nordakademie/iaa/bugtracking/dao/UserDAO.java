package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The user DAO that manages all persistence functionality.
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
        List<User> userList = entityManager.createQuery("select user from User user where user.email = :email")
                .setParameter("email", email).getResultList();
        if (userList.isEmpty()) {
            return null;
        } else {
            return userList.get(0);
        }
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

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
