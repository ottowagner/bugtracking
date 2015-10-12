package de.nordakademie.iaa.roommgmt.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Course entity.
 *
 * @author Stephan Anft
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"FIElD_OF_STUDY", "NUMBER"}))
public class Course implements Serializable {
    private static final long serialVersionUID = 4823420203175053129L;

    /** The unique identifier. */
    private Long id;

    /** The field of study. */
    private String fieldOfStudy;

    /** The course's number. */
    private Integer number;

    /** The name of the lecturer. */
    private String lecturer;

    /** The course's title. */
    private String title;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FIELD_OF_STUDY", nullable = false)
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    @Column(nullable = false)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(length = 50, nullable = false)
    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    @Column(length = 100, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
