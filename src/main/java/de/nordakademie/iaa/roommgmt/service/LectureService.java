package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.model.Lecture;

import java.util.Date;
import java.util.List;

/**
 * Interface for the lecture service.
 *
 * @author Stephan Anft
 */
public interface LectureService {

    /**
     * Creates a new lecture in the database.
     *
     * @param startTime The lecture's start time.
     * @param endTime   The lecture's end time.
     * @param courseId  The course the lecture belongs to.
     * @param roomId    The room in which the lecture is given.
     * @throws EntityNotFoundException if the room or couse could not be found.
     */
    void createLecture(Date startTime, Date endTime, Long courseId, Long roomId) throws EntityNotFoundException;

    /**
     * List all lectures currently stored in the database.
     *
     * @return a list of Lecture entities. If no lecture was found an empty list is
     * returned.
     */
    List<Lecture> listLectures();

    /**
     * Deletes the lecture with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no lecture could be found for the given id.
     */
    void deleteLecture(Long id) throws EntityNotFoundException;

}
