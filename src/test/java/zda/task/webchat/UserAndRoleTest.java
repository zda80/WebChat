package zda.task.webchat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import zda.task.webchat.model.Role;
import zda.task.webchat.model.User;
import zda.task.webchat.repository.RoleRepository;
import zda.task.webchat.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * Test for Creation Users and Roles
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserAndRoleTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    Role role;

    @Before
    public void setUp() {
        role = new Role(1L, "ROLE_USER");
    }

    @Test
    public void givenRoles_whenFindOneRole_thenReturnOneRole() {
        entityManager.persist(role);
        Optional<Role> found = roleRepository.findOne(Example.of(role));

        assertTrue(found.isPresent());
    }

    @Test
    public void givenUsers_whenFinfByUsername_thenReturnUser() {
        User user = new User("User1", "password", "password");
        user.setRoles(Collections.singleton(role));

        entityManager.persist(role);
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername(user.getUsername());

        assertTrue(found.getId() == 1);
        assertThat(found.getUsername(), equalTo("User1"));
        assertThat(found.getPassword(), equalTo("password"));
        assertThat(found.getPasswordConfirm(), equalTo("password"));
        assertTrue(found.getRoles().contains(role));
    }
}