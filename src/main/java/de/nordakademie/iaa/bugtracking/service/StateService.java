package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.State;

import java.util.List;

/**
 * Interface for the state service.
 *
 * @author Otto Wagner
 */
public interface StateService {


    /**
     * Returns the state identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    State loadState(Long id) throws EntityNotFoundException;


}
