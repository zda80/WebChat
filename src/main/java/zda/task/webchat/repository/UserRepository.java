package zda.task.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zda.task.webchat.model.User;

/**
 * UserRepository interface
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}