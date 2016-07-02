package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.BugException;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.exception.ErrorDetail;
import de.nordakademie.iaa.bugtracking.exception.StateException;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.service.BugService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * REST controller for the bug service.
 */
@RestController
public class BugController {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BugException.class)
    public ErrorDetail myError(HttpServletRequest request, Exception exception) {
        ErrorDetail error = new ErrorDetail();
        error.setMessage(exception.getLocalizedMessage());
        return error;
    }

    /**
     * The bug service.
     */
    private BugService bugService;

    /**
     * List all existing bugs.
     *
     * @return the list of bugs.
     */
    @RequestMapping(value = "/bugs", method = RequestMethod.GET)
    public List<Bug> listBugs() {
        return bugService.listBugs();
    }

    /**
     * Load the bug with the given identifier.
     *
     * @param id The bug's identifier.
     * @return the Bug.
     * @throws BugException when bug not exist.
     */
    @RequestMapping(value = "/bugs/{id}", method = RequestMethod.GET)
    public Bug loadBug(@PathVariable Long id) {
        try {
            return bugService.loadBug(id);
        } catch (EntityNotFoundException e) {
            throw new BugException("Fehler mit der ID " + id + " nicht vorhanden");
        }
    }

    /**
     * Saves the given bug.
     *
     * @param bug The bug to be saved.
     * @return The saved Bug.
     * @throws BugException when bug already exist.
     */
    @RequestMapping(value = "/bugs", method = RequestMethod.PUT)
    public Bug saveBug(@RequestBody Bug bug) {
        try {
            return bugService.saveBug(bug);
        } catch (Exception e) {
            throw new BugException("Fehler ist bereits vorhanden");
        }
    }


    /**
     * Saves the given state.
     *
     * @param bugId The bug identifier, stateId The bug state to be saved.
     * @return the bug with new state.
     * @throws StateException when there is a state exception, BugException when bug not exist.
     */
    @RequestMapping(value = "/bugs/{bugId}/state/change/{stateId}", method = RequestMethod.PUT)
    public Bug setBugState(@PathVariable Long bugId, @PathVariable Long stateId) {
        try {
            return bugService.setBugState(bugId, stateId);
        } catch (StateException e) {
            throw new BugException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new BugException("Fehler mit der ID " + bugId + " nicht vorhanden");
        }
    }

    @Inject
    public void setBugService(BugService bugService) {
        this.bugService = bugService;
    }
}
