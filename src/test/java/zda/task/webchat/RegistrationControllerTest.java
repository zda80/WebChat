package zda.task.webchat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import zda.task.webchat.controller.RegistrationController;
import zda.task.webchat.model.User;
import zda.task.webchat.service.UserService;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test for RegistrationController
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = RegistrationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(secure = false)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private User user;

    @MockBean
    UserService userService;

    @Mock
    RegistrationController registrationController;


    @Test
    @WithMockUser
    public void givenRegistrationController_whenGetRegistration_thenGetNewUser() throws Exception {

        mvc.perform(get("/registration")
                .param("username", "user")
                .param("password", "pas")
                .param("passwordConfirm", "pas"))
                .andExpect(status().is(200))
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    public void givenRegistrationController_whenGetRegistrationAndEnterBadUsername_thenGetError() throws Exception {

        mvc.perform(post("/registration")
                .param("username", "1")
                .param("password", "pas")
                .param("passwordConfirm", "pas"))
                .andExpect(status().is(200))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attribute("error", equalTo(registrationController.BAD_USERNAME)))
                .andExpect(model().attributeHasErrors("regForm"))
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    public void givenRegistrationController_whenGetRegistrationAndDifferentPasswords_thenGetError() throws Exception {

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                .param("username", "user")
                .param("password", "pas")
                .param("passwordConfirm", "pas22"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("error", equalTo(registrationController.BAD_PASSWORD)))
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    public void givenRegistrationController_whenGetRegistrationAndUserAlradyTaken_thenGetError() throws Exception {

        given(userService.saveUser(any())).willReturn(false);

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                .param("username", "user")
                .param("password", "pas1")
                .param("passwordConfirm", "pas1"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("error", equalTo(registrationController.USER_TAKEN)))
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    public void givenRegistrationController_whenSGetRegistrationAndAllCorrect_thenGoMAin() throws Exception {

        given(userService.saveUser(any())).willReturn(true);

        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON)
                .param("username", "user")
                .param("password", "pas1")
                .param("passwordConfirm", "pas1"))
                .andExpect(status().is(302))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/"));
    }
}