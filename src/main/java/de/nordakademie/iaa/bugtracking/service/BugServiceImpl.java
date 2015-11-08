package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
import de.nordakademie.iaa.bugtracking.exception.EntityAlreadyPresentException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.exception.StateException;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.model.State;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Bug service implementation.
 *
 * @author Otto Wagner, Johan Ahrens
 */
public class BugServiceImpl implements BugService {

    private BugDAO bugDAO;
    private StateDAO stateDAO;
    private CommentService commentService;
    private UserService userService;

    /**
     * saves a bug
     * @param bug The bug to be saved.
     * @return
     * @throws EntityAlreadyPresentException
     * @throws EntityNotFoundException
     */
    @Override
    public Bug saveBug(Bug bug) throws EntityAlreadyPresentException, EntityNotFoundException {
        Bug savedBug = null;
        Comment comment = new Comment();
        StringBuffer description = new StringBuffer();


        if (bug.getState() == null) {
            Date creationDate = new Date();
            bug.setCreationDate(creationDate);
            State state = stateDAO.load((long) 1);
            bug.setState(state);
            bug.setAuthor(userService.getLogin());

            savedBug = bugDAO.save(bug);

            description.append("Titel: \n");
            description.append(bug.getTitle());
            if (bug.getDescription() != null) {
                description.append("\nBeschreibung: \n");
                description.append(bug.getDescription());
            }
            comment.setTitle("Fehler wurde angelegt");
            comment.setDescription(description.toString());
        } else {
            Bug oldBug = bugDAO.load(bug.getId());
            if (!bug.getTitle().equals(oldBug.getTitle())) {
                description.append("Titel wurde bearbeitet: \n");
                description.append(bug.getTitle());
            }
            if (bug.getDescription() == null && oldBug.getDescription() != null) {
                description.append("\nBeschreibung wurde gelöscht");
            } else if (bug.getDescription() != null && !bug.getDescription().equals(oldBug.getDescription())) {
                description.append("\nBeschreibung wurde bearbeitet: \n");
                description.append(bug.getDescription());
            }

            savedBug = bugDAO.save(bug);
            comment.setTitle("Fehler wurde bearbeitet");
            comment.setDescription(description.toString());
        }
        comment.setAuthor(userService.getLogin());
        commentService.saveComment(bug.getId(), comment);

        return savedBug;
    }

    /**
     * sets the state of a bug
     * @param bugId
     * @param stateId The stateId to be saved.
     * @return
     * @throws EntityAlreadyPresentException
     * @throws StateException
     * @throws EntityNotFoundException
     */
    @Override
    public Bug setBugState(Long bugId, Long stateId) throws EntityNotFoundException, StateException  {
        Bug bug = null;
        Date updateDate = new Date();

        bug = this.loadBug(bugId);

        State state = stateDAO.load(stateId);

        Set<Long> allowedStates =  bug.getState().getToStateId();
        if(!allowedStates.contains(state.getId()))
            throw new StateException("Statuswechel auf "+ state.getTitle()+ " nicht erlaubt");

        if (state.getTitle().equalsIgnoreCase("In Bearbeitung") && !userService.getLogin().equals(bug.getDeveloper())) {
            throw new StateException("Statuswechsel nicht erlaubt");
        } else if ((state.getTitle().equalsIgnoreCase("Behoben") || state.getTitle().equalsIgnoreCase("Abgelehnt")) &&
                !userService.getLogin().equals(bug.getAuthor())) {
            throw new StateException("Statuswechsel nicht erlaubt");
        }
        bug.setState(state);

        if (state.getTitle().equals("In Bearbeitung")) {

            bug.setDeveloper(userService.getLogin());

        } else {
            bug.setDeveloper(null);
        }
        bug.setLastUpdateDate(updateDate);
        return bugDAO.save(bug);
    }

    /**
     * list all bugs in DB
     * @return
     */
    @Override
    public List<Bug> listBugs() {
        return bugDAO.findAll();
    }

    /**
     * load a bug by id
     * @param id The identifier.
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public Bug loadBug(Long id) throws EntityNotFoundException {
        Bug bug = bugDAO.load(id);
        if (bug == null) {
            throw new EntityNotFoundException("Kein Bug mit der ID gefunden");
        }
        return bug;
    }

    /**
     * delete a bug by id
     * @param id The identifier.
     * @throws EntityNotFoundException
     */
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

    @Inject
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Inject
    public void setUserService(UserService userService) {this.userService = userService;}
}
