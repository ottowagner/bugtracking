package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.exception.ErrorDetail;
import de.nordakademie.iaa.bugtracking.exception.StateException;
import de.nordakademie.iaa.bugtracking.model.State;
import de.nordakademie.iaa.bugtracking.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for the state service.
 */
@RestController
public class StateController {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StateException.class)
    public ErrorDetail myError(Exception exception) {
        ErrorDetail error = new ErrorDetail();
        error.setMessage(exception.getLocalizedMessage());
        return error;
    }

    /**
     * The state service.
     */
    private StateService stateService;


    /**
     * Load the state with the given identifier.
     *
     * @param id The state identifier.
     * @return the State.
     * @throws StateException when state not exist.
     */
    @RequestMapping(value = "/states/{id}", method = RequestMethod.GET)
    public State loadState(@PathVariable Long id) {
        try {
            return stateService.loadState(id);
        } catch (EntityNotFoundException e) {
            throw new StateException("Status nicht vorhanden");
        }
    }

    /**
     * Load the allowed toStates with the given bug identifier
     *
     * @param bugId The bug's identifier.
     * @return Allowed toStates for user
     * @throws StateException when bug not exist.
     */
    @RequestMapping(value = "/bugs/{bugId}/states", method = RequestMethod.GET)
    public List<State> listToStates(@PathVariable Long bugId) {
        try {
            return stateService.listToStates(bugId);
        } catch (EntityNotFoundException e) {
            throw new StateException(e.getMessage());
        }
    }

    @Inject
    public void setBugService(StateService stateService) {
        this.stateService = stateService;
    }
}
