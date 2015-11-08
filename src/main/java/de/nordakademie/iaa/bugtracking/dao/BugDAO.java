package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.Bug;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The bug DAO that manages all persistence functionality.
 *
 * @author Johan Ahrens, Otto Wagner
 */
public class BugDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * List all bugs currently stored in the database.
     *
     * @return a list of Bug entities. If no bug was found an empty list is
     * returned.
     */
    @SuppressWarnings("unchecked")
    public List<Bug> findAll() {
        return entityManager.createQuery("select bug from Bug bug").getResultList();
    }

    /**
     * Returns the bug identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    public Bug load(Long id) {
        return entityManager.find(Bug.class, id);
    }

    /**
     * Stores the given bug entity into the database.
     *
     * @param bug The bug to be saved.
     */
    public Bug save(Bug bug) {
        if (bug.getId() == null) {
            entityManager.persist(bug);
        } else {
            entityManager.merge(bug);
        }
        return bug;
    }

    /**
     * Deletes the given bug.
     *
     * @param bug The bug to be deleted.
     */
    public void delete(Bug bug) {
        entityManager.remove(bug);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
