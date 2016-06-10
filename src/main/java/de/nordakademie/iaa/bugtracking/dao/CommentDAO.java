package de.nordakademie.iaa.bugtracking.dao;

import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The comment DAO that manages all persistence functionality.
 */
public class CommentDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * List all comments currently stored in the database.
     *
     * @param bug The Bug.
     * @return a list of Comment entities. If no comment was found an empty list is returned.
     */
    public List<Comment> findAllByBug(Bug bug) {
        return entityManager.createQuery("select comment from Comment comment Where comment.bug = :bug")
                .setParameter("bug", bug).getResultList();
    }

    /**
     * Stores the given comment entity into the database.
     *
     * @param comment The comment to be saved.
     */
    public void save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
        } else {
            entityManager.merge(comment);
        }
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
