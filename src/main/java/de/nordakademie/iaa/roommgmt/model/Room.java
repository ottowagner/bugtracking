package de.nordakademie.iaa.roommgmt.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Room entity.
 *
 * @author Stephan Anft
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"BUILDING", "ROOM_NUMBER"}))
public class Room implements Serializable {
    private static final long serialVersionUID = 6925248180274039273L;

    /** The unique identifier. */
    private Long id;

    /** The building. */
    private String building;

    /** The room's number. */
    private Integer roomNumber;

    /** Number of seats in the room. */
    private Integer seats;

    /** Indicates whether a beamer is present or not. */
    private Boolean beamerPresent;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Column(name = "room_number", nullable = false)
    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Column(nullable = false)
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Column(nullable = false)
    public Boolean getBeamerPresent() {
        return beamerPresent;
    }

    public void setBeamerPresent(Boolean beamerPresent) {
        this.beamerPresent = beamerPresent;
    }
}
