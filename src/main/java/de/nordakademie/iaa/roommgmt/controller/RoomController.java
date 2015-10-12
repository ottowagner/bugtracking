package de.nordakademie.iaa.roommgmt.controller;

import de.nordakademie.iaa.roommgmt.model.Room;
import de.nordakademie.iaa.roommgmt.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for the room service.
 *
 * @author Stephan Anft
 */
@RestController
public class RoomController {

    /**
     * The room service.
     */
    private RoomService roomService;

    /**
     * List all existing rooms.
     *
     * @return the list of rooms.
     */
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public List<Room> listRooms() {
        return roomService.listRooms();
    }

    /**
     * Saves the given room.
     *
     * @param room The room to be saved.
     */
    @RequestMapping(value = "/rooms", method = RequestMethod.PUT)
    public void saveRoom(@RequestBody Room room) throws Exception {
        roomService.saveRoom(room);
    }

    /**
     * Deletes the room with the given identifier.
     *
     * @param id The room's identifier.
     */
    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.DELETE)
    public void deleteRoom(@PathVariable Long id) throws Exception {
        roomService.deleteRoom(id);
    }

    @Inject
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
}
