package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.exception.StateException;
import de.nordakademie.iaa.bugtracking.model.Bug;

import java.util.List;

/**
 * Interface for the bug service.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public interface BugService {

    /**
     * Stores the given bug into the database.
     *
     * @param bug The bug to be saved.
     * @return bug The bug which was created
     * @throws EntityAlreadyPresentException if the bug is already saved
     */
    Bug saveBug(Bug bug) throws EntityAlreadyPresentException, EntityNotFoundException;

    /**
     * Update the given bug state into the database.
     *
     * @param stateId The stateId to be saved.
     * @return bug The bug which was updated
     * @throws EntityNotFoundException if the bug not exist, StateException there is a state exception
     */
    Bug setBugState(Long bugId, Long stateId) throws EntityNotFoundException, StateException;

    /**
     * List all bugs currently stored in the database.
     *
     * @return a list of Bug entities. If no bug was found an empty list is returned.
     */
    List<Bug> listBugs();

    /**
     * Returns the bug identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     * @throws EntityNotFoundException if the bug not exist
     */
    Bug loadBug(Long id) throws EntityNotFoundException;

}
