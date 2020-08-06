package zda.task.webchat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import zda.task.webchat.model.User;
import zda.task.webchat.service.UserService;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Test for UserService
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void givenUser_whenSaveUser_thenSaveUserInDAO() {
        User user = new User("User1", "password", "password");

        boolean save = userService.saveUser(user);

        assertTrue(save);
        assertEquals(userService.findUserByUsername("User1"), user);
    }

    @Test
    public void givenUser_whenSaveUserTwice_thenFalse() {
        User user = new User("User1", "password", "password");

        boolean saveOne = userService.saveUser(user);
        boolean saveTwo = userService.saveUser(user);

        assertTrue(saveOne);
        assertFalse(saveTwo);
    }

    @Test
    public void givenUsers_whenFindUserByUsername_thenReturnUser() {
        User user1 = new User("User1", "password", "password");
        User user2 = new User("User2", "password2", "password2");

        userService.saveUser(user1);
        userService.saveUser(user2);

        User found = userService.findUserByUsername("User1");
        assertEquals(found, user1);
    }

    @Test
    public void givenNeedFindRole_whenIsRoleExist_thenTrue() {
        jdbcTemplate.execute("INSERT INTO role(id, name) VALUES (555, 'ROLE_USER2')");
        assertTrue(userService.isRoleExists("ROLE_USER2"));
    }
}