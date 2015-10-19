package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Bug service implementation.
 *
 * @author Otto Wagner
 */
public class BugServiceImpl implements BugService {

    private BugDAO bugDAO;
    private StateDAO stateDAO;

    @Override
    public Bug saveBug(Bug bug) throws EntityAlreadyPresentException {
        Date creationDate = new Date();
        if (bug.getState() == null) {//TODO: bessere lösung implementieren...
            bug.setCreationDate(creationDate);
            bug.setState(stateDAO.load((long) 1));
            bug.setPossibleStates(stateDAO.load((long) 2));
        }else{
            bug.setPossibleStates(stateDAO.load((long) 3)); // TODO: Muss ein array oder so werden & fromState ergebnisse zurückgeben!
        }
        return bugDAO.save(bug);
    }

    @Override
    public List<Bug> listBugs() {
        return bugDAO.findAll();
    }

    @Override
    public Bug loadBug(Long id) throws EntityNotFoundException {
        Bug bug = bugDAO.load(id);
        if (bug == null) {
            throw new EntityNotFoundException("Kein Bug mit der ID gefunden");
        }
        return bugDAO.load(id);
    }

    @Override
    public void deleteBug(Long id) throws EntityNotFoundException {
        Bug bug = loadBug(id);
        if (bug == null) {
            throw new EntityNotFoundException();
        }
        bugDAO.delete(bug);
    }

    @Inject
    public void setBugDAO(BugDAO bugDAO) {
        this.bugDAO = bugDAO;
    }

    @Inject
    public void setStateDAO(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }
}
