package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.service.StateService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * REST controller for the state service.
 *
 * @author Otto Wagner
 */
//TODO: kann evtl komplett raus! Idee: Falls wird Status dynamisch machen wollen
@RestController
public class StateController {
    /**
     * The state service.
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
