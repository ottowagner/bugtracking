package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.State;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The state DAO that manages all persistence functionality.
 *
 * @author Otto Wagner
 */
public class StateDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * List all states currently stored in the database.
     *
     * @return a list of state entities. If no state was found an empty list is
     * returned.
     */
    @SuppressWarnings("unchecked")
    public List<State> findAll() {
        return entityManager.createQuery("select state from State state").getResultList();
    }

    /**
     * Returns the state identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    public State load(Long id) {
        return entityManager.find(State.class, id);
    }

    /**
     * Stores the given state entity into the database.
     *
     * @param state The state to be saved.
     */
    public State save(State state) {
        if (state.getId() == null) {
            entityManager.persist(state);
        } else {
            entityManager.merge(state);
        }
        return state;
    }

    /**
     * Deletes the given state.
     *
     * @param state The state to be deleted.
     */
    public void delete(State state) {
        entityManager.remove(state);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
