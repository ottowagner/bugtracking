package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.UserDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;

import javax.inject.Inject;
import java.util.List;

/**
 * Bug service implementation.
 *
 * @author Otto Wagner
 */
public class BugServiceImpl implements BugService {

    /**
     * The bug DAO.
     */
    private BugDAO bugDAO;

    /**
     * The user DAO.
     */
    private UserDAO userDAO;

    @Override
    public Bug saveBug(Bug bug) throws EntityAlreadyPresentException {
        bug.setCreationDate("01.01.2015");//TODO: Daten müssen sinnvoll angelegt werden
        if(bug.getState() == null){//TODO: bessere lösung implementieren...
            bug.setState("Angelegt"); //TODO: ENUM
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
            throw new EntityNotFoundException();
        }
        return bug;
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
}
