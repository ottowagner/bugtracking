package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.model.State;

import java.util.List;

/**
 * Interface for the state service.
 *
 * @author Otto Wagner
 */
public interface StateService {

//TODO: Sinnvoller text

    /**
     * Returns the state identified by the given id.
     *
     * @param bugId The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    List<State> listToStates(Long bugId) throws EntityNotFoundException;

}
