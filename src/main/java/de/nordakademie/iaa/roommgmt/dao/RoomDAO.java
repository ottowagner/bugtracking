package de.nordakademie.iaa.roommgmt.dao;

import de.nordakademie.iaa.roommgmt.model.Room;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * The room DAO that manages all persistence functionality.
 *
 * @author Stephan Anft
 */
public class RoomDAO {
    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of Room entities. If no room was found an empty list is
     * returned.
     */
    @SuppressWarnings("unchecked")
    public List<Room> findAll() {
        return entityManager.createQuery("select room from Room room").getResultList();
    }

    /**
     * Returns the room identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    public Room load(Long id) {
        return entityManager.find(Room.class, id);
    }

    /**
     * Stores the given room entity into the database.
     *
     * @param room The room to be saved.
     */
    public void save(Room room) {
        if (room.getId() == null) {
            entityManager.persist(room);
        } else {
            entityManager.merge(room);
        }
    }

    /**
     * Deletes the given room.
     *
     * @param room The room to be deleted.
     */
    public void delete(Room room) {
        entityManager.remove(room);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
