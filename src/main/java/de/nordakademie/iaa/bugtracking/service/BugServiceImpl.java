package de.nordakademie.iaa.bugtracking.service;

import de.nordakademie.iaa.bugtracking.dao.BugDAO;
import de.nordakademie.iaa.bugtracking.dao.StateDAO;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Comment;
import de.nordakademie.iaa.bugtracking.model.State;

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
    //TODO: evtl über bugservice gehen.. kp ob wir hier direkt auf die dao gehen sollten
    private StateDAO stateDAO;

    private CommentService commentService;

    @Override
    public Bug saveBug(Bug bug) throws EntityAlreadyPresentException, EntityNotFoundException {
        Bug savedBug = null;
        Comment comment = new Comment();
        StringBuffer description = new StringBuffer();

        //TODO: bessere lösung implementieren...
        if (bug.getState() == null) {
            Date creationDate = new Date();
            bug.setCreationDate(creationDate);
            State state = stateDAO.load((long) 1);
            bug.setState(state);

            savedBug =  bugDAO.save(bug);

            description.append("Titel: \n");
            description.append(bug.getTitle());
            if(bug.getDescription() != null) {
                description.append("\nBeschreibung: \n");
                description.append(bug.getDescription());
            }
            comment.setTitle("Fehler wurde angelegt");
            comment.setDescription(description.toString());
        }else{
            Bug oldBug = bugDAO.load(bug.getId());
            if(!bug.getTitle().equals(oldBug.getTitle())){
                description.append("Titel wurde bearbeitet: \n");
                description.append(bug.getTitle());
            }
            if(bug.getDescription() == null && oldBug.getDescription() != null) {
                description.append("\nBeschreibung wurde gelöscht");
            }else if(bug.getDescription() != null && !bug.getDescription().equals(oldBug.getDescription())) {
                description.append("\nBeschreibung wurde bearbeitet: \n");
                description.append(bug.getDescription());
            }

            savedBug = bugDAO.save(bug);
            comment.setTitle("Fehler wurde bearbeitet");
            comment.setDescription(description.toString());
        }
        comment.setAutor(bug.getAutor());
        commentService.saveComment(bug.getId(),comment);

        return savedBug;
    }

    @Override
    public Bug setBugState(Long bugId, Long stateId) throws EntityAlreadyPresentException {
//        TODO: Prüfe ob status gesetzt werden darf!!!!! und ggf. Fehler werfen! (Es ist sonst via url möglich nicht erlaubte statuswechsle durchzuführen)
        Bug bug = null;
        try {
            bug = this.loadBug(bugId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        State state = stateDAO.load(stateId);
        bug.setState(state);
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
}
