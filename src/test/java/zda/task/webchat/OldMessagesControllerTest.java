package zda.task.webchat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import zda.task.webchat.controller.OldMessagesController;
import zda.task.webchat.model.*;
import zda.task.webchat.service.ChatMessageService;
import zda.task.webchat.service.Converter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OldMessagesController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(secure = false)
public class OldMessagesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChatMessageService chatMessageService;

    @MockBean
    private Converter converter;

    @Test
    public void givenOldMessageController_whenGetAllMessages_thenReturnJsonArray() throws Exception {
        User user = new User("User1", "password", "password");
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));

        ChatMessage chatMessage1= new ChatMessage(MessageType.CHAT, "Привет!", user);
        ChatMessage chatMessage2= new ChatMessage(MessageType.CHAT, "Как дела?", user);

        ChatMessageDto chatMessageDto1 = new ChatMessageDto(MessageType.CHAT, "Привет!", user.getUsername());
        ChatMessageDto chatMessageDto2 = new ChatMessageDto(MessageType.CHAT, "Как дела?", user.getUsername());
        chatMessageDto1.setDate(LocalDateTime.parse("2020-04-08T23:09:27"));
        chatMessageDto2.setDate(LocalDateTime.parse("2020-04-08T23:09:28"));

        List<ChatMessage> allMessages = Arrays.asList(chatMessage1, chatMessage2);

        given(converter.toChatMessageDto(chatMessage1)).willReturn(chatMessageDto1);
        given(converter.toChatMessageDto(chatMessage2)).willReturn(chatMessageDto2);
        given(chatMessageService.findLast1000By()).willReturn(allMessages);

        mvc.perform(post("/app/messages.oldMessages")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].message", is("Привет!")))
                .andExpect(jsonPath("$[0].username", is("User1")))
                .andExpect(jsonPath("$[0].type", is("CHAT")))
                .andExpect(jsonPath("$[0].date", is("2020-04-08T23:09:27")))
                .andExpect(jsonPath("$[1].message", is("Как дела?")))
                .andExpect(jsonPath("$[1].username", is("User1")))
                .andExpect(jsonPath("$[1].type", is("CHAT")))
                .andExpect(jsonPath("$[1].date", is("2020-04-08T23:09:28")));
    }
}