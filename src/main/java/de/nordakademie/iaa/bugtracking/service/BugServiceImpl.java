package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.RoomDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Room;

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

    @Override
    public void saveBug(Bug bug) throws EntityAlreadyPresentException {
        bugDAO.save(bug);
    }

    @Override
    public List<Bug> listBugs() {
        return bugDAO.findAll();
    }

    @Override
    public Bug loadBug(Long id) {
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
}
