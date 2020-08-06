package zda.task.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zda.task.webchat.model.Role;

import java.util.List;

/**
 * RoleRepository interface
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
}