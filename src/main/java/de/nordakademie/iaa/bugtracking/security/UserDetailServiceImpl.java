package de.nordakademie.iaa.bugtracking.security;
import de.nordakademie.iaa.bugtracking.model.User;
import de.nordakademie.iaa.bugtracking.service.EntityNotFoundException;
import de.nordakademie.iaa.bugtracking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
/**
 * Created by 13190 on 26.10.2015.
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.loadUser(email);
            return new AccountUserDetails(user);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("no user found with " + email);
        }
    }
}