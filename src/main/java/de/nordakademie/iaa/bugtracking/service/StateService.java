package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.State;

import java.util.List;

/**
 * Interface for the state service.
 */
public interface StateService {

    /**
     * Returns the State identified by the given id.
     *
     * @param id The state identifier.
     * @return the State
     * @throws EntityNotFoundException when state not exist.
     */
    State loadState(Long id) throws EntityNotFoundException;

    /**
     * Returns the state identified by the given id.
     *
     * @param bugId The Bug identifier.
     * @return List with possible toStates
     * @throws EntityNotFoundException when Bug not exist.
     */
    List<State> listToStates(Long bugId) throws EntityNotFoundException;

}
