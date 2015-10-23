package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.State;
import de.nordakademie.iaa.bugtracking.service.StateService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

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
     * Load the allowed toStates with the given bug identifier and user role. //TODO: user role oder sowas impl..
     *
     * @param bugId The bug's identifier.
     * @return Allowed toStates for user
     */
    @RequestMapping(value = "/bugs/{bugId}/states", method = RequestMethod.GET)
    public List<State> listToStates(@PathVariable Long bugId) throws Exception {
        return stateService.listToStates(bugId);
    }

    @Inject
    public void setBugService(StateService stateService) {
        this.stateService = stateService;
    }
}
