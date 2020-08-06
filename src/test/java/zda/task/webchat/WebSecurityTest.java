package zda.task.webchat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Test for WebSecurity
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebChatApplication.class)
@WebAppConfiguration
public class WebSecurityTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void givenWebSecurity_whenUserGetChat_thenOk() throws Exception {
        mockMvc.perform(get("/chat").with(user("user").password("password").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    public void givenWebSecurity_whenNoRoleGetChat_thenRedirect() throws Exception {
        mockMvc.perform(get("/chat"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void givenWebSecurity_whenRoleAnonymousGetChat_thenForbidden() throws Exception {
        mockMvc.perform(get("/chat").with(user("user").password("password").roles("ANONYMOUS")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenWebSecurity_whenGetMainPage_thenRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void givenWebSecurity_whenGetLogout_thenAuthenticated() throws Exception {
        mockMvc.perform(logout()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    @Test
    public void givenWebSecurity_whenAnyUserGetRegistration_thenOk() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk());
    }
}