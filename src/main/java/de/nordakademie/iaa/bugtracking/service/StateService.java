package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.model.State;

import java.util.List;

/**
 * Interface for the state service.
 *
 * @author Otto Wagner
 */
public interface StateService {
    //TODO: Sinnvolle Texte!

    /**
     * Returns the State identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    State loadState(Long id) throws EntityNotFoundException;

    /**
     * Returns the state identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    List<State> listToStates(Long id) throws EntityNotFoundException;

}
