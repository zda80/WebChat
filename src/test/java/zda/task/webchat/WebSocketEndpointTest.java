package zda.task.webchat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Test for StompEndpoint
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebChatApplication.class)
@WebAppConfiguration
public class WebSocketEndpointTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void givenEndpoint_whenGetEndpoint_thenOk() throws Exception {
        mockMvc.perform(get("/ws"))
                .andExpect(status().isOk());
    }
}