package zda.task.webchat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import zda.task.webchat.controller.LoginController;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test for LoginController
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    LoginController loginController;

    @Test
    @WithMockUser
    public void givenLoginController_whenGetStartPage_thenRedirectToLoginPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    public void givenLoginController_whenLogout_thenRedirectToLoginPage() throws Exception {
        mvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser
    public void givenLoginController_whenGoToChatPage_thenModelHasUser() throws Exception {
        given(loginController.getUserName()).willReturn("user1");

        mvc.perform(get("/chat"))
                .andExpect(status().isOk())
                .andExpect(view().name("chat"))
                .andExpect(model().attribute("username", equalTo("user1")));
    }
}