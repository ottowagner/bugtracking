package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
import de.nordakademie.iaa.bugtracking.dao.UserDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.State;

import javax.inject.Inject;
import java.util.Date;
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

    @Override
    public State loadState(Long id) throws EntityNotFoundException {
        State state = stateDAO.load(id);
        if (state == null) {
            throw new EntityNotFoundException();
        }
        return state;
    }

    @Inject
    public void setStateDAO(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }
}
