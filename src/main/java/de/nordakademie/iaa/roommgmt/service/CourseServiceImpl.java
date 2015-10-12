package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.dao.CourseDAO;
import de.nordakademie.iaa.roommgmt.model.Course;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import java.util.List;

/**
 * Course service implementation.
 *
 * @author Stephan Anft
 */
public class CourseServiceImpl implements CourseService {

    /**
     * The course DAO.
     */
    private CourseDAO courseDAO;

    @Override
    public void saveCourse(Course course) throws EntityAlreadyPresentException {
        try {
            courseDAO.save(course);
        } catch (ConstraintViolationException e) {
            throw new EntityAlreadyPresentException();
        }
    }

    @Override
    public List<Course> listCourses() {
        return courseDAO.findAll();
    }

    @Override
    public Course loadCourse(Long id) {
        return courseDAO.load(id);
    }

    @Override
    public void deleteCourse(Long id) throws EntityNotFoundException {
        Course course = loadCourse(id);
        if (course == null) {
            throw new EntityNotFoundException();
        }
        courseDAO.deleteCourse(course);
    }

    @Inject
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
}
