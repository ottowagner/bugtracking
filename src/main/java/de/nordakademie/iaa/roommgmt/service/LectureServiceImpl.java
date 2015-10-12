package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.dao.LectureDAO;
import de.nordakademie.iaa.roommgmt.model.Course;
import de.nordakademie.iaa.roommgmt.model.Lecture;
import de.nordakademie.iaa.roommgmt.model.Room;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Lecture service implementation.
 *
 * @author Stephan Anft
 */
public class LectureServiceImpl implements LectureService {

    /**
     * The lecture DAO.
     */
    private LectureDAO lectureDAO;

    /**
     * The room service.
     */
    private RoomService roomService;

    /**
     * The course service.
     */
    private CourseService courseService;

    @Override
    public void createLecture(Date startTime, Date endTime, Long courseId, Long roomId) throws EntityNotFoundException {
        Course course = courseService.loadCourse(courseId);
        if (course == null) {
            throw new EntityNotFoundException("Course not found");
        }
        Room room = roomService.loadRoom(roomId);
        if (room == null) {
            throw new EntityNotFoundException("Room not found");
        }
        Lecture lecture = new Lecture();
        lecture.setBegin(startTime);
        lecture.setEnd(endTime);
        lecture.setCourse(course);
        lecture.setRoom(room);
        lectureDAO.save(lecture);
    }

    @Override
    public List<Lecture> listLectures() {
        return lectureDAO.findAll();
    }

    @Override
    public void deleteLecture(Long id) throws EntityNotFoundException {
        Lecture lecture = lectureDAO.load(id);
        if (lecture == null) {
            throw new EntityNotFoundException();
        }
        lectureDAO.delete(lecture);
    }

    @Inject
    public void setLectureDAO(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    @Inject
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Inject
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
}
