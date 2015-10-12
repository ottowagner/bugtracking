package de.nordakademie.iaa.roommgmt.dao;

import de.nordakademie.iaa.roommgmt.model.Course;
import de.nordakademie.iaa.roommgmt.model.Lecture;
import de.nordakademie.iaa.roommgmt.model.Room;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * The lecture DAO that manages all persistence functionality.
 *
 * @author Stephan Anft
 */
public class LectureDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * List all lectures currently stored in the database.
     *
     * @return a list of Lecture entities. If no lecture was found an empty list is
     * returned.
     */
    @SuppressWarnings("unchecked")
    public List<Lecture> findAll() {
        return entityManager.createQuery("select lecture from Lecture lecture").getResultList();
    }

    /**
     * Loads the lecture with the given id.
     * @param id The identifier.
     * @return the lecture or {@code null} if no matching entity was found.
     */
    public Lecture load(Long id) {
        return entityManager.find(Lecture.class, id);
    }

    /**
     * Stores the given lecture into the database.
     *
     * @param lecture The lecture to be stored.
     */
    public void save(Lecture lecture) {
        if (lecture.getId() == null) {
            entityManager.persist(lecture);
        } else {
            entityManager.merge(lecture);
        }
    }

    /**
     * Deletes the given lecture.
     *
     * @param lecture The lecture to be deleted.
     */
    public void delete(Lecture lecture) {
        entityManager.remove(lecture);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
