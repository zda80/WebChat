package zda.task.webchat.repository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * UserDetails interface
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
public interface UserDetails {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}