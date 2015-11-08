package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.exception.*;
import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.service.BugService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * REST controller for the bug service.
 *
 * @author Otto Wagner, Johan Ahrens
 */
@RestController
public class BugController {
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BugException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetail myError(HttpServletRequest request, Exception exception) {
        ErrorDetail error = new ErrorDetail();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getLocalizedMessage());
        error.setUrl(request.getRequestURL().append("/exception/111").toString());
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
     */
    @RequestMapping(value = "/bugs/{id}", method = RequestMethod.GET)
    public Bug loadBug(@PathVariable Long id) {
        try {
            return bugService.loadBug(id);
        } catch (EntityNotFoundException e) {
            throw new BugException("Bug nicht vorhanden");
        }
    }

    /**
     * Saves the given bug.
     *
     * @param bug The bug to be saved.
     */
    @RequestMapping(value = "/bugs", method = RequestMethod.PUT)
    public Bug saveBug(@RequestBody Bug bug) {
        try {
            return bugService.saveBug(bug);
        } catch (Exception e) {
            throw new BugException("Bug ist bereits vorhanden");
        }
    }


    /**
     * Saves the given state.
     *
     * @param stateId The bug state to be saved.
     */
    @RequestMapping(value = "/bugs/{bugId}/state/change/{stateId}", method = RequestMethod.PUT)
    public Bug setBugState(@PathVariable Long bugId, @PathVariable Long stateId) {
        try {
            return bugService.setBugState(bugId, stateId);
        } catch (StateException e) {
            throw new BugException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new BugException("Bug nicht vorhanden");
        }
    }

    /**
     * Deletes the bug with the given identifier.
     *
     * @param id The bug's identifier.
     */
    @RequestMapping(value = "/bugs/{id}", method = RequestMethod.DELETE)
    public void deleteBug(@PathVariable Long id) {
        try {
            bugService.deleteBug(id);
        } catch (EntityNotFoundException e) {
            throw new BugException("Der Bug existiert nicht");
        }
    }

    @Inject
    public void setBugService(BugService bugService) {
        this.bugService = bugService;
    }
}
