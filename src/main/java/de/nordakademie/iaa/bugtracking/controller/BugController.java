package de.nordakademie.iaa.bugtracking.controller;

import de.nordakademie.iaa.bugtracking.model.Bug;
import de.nordakademie.iaa.bugtracking.model.Room;
import de.nordakademie.iaa.bugtracking.service.BugService;
import de.nordakademie.iaa.bugtracking.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for the bug service.
 *
 * @author Stephan Anft
 */
@RestController
public class BugController {

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
    public List<Bug> listbugs() {
        return bugService.listBugs();
    }

    /**
     * Saves the given bug.
     *
     * @param bug The bug to be saved.
     */
    @RequestMapping(value = "/bugs", method = RequestMethod.PUT)
    public void saveBug(@RequestBody Bug bug) throws Exception {
        bugService.saveBug(bug);
    }

    /**
     * Deletes the bug with the given identifier.
     *
     * @param id The bug's identifier.
     */
    @RequestMapping(value = "/bugs/{id}", method = RequestMethod.DELETE)
    public void deleteBug(@PathVariable Long id) throws Exception {
        bugService.deleteBug(id);
    }

    @Inject
    public void setBugService(BugService bugService) {
        this.bugService = bugService;
    }
}
