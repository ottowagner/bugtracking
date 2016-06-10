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

    /**
     * Returns the State identified by the given id.
     *
     * @param id The state identifier.
     * @return the state
     * @throws EntityNotFoundException when state not exist.
     */
    @Override
    public State loadState(Long id) throws EntityNotFoundException {
        State state = stateDAO.load(id);
        if (state == null) {
            throw new EntityNotFoundException("Status mit der ID " + id + " nicht vorhanden");
        }
        return state;
    }

    /**
     * Returns the state identified by the given id.
     *
     * @param bugId The Bug identifier.
     * @return toStates The List with possible toStates
     * @throws EntityNotFoundException when Bug not exist.
     */
    @Override
    public List<State> listToStates(Long bugId) throws EntityNotFoundException {
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
