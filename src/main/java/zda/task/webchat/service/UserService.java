package zda.task.webchat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zda.task.webchat.controller.WebSocketEventController;
import zda.task.webchat.model.Role;
import zda.task.webchat.model.User;
import zda.task.webchat.repository.RoleRepository;
import zda.task.webchat.repository.UserRepository;

import java.util.Collections;

/**
 * Service for saving and searching Users
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            LOGGER.info("Username already present in DB");
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean isRoleExists(String role) {
        return roleRepository.findAll().stream().anyMatch(l -> l.getName().equals(role));
    }
}