package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.State;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The state DAO that manages all persistence functionality.
 */
public class StateDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * Returns the state identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    public State load(Long id) {
        return entityManager.find(State.class, id);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
