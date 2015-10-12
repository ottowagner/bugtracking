package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.model.Room;

import java.util.List;

/**
 * Interface for the room service.
 *
 * @author Stephan Anft
 */
public interface RoomService {

    /**
     * Stores the given room into the database.
     *
     * @param room The room to be saved.
     * @throws EntityAlreadyPresentException if a room with the same building/room number
     *                                       combination is already present in the database.
     */
    void saveRoom(Room room) throws EntityAlreadyPresentException;

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of Room entities. If no room was found an empty list is
     * returned.
     */
    List<Room> listRooms();

    /**
     * Returns the room identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Room loadRoom(Long id);

    /**
     * Deletes the room with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no room could be fount for the given id.
     */
    void deleteRoom(Long id) throws EntityNotFoundException;

}
