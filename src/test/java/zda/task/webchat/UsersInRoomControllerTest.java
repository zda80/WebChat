package zda.task.webchat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import zda.task.webchat.controller.UsersInRoomController;
import zda.task.webchat.model.User;
import zda.task.webchat.model.UsersInRoom;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test for UsersInRoomController
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = UsersInRoomController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(secure = false)
public class UsersInRoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersInRoom usersInRoom;

    @Test
    @WithMockUser
    public void givenUsersInRoomController_whenGetUsers_thenReturnJsonArray() throws Exception {
        User user1 = new User("User1", "password", "password");
        User user2 = new User("User2", "password2", "password2");
        List<User> allUsers = Arrays.asList(user1, user2);

        given(usersInRoom.getUsers()).willReturn(allUsers);

        mvc.perform(post("/app/messages.usersInRoom")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("User1")))
                .andExpect(jsonPath("$[1]", is("User2")));
    }
}