package de.nordakademie.iaa.roommgmt.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity entity.
 * @author Stephan Anft
 */
@Entity
public class Lecture implements Serializable {
    private static final long serialVersionUID = -8919215764600439763L;

    /** The unique identifier. */
    private Long id;

    /** Start time of the lecture. */
    private Date begin;

    /** End time of the lecture. */
    private Date end;

    /** The course this lecture belongs to. */
    private Course course;

    /** The room this lecture is given. */
    private Room room;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @ManyToOne(optional = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(optional = false)
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
