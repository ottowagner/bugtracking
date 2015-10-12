package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.dao.RoomDAO;
import de.nordakademie.iaa.roommgmt.model.Room;

import javax.inject.Inject;
import java.util.List;

/**
 * Room service implementation.
 *
 * @author Stephan Anft
 */
public class RoomServiceImpl implements RoomService {

    /**
     * The room DAO.
     */
    private RoomDAO roomDAO;

    @Override
    public void saveRoom(Room room) throws EntityAlreadyPresentException {
        roomDAO.save(room);
    }

    @Override
    public List<Room> listRooms() {
        return roomDAO.findAll();
    }

    @Override
    public Room loadRoom(Long id) {
        return roomDAO.load(id);
    }

    @Override
    public void deleteRoom(Long id) throws EntityNotFoundException {
        Room room = loadRoom(id);
        if (room == null) {
            throw new EntityNotFoundException();
        }
        roomDAO.delete(room);
    }

    @Inject
    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }
}
