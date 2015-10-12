package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.model.Course;

import java.util.List;

/**
 * Interface for the course service.
 *
 * @author Stephan Anft
 */
public interface CourseService {

    /**
     * Stores the given course in the database.
     *
     * @param course The course to be saved.
     * @throws EntityAlreadyPresentException if a course with the same field of study/number
     *                                       combination is already present in the database.
     */
    void saveCourse(Course course) throws EntityAlreadyPresentException;

    /**
     * List all courses currently stored in the database.
     *
     * @return a list of Course entities. If no course was found an empty list is
     * returned.
     */
    List<Course> listCourses();

    /**
     * Returns the course identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Course loadCourse(Long id);

    /**
     * Deletes the course with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no course could be found for the given id.
     */
    void deleteCourse(Long id) throws EntityNotFoundException;

}
