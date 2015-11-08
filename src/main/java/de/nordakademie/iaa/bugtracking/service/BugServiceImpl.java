package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
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
 * @author Otto Wagner
 */
public class BugServiceImpl implements BugService {

    private BugDAO bugDAO;
    private StateDAO stateDAO;
    //TODO: Service muss weg.. Die Frontend-Schicht arbeitet nur mit den Services, DAOs werden nur Spring-intern von den Service-Klassen verwendet.
    private CommentService commentService;
    private UserService userService;

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
            bug.setAutor(userService.getLogin());

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
        //TODO: bessere lösung implementieren...
        comment.setAutor(userService.getLogin());
        commentService.saveComment(bug.getId(), comment);

        return savedBug;
    }

    @Override
    public Bug setBugState(Long bugId, Long stateId) throws EntityAlreadyPresentException, IlleagalToStateException, EntityNotFoundException {
        Bug bug = null;
        Date updateDate = new Date();
        try {
            bug = this.loadBug(bugId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        State state = stateDAO.load(stateId);

        Set<Long> allowedStates =  bug.getState().getToStateId();
        if(!allowedStates.contains(state.getId()))
            throw new IlleagalToStateException("Statuswechel auf "+ state+ " nicht erlaubt");

        bug.setState(state);
        if (state.getTitle().equals("In Bearbeitung")) {

            bug.setDeveloper(userService.getLogin());

        } else {
            bug.setDeveloper(null);
        }
        bug.setLastUpdateDate(updateDate);
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
