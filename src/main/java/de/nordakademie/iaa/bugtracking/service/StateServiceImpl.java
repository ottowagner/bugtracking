package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
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
    private BugDAO bugDAO;

    @Override
    public List<State> listToStates(Long bugId) throws EntityNotFoundException {
        Bug bug = bugDAO.load(bugId);
        State state = bug.getState();
        List<State> toStates = new ArrayList<State>(); //TODO: Vllt fällt dir was besseres ein.. ZUDEM MUSS hier noch geprüft werden, ob er wechseln darf..
        for (Long stateId : state.getToStateId()) {
            toStates.add(stateDAO.load(stateId));
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
}
