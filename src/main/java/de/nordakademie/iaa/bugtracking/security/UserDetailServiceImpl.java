package de.nordakademie.iaa.bugtracking.security;
import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.exception.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
/** UserService for security framework
 * @author Johan Ahrens
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * loads a user for security framework
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.loadUser(email);
            return new AccountUserDetails(user);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Kein Nutzer " + email + " vorhanden");
        }
    }
}