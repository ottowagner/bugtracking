package de.nordakademie.iaa.roommgmt.dao;

import de.nordakademie.iaa.roommgmt.model.Course;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The course DAO that manages all persistence functionality.
 *
 * @author Stephan Anft
 */
public class CourseDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * List all courses currently stored in the database.
     *
     * @return a list of Course entities. If no course was found an empty list is
     * returned.
     */
    @SuppressWarnings("unchecked")
    public List<Course> findAll() {
        return entityManager.createQuery("select course from Course course").getResultList();
    }

    /**
     * Returns the course identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    public Course load(Long id) {
        return entityManager.find(Course.class, id);
    }

    /**
     * Stores the given course entity into the database.
     *
     * @param course The course to be saved.
     */
    public void save(Course course) {
        if (course.getId() == null) {
            entityManager.persist(course);
        } else {
            entityManager.merge(course);
        }
    }

    /**
     * Deletes the given course.
     *
     * @param course The course to be deleted.
     */
    public void deleteCourse(Course course) {
        entityManager.remove(course);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
