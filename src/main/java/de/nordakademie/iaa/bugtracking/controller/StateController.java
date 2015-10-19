package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.State;
import de.nordakademie.iaa.bugtracking.service.BugService;
import de.nordakademie.iaa.bugtracking.service.StateService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for the state service.
 *
 * @author Otto Wagner
 */
@RestController
public class StateController {
    /**
     * The comment service.
     */
    private StateService stateService;

    /**
     * Deletes the state with the given identifier.
     *
     * @param id The state's identifier.
     */
    @RequestMapping(value = "/states/{id}", method = RequestMethod.GET)
    public void loadState(@PathVariable Long id) throws Exception {
        stateService.loadState(id);
    }

    @Inject
    public void setBugService(StateService stateService) {
        this.stateService = stateService;
    }
}
