package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.State;

import java.util.List;

/**
 * Interface for the state service.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public interface StateService {

    /**
     * Returns the State identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity
     */
    State loadState(Long id) throws EntityNotFoundException;

    /**
     * Returns the state identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity
     */
    List<State> listToStates(Long id);

}
