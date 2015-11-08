package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.State;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * State service implementation.
 *
 * @author Otto Wagner
 */
public class StateServiceImpl implements StateService {

    /**
     * The state DAO.
     */
    private StateDAO stateDAO;
    /**
     * the bug DAO
     */
    private BugDAO bugDAO;

    /**
     * userService
     */
    private UserService userService;

    @Override
    public State loadState(Long id) throws EntityNotFoundException {
        State state = stateDAO.load(id);
        if (state == null) {
            throw new EntityNotFoundException("Kein Status mit der ID gefunden");
        }
        return state;
    }

    @Override
    public List<State> listToStates(Long bugId) {
        Bug bug = bugDAO.load(bugId);
        State state = bug.getState();
        List<State> toStates = new ArrayList<State>();
        if (state.getTitle().equalsIgnoreCase("In Bearbeitung") && !userService.getLogin().equals(bug.getDeveloper())) {
        } else if ((state.getTitle().equalsIgnoreCase("Behoben") || state.getTitle().equalsIgnoreCase("Abgelehnt")) &&
                !userService.getLogin().equals(bug.getAuthor())) {
        } else {
            for (Long stateId : state.getToStateId()) {
                toStates.add(stateDAO.load(stateId));
            }
        }
        return toStates;
    }

    @Inject
    public void setStateDAO(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }

    @Inject
    public void setBugDAO(BugDAO bugDAO) {
        this.bugDAO = bugDAO;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
