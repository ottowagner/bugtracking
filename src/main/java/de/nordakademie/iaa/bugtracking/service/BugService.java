package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.model.Bug;

import java.util.List;

/**
 * Interface for the bug service.
 *
 * @author Otto Wagner
 */
public interface BugService {

    /**
     * Stores the given bug into the database.
     *
     * @param bug The bug to be saved.
     * @throws EntityAlreadyPresentException if a bug with the same building/bug number
     *                                       combination is already present in the database.
     */
    void saveBug(Bug bug) throws EntityAlreadyPresentException;

    /**
     * List all bugs currently stored in the database.
     *
     * @return a list of Bug entities. If no bug was found an empty list is
     * returned.
     */
    List<Bug> listBugs();

    /**
     * Returns the bug identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Bug loadBug(Long id);

    /**
     * Deletes the bug with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no bug could be fount for the given id.
     */
    void deleteBug(Long id) throws EntityNotFoundException;

}
